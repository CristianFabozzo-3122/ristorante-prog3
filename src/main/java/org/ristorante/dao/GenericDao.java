    package org.ristorante.dao;

    import org.ristorante.dao.custom_error.DaoError;
    import org.ristorante.utils.DBConnection;

    import java.sql.*;
    import java.util.ArrayList;
    import java.util.List;


    //<T> è un segnaposto: verrà sostituito da User, RestaurantTable, Order, ecc.
    public abstract class GenericDao<T> {


        //GenericDao non sa come mappare l'oggetto quindi rimane un abstract method
        //a conoscenza ovviamente solo di chi eredita
        //questo metodo serve a mappare l'oggetto dal db ad un oggetto di java
        protected abstract T mapRow(ResultSet rs) throws SQLException;


        //Serve per le Select che restituiscono molte righe (getall...)

        protected List<T> queryForList(String sql, Object... params) {
            List<T> list = new ArrayList<>();

            // Il try-with-resources è fondamentale: chiude Connection e Statement anche se esplode tutto.
            try (Connection conn = DBConnection.getInstance().getConnection();
                 PreparedStatement ps = prepare(conn, sql, params);
                 ResultSet rs = ps.executeQuery()) {


                //chiediamo ai figli di usare il loro metodo mapRow
                while (rs.next()) {

                    list.add(mapRow(rs));
                }

            } catch (SQLException e) {
                //lanciamo l'errore dal generic
                throw new DaoError("Errore dal generic Dao queryForList " + sql, e);
            }
            return list;
        }

        //query che restituiscono un solo risultato
        protected T queryForSingle(String sql, Object... params) {
            try (Connection conn = DBConnection.getInstance().getConnection();
                 PreparedStatement ps = prepare(conn, sql, params);
                 ResultSet rs = ps.executeQuery()) {

                // se c'e' almeno un risultato, lo mappiamo e lo restituiamo.
                if (rs.next()) {

                    return mapRow(rs);
                }

            } catch (SQLException e) {
                throw new DaoError("Errore del generico Dao queryForSingle " + sql, e);
            }
            return null;
        }

        //per delete, update, insert
        //si chiama update perche' l'azione principale e' quella di usare un executeUpdate()

        protected boolean update(String sql, Object... params) {
            try (Connection conn = DBConnection.getInstance().getConnection();
                 PreparedStatement ps = prepare(conn, sql, params)) {

                // executeUpdate ci dice quante righe sono state toccate
                return ps.executeUpdate() > 0;

            } catch (SQLException e) {
                throw new DaoError("GenericDao - Errore nell'update/delete: " + sql, e);
            }
        }



        // prende i parametri variabili (...) e li mette al posto giusto nei ?
        private PreparedStatement prepare(Connection conn, String sql, Object... params) throws SQLException {
            PreparedStatement ps = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                // setObject capisce da solo se gli stai passando un int, una stringa o un double.
                ps.setObject(i + 1, params[i]);
            }
            return ps;
        }
    }