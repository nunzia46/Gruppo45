package ospedale;

import ospedale.clinica.Paziente;
import ospedale.utenti.Amministratore;
import ospedale.struttura.Letto;

import javax.swing.*;
import java.time.LocalDateTime;

    public class Main {
        public static void main(String[] args) {

            // creiamo un amministratore
            Amministratore admin = new Amministratore();
            admin.setLogin("admin01");
            admin.setPassword("password123");

            // creiamo un paziente
            Paziente p = new Paziente("Mario", "Rossi");

            // creiamo un letto
            Letto l = new Letto("L-101");

            LocalDateTime dataInizio = LocalDateTime.now();
            LocalDateTime dataFine = LocalDateTime.now();


            // l'amministratore registra il ricovero
            admin.registraRicovero(p, l, dataFine, dataFine);

            // stampa a schermo
            System.out.println("Sistema Ospedaliero Avviato!");
            System.out.println("Utente loggato: " + admin.getLogin());
            System.out.println("È in corso il ricovero per: " + p.getNome() + " " + p.getCognome());
        }
    }

