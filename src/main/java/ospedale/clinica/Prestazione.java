package ospedale.clinica;
import java.time.LocalDateTime;

public class Prestazione {
    private LocalDateTime inizio;
    private LocalDateTime fine;
    private String esito;
    private Ricovero ricovero;

    public Prestazione(LocalDateTime inizio, Ricovero ricovero){
        this.inizio=inizio;
        this.ricovero=ricovero;
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

    public void setEsito(String esito) {
        this.esito = esito;
    }
    public String getEsito() {
        return esito;
    }

    public void setRicovero(Ricovero ricovero) {
        this.ricovero = ricovero;
    }
    public Ricovero getRicovero() {
        return ricovero;
    }
}
