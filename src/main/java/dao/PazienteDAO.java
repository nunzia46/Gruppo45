package dao;

import ospedale.clinica.Paziente;
import java.util.List;

/**
 * Interfaccia DAO per la gestione dell'entità Paziente.
 * Definisce le operazioni CRUD standard.
 */
public interface PazienteDAO {
    /**
     * Salva un nuovo paziente nel database o lo aggiorna se già esiste.
     * @param paziente Il paziente da salvare.
     */
    void salva(Paziente paziente);

    /**
     * Aggiorna i dati di un paziente esistente.
     * @param paziente Il paziente con i dati aggiornati.
     */
    void aggiorna(Paziente paziente);

    /**
     * Elimina un paziente dal database tramite il suo codice fiscale.
     * @param codiceFiscale Il codice fiscale del paziente da eliminare.
     */
    void elimina(String codiceFiscale);

    /**
     * Trova un paziente specifico tramite il suo codice fiscale.
     * @param codiceFiscale Il codice fiscale da cercare.
     * @return L'oggetto Paziente se trovato, null altrimenti.
     */
    Paziente trovaPerCodiceFiscale(String codiceFiscale);

    /**
     * Restituisce la lista di tutti i pazienti registrati.
     * @return Lista di pazienti.
     */
    List<Paziente> trovaTutti();
}