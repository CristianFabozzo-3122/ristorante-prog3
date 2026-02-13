package org.ristorante.model.ConcreteState;

import org.ristorante.model.IOrderState;
import org.ristorante.model.RestaurantOrder;
import org.ristorante.service.custom_error.ValidationError;

public class PaidState implements IOrderState {
    /*@Override
    public void next(RestaurantOrder o) {
        throw new ValidationError("Quest'ordine e' stato completato");
    }*/

    @Override
    public void cancel(RestaurantOrder o) {
        throw new ValidationError("Non puoi cancellare un ordine gia' incassato");
    }

    @Override
    public void edit(RestaurantOrder o) {
        throw new ValidationError("Non puoi aggiornare un ordine gia' pagato");
    }

    @Override
    public String getStatusName() {
        return "PAID";
    }

    @Override
    public void pay(RestaurantOrder o) {
        throw new ValidationError("Ordine gia' pagato");
    }

    @Override
    public void serve(RestaurantOrder o) {
        throw  new ValidationError("Ordine gia' serivto");
    }

    @Override
    public String toString() {
        return "PAID";
    }
}
