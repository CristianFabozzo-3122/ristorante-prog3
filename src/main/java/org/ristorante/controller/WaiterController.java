package org.ristorante.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class WaiterController extends BaseController{
    @FXML
    Button tableButton;
    //@FXML Button readyOrderButton;

    @FXML
    void handleTableButton(){
        //visualizzera' una lista di tavoli
        String path = "/org/ristorante/view/table_list.fxml";
        String title = "Lista tavoli";
        System.out.println("Andando alla lista dei tavoli");
        super.changeScene(path,title);
    }

//    @FXML
//    void handleReadyOrderButton(){
//        //visualizzera' solo gli ordini pronti
//        String path = "/org/ristorante/view/ready_order.fxml";
//        String title = "Ordini pronti";
//        System.out.println("Andando alla lista degli ordini pronti");
//        super.changeScene(path,title);
//    }
}
