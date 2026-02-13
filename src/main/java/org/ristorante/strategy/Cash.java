package org.ristorante.strategy;

public class Cash implements PaymentMethod{

    @Override
    public boolean pay(double amount) {
        System.out.println("Hai scelto pagamento con contanti");
        if(amount < 0){
            return false;
        }
        return true;
    }

    @Override
    public String toString(){
        return "CASH";
    }
}
