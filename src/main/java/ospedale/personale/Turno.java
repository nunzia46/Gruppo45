package ospedale.personale;

import java.time.LocalDateTime;

public class Turno {
    private LocalDateTime inizio;
    private LocalDateTime fine;

    public Turno(LocalDateTime inizio, LocalDateTime fine){
        this.inizio=inizio;
        this.fine=fine;
    }

    //permettono all'amministratore e all'ospedale di leggere i dati
    public void setInizio(LocalDateTime inizio) {
        this.inizio = inizio;
    }
    public LocalDateTime getInizio() {
        return inizio;
    }

    public void setFine(LocalDateTime fine) {
        this.fine = fine;
    }
    public LocalDateTime getFine() {
        return fine;
    }
}
