package org.ristorante.controller;

import javafx.scene.control.ButtonType;
import org.ristorante.utils.SceneManager;
import javafx.scene.control.Alert;
import java.util.function.Consumer;

//si preferisce l'abstract poiche' non esiste un BaseController ma
//non ha senso creare un basecontroller
abstract public class BaseController {

    //rendiamo tutto protected, ci interessa che solo i figli possano utilizzare questi metodi
    //cioe' tutti i controller.
    //e' impossibile ad esempio fare:
    //Login Controller = new LoginController();
    //controller.changeScene()...
    //ma dovresti passare per login()

    //delega il cambio scena allo SceneManager statico
    protected void changeScene(String path,String title) {
        SceneManager.changeScene(path, title);
    };

    protected <T> void changeScene(String path, String title, Consumer<T> consumerSetup) {
        SceneManager.changeScene(path, title, consumerSetup);
    }

    protected void showError(String context) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(context);
        alert.show();
    }
}
