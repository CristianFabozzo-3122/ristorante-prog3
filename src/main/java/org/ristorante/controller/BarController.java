package org.ristorante.controller;

import javafx.fxml.FXML;
import org.ristorante.controller.components.OrderDetailController;
import org.ristorante.controller.components.OrderListController;
import org.ristorante.dao.OrderDetailDao;
import org.ristorante.dao.RestaurantOrderDao;
import org.ristorante.model.OrderDetail;
import org.ristorante.model.RestaurantOrder;
import org.ristorante.service.OrderService;

import java.util.List;

public class BarController extends  BaseController{
    @FXML
    private OrderListController orderListController;
    @FXML private OrderDetailController orderDetailController;
    private OrderService service;

    @FXML public void initialize(){
        service = new OrderService(new RestaurantOrderDao(), new OrderDetailDao());
        // carico le comande dove c'e' almeno un ordine del bar
        List<RestaurantOrder> kitchenOrders = service.getOrdersByCategory("BAR");

        // passo i dati alla lista sinistra
        orderListController.setOrders(kitchenOrders);

        // Quando cliccano a sinistra, mostra i dettagli a destra
        orderListController.setOnOrderSelectedListener(selectedOrder -> {
            // Carico i dettagli specifici (solo piatti cucina)
            List<OrderDetail> details = service.getDetails(selectedOrder);

            // Passo i dettagli al pannello di destra che e' un altro componente riutilizzabile
            orderDetailController.setOrderDetails(details);
        });
    }
}
