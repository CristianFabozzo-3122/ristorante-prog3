package org.ristorante.command;

//l'invoker prende il ruolo di chi dice come e quando eseguire i comandi
//serve a snellire il controller. Quest'ultimo deve solo ricevere un evento UI(Fxml)
//raccogliere i dati dalla view/file.xml e costruire il command

import java.util.Stack;

public class CommandInvoker {
    //memorizziamo nello stack (che e' perfetto per rappresentare concetto di ultima azione)
    //oggetti che rispettano il contratto ICommand (se in futuro volessimo aggiungere altre eventi e' comodo)
    //potremmo anche dire CreateOrderCommand, ma e' antipattern

    //sfruttiamo quelle che sono le tipiche proprieta' di uno stack
    //push, pop, isempty...
    private Stack<ICommand> commandHistory = new Stack<>();

    public void executeCommand(ICommand cmd){
        //quindi eseguiamo e registriamo questa richiesta nello stack
        cmd.execute();
        commandHistory.push(cmd);

        System.out.println("L'invoker ha eseguito e memorizzato la richiesta");
        System.out.println("Dimensione stack " + commandHistory.size());
    }

    public void undoLastCommand(){
        if(commandHistory.isEmpty()){
            System.out.println("Niente da annullare");
            return;
        }

        //cacciamo fuori dallo stack l'ultima richiesta
        //tenendola ovviamente in memoria
        //potremmo anche prima visualizzare l'ultimo comando, annullare e poi cacciare dallo stack
        ICommand lastCmd = commandHistory.pop();
        lastCmd.undo();

        System.out.println("L'invoker ha annullato la richiesta");
        System.out.println("Dimensione stack " + commandHistory.size());
    }
}
