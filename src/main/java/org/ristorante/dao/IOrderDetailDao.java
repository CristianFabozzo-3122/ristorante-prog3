package org.ristorante.dao;

import org.ristorante.model.MenuItem;
import org.ristorante.model.OrderDetail;
import org.ristorante.model.RestaurantOrder;

import java.util.List;

public interface IOrderDetailDao {
    public OrderDetail create(OrderDetail o);
    public OrderDetail findByID(int id);
    public OrderDetail update(OrderDetail o);
    public boolean delete(int id);
    public List<OrderDetail> getAll();
    public List<OrderDetail> getDetailsByOrder(int orderId);
}
