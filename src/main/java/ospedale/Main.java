package ospedale;

import ospedale.clinica.Paziente;
import ospedale.clinica.Ricovero;
import ospedale.personale.Turno;
import ospedale.utenti.Amministratore;
import ospedale.struttura.Letto;
import ospedale.utenti.Medico;

import java.time.LocalDateTime;

/**
 * Classe di test per la dimostrazione del modello ospedaliero.
 * Crea istanze di esempio e simula le operazioni principali.
 */
public class Main {

    /**
     * Punto di ingresso per la dimostrazione da console.
     *
     * @param args argomenti da riga di comando
     */
    public static void main(String[] args) {

        Amministratore admin = new Amministratore();
        admin.setLogin("admin01");
        admin.setPassword("password123");

        Medico medico = new Medico("Marco", "1234");

        Paziente p = new Paziente("Mario", "Rossi", "MRRSS987400JQ");

        Letto l = new Letto("L-101");

        LocalDateTime dataInizio = LocalDateTime.now();
        LocalDateTime dataFine = LocalDateTime.now();

        Ricovero ricovero = new Ricovero(dataInizio, p, l);
        ricovero.setDimissionePrevista(dataFine);

        admin.registraRicovero(p, l, dataInizio, dataFine);
        admin.registraAssenza(medico, dataInizio, dataFine);

        Turno t1 = new Turno(dataInizio, dataFine);
        medico.addTurno(t1);

        medico.registraPrestazione(ricovero, dataInizio, dataFine);

        System.out.println("Sistema Ospedaliero Avviato!");
        System.out.println("Utente loggato: " + admin.getLogin());
        System.out.println("È in corso il ricovero per: " + p.getNome() + " " + p.getCognome());
    }
}