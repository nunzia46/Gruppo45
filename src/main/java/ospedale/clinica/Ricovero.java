package ospedale.clinica;

import ospedale.struttura.Letto;
import java.time.LocalDateTime;

/**
 * Rappresenta un ricovero di un paziente.
 * Collega il paziente a un letto e traccia le date di inizio e dimissione.
 */
public class Ricovero {
    private LocalDateTime inizio;
    private LocalDateTime dimissionePrevista;
    private LocalDateTime dimissioneEffettiva;
    private Paziente paziente;
    private Letto letto;

    /**
     * Costruisce un ricovero con inizio, paziente e letto specificati.
     *
     * @param inizio   data e ora di inizio ricovero
     * @param paziente il paziente ricoverato
     * @param letto    il letto assegnato
     */
    public Ricovero(LocalDateTime inizio, Paziente paziente, Letto letto) {
        this.inizio = inizio;
        this.paziente = paziente;
        this.letto = letto;
    }

    /** Imposta la data e ora di inizio del ricovero. */
    public void setInizio(LocalDateTime inizio) {
        this.inizio = inizio;
    }

    /** Restituisce la data e ora di inizio del ricovero. */
    public LocalDateTime getInizio() {
        return inizio;
    }

    /** Imposta la data e ora di dimissione prevista. */
    public void setDimissionePrevista(LocalDateTime dimissionePrevista) {
        this.dimissionePrevista = dimissionePrevista;
    }

    /** Restituisce la data e ora di dimissione prevista. */
    public LocalDateTime getDimissionePrevista() {
        return dimissionePrevista;
    }

    /** Imposta la data e ora di dimissione effettiva. */
    public void setDimissioneEffettiva(LocalDateTime dimissioneEffettiva) {
        this.dimissioneEffettiva = dimissioneEffettiva;
    }

    /** Restituisce la data e ora di dimissione effettiva. */
    public LocalDateTime getDimissioneEffettiva() {
        return dimissioneEffettiva;
    }

    /** Imposta il paziente associato al ricovero. */
    public void setPaziente(Paziente paziente) {
        this.paziente = paziente;
    }

    /** Restituisce il paziente associato al ricovero. */
    public Paziente getPaziente() {
        return paziente;
    }

    /** Imposta il letto associato al ricovero. */
    public void setLetto(Letto letto) {
        this.letto = letto;
    }

    /** Restituisce il letto associato al ricovero. */
    public Letto getLetto() {
        return letto;
    }
}