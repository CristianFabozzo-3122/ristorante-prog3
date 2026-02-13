package org.ristorante.command.ConcreteCommand;

import org.ristorante.command.ICommand;
import org.ristorante.model.OrderDetail;
import org.ristorante.model.RestaurantOrder;
import org.ristorante.service.OrderService;

import java.util.List;

public class CreateOrderCommand implements ICommand {

    // i pacchetti già pronti (POJO) creati dal Controller
    final private RestaurantOrder orderHeader;   //l'ordine (senza id)
    final private List<OrderDetail> orderDetails; // le voci (a cui dobbiamo assegnare ancora un id ordine)
    final private OrderService service;

    // ci serve per sapere quale ordine abbiamo creato (per l'undo)
    private RestaurantOrder savedOrder;

    // riceve i POJO pronti
    public CreateOrderCommand(RestaurantOrder orderHeader, List<OrderDetail> orderDetails, OrderService service) {
        this.orderHeader = orderHeader;
        this.orderDetails = orderDetails;
        this.service = service;
    }

    @Override
    public void execute() {
        System.out.println("Command: invio al service la richiesta di creazione...");

        // Passiamo i dati al Service.
        // Il Service salverà l'header, prenderà l'ID, lo metterà nei dettagli e salverà i dettagli.
        // Ci restituisce l'oggetto "Pieno" (con ID e tutto).
        this.savedOrder = service.createOrder(this.orderDetails, this.orderHeader);

        System.out.println("Command: Ordine creato con ID " + savedOrder.getOrder_id());
    }

    @Override
    public void undo() {
        // Se non ho mai salvato nulla (o è fallito), non faccio nulla
        if (savedOrder == null || savedOrder.getOrder_id() == 0) {
            return;
        }

        System.out.println("Command: ANNULLO ordine ID " + savedOrder.getOrder_id());

        // Chiamo il service per cancellare
        service.cancelOrder(savedOrder.getOrder_id());

        // Reset per evitare doppi undo
        this.savedOrder = null;
    }
}