package org.ristorante.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Consumer;

public class SceneManager {

    private static Stage stage;


    public static void setStage(Stage s){
        stage = s;
    }


    static public void changeScene(String path, String title) {
        try {

            //carichiamo il file fxml
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(path));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setTitle(title);

            stage.show();

        }catch (IOException e){
            System.err.println("Errore durante il caricamento: " + path );
            e.printStackTrace();
        }
    }


    static public <T> void changeScene(String path, String title, Consumer<T> controllerSetup){
        //carichiamo il file fxml
        try {


            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(path));
            Parent root = loader.load();

            //recuperiamo il controller appena creato
            T controller = loader.getController();

            //iniettiamo il controller se passiamo un azione di setup
            if (controllerSetup != null) {
                controllerSetup.accept(controller);
            }

            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setTitle(title);

            stage.show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    static public void showError(String msg) {

    }


}
