package org.ristorante.dao;
import java.util.List;

import org.ristorante.model.User;

public interface IUserDao {
    //trova l'user tramite id
    User findById(int id);
    //prende tutti gli utenti
    List<User> getAll();
    //crea un user
    User create(User u);
    //aggiorna un user
    User update(User u);
    //cancella un user
    boolean delete (int id);

    User findByUsername(String username);
}
