package org.ristorante.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AdminController extends BaseController {
    @FXML Button menuButton;
    @FXML Button paymentButton;

    @FXML
    public void goToMenu() {
        //questa pagina visualizzera' una lista di voci del menu
        //con la possibilita' di aggiungerne altri

        String path = "/org/ristorante/view/menu.fxml";
        String title = "Menu";
        System.out.println("Andando al menu");
        super.changeScene(path,title);
    }

    @FXML void goToPayment(){
        //questa pagina visualizzera' una lista di ordini
        //con la possibilita' di mostrare l'intera comanda
        //e la possibilita' di pagere l'ordine (quindi utilizzo di state da SERVED a PAGATO)

        String path = "/org/ristorante/view/payment.fxml";
        String title = "Payment";
        System.out.println("Andando alla cassa");
        super.changeScene(path,title);
    }


}
