package org.ristorante.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.ristorante.command.CommandInvoker;
import org.ristorante.command.ConcreteCommand.CreateOrderCommand;
import org.ristorante.dao.MenuItemDao;
import org.ristorante.dao.OrderDetailDao;
import org.ristorante.dao.RestaurantOrderDao;
import org.ristorante.model.*;
import org.ristorante.model.ConcreteState.OrderedState;
import org.ristorante.model.MenuItem;
import org.ristorante.service.OrderService;
import org.ristorante.utils.UserSession;
import javafx.scene.control.Alert;
//importante per aiutarci nella traduzione per le voci degli ordini
import javafx.beans.property.SimpleStringProperty;

import java.util.*;

public class OrderController extends BaseController {

    @FXML private ListView<MenuItem> menuListView; // Lista Sinistra
    @FXML private TableView<OrderDetail> orderTableView; // Lista Destra (Riepilogo)
    //un hashmap, forniamo l'id e troviamo la stringa corrispondente
    private Map<Integer, String> productNamesMap = new HashMap<>();

    @FXML private Label totalLabel; // Etichetta per il totale provvisorio
    @FXML private Button addProductButton;
    @FXML private Button removeProductButton;
    @FXML private Button sendOrderButton;
    @FXML private TableColumn<OrderDetail, String> colName;
    @FXML private TableColumn<OrderDetail, Integer> colQty;
    @FXML private Button backButton;

    private int activeUserID; //l'utente che sta usando la sessione
    private RestaurantTable currentTable; // Il tavolo selezionato nella schermata prima
    private ObservableList<OrderDetail> tempOrderList; // La lista temporanea
    private CommandInvoker invoker;
    private OrderService orderService;
    private MenuItemDao menuItemDao;

    private double total;

    //gli viene passato da TableDetailController
    public void setTable(RestaurantTable t){
        this.currentTable = t;
    }

    // Metodo chiamato quando apri la finestra
    @FXML public void initialize() {
        this.invoker = new CommandInvoker();
        this.orderService = new OrderService(new RestaurantOrderDao(), new OrderDetailDao());
        this.menuItemDao = new MenuItemDao();
        this.activeUserID = UserSession.getInstance().getUser().getUser_id();
        // Inizializzo la lista temporanea vuota
        this.tempOrderList = FXCollections.observableArrayList();
        this.orderTableView.setItems(tempOrderList);

        loadMenu();

        columnsConfig();
    }

    private void loadMenu() {
        // Carico le voci dal DB e le mostro a sinistra
        List<MenuItem> items = menuItemDao.getAll();
        //qui ci sono 2 alternative: utilizzi la cellFactory di javafx
        //oppure usi toString
        //siccome l'utilizzo di cellFactory e' complesso (usa lambda, factory, classi anonime...)
        //per semplciita' lasciamo in automatico il metodo toString
        menuListView.getItems().addAll(items);

        //riempio l'hashmap
        for(MenuItem item : items){
            //qui c'e' la logica di associazione chiave - valore
            //esempio id = 1, name = spaghetti, quindi nell'hashmap accedendo ad 1 trovo spaghetti
            productNamesMap.put(item.getMenu_item_id(), item.getName());
        }
    }

    // bottone per l'aggiunta del prodtto
    @FXML
    public void handleAddProduct() {
        // prendo l'elemento selezionato a sinistra
        MenuItem selectedItem = menuListView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) return;

        System.out.println("Sto aggiungendo " + selectedItem);

        // applico la logica di raggruppamento (cioe' aggiorno quantity se oggetti uguali)
        boolean findedItem = false;

        for (OrderDetail detail : tempOrderList) {
            // Se nella lista c'è già questo piatto (controllo ID)
            if (detail.getMenu_item_fk() == selectedItem.getMenu_item_id()) {
                // Aumento quantità
                detail.setQuantity(detail.getQuantity() + 1);
                findedItem = true;
                break;
            }
        }

        // se non c'era, lo creo nuovo
        if (!findedItem) {
            OrderDetail newDetail = new OrderDetail();
            // order_id_fk è ancora 0 o null! Verrà messo dal Service dopo il salvataggio.
            newDetail.setMenu_item_fk(selectedItem.getMenu_item_id());
            newDetail.setQuantity(1);
            newDetail.setCopy_price(selectedItem.getPrice()); // Congelo il prezzo

            // Aggiungo alla lista visiva
            tempOrderList.add(newDetail);
        }

