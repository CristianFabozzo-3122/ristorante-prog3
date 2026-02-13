package org.ristorante.model;

public class RestaurantTable {
    private int table_id;
    private String name;

    public int getTable_id() {
        return table_id;
    }

    public void setTable_id(int table_id) {
        this.table_id = table_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RestaurantTable{" +
                "table_id=" + table_id +
                ", name='" + name + '\'' +
                '}';
    }
}
