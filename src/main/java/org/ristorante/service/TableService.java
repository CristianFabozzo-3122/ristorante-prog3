package org.ristorante.service;

import org.ristorante.dao.IRestaurantTableDao;
import org.ristorante.model.RestaurantTable;

import java.util.List;

public class TableService {
    private final IRestaurantTableDao dao;

    public TableService(IRestaurantTableDao dao){
        this.dao = dao;
    }

    public List<RestaurantTable> getList(){
        System.out.println("Recuperando la lista dei tavoli");
        return dao.getAll();
    }

    public RestaurantTable updateRestaurantTable(RestaurantTable t){
        System.out.println("TableService sta richiedendo un aggiornamento del tavolo " + t.toString());
        return dao.update(t);
    }

    public void deleteRestaurantTable(int id){ dao.delete(id); };
}
