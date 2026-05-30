package ospedale.clinica;

import ospedale.struttura.Letto;
import java.time.LocalDateTime;

public class Ricovero {
    private LocalDateTime inizio;
    private LocalDateTime dimissionePrevista;
    private LocalDateTime dimissioneEffettiva;
    private Paziente paziente;
    private Letto letto;

    public Ricovero(LocalDateTime inizio, Paziente paziente, Letto letto){
        this.inizio=inizio;
        this.paziente=paziente;
        this.letto=letto;
    }

    public void setInizio(LocalDateTime inizio) {this.inizio = inizio;}
    public LocalDateTime getInizio() {return inizio;}

    public void setDimissionePrevista(LocalDateTime dimissionePrevista) {
        this.dimissionePrevista = dimissionePrevista;
    }
    public LocalDateTime getDimissionePrevista() {
        return dimissionePrevista;
    }

    public void setDimissioneEffettiva(LocalDateTime dimissioneEffettiva) {
        this.dimissioneEffettiva = dimissioneEffettiva;
    }
    public LocalDateTime getDimissioneEffettiva() {
        return dimissioneEffettiva;
    }

    public void setPaziente(Paziente paziente) {this.paziente = paziente;}
    public Paziente getPaziente() {return paziente;}

    public void setLetto(Letto letto) {this.letto = letto;}

    public Letto getLetto() {return letto;}
}