package org.ristorante.strategy;

import org.ristorante.model.Role;

import java.util.EnumMap;


/*facotry + strategy
questo file rappresenta l'unico punto di accesso per la creazione delle logiche.
Il factory e' spesso il compagno dello strategy, offre varie potenzialita':
1. La factory si assume la responsabilita' di decidere quale strategy dare al programma in base al ruolo
2. niente dipendency injection, esternamente passiamo una stringa sara' la factory poi a decidere
    si nota la presenza di questa EnumMap (che tale potrebbe essere un hashmap o altre strutture chiave-valore)
    noi diamo la stringa e a quella stringa corrisponde una strategy. Si usa EnumMap per un fatto tecnico
    e' piu veloce, non usa algoritmi complessi di hashing ma usa l'indice.
    passando Role.class si accede proprio a tutti questi vantaggi, in pratica e' come se dessimo un cassetto
    specifico dell'armadio.
3. Se domani dovesse nascere un nuovo ruolo, basta aggiungere una riga qui e un altra costante nell'enum

 */

public class StrategyFactory {
    private final EnumMap<Role, DashboardStrategy> strategyMap;
    public StrategyFactory(){
        //inizializza la mappa per gli Enum
        strategyMap = new EnumMap<>(Role.class);
        strategyMap.put(Role.ADMIN, new AdminStrategy());
        strategyMap.put(Role.WAITER, new WaiterStrategy());
        strategyMap.put(Role.CHEF, new KitchenStrategy());
        strategyMap.put(Role.BARTENDER, new BarStrategy());

        System.out.println("Ho caricato correttamente le strategie");

    }

    //questo modo potrebbe anche decidere di lanciare un errore
    //per esempio "strategy non valida"
    public DashboardStrategy createStrategy(Role role){
        System.out.println("Sto ottenendo la strategy corretta...");
        return strategyMap.get(role);
    }

}
