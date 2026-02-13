package org.ristorante.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.ristorante.dao.UserDao;
import org.ristorante.dao.custom_error.DaoError;
import org.ristorante.model.User;
import org.ristorante.service.AuthService;
import org.ristorante.service.custom_error.ValidationError;
import org.ristorante.strategy.DashboardStrategy;
import org.ristorante.strategy.StrategyFactory;
import org.ristorante.utils.UserSession;

public class LoginController extends BaseController{
    //private StrategyFactory strategyFactory;
    private AuthService authService;

    //in sintesi il nome della variabile dovrai poi corrispondere all'id dell'input di javafx
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    @FXML private Button loginButton;

    public LoginController(){};


    //questo metodo viene chiamato automaticamente da Javafx dopo aver caricato l'fxml

    @FXML
    public void initialize(){
        this.authService = new AuthService(new UserDao());
    }

    //gestione evento login

    @FXML
    public void handleLogin(){
        try {
            //pulizia degli errori precedenti
            errorLabel.setVisible(false);

            User loggedUser = authService.login(usernameField.getText(), passwordField.getText());

            System.out.println("Settando la strategia");
            //creo la factory e decido dove andare
            StrategyFactory factory = new StrategyFactory();
            DashboardStrategy strategy = factory.createStrategy(loggedUser.getRole());

            System.out.println("Settando l'user corrente nella sessione");
            //settiamo l'utente loggato
            UserSession.getInstance().setSession(loggedUser, strategy);

            //cambio scena con i dati forniti dallo strategy
            System.out.println("Cambiando scena in base al ruolo dell'utente");
            super.changeScene(strategy.getFxmlPath(), strategy.getTitle());


        }catch (DaoError e){
            errorLabel.setText("Sistema momentaneamente non disponibile.");
            errorLabel.setStyle("-fx-text-fill: red;");
            errorLabel.setVisible(true);

        }catch (ValidationError e){
            errorLabel.setText(e.getMessage());
            errorLabel.setStyle("-fx-text-fill: orange;");
            errorLabel.setVisible(true);

        }catch (Exception e){
            errorLabel.setText("Errore sconosciuto.");
            e.printStackTrace();
        }
    }

    @FXML
    void handleLogout(){
        //pulisce la sessione al click del logout
        UserSession.getInstance().cleanUserSession();
        //ti indirizza alla pagina di login
    }


}
