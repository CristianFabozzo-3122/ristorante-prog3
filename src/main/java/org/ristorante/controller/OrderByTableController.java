package org.ristorante.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.ristorante.controller.components.OrderDetailController;
import org.ristorante.controller.components.OrderListController;
import org.ristorante.dao.OrderDetailDao;
import org.ristorante.dao.RestaurantOrderDao;
import org.ristorante.model.OrderDetail;
import org.ristorante.model.RestaurantOrder;
import org.ristorante.model.RestaurantTable;
import org.ristorante.service.OrderService;

import java.util.List;

public class OrderByTableController extends BaseController {

    OrderService service;

    // injection del component Lista ordini (Sinistra)
    @FXML private OrderListController orderListController;

    // Injection del component Dettagli dell'ordine (Destra)
    @FXML private OrderDetailController detailViewController;

    @FXML Button orderedFilter;
    @FXML Button servedFilter;
    @FXML Button servedButton;
    @FXML Button backButton;

    private RestaurantTable table;

    public void setTable(RestaurantTable t){
        this.table = t;
        // scegliamo di far vedere gli ordini non ancora seriviti
        // non lo facciamo in initiliaze() poiche' setTable verra' sempre richiamato dopo
        applyOrderedFilter();
    }

    @FXML public void initialize(){
        service = new OrderService(new RestaurantOrderDao(), new OrderDetailDao());

        //appena l'utente clicca una riga, esegui questo codice
        orderListController.setOnOrderSelectedListener(selectedOrder -> {

            System.out.println("Carico dettagli per ordine " + selectedOrder.getOrder_id());

            // Recupero i dettagli dal DB
            List<OrderDetail> details = service.getDetails(selectedOrder);

            // Aggiorno subito il pannello di destra
            detailViewController.setOrderDetails(details);
        });
    }

    // funzione per filtrare in base agli ordini solo ordinati di quel tavolo
    @FXML public void applyOrderedFilter(){
        if (table == null) return;
        List<RestaurantOrder> orderedOrders = service.orderedOrder(table.getTable_id());

        // passiamo i dati al componente lista
        orderListController.setOrders(orderedOrders);

        // pulisco i dettagli quando cambio filtro
        detailViewController.clear();
    }

    // funzione per filtrare agli ordini solo serviti di quel tavolo
    @FXML public void applyServedFilter(){
        if (table == null) return;
        List<RestaurantOrder> servedOrders = service.servedOrder(table.getTable_id());

        //passiamo i dati al componente lista
        orderListController.setOrders(servedOrders);

        detailViewController.clear();
    }

    @FXML public void handleServedButton(){
        //chiediamo al componente lista qual è l'ordine selezionato
        RestaurantOrder selectedOrder = orderListController.getSelectedOrder();

        if(selectedOrder == null){
            return;
        }
        // qui si usa il pattern state
        service.serveOrder(selectedOrder);
        // poi riaggiorniamo la lista
        applyOrderedFilter();
    }

    @FXML public void handleBackButton(){
        String path = "/org/ristorante/view/table_detail.fxml";
        String title = "Dettaglio tavolo";
        super.changeScene(path, title, (TableDetailController controller) -> {
            controller.setTable(table);
        });
    }
}