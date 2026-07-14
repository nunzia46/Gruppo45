package ospedale.utenti;

import ospedale.clinica.Paziente;
import ospedale.clinica.Ricovero;
import ospedale.personale.Assenza;
import ospedale.struttura.Letto;

import java.time.LocalDateTime;

/**
 * Rappresenta un amministratore del sistema ospedaliero.
 * Gestisce la registrazione di ricoveri e assenze.
 */
public class Amministratore extends Utente {

    /**
     * Registra un nuovo ricovero per un paziente.
     *
     * @param paziente il paziente da ricoverare
     * @param letto    il letto assegnato
     * @param inizio   data e ora di inizio ricovero
     * @param fine     data e ora di dimissione prevista
     */
    public void registraRicovero(Paziente paziente, Letto letto, LocalDateTime inizio, LocalDateTime fine) {
        Ricovero ricovero = new Ricovero(inizio, paziente, letto);
        ricovero.setDimissionePrevista(fine);
        paziente.addRicovero(ricovero);
        System.out.println("Ricovero registrato per il paziente:" + paziente.getNome());
    }

    /**
     * Registra un'assenza per un medico.
     *
     * @param medico il medico assente
     * @param inizio data e ora di inizio assenza
     * @param fine   data e ora di fine assenza
     */
    public void registraAssenza(Medico medico, LocalDateTime inizio, LocalDateTime fine) {
        Assenza assenza = new Assenza(inizio, fine);
        medico.addAssenza(assenza);
        System.out.println("Assenza registrata per il medico.");
    }
}