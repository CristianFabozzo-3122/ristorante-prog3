package org.ristorante.dao;

import org.ristorante.model.IOrderState;
import org.ristorante.model.MenuItem;
import org.ristorante.model.RestaurantOrder;

import java.util.List;

public interface IRestaurantOrderDao {
    public RestaurantOrder create(RestaurantOrder o);
    public RestaurantOrder findByID(int id);
    public RestaurantOrder update(RestaurantOrder o);
    public boolean delete(int id);
    public List<RestaurantOrder> getAll();
    public List<RestaurantOrder> getOrdersByState(String state, int tableId);
    //overload del metodo di sopra
    public List<RestaurantOrder> getOrdersByState(String state);
    public List<RestaurantOrder> getOrdersByCategory(String category);

}
