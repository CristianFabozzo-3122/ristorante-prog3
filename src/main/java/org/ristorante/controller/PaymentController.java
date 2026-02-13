package org.ristorante.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.ristorante.controller.components.OrderDetailController;
import org.ristorante.controller.components.OrderListController;
import org.ristorante.dao.OrderDetailDao;
import org.ristorante.dao.RestaurantOrderDao;
import org.ristorante.model.OrderDetail;
import org.ristorante.strategy.PaymentMethod;
import org.ristorante.model.RestaurantOrder;
import org.ristorante.service.OrderService;
import org.ristorante.service.custom_error.PaymentException;
import org.ristorante.strategy.Atm;
import org.ristorante.strategy.Card;
import org.ristorante.strategy.Cash;

import java.util.List;

public class PaymentController extends BaseController{

    //injection dei components/controller figli
    @FXML
    private OrderListController orderListController;
    @FXML
    private OrderDetailController orderDetailController;

    //bottoni per pagare
    @FXML
    Button cardButton;
    @FXML
    Button cashButton;
    @FXML Button atmButton;
    @FXML Button backButton;

    private OrderService service;


    @FXML void initialize(){
        service = new OrderService(new RestaurantOrderDao(), new OrderDetailDao());
        // Sul click di un elemento nella lista di sinistra, mostra i dettagli a destra
        orderListController.setOnOrderSelectedListener(selectedOrder -> {
            // Carico i dettagli specifici (solo piatti cucina)
            List<OrderDetail> details = service.getDetails(selectedOrder);

            // Passo i dettagli al pannello di destra che e' un altro componente riutilizzabile
            orderDetailController.setOrderDetails(details);
        });
        loadOrdersToPay();
    }

    private void loadOrdersToPay() {
        // Prendo solo gli ordini in stato SERVED
        List<RestaurantOrder> orders = service.servedOrder();

        // Aggiorno la lista
        orderListController.setOrders(orders);

        // pulisco i dettagli a destra, perché la selezione precedente potrebbe non esistere più
        orderDetailController.clear();
    }

    private void processPayment(PaymentMethod strategy) {
        // 1. Recupero l'ordine selezionato dal componente figlio
        RestaurantOrder selected = orderListController.getSelectedOrder();

        if (selected == null) {
            super.showError("Seleziona un ordine da pagare!");
            return;
        }

        try {
            // eseguo il pagamento
            service.payOrder(selected, strategy);

            //si potrebbe utilizzare un metodo in base controller per il feedback positivo
            //super.showInfo("Pagamento riuscito con " + strategy.getMethodName());

            // ricarico la lista per far sparire l'ordine appena pagato
            loadOrdersToPay();

        } catch (PaymentException e) {
            // Gestione errore specifica del pagamento
            super.showError("Errore durante il pagamento: " + e.getMessage());
        } catch (Exception e) {
            // Gestione errori generici
            super.showError("Errore di sistema: " + e.getMessage());
        }
    }

    //i bottoni sono trigger, attivano solo il pagamento poi
    //la funzione process payment si occupera di centralizzare gli errori
    //se ci fosse bisogno di personalizzare gli errori allora la logica
    //si sposterebbe nello strategy definendo quindi una classe di errori
    //personalizzata appartenente agli strategy che ognuno dovrebbe implementare

    @FXML
    public void handleCardButton() {
        processPayment(new Card());
    }

    @FXML
    public void handleAtmButton() {
        processPayment(new Atm());
    }

    @FXML
    public void handleCashButton() {
        processPayment(new Cash());
    }

    @FXML public void handleBackButton(){
        String path = "/org/ristorante/view/admin_dashboard.fxml";
        String title = "Pannello admin";
        changeScene(path, title);
    }
}
