package org.ristorante;

import javafx.application.Application;
import javafx.stage.Stage;
import org.ristorante.utils.SceneManager;
import org.ristorante.utils.UserSession;

public class App extends Application {
    @Override
    public void start(Stage primaryStage){
        System.out.println("L'app sta restituendo il primary stage...");
        SceneManager.setStage(primaryStage);
        SceneManager.changeScene("/org/ristorante/view/login.fxml", "Login");

    }

    @Override
    public void stop(){
        System.out.println("Sto chiudendo l'app...");
        //quando ci si scollega puliamo la sessione
        UserSession.getInstance().cleanUserSession();
    }

    //se si lancia dall'IDE c'e' bisogno di questo metodo
    public static void main(String[] args) {
        System.out.println("Sto lanciando l'app...");
        launch(args);
    }
}
