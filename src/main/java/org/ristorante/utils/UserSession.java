package org.ristorante.utils;

import org.ristorante.strategy.DashboardStrategy;
import org.ristorante.model.User;

public class UserSession {
    //è un sigleton per gestire la sessione utente
    //siccome è un singleton ci assicuriamo che l'utente loggato sia sempre al massimo 1

    private static UserSession instance;
    private User u;
    DashboardStrategy dashboardStrategy;
    private UserSession(){};

    public static UserSession getInstance() {
        if(instance == null) {
            instance = new UserSession();
            System.out.println("Ok getInstance UserSession");
        }
        return instance;
    }

    //al login riceveremo un user passato dal AuthService
    //l'user passato sara' il nuovo user della sessione
    public void setSession(User u, DashboardStrategy strategy){
        System.out.println("Sto settando la sessione, con user_id: " + u.getUser_id());
        this.u = u;
    }

    //al logout puliremo la sessione
    public void cleanUserSession(){
        System.out.println("Sto pulendo la sessione...");
        this.u = null;
        this.dashboardStrategy = null;
    }

    //Questo metodo serve solo a capire chi è loggato
    public User getUser(){
        System.out.println("Sto restituendo l'utente corrente..." + u);
        return u;
    }


}
