package ospedale.clinica;

import java.time.LocalDateTime;

/**
 * Rappresenta una prestazione medica effettuata durante un ricovero.
 * Contiene inizio, fine, esito e il riferimento al ricovero associato.
 */
public class Prestazione {
    private LocalDateTime inizio;
    private LocalDateTime fine;
    private String esito;
    private Ricovero ricovero;

    /**
     * Costruisce una prestazione con inizio e ricovero specificati.
     *
     * @param inizio   data e ora di inizio
     * @param ricovero il ricovero associato
     */
    public Prestazione(LocalDateTime inizio, Ricovero ricovero) {
        this.inizio = inizio;
        this.ricovero = ricovero;
    }

    /** Imposta la data e ora di inizio della prestazione. */
    public void setInizio(LocalDateTime inizio) {
        this.inizio = inizio;
    }

    /** Restituisce la data e ora di inizio della prestazione. */
    public LocalDateTime getInizio() {
        return inizio;
    }

    /** Imposta la data e ora di fine della prestazione. */
    public void setFine(LocalDateTime fine) {
        this.fine = fine;
    }

    /** Restituisce la data e ora di fine della prestazione. */
    public LocalDateTime getFine() {
        return fine;
    }

    /** Imposta l'esito della prestazione. */
    public void setEsito(String esito) {
        this.esito = esito;
    }

    /** Restituisce l'esito della prestazione. */
    public String getEsito() {
        return esito;
    }

    /** Imposta il ricovero associato alla prestazione. */
    public void setRicovero(Ricovero ricovero) {
        this.ricovero = ricovero;
    }

    /** Restituisce il ricovero associato alla prestazione. */
    public Ricovero getRicovero() {
        return ricovero;
    }
}