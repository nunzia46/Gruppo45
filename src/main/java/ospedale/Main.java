package ospedale;

import ospedale.clinica.Paziente;
import ospedale.clinica.Ricovero;
import ospedale.personale.Turno;
import ospedale.utenti.Amministratore;
import ospedale.struttura.Letto;
import ospedale.utenti.Medico;

import javax.swing.*;
import java.time.LocalDateTime;

    public class Main {
        public static void main(String[] args) {

            // creiamo un amministratore
            Amministratore admin = new Amministratore();
            admin.setLogin("admin01");
            admin.setPassword("password123");

            // creiamo un medico
            Medico medico = new Medico("Marco", "1234");

            // creiamo un paziente
            Paziente p = new Paziente("Mario", "Rossi", "MRRSS987400JQ");

            // creiamo un letto
            Letto l = new Letto("L-101");

            LocalDateTime dataInizio = LocalDateTime.now();
            LocalDateTime dataFine = LocalDateTime.now();

            // creiamo un ricovero
            Ricovero ricovero = new Ricovero(dataInizio, p, l);
            ricovero.setDimissionePrevista(dataFine);

            // l'amministratore registra il ricovero
            admin.registraRicovero(p, l, dataInizio, dataFine);
            // l'amministratore registra l'assenza
            admin.registraAssenza(medico, dataInizio, dataFine);

            // creiamo un turno per il medico
            Turno t1 = new Turno(dataInizio, dataFine);
            medico.addTurno(t1);

            // il medico registra una prestazione
            medico.registraPrestazione(ricovero, dataInizio, dataFine);

            // stampa a schermo
            System.out.println("Sistema Ospedaliero Avviato!");
            System.out.println("Utente loggato: " + admin.getLogin());
            System.out.println("È in corso il ricovero per: " + p.getNome() + " " + p.getCognome());
        }
    }

