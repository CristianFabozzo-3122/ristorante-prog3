package org.ristorante.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.ristorante.dao.MenuItemDao;
import org.ristorante.model.MenuItem;
import org.ristorante.service.MenuService;

public class UpdateMenuController extends BaseController {
    @FXML
    TextField name;
    @FXML
    TextField price;
    @FXML
    Button saveButton;
    //si potrebbe anche utilizzare la stessa logica dello stack dei command per tornare indietro
    //ad esempio memorizzare l'ultimo file fxml acceduto e premere un tasto per indirizzarci ad esso
    //per semplicita' non l'ho fatto
    @FXML
    Button backButton;

    private MenuItem item;
    private MenuService service;

    @FXML public void initialize(){
        service = new MenuService(new MenuItemDao());
    };

    //questa funzione riceve l'item da MenuController
    public void setItem(MenuItem item){
        System.out.println("Sto settando l'item da aggiornare " + item);
        this.item = item;

        //imposto anche i parametri nei campi
        name.setText(item.getName());
        price.setText(String.valueOf(item.getPrice()));
    }

    @FXML public void handleSaveButton(){
        item.setName(name.getText());
        item.setPrice(Double.parseDouble(price.getText()));
        System.out.println("Questo e' il nuovo item che sto mandando " + item);
        service.updateMenuItem(item);
        //eventuale label di errore o successo...

        //rindirizzamento nella schermata precedente richiamando la stessa
        //handleBackButton
        handleBackButton();

    }

    @FXML  public void handleBackButton(){
        String path = "/org/ristorante/view/menu.fxml";
        String title = "Menu";
        super.changeScene(path, title);
    }

}
