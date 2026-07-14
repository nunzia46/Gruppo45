package ospedale.utenti;

import ospedale.clinica.Prestazione;
import ospedale.clinica.Ricovero;
import ospedale.personale.Assenza;
import ospedale.personale.Turno;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Rappresenta un medico dell'ospedale.
 * Estende Utente e gestisce turni, prestazioni e assenze.
 */
public class Medico extends Utente {

    private final List<Turno> turni;
    private final List<Prestazione> prestazioni;
    private final List<Assenza> assenze;

    /**
     * Costruisce un medico con le credenziali specificate.
     * Inizializza le liste di turni, prestazioni e assenze.
     *
     * @param login    il nome utente
     * @param password la password
     */
    public Medico(String login, String password) {
        super(login, password);
        this.turni = new ArrayList<>();
        this.prestazioni = new ArrayList<>();
        this.assenze = new ArrayList<>();
    }

    /**
     * Registra una nuova prestazione su un ricovero.
     *
     * @param ricovero il ricovero associato
     * @param inizio   data e ora di inizio
     * @param fine     data e ora di fine
     */
    public void registraPrestazione(Ricovero ricovero, LocalDateTime inizio, LocalDateTime fine) {
        Prestazione prestazione = new Prestazione(inizio, ricovero);
        prestazione.setFine(fine);
        this.prestazioni.add(prestazione);
        System.out.println("Prestazione Registrata");
    }

    /** Restituisce la lista dei turni del medico. */
    public List<Turno> getTurni() {
        return turni;
    }

    /** Restituisce la lista delle prestazioni del medico. */
    public List<Prestazione> getPrestazioni() {
        return prestazioni;
    }

    /** Restituisce la lista delle assenze del medico. */
    public List<Assenza> getAssenze() {
        return assenze;
    }

    /** Aggiunge un turno alla lista. */
    public void addTurno(Turno t) {
        this.turni.add(t);
    }

    /** Aggiunge una prestazione alla lista. */
    public void addPrestazioni(Prestazione p) {
        this.prestazioni.add(p);
    }

    /** Aggiunge un'assenza alla lista. */
    public void addAssenza(Assenza a) {
        this.assenze.add(a);
    }
}