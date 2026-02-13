module org.ristorante {
    // Dice a Java che ci servono questi pezzi di JavaFX
    requires javafx.controls;
    requires javafx.fxml;

    // Richiede SQL e il Driver SQLite
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    // Apre il tuo pacchetto a JavaFX per fargli gestire la grafica
    opens org.ristorante to javafx.fxml;
    // e i controller
    opens org.ristorante.controller to javafx.fxml;
    opens org.ristorante.controller.components to javafx.fxml;

    //rendo visibile anche i components della view
    opens org.ristorante.view.components to javafx.fxml;

    //rende visibile anche i model
    opens org.ristorante.model to javafx.base;
    // Esporta il tuo pacchetto affinché sia visibile
    exports org.ristorante;
    opens org.ristorante.strategy to javafx.fxml;

}