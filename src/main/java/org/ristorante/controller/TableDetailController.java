package org.ristorante.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.ristorante.dao.RestaurantTableDao;
import org.ristorante.model.RestaurantTable;
import org.ristorante.service.TableService;

public class TableDetailController extends BaseController{
    TableService tableService;

    RestaurantTable table;
    @FXML
    TextField name;
    @FXML
    Button takeOrderButton;
    @FXML Button updateTableButton;
    @FXML Button deleteButton;
    @FXML Button backButton;
    @FXML private Button viewOrderButton;
    @FXML public void initialize(){
        tableService = new TableService(new RestaurantTableDao());
    }

    public void setTable(RestaurantTable t){
        System.out.println("Sto settando il tavolo per il dettaglio " + t);
        table = t;
        //mappiamo il nome e lo rendiamo disponibile anche ad aggiornamenti
        name.setText(t.getName());
    }

    @FXML
    public void handleUpdateButton(){
        System.out.println("Aggiornando il tavolo " + table);
        table.setName(name.getText());
        tableService.updateRestaurantTable(table);
        handleBackButton();
    }

    @FXML
    public void handleDeleteButton(){
        System.out.println("Cancellando il tavolo " + table);
        tableService.deleteRestaurantTable(table.getTable_id());
        handleBackButton();
    }

    @FXML
    public void handleTakeOrder(){
        String path = "/org/ristorante/view/order.fxml";
        String title = "Ordine tavolo - " + table.getName();
        //passiamo l'oggetto table nella schermata dell'ordine
        System.out.println("Sto andando nella schermata dell'ordine del tavolo " + table.getTable_id());
        super.changeScene(path, title, (OrderController controller) -> {
            controller.setTable(table);
        });
    }

    @FXML
    public void handleBackButton(){
        String path = "/org/ristorante/view/table_list.fxml";
        String title = "Lista tavoli";
        super.changeScene(path, title);
    }

    @FXML
    public void handleViewOrder(){
        String path = "/org/ristorante/view/order_by_table.fxml";
        String title = "Ordini del tavolo " + table.getName();
        //passiamo il tavolo alla schermata dell'ordine cosi che possiamo prendere
        //gli ordini del tavolo selezionato
        super.changeScene(path, title, (OrderByTableController controller) -> {
            controller.setTable(table);
        });
    }
}
