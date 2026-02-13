package org.ristorante.strategy;

public class Card implements PaymentMethod{

    @Override
    public boolean pay(double amount) {
        System.out.println("Hai scelto pagamento con carta");
        if(amount < 0){
            return false;
        }
        return true;
    }
    @Override
    public String toString(){
        return "CARD";
    }
}
