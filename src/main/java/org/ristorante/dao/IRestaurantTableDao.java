package org.ristorante.dao;

import org.ristorante.model.RestaurantTable;

import java.util.List;

public interface IRestaurantTableDao {
    public RestaurantTable create(RestaurantTable t);
    public RestaurantTable findByID(int id);
    public RestaurantTable update(RestaurantTable t);
    public boolean delete(int id);
    public List<RestaurantTable> getAll();
}
