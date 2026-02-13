package org.ristorante.command;

//pattern comportamentale che in questo contesto ci permette di annullare le ultime azioni
//per ogni azione diversa = command diverso
//gli oggetti dello stesso dominio entrano a far parte di una coda
//la traccia chiede esplicitamente di annullare l'ultimo ordine effettuato
//poniamo il caso, piu avanti nel progetto si vuole anche annullare l'ultima modifica, o comunque
//un ultima azione sull'ordine (cancellazione, aggiornamento ...) basterebbe cliccare semplicemente
//tasto annulla (ctrl+z), quel tasto annulla non conosce i dettagli, lui chiama undo() e
//pesca l'ultimo oggetto creato che altro non e'
// che una richiesta. Grazie al polimorfismo verra' tradotto nell'undo giusto
public interface ICommand {
    public void execute();
    public void undo();
}
