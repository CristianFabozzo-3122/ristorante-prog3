package org.ristorante.controller.components;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.ristorante.controller.BaseController;
import org.ristorante.utils.SceneManager;
import org.ristorante.utils.UserSession;

//questo componente riutilizzabile e' semplicissimo
//in ogni dashobard e' presente per permettere la disconnessione
//sfrutta quella che e' la userSession
public class HeaderController extends BaseController {
    @FXML
    Button logoutButton;

    @FXML public void handleLogout(){
        //al logout puliamo la sessione
        System.out.println("Utente attuale" + UserSession.getInstance().getUser());
        UserSession.getInstance().cleanUserSession();
        System.out.println("disconnessione dell' utente");
        //riporta al login
        System.out.println("Riportando al login");
        SceneManager.changeScene("/org/ristorante/view/login.fxml", "Login");
    }

}
