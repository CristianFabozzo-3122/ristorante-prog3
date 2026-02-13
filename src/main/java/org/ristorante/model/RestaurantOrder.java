package org.ristorante.model;

import java.util.Date;

public class RestaurantOrder {
    private int order_id;
    private int user_id_fk;
    private int table_id_fk;
    private Date creation_date;
    //usiamo il pattern State per la gestione dello stato
    private IOrderState currentState;
    private PaymentMethod paymentMethod;
    private double totalPrice;

    /*public void nextState(){
        if(currentState != null){
            currentState.next(this);
        }
    }*/


    public void payOrder(){
        currentState.pay(this);
    }

    public void serveOrder(){
        currentState.serve(this);
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public IOrderState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(IOrderState currentState) {
        this.currentState = currentState;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public int getTable_id_fk() {
        return table_id_fk;
    }

    public void setTable_id_fk(int table_id_fk) {
        this.table_id_fk = table_id_fk;
    }

    public int getUser_id_fk() {
        return user_id_fk;
    }

    public void setUser_id_fk(int user_id_fk) {
        this.user_id_fk = user_id_fk;
    }

    @Override
    public String toString() {
        /*return "RestaurantOrder{" +
                "order_id=" + order_id +
                ", user_id_fk=" + user_id_fk +
                ", table_id_fk=" + table_id_fk +
                ", creation_date=" + creation_date +
                ", currentState=" + currentState +
                ", paymentMethod=" + paymentMethod +
                ", totalPrice=" + totalPrice +
                '}';*/
        return "Ordine del " + creation_date + " stato: " + currentState.toString();
    }


}
