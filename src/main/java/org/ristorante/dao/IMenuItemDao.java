package org.ristorante.dao;

import org.ristorante.model.MenuItem;

import java.util.List;

public interface IMenuItemDao {
    public MenuItem create(MenuItem item);
    public MenuItem update(MenuItem item);
    public boolean delete(int id);
    public MenuItem findByID(int id);
    public List<MenuItem> getAll();
}
