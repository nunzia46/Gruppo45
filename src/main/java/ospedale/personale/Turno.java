package ospedale.personale;

import java.time.LocalDateTime;

/**
 * Rappresenta un turno di lavoro di un medico.
 * Definito da un intervallo di tempo con inizio e fine.
 */
public class Turno {
    private LocalDateTime inizio;
    private LocalDateTime fine;

    /**
     * Costruisce un turno con inizio e fine specificati.
     *
     * @param inizio data e ora di inizio turno
     * @param fine   data e ora di fine turno
     */
    public Turno(LocalDateTime inizio, LocalDateTime fine) {
        this.inizio = inizio;
        this.fine = fine;
    }

    /** Imposta la data e ora di inizio del turno. */
    public void setInizio(LocalDateTime inizio) {
        this.inizio = inizio;
    }

    /** Restituisce la data e ora di inizio del turno. */
    public LocalDateTime getInizio() {
        return inizio;
    }

    /** Imposta la data e ora di fine del turno. */
    public void setFine(LocalDateTime fine) {
        this.fine = fine;
    }

    /** Restituisce la data e ora di fine del turno. */
    public LocalDateTime getFine() {
        return fine;
    }
}