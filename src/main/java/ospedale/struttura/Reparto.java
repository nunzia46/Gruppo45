package ospedale.struttura;

import ospedale.utenti.Medico;

import java.util.ArrayList;
import java.util.List;

/**
 * Rappresenta un reparto ospedaliero.
 * Contiene stanze, letti e medici assegnati.
 */
public class Reparto {
    private String nome;
    private List<Stanza> stanze;
    private List<Medico> medici;

    /**
     * Costruisce un reparto con il nome specificato.
     * Inizializza le liste di stanze e medici.
     *
     * @param nome il nome del reparto
     */
    public Reparto(String nome) {
        this.nome = nome;
        this.stanze = new ArrayList<>();
        this.medici = new ArrayList<>();
    }

    /** Imposta il nome del reparto. */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /** Restituisce il nome del reparto. */
    public String getNome() {
        return nome;
    }

    /** Restituisce la lista delle stanze del reparto. */
    public List<Stanza> getStanze() {
        return stanze;
    }

    /** Restituisce la lista dei medici del reparto. */
    public List<Medico> getMedici() {
        return medici;
    }

    /** Aggiunge una stanza al reparto. */
    public void addStanza(Stanza s) {
        this.stanze.add(s);
    }

    /** Aggiunge un medico al reparto. */
    public void addMedico(Medico m) {
        this.medici.add(m);
    }
}