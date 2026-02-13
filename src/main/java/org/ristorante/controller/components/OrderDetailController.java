package org.ristorante.controller.components;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.ristorante.dao.MenuItemDao;
import org.ristorante.model.MenuItem;
import org.ristorante.model.OrderDetail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Questo controller è un componente riutilizzabile
// Non sa nulla del tavolo o dei filtri. Sa solo mostrare una lista di dettagli.
public class OrderDetailController {

    @FXML TableView<OrderDetail> orderDetails;

    // colonne per i dettagli degli ordini
    @FXML TableColumn<OrderDetail, String> colName;
    @FXML TableColumn<OrderDetail, Integer> colQty;

    ObservableList<OrderDetail> detailsData;

    // nome per la traduzione dei piatti
    private Map<Integer, String> productNamesMap = new HashMap<>();

    @FXML public void initialize() {
        //Inizializziamo solo la parte relativa alla tabella dettagli
        detailsData = FXCollections.observableArrayList();
        orderDetails.setItems(detailsData);

        loadMenuMap();

        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        // l'oggetto non conosce direttamente il nome del item del menu quindi va scritta
        // una lambda
        colName.setCellValueFactory(cellData -> {
            // questo controllo serve poiche' javafx fa calcoli anche per la riga vuota
            // o ancora da riempire
            if (cellData.getValue() == null) {
                return new SimpleStringProperty("");
            }
            // prendiamo l'id dell'item, ricordiamo che cellData e' di tipo OrderDetail
            int itemId = cellData.getValue().getMenu_item_fk();
            return new SimpleStringProperty(productNamesMap.getOrDefault(itemId, "ID: " + itemId));
        });
    }

    // metodo pubblico che il Genitore (OrderByTable, Kitchen, Bar...) chiamerà
    // per passare i dati da visualizzare.
    public void setOrderDetails(List<OrderDetail> details) {
        System.out.println("Componente Figlio: Ricevuti " + details.size() + " dettagli.");
        this.detailsData.setAll(details);
    }

    // metodo utile per pulire la tabella se necessario
    public void clear() {
        this.detailsData.clear();
    }

    // per la traduzione degli id delle voci del menu
    private void loadMenuMap() {
        MenuItemDao menuDao = new MenuItemDao();
        List<MenuItem> items = menuDao.getAll();
        for (MenuItem item : items) {
            productNamesMap.put(item.getMenu_item_id(), item.getName());
        }
    }
}