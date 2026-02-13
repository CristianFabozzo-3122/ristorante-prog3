package org.ristorante.strategy;

public class Atm implements PaymentMethod{
    @Override
    public boolean pay(double amount) {
        System.out.println("Hai scelto pagamento con bancomat/atm");
        if(amount < 0){
            return false;
        }
        return true;
    }
    @Override
    public String toString(){
        return "ATM";
    }

}
