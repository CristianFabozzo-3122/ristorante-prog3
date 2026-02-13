package org.ristorante.dao;

import org.ristorante.model.RestaurantTable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RestaurantTableDao extends GenericDao<RestaurantTable> implements IRestaurantTableDao{
    @Override
    public RestaurantTable create(RestaurantTable t) {
        String sql = "INSERT INTO RestaurantTable (name) VALUES (?)";

        // passiamo i valori nello stesso ordine dei "?"
        boolean success = super.update(sql, t.getName());

        // restituisco l'oggetto altrimenti null
        return success ? t : null;
    }

    @Override
    public RestaurantTable findByID(int id) {
        String sql = "SELECT * FROM RestaurantTable WHERE tabe_id = ?";
        return super.queryForSingle(sql, id);
    }

    @Override
    public RestaurantTable update(RestaurantTable t) {
        String sql = "UPDATE RestaurantTable SET name = ? WHERE table_id = ?";
        boolean success = super.update(sql, t.getName(), t.getTable_id());
        return success ? t : null;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM RestaurantTable where table_id = ?";
        return super.update(sql, id);
    }

    @Override
    public List<RestaurantTable> getAll() {
        String sql = "SELECT * FROM RestaurantTable";
        return super.queryForList(sql);
    }

    @Override
    protected RestaurantTable mapRow(ResultSet rs) throws SQLException {
        RestaurantTable table = new RestaurantTable();
        table.setTable_id(rs.getInt("table_id"));
        table.setName(rs.getString("name"));
        return table;
    }
}
