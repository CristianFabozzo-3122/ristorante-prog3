package org.ristorante.strategy;

public class BarStrategy implements DashboardStrategy {

    @Override
    public String getFxmlPath() {
        System.out.println("Sto restituendo il path grazie alla strategy...");
        return "/org/ristorante/view/bar_dashboard.fxml";
    }

    @Override
    public String getTitle() {
        System.out.println("Sto restituendo il title grazie alla strategy...");
        return "Bar";
    }
}
