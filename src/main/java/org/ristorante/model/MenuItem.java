package org.ristorante.model;

public class MenuItem {
    private int menu_item_id;
    private String name;
    private double price;
    private ItemCategory category;

    public int getMenu_item_id() {
        return menu_item_id;
    }

    public void setMenu_item_id(int menu_item_id) {
        this.menu_item_id = menu_item_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ItemCategory getCategory() {
        return category;
    }

    public void setCategory(ItemCategory category) {
        this.category = category;
    }

    @Override
    public String toString() {
        //è diverso dagli altri toString perche' la useremo nel ListView di order.fxml
        return "- " + menu_item_id + " - " + name + " - " + price + " $ ";
    }
}
