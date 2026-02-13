package org.ristorante.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.ristorante.dao.MenuItemDao;
import org.ristorante.model.ItemCategory;
import org.ristorante.model.MenuItem;
import org.ristorante.service.MenuService;

import java.util.Locale;

public class CreateMenuController extends BaseController{

    private MenuService service;
    @FXML
    TextField name;
    @FXML
    TextField price;
    @FXML
    ChoiceBox<ItemCategory> select;
    @FXML
    Button saveButton;
    @FXML
    Button backButton;

    //eventuali label per gli errori


    @FXML  public void initialize(){

        service = new MenuService(new MenuItemDao());

        //settiamo tutti i valori dell'enum di Item Category
        select.getItems().setAll(ItemCategory.values());

        //preselezioniamo gia' il primo elemento per non lasciarlo vuoto
        select.getSelectionModel().selectFirst();
    }

    public void handleSaveButton(){
        //parsing dei dati inseriti
        try {
            MenuItem item = new MenuItem();
            item.setName(name.getText());
            item.setPrice(Double.parseDouble(price.getText()));
            item.setCategory(select.getValue());

            //eventuali controlli sui campi


            System.out.println("Sto aggiungendo una voce al menu " + item);
            //attivazione del service
            service.addMenuItem(item);

            System.out.println("Sto pulendo i campi");
            //pulizia degli input
            name.clear();
            price.clear();
            //la combobox la facciamo tornare al primo valore
            select.getSelectionModel().selectFirst();

        }catch(NumberFormatException e){
            System.out.println("Il prezzo deve essere un numero valido");
        }
    }

    @FXML  public void handleBackButton(){
        String path = "/org/ristorante/view/menu.fxml";
        String title = "Menu";
        super.changeScene(path, title);
    }

}
