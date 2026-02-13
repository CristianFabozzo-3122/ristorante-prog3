package org.ristorante.strategy;

public interface DashboardStrategy {
    //e' il contratto che devono seguire le strategy

    //ottiene il Path per la pagina fxml giusta
    public String getFxmlPath();
    //ottiene informazioni riguardo il titolo
    public String getTitle();

    //@TODO implementare logica di business: se sono cameriere ho certi permessi, se sono admin ne ho altri...

}