        // Aggiorno la grafica (fondamentale per vedere la quantità cambiare)
        orderTableView.refresh();
        // Calcolo totale provvisorio
        calculateTotal();
        totalLabel.setText("Totale " + String.valueOf(this.total) + "$");
    }

    //logica del bottone per inviare gli ordini, colui che usera' il command
    @FXML
    public void handleSendOrder() {
        if (tempOrderList.isEmpty()) {
            System.out.println("La comanda è vuota");
            return;
        }

        // preparo i pojo (Pacchetti dati)

        // Header dell'ordine
        RestaurantOrder orderHeader = new RestaurantOrder();
        orderHeader.setTable_id_fk(currentTable.getTable_id());
        orderHeader.setUser_id_fk(activeUserID);
        //impostiamo lo stato a "ORDERED" passando quindi un oggetto IOrderState
        orderHeader.setCurrentState(new OrderedState());
        //passiamo il totale, ci assicuriamo che e' sempre aggiornato poiche' ogni volta
        //che aggiungiamo una voce questa chiama la funzione del calcolo totale
        //parsa tutti gli oggetti e ne restituisce il totale globalmente
        orderHeader.setTotalPrice(this.total);
        // Lista dei dettagli (Converto ObservableList in ArrayList normale)
        List<OrderDetail> detailsToSend = new ArrayList<>(tempOrderList);

        // creo il command
        // passo i pacchetti pronti. Il Command non sa nulla di UI.
        CreateOrderCommand cmd = new CreateOrderCommand(orderHeader, detailsToSend, orderService);

        // eseguiamo tramite l'invoker
        invoker.executeCommand(cmd);

        // resetUi
        tempOrderList.clear();
        //puliamo il totale e la label del totale
        //in questo modo non e' la miglior pratica pero'
        //cerchiamo di velocizzare tutti i processi grafici
        //per concentrarci sulla logica dei pattern
        total = 0;
        totalLabel.setText("Totale: 0$");
        System.out.println("Ordine inviato");

        //vogliamo mostrare un alert con i seguenti dati
        //abbiamo bisogno di un tipo Optional perche' l'alert puo' restituire anche null
        Optional<ButtonType> result = showAlert(
                Alert.AlertType.CONFIRMATION,
                "Ordine Inviato",
                "L'ordine è stato trasmesso.",
                "Premi OK per confermare, oppure Cancel per annullarlo subito."
        );

        //se l'utente ha premuto su cancel (quindi ha annullato l'ordine)
        if(result.get() == ButtonType.CANCEL){
            System.out.println("l'ordine e' stato cancellato");
            invoker.undoLastCommand();
            //richiamiamo
            showAlert(Alert.AlertType.INFORMATION, "Annullato", null, "L'ordine è stato rimosso correttamente.");
        }

    }

    // Helper per calcolare il totale visivo
    private void calculateTotal() {
        double total = 0;
        for (OrderDetail d : tempOrderList) {
            total += (d.getCopy_price() * d.getQuantity());
        }
        this.total = total;
    }

    public void columnsConfig(){
        //l'oggetto ha getQuantity quindi subito riusciamo a prendere il dato che ci serve
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        //qui c'e' bisogno della traduzione da id a nome
        //ovviamente il programma sa che e' di tipo OrderDetai l'oggetto poiche'
        //la colonna appartiene ad una TableView che accetta OrderDetail

        colName.setCellValueFactory(cellData -> {
            OrderDetail detail = cellData.getValue();

            int menuItemId = detail.getMenu_item_fk();
            //qui usiamo l'hasmap per risalire al nome della voce del menu
            String menuItemName = productNamesMap.get(menuItemId);

            //restituisco il nome del piatto
            return new SimpleStringProperty(menuItemName);

        });

    }

    //visualizziamo un alert per la conferma o annullamento ordine che sfruttera' l'undo del Command
    private Optional<ButtonType> showAlert(Alert.AlertType type, String title, String header, String content){
        //al momento della creazione dell'arte diamo il tipo
        //che e' di tipo confirm (vogliamo confermare o annullare l'ordine)
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        //questo ci da il comportamento per il quale l'alert mostra e aspetta che l'utente interagisca
        return alert.showAndWait();
    }

    @FXML  public void handleBackButton(){
        String path = "/org/ristorante/view/table_detail.fxml";
        String title = "Dettaglio tavolo";
        //devo ripassare i dati al controller
        super.changeScene(path, title, (TableDetailController controller) -> {
            controller.setTable(currentTable);
        });
    }

    //@TODO aggiungere logica di rimozione piatto, ricorda che si interviene sulla lista di destra
    //function remove...
}