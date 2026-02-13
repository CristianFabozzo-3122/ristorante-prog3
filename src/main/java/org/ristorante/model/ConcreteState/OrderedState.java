package org.ristorante.model.ConcreteState;

import org.ristorante.model.IOrderState;
import org.ristorante.model.RestaurantOrder;
import org.ristorante.service.custom_error.ValidationError;

public class OrderedState implements IOrderState {

    /*@Override
    public void next(RestaurantOrder o) {
        System.out.println("Sto mandando l'ordine");
        o.setCurrentState(new ServedState());
    }*/

    @Override
    public void cancel(RestaurantOrder o) {
        System.out.println("cancellazione dell'ordine: OK");
    }

    @Override
    public void edit(RestaurantOrder o) {
        System.out.println("aggiornamento dell'ordine OK");
    }

    @Override
    public String getStatusName() {
        return "ORDERED";
    }

    @Override
    public void pay(RestaurantOrder o) {
        throw new ValidationError("Non puoi pagare un ordine se non e' stato servito");
    }

    @Override
    public void serve(RestaurantOrder o) {
        System.out.println("Sto segnando l'ordine come SERVED");
        o.setCurrentState(new ServedState());
    }

    @Override
    public String toString() {
        return "ORDERED";
    }
}
