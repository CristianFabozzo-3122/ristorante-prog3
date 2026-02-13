package org.ristorante.strategy;

//la strategy si occupa solo del pagamento
//tipo: controllare se la carta e' valida, calcolare il resto...

public interface PaymentMethod {
    //ogni strategy concreta potra' implementare il metodo di pagamento con la propria logica
    //per ora visualizzeranno in modo diverso solo la stampa
    public boolean pay(double amount);
}
