package org.ristorante.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    //crea un oggetto di DBConnection
    private static DBConnection instance;

    private final Connection connection;

    //essendo un singleton il costruttore deve essere privato
    //cosi non puo' essere istanziato esternamente
    private DBConnection() throws SQLException {
        //tenta la connessione al db
        String url = "jdbc:sqlite:db_ristorante.sqlite";

        //DriverManager e' fondamentale per creare il collegamento
        this.connection = DriverManager.getConnection(url);
        //connection.createStatement().execute("PRAGMA foreign_keys = ON");

        System.out.println("Ok DBConnection");
    };

    //prendi l'unico oggetto disponibile
    public static DBConnection getInstance() throws SQLException {
        //isClosed e' un metodo ereditato
        if(instance == null || instance.getConnection().isClosed()){
            instance = new DBConnection();
            System.err.println("Ok getInstance DBConnection");
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}