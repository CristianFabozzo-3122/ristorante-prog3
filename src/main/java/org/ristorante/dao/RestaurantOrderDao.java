package org.ristorante.dao;

import org.ristorante.model.ConcreteState.OrderedState;
import org.ristorante.model.ConcreteState.PaidState;
import org.ristorante.model.ConcreteState.ServedState;
import org.ristorante.model.IOrderState;
import org.ristorante.model.OrderDetail;
import org.ristorante.model.RestaurantOrder;
import org.ristorante.model.PaymentMethod; // Presumo tu abbia questo Enum

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RestaurantOrderDao extends GenericDao<RestaurantOrder> implements IRestaurantOrderDao {

    @Override
    public RestaurantOrder create(RestaurantOrder o) {
        // Non inseriamo 'order_id' (autoincrement) e 'creation_date' (ha il default current_timestamp)
        String sql = "INSERT INTO RestaurantOrder (user_id_fk, table_id_fk, current_state, totalPrice, payment_method) VALUES (?, ?, ?, ?, ?)";

        boolean success = super.update(sql,
                o.getUser_id_fk(),          // FK Utente (Cameriere)
                o.getTable_id_fk(),         // FK Tavolo
                o.getCurrentState().toString(), // prendiamo il nome dello stato grazie a toString()
                o.getTotalPrice(),          // Prezzo (inizialmente 0 o calcolato)
                (o.getPaymentMethod() != null) ? o.getPaymentMethod().name() : null // Gestione null per il pagamento
        );

        // 2. RECUPERO ID (Cruciale per il Command Pattern/Undo)
        if (success) {
            //Prendiamo l'ordine più recente creato per questo tavolo
            String recentOrder = "SELECT * FROM RestaurantOrder WHERE table_id_fk = ? ORDER BY order_id DESC LIMIT 1";

            // Riutilizziamo queryForSingle che usa il mapRow
            RestaurantOrder insertedOrder = super.queryForSingle(recentOrder, o.getTable_id_fk());

            if (insertedOrder != null) {
                // Aggiorniamo l'oggetto originale con i dati generati dal DB
                o.setOrder_id(insertedOrder.getOrder_id());
                o.setCreation_date(insertedOrder.getCreation_date());

                // o ha l'id - signifca che ora l'oggetto ha un id e quindi possiamo effettuare
                //l'undo
                return o;
            }
        }

        return null;


    }

    @Override
    public RestaurantOrder update(RestaurantOrder o) {
        // Aggiorniamo stato, metodo di pagamento e totale.
        // Non tocchiamo data creazione, utente o tavolo (di solito non cambiano)
        String sql = "UPDATE RestaurantOrder SET current_state = ?, payment_method = ?, totalPrice = ? WHERE order_id = ?";

        boolean success = super.update(sql,
                o.getCurrentState().toString(),
                (o.getPaymentMethod() != null) ? o.getPaymentMethod().name() : null,
                o.getTotalPrice(),
                o.getOrder_id() // WHERE order_id = ?
        );

        return success ? o : null;
    }

    @Override
    public boolean delete(int id) {
        // Grazie al "ON DELETE CASCADE" nel DB[cite: 12], cancellando l'ordine
        // si cancelleranno da sole le righe in OrderDetail.
        String sql = "DELETE FROM RestaurantOrder WHERE order_id = ?";
        return super.update(sql, id);
    }

    @Override
    public RestaurantOrder findByID(int id) {
        String sql = "SELECT * FROM RestaurantOrder WHERE order_id = ?";
        return super.queryForSingle(sql, id);
    }

    @Override
    public List<RestaurantOrder> getAll() {
        String sql = "SELECT * FROM RestaurantOrder";
        return super.queryForList(sql);
    }

    // Ottenere ordini per stato (es. per la Cucina vedere solo ORDERED)
    public List<RestaurantOrder> getOrdersByState(String state, int tableId) {
        String sql = "SELECT * FROM RestaurantOrder WHERE current_state = ? AND table_id_fk = ?";
        return super.queryForList(sql, state, tableId);
    }

    //overloading del metodo getOrdersByState
    public List<RestaurantOrder> getOrdersByState(String state) {
        String sql = "SELECT * FROM RestaurantOrder WHERE current_state = ?";
        return super.queryForList(sql, state);
    }

    @Override
    protected RestaurantOrder mapRow(ResultSet rs) throws SQLException {
        RestaurantOrder order = new RestaurantOrder();

        // Mappatura 1:1 con le colonne del DB
        order.setOrder_id(rs.getInt("order_id"));
        order.setUser_id_fk(rs.getInt("user_id_fk"));
        order.setTable_id_fk(rs.getInt("table_id_fk"));

        // SQLite restituisce la data come Stringa
        order.setCreation_date(rs.getDate("creation_date"));

        order.setTotalPrice(rs.getDouble("totalPrice"));

        String stateStr = rs.getString("current_state");

        // Si potrebbe anche utilizzare una factory e passare solamente la stringa del db
        // poi decidere a runtime con factory la soluzione
        switch (stateStr) {
            case "ORDERED":
                order.setCurrentState(new OrderedState());
                break;
            case "SERVED":
                order.setCurrentState(new ServedState());
                break;
            case "PAID":
                order.setCurrentState(new PaidState());
                break;
            default:
                // fallback
                System.err.println("Stato sconosciuto nel DB: " + stateStr);
                order.setCurrentState(new OrderedState());
        }

        // Conversione String -> Enum per il Pagamento (può essere null)
        String pm = rs.getString("payment_method");
        if (pm != null) {
            order.setPaymentMethod(PaymentMethod.valueOf(pm));
        } else {
            order.setPaymentMethod(null);
        }

        return order;
    }

    public List<RestaurantOrder> getOrdersByCategory(String category){
        //di default vogliamo sempre che lo stato sia ORDERED, bar e cucina non si interessano
        //certamente degli ordini serviti o pagati
        String sql = """
            SELECT DISTINCT ro.* FROM RestaurantOrder ro
            JOIN OrderDetail od ON ro.order_id = od.order_id_fk
            JOIN MenuItem mi ON od.menu_item_fk = mi.menu_item_id
            WHERE ro.current_state = 'ORDERED'
            AND mi.category = ?
            """;

        return super.queryForList(sql, category);
    }


}