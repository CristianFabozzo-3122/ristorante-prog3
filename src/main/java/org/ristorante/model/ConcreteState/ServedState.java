package org.ristorante.model.ConcreteState;

import org.ristorante.model.IOrderState;
import org.ristorante.model.RestaurantOrder;
import org.ristorante.service.custom_error.ValidationError;

public class ServedState implements IOrderState {
    /*@Override
    public void next(RestaurantOrder o) {
        o.setCurrentState(new PaidState());
    }*/

    @Override
    public void cancel(RestaurantOrder o) {
        throw new ValidationError("Per sicurezza non puoi cancellare un ordine gia' servito");
    }

    @Override
    public void edit(RestaurantOrder o) {
        throw new ValidationError("Non puoi aggiornare un ordine gia' servito - creane un altro");
    }

    @Override
    public String getStatusName() {
        return "SERVED";
    }

    @Override
    public void pay(RestaurantOrder o) {
        System.out.println("Pagamento ordine OK");
        o.setCurrentState(new PaidState());
    }

    @Override
    public void serve(RestaurantOrder o) {
        throw new ValidationError("Ordine gia' servito");
    }

    @Override
    public String toString() {
        return "SERVED";
    }
}
