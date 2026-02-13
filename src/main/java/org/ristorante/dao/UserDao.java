package org.ristorante.dao;

import org.ristorante.model.Role;
import org.ristorante.model.User;
import org.ristorante.dao.GenericDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

//T = User
public class UserDao extends GenericDao<User> implements IUserDao {


    //maprow non puo' essere gestita dal Generic quindi ogni Dao ha la propria maprow
    @Override
    protected User mapRow(ResultSet rs) throws SQLException {
        // qui spieghiamo al padre come leggere la tabella User
        User u = new User();
        u.setUser_id(rs.getInt("user_id"));
        u.setUsername(rs.getString("username"));
        u.setPassword(rs.getString("password"));
        //convertiamo la stringa del DB nell'Enum di Java
        u.setRole(Role.valueOf(rs.getString("role")));
        return u;
    }

    //tutti i metodi CRUD sono delegati al padre

    @Override
    public User findById(int id) {
        //query + parametro
        String sql = "SELECT * FROM User WHERE user_id = ?";
        return super.queryForSingle(sql, id);
    }

    @Override
    public User findByUsername(String username) {
        System.out.println("Sto utilizzando UserDao");
        String sql = "SELECT * FROM User WHERE username = ?";
        return super.queryForSingle(sql, username);
    }

    @Override
    public List<User> getAll() {
        // Qui non servono parametri, usiamo queryForList
        return super.queryForList("SELECT * FROM User");
    }

    @Override
    public boolean delete(int id) {
        return super.update("DELETE FROM User WHERE user_id = ?", id);
    }

    @Override
    public User create(User u) {
        String sql = "INSERT INTO User (username, password, role) VALUES (?, ?, ?)";

        // passiamo i valori nello stesso ordine dei "?"
        boolean success = super.update(sql,
                u.getUsername(),
                u.getPassword(),
                u.getRole().toString()); // l'enum va salvato come stringa

        // restituisco l'oggetto altrimenti null
        return success ? u : null;
    }

    @Override
    public User update(User u) {
        String sql = "UPDATE User SET username = ?, password = ?, role = ? WHERE user_id = ?";

        boolean success = super.update(sql,
                u.getUsername(),
                u.getPassword(),
                u.getRole().toString(),
                u.getUser_id());

        return success ? u : null;
    }
}