package org.ristorante.strategy;

public class WaiterStrategy implements DashboardStrategy {
    @Override
    public String getFxmlPath() {
        //ricordati che devi omettere /resources
        //poiche' e' considerata come una root
        System.out.println("Sto restituendo il path grazie alla strategy...");
        return "/org/ristorante/view/waiter_dashboard.fxml";
    }

    @Override
    public String getTitle() {
        System.out.println("Sto restituendo il title grazie alla strategy...");
        return "Pannello cameriere";
    }
}
