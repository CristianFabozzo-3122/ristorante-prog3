package org.ristorante.service;

import org.ristorante.dao.IUserDao;
import org.ristorante.model.User;
import org.ristorante.service.custom_error.ValidationError;

public class AuthService {
    private final IUserDao dao;


    //ha bisogno di un oggetto che rispetti il contratto IUserDao
    //non ci interessiamo del dao concreto ma dell'interfaccia (rispetta DIP)
    public AuthService(IUserDao dao){
        this.dao = dao;
    }


    public User login (String username, String password) throws ValidationError{

        //cerchiamo tramite username
        //se il DB fallisce qui, partirà una DaoError (RuntimeException) che salirà automaticamente
        User u = dao.findByUsername(username);

        System.out.println("il Service delega al dao di cercare un Username");

        if(u == null){
            throw new ValidationError("username non valido");
        }

        System.out.println("Controllando la password...");

        //@NOTA: per le stringhe devo usare equals
        if(!(u.getPassword().equals(password))){
            throw new ValidationError("password non valida");
        }

        return u;

    }

    public void logout (){
        System.out.println("Recuperando dal dao dati per il logout");
    }

}