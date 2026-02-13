package org.ristorante.dao;

import org.ristorante.model.ItemCategory;
import org.ristorante.model.MenuItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MenuItemDao extends GenericDao<MenuItem> implements IMenuItemDao {

    @Override
    public MenuItem create(MenuItem item) {
        String sql = "INSERT INTO MenuItem (name, price, category) VALUES (?, ?, ?)";

        // passiamo i valori nello stesso ordine dei "?"
        boolean success = super.update(sql,
                item.getName(),
                item.getPrice(),
                item.getCategory()); // l'enum va salvato come stringa

        // restituisco l'oggetto altrimenti null
        return success ? item : null;
    }

    @Override
    public MenuItem update(MenuItem item) {
        String sql = "UPDATE MenuItem SET name = ?, price = ?, category = ? WHERE menu_item_id = ?";

        boolean success = super.update(sql,
                item.getName(),
                item.getPrice(),
                item.getCategory(),
                item.getMenu_item_id());

        return success ? item : null;
    }

    @Override
    public boolean delete(int id) {
        return super.update("DELETE FROM MenuItem WHERE menu_item_id = ?", id);
    }

    @Override
    public MenuItem findByID(int id) {
        String sql = "SELECT * FROM MenuItem WHERE menu_item_id = ?";
        return super.queryForSingle(sql, id);
    }

    @Override
    public List<MenuItem> getAll() {
        String sql = "SELECT * FROM MenuItem";
        return super.queryForList(sql);
    }

    public List<MenuItem> getKitchenItem(){
        String sql = "SELECT * FROM MenuItem where category = KITCHEN";
        return super.queryForList(sql);
    }

    public List<MenuItem> getBarItem(){
        String sql = "SELECT * FROM MenuItem where category = BAR";
        return super.queryForList(sql);
    }

    @Override
    protected MenuItem mapRow(ResultSet rs) throws SQLException {
        MenuItem item = new MenuItem();

        item.setMenu_item_id(rs.getInt("menu_item_id"));
        item.setName(rs.getString("name"));
        item.setPrice(rs.getDouble("price"));
        item.setCategory(ItemCategory.valueOf(rs.getString("category")));

        return item;
    }
}
