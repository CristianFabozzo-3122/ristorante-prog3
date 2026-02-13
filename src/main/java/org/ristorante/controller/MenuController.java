package org.ristorante.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.ristorante.dao.MenuItemDao;
import org.ristorante.model.MenuItem;
import org.ristorante.service.MenuService;
import java.util.List;



public class MenuController extends BaseController {
    @FXML Button backButton;
    @FXML Button updateButton;
    @FXML Button deleteButton;
    @FXML Button createButton;
    @FXML TableView<MenuItem> menu; //in pratica la lista di piatti

    //prendiamo le colonne
    @FXML TableColumn<MenuItem, String> colName;
    //accetta solo le classi non tipi primitivi per questo Double
    @FXML TableColumn<MenuItem, Double> colPrice;
    @FXML TableColumn<MenuItem, String> colCategory;

    private ObservableList<MenuItem> menuData; //e' una lista osservabile, quando aggiungeremo qualcosa
    //questa aggiorna automaticamente senza chiamare nuovamente il service (ce la offre Java stesso)
    private MenuService menuService;

    @FXML public void initialize(){
        menuService = new MenuService(new MenuItemDao());
        //inzializzo la lista osservabile
        menuData = FXCollections.observableArrayList();

        //facciamo il binding tra la lista osservabile e il menu
        menu.setItems(menuData);

        //chiediamo al service solo una volta di caricare tutti i piatti
        //ma i piatti li metteremo nella lista osservabile non nel menu
        List<MenuItem> allDishes = menuService.getAll();
        menuData.addAll(allDishes);

        //mappiamo le colonne
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));

    }

    @FXML public void handleUpdateButton(){
        //recupero l'elemento selezionato
        MenuItem selectedItem = menu.getSelectionModel().getSelectedItem();
        //se e' null ritorno
        if(selectedItem == null){
            return;
        }
        String path = "/org/ristorante/view/update_item_menu.fxml";
        String title = "Update item";
        System.out.println("Sto andando nel form di aggiornamento di un item");
        super.changeScene(path, title, (UpdateMenuController controller ) -> {
            controller.setItem(selectedItem);
        });

    };
    @FXML public void handleDeleteButton(){
        //recupero l'elemento selezionato. JavaFX mi mette a disposizione le apposite componenti
        MenuItem selectedItem = menu.getSelectionModel().getSelectedItem();

        //se si clicca delete e non abbiamo selezionato nulla allora ci fermiamo
        if (selectedItem == null){
            //eventuale label di errore
            return;
        }

        int id = selectedItem.getMenu_item_id();

        System.out.println("Sto cancellando un piatto dal menu id: " + id);

        //delega al service
        menuService.deleteMenuItem(id);

        //uso dell'observable
        menuData.remove(selectedItem);

    };
    @FXML public void createItem(){
        String path = "/org/ristorante/view/create_menu.fxml";
        String title = "Form di aggiunta item";
        System.out.println("Sto andando nel form di creazione di un item");
        super.changeScene(path, title);
    };

    @FXML public void handleBackButton(){
        String path = "/org/ristorante/view/admin_dashboard.fxml";
        String title = "Pannello amministratore";
        super.changeScene(path, title);
    }

}
