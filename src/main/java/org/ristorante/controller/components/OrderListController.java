package org.ristorante.controller.components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import org.ristorante.model.RestaurantOrder;

import java.util.List;
import java.util.function.Consumer;

public class OrderListController {

    @FXML
    private ListView<RestaurantOrder> ordersListView;

    private ObservableList<RestaurantOrder> ordersData;

    // Questo serve solo per la modalità "Attiva" (Cucina/Bar)
    private Consumer<RestaurantOrder> onOrderSelected;

    @FXML
    public void initialize() {
        //inizializziamo l'observable list
        //e facciamo il binding della ListView con l'observablelist

        ordersData = FXCollections.observableArrayList();
        ordersListView.setItems(ordersData);

        // configurazione delle celle (CellFactory)
        ordersListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(RestaurantOrder item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    // usiamo toString() universale.
                    // esempio: "Tavolo X - Data - Stato"
                    setText(item.toString());
                }
            }
        });

        // gestione del click
        // qui c'e' l'utilizzo di un observer. E' una funzione della libreria di java
        ordersListView.getSelectionModel().selectedItemProperty().addListener((obs, old, newVal) -> {
            // Se c'è un listener configurato (Cucina/Bar), eseguiamo subito l'azione
            if (newVal != null && onOrderSelected != null) {
                onOrderSelected.accept(newVal);
            }
            // Se onOrderSelected è null (Tavolo/Pagamenti), non facciamo nulla.
            // Il genitore userà getSelectedOrder() quando preme il bottone.
        });
    }

    // metodi usati dai genitori

    // Popola la lista
    public void setOrders(List<RestaurantOrder> orders) {
        this.ordersData.setAll(orders);
    }

    // il genitore chiede chi è selezionato (es. al click di un bottone)
    public RestaurantOrder getSelectedOrder() {
        return ordersListView.getSelectionModel().getSelectedItem();
    }

    // il genitore dice "Avvisami appena cliccano"
    public void setOnOrderSelectedListener(Consumer<RestaurantOrder> listener) {
        this.onOrderSelected = listener;
    }

    // Pulisce tutto
    public void clear() {
        this.ordersData.clear();
    }
}