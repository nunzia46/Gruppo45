package ospedale.personale;

import java.time.LocalDateTime;

/**
 * Rappresenta un periodo di assenza di un medico.
 * Definito da un intervallo di tempo con inizio e fine.
 */
public class Assenza {
    private LocalDateTime inizio;
    private LocalDateTime fine;

    /**
     * Costruisce un'assenza con inizio e fine specificati.
     *
     * @param inizio data e ora di inizio assenza
     * @param fine   data e ora di fine assenza
     */
    public Assenza(LocalDateTime inizio, LocalDateTime fine) {
        this.inizio = inizio;
        this.fine = fine;
    }

    /** Imposta la data e ora di inizio dell'assenza. */
    public void setInizio(LocalDateTime inizio) {
        this.inizio = inizio;
    }

    /** Restituisce la data e ora di inizio dell'assenza. */
    public LocalDateTime getInizio() {
        return inizio;
    }

    /** Imposta la data e ora di fine dell'assenza. */
    public void setFine(LocalDateTime fine) {
        this.fine = fine;
    }

    /** Restituisce la data e ora di fine dell'assenza. */
    public LocalDateTime getFine() {
        return fine;
    }
}