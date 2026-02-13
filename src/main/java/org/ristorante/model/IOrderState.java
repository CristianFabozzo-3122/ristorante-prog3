package org.ristorante.model;

//Implementazione del pattern state, ogni evento riceve il context (this)
//e ne modifica lo stato con next passando
//il prossimo stato oppure vede se e' consentito fare le altre azioni
//se non e' consentito lanciamo un errore

//il flusso e' ORDERED - SERVED - PAID

//Lo stato e' gestito solo dal cameriere e per ultimo dall'admin
//La cucina e il bar invece invieranno una notifica per segnalare che i loro ordini sono pronti

public interface IOrderState {
    //public void next(RestaurantOrder o);
    public void cancel(RestaurantOrder o);
    public void edit(RestaurantOrder o);
    public String getStatusName();
    public void pay(RestaurantOrder o);
    public void serve(RestaurantOrder o);
    public String toString();
}
