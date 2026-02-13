package org.ristorante.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import org.ristorante.dao.RestaurantTableDao;
import org.ristorante.model.RestaurantTable;
import org.ristorante.service.TableService;

import java.util.List;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class TableControllerWaiter extends BaseController{
    TableService tableService;
    @FXML TilePane tableContainers;
    @FXML Button backButton;

    @FXML public void initialize(){
        tableService = new TableService(new RestaurantTableDao());

        List<RestaurantTable> tables = tableService.getList();
        //creiamo i tavoli come bottoni javafx
        for(RestaurantTable t : tables){
            //mostriamo il numero identificativo del tavolo
            Button btn = new Button("" + t.getTable_id());
            btn.setPrefSize(USE_COMPUTED_SIZE,USE_COMPUTED_SIZE);

            //deleghiamo per ogni bottone la funzione (lambda) selectTable
            btn.setOnAction( event -> {
                selectTable(t);
            });
            //attacchiamo al container i bottoni (tavoli)
            tableContainers.getChildren().add(btn);
        }


    }

    public void selectTable(RestaurantTable t){
        //passiamo gli elementi del tavolo selezionato a un altro controller

        //quindi indirizziamo alla pagina del dettaglio del tavolo
        String path = "/org/ristorante/view/table_detail.fxml";
        String title = "Dettaglio del tavolo";
        System.out.println("Sto andando al dettaglio del tavolo " + t.getTable_id());
        super.changeScene(path, title, (TableDetailController controller) -> {
            controller.setTable(t);
        });
    }

    @FXML  public void handleBackButton(){
        String path = "/org/ristorante/view/waiter_dashboard.fxml";
        String title = "Pannello cameriere";
        super.changeScene(path, title);
    }

}
