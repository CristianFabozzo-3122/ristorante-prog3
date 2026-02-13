package org.ristorante.dao;

import org.ristorante.model.MenuItem;
import org.ristorante.model.OrderDetail;
import org.ristorante.model.RestaurantOrder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailDao extends GenericDao<OrderDetail> implements IOrderDetailDao{
    @Override
    public OrderDetail create(OrderDetail o) {

        String sql = "INSERT into OrderDetail (order_id_fk, menu_item_fk, quantity, copy_price) VALUES" +
                " (?, ?, ?, ?)";

        super.update(sql,
                o.getOrder_id_fk(),
                o.getMenu_item_fk(),
                o.getQuantity(),
                o.getCopy_price());
        return o;
    }

    public List<OrderDetail> getDetailsByOrder(int orderId){
        String sql = "SELECT * FROM OrderDetail WHERE order_id_fk = ?";
        return super.queryForList(sql, orderId);
    }

    @Override
    public OrderDetail findByID(int id) {
        return null;
    }

    @Override
    public OrderDetail update(OrderDetail o) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<OrderDetail> getAll() {
        return List.of();
    }

    @Override
    protected OrderDetail mapRow(ResultSet rs) throws SQLException {
        OrderDetail od = new OrderDetail();
        od.setId(rs.getInt("id"));
        od.setOrder_id_fk(rs.getInt("order_id_fk"));
        od.setMenu_item_fk(rs.getInt("menu_item_fk"));
        od.setQuantity(rs.getInt("quantity"));
        od.setCopy_price(rs.getInt("copy_price"));
        return od;
    }
}
