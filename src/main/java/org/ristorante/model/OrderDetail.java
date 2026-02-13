package org.ristorante.model;

public class OrderDetail {
    private int id;
    private int order_id_fk;
    private int menu_item_fk;
    private int quantity;
    private double copy_price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCopy_price() {
        return copy_price;
    }

    public void setCopy_price(double copy_price) {
        this.copy_price = copy_price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getOrder_id_fk() {
        return order_id_fk;
    }

    public void setOrder_id_fk(int order_id_fk) {
        this.order_id_fk = order_id_fk;
    }

    public int getMenu_item_fk() {
        return menu_item_fk;
    }

    public void setMenu_item_fk(int menu_item_fk) {
        this.menu_item_fk = menu_item_fk;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                ", order_id_fk=" + order_id_fk +
                ", menu_item_fk=" + menu_item_fk +
                ", quantity=" + quantity +
                ", copy_price=" + copy_price +
                '}';
    }
}
