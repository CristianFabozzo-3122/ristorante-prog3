package org.ristorante.service;

import org.ristorante.dao.IMenuItemDao;
import org.ristorante.model.MenuItem;
import org.ristorante.service.custom_error.ValidationError;

import java.util.List;

public class MenuService {
    private final IMenuItemDao dao;

    public MenuService(IMenuItemDao dao){
        this.dao = dao;
    }

    public MenuItem addMenuItem(MenuItem item){

        //logica di business

        if(item == null){
            throw new ValidationError("Campi vuoti");
        }

        if(item.getPrice() <= 0){
            throw new ValidationError("Prezzo deve essere superiore a 0");
        }

        //...altre logiche di business

        System.out.println("Il service sta richiedendo la creazione di un nuovo itemMenu");
        //delego al dao la creazione di un item
        return dao.create(item);

    }

    public MenuItem updateMenuItem(MenuItem item){

        if (item == null){
            throw new ValidationError("Campi vuoti");
        }
        //altre logiche di business si potrebbero anche centralizzare in un helper

        System.out.println("Il service sta richiedendo l'aggiornamento di un item");
        return dao.update(item);
    }

    public void deleteMenuItem(int itemID){
        dao.delete(itemID);
    }

    public List<MenuItem> getAll(){
        System.out.println("Recuperando il menu");
        return dao.getAll();
    }
}
