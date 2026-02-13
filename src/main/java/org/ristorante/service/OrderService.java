package org.ristorante.service;

import org.ristorante.dao.IOrderDetailDao;
import org.ristorante.dao.IRestaurantOrderDao;
import org.ristorante.model.*;
import org.ristorante.service.custom_error.PaymentException;
import org.ristorante.strategy.PaymentMethod;

import java.util.List;

public class OrderService {
    private final IRestaurantOrderDao dao;
    private final IOrderDetailDao detaildao;

    public OrderService(IRestaurantOrderDao dao, IOrderDetailDao detaildao){
        this.dao = dao;
        this.detaildao = detaildao;
    }

    public RestaurantOrder createOrder(List<OrderDetail> details,RestaurantOrder ro){
        System.out.println("Il service sta creando un ordine");
        //per ogni voce dell'ordine crea un dato nel db

        //crea l'ordine
        RestaurantOrder order = dao.create(ro);
        //per ogni voce dell'ordine crea un OrderDetail
        for(OrderDetail d : details){
            System.out.println("Sto creando la voce dell'ordine");
            //inietto l'id dell'ordine appena creato
            d.setOrder_id_fk(order.getOrder_id());
            detaildao.create(d);
        }
        //quindi crea l'ordine
        return order;
    }

    public void cancelOrder(int id){
        System.out.println("Il service sta cancellando l'ordine");
        dao.delete(id);
    }

    public RestaurantOrder updateOrder (RestaurantOrder o){
        System.out.println("Il service sta aggiornando l'ordine");
        return dao.update(o);
    }

    public List<RestaurantOrder> servedOrder(int idTable){
        //prende tutti gli ordini serviti di uno specifico tavolo
        System.out.println("Il service sta ottenendo solo gli ordini serviti (non pagati)");
        System.out.println("Ottenuto " + dao.getOrdersByState("SERVED", idTable));
        return dao.getOrdersByState("SERVED", idTable);
    }

    //overload di servedOrder
    public List<RestaurantOrder> servedOrder(){
        //prende tutti gli ordini serviti in generale
        System.out.println("Il service sta ottenendo solo gli ordini serviti (non pagati)");
        System.out.println("Ottenuto " + dao.getOrdersByState("SERVED"));
        return dao.getOrdersByState("SERVED");
    }

    public List<RestaurantOrder> orderedOrder(int idTable){
        //prende tutti gli ordini appena creati (ordered) di uno specifico tavolo
        System.out.println("Il service sta ottenendo solo gli ordini non serviti e non pagati");
        System.out.println("Ottenuto " + dao.getOrdersByState("ORDERED", idTable));
        return dao.getOrdersByState("ORDERED", idTable);
    }

    public List<RestaurantOrder> paidOrder(){
        //prende tutti gli ordini pagati in generale
        //utile per la "cassa"
        //utilizza il metodo di cui abbiamo fatto l'overload nel dao
        //cioe' orderByStatus
        System.out.println("Il service sta ottenendo solo gli ordini pagati");
        System.out.println("Ottenuto " + dao.getOrdersByState("PAID"));
        return dao.getOrdersByState("PAID");
    }

    public List<OrderDetail> getDetails(RestaurantOrder o){
        //mostra i dettagli di uno specifico ordine
        return detaildao.getDetailsByOrder(o.getOrder_id());
    }

    //segna come SERVED l'ordine
    public void serveOrder(RestaurantOrder o){
        o.serveOrder();
        System.out.println("Cambiando sul db lo stato dell'ordine in SERVED");
        dao.update(o);
    }


    //logica per il pagamento dell'ordine
    //il service si interpone fra strategy e state
    //ha un ruolo principale
    //dice: con quale strategia devo pagare
    //      in quale state cambiare
    //      e quando devo aggiornare (in caso di success) il metodo di pagamento dell'ordine

    public void payOrder(RestaurantOrder o, PaymentMethod strategy) throws PaymentException{
        //Prendo il totale dell'ordine
        double amount = o.getTotalPrice();
        System.out.println("Il cliente ha pagato" + o.getTotalPrice());
        //Eseguo la strategy, eseternamente viene passato l'oggetto scelto
        //quindi c'e' un constructor injection
        boolean success = strategy.pay(amount);

        //se il pagamento ha successo allora eseguo le seguenti operazioni
        if(success){
            System.out.println("Pagamento riuscito con successo");

            System.out.println("Cambiando sul db lo stato dell'ordine in PAID");
            o.payOrder();
            System.out.println("Aggiornando il db");
            //aggiorno lo stato del sistema
            o.setPaymentMethod(org.ristorante.model.PaymentMethod.valueOf(strategy.toString()));
            dao.update(o);
        }else{
            //lancia un errore per i pagamenti
            throw new PaymentException("errore pagamenti");
        }

    }

    public List<RestaurantOrder> getOrdersByCategory(String category){
        //aggiungere eventuale controllo se la stringa fa parte degli enum

        //si immagini la Cucina o il bar che vuole sapere se ci sono ordini anche per lui
        //questa query prende in considerazione una comanda per la cucina se e' presente almeno un
        //ordine per lui, mettendo comunque informazioni del bar. Questa scelta in realta' dipende
        //da come si vuole organizzare la realta': Un ambiente estremamente organizzato si occuperebbe
        //solo del proprio reparto. Un altro ambiente magari vuole capire il tipo di vino
        // che si deve accompagnare
        //vicino la carne e quindi deciderne la cottura, un altro ambiente piu ristretto vorrebbe sapere
        //tutto e basta: es se un cuoco vuole improvvisarsi barista
        return dao.getOrdersByCategory(category);
    }

}
