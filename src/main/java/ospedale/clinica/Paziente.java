package ospedale.clinica;

import java.util.ArrayList;
import java.util.List;

/**
 * Rappresenta un paziente dell'ospedale.
 * Mantiene i dati anagrafici e la lista dei ricoveri.
 */
public class Paziente {
    private String nome;
    private String cognome;
    private String codiceFiscale;
    private final List<Ricovero> ricoveri;

    /**
     * Costruisce un paziente con nome, cognome e codice fiscale.
     * Inizializza la lista dei ricoveri.
     *
     * @param nome          il nome del paziente
     * @param cognome       il cognome del paziente
     * @param codiceFiscale il codice fiscale del paziente
     */
    public Paziente(String nome, String cognome, String codiceFiscale) {
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.ricoveri = new ArrayList<>();
    }

    /** Imposta il nome del paziente. */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /** Restituisce il nome del paziente. */
    public String getNome() {
        return nome;
    }

    /** Imposta il cognome del paziente. */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /** Restituisce il cognome del paziente. */
    public String getCognome() {
        return cognome;
    }

    /** Imposta il codice fiscale del paziente. */
    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    /** Restituisce il codice fiscale del paziente. */
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    /** Aggiunge un ricovero alla lista del paziente. */
    public void addRicovero(Ricovero r) {
        this.ricoveri.add(r);
    }

    /** Restituisce i ricoveri del paziente. */
    public List<Ricovero> getRicoveri(){
        return ricoveri;
    }
}