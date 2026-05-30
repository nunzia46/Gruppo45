package ospedale.personale;

import java.time.LocalDateTime;

public class Assenza {
    private LocalDateTime inizio;
    private LocalDateTime fine;

    public Assenza(LocalDateTime inizio, LocalDateTime fine){
        this.inizio=inizio;
        this.fine=fine;
    }

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
