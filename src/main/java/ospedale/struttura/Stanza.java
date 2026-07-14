package ospedale.struttura;

import java.util.ArrayList;
import java.util.List;

/**
 * Rappresenta una stanza all'interno di un reparto ospedaliero.
 * Contiene una lista di letti.
 */
public class Stanza {
    private int numStanza;
    private final List<Letto> letti;

    /**
     * Costruisce una stanza con il numero specificato.
     * Inizializza la lista dei letti.
     *
     * @param numStanza il numero della stanza
     */
    public Stanza(int numStanza) {
        this.numStanza = numStanza;
        this.letti = new ArrayList<>();
    }

    /** Imposta il numero della stanza. */
    public void setNumStanza(int numStanza) {
        this.numStanza = numStanza;
    }

    /** Restituisce il numero della stanza. */
    public int getNumStanza() {
        return numStanza;
    }

    /** Restituisce la lista dei letti presenti nella stanza. */
    public List<Letto> getLetti() {
        return letti;
    }

    /** Aggiunge un letto alla stanza. */
    public void addLetto(Letto l) {
        this.letti.add(l);
    }
}
