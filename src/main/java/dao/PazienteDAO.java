package dao;

import ospedale.clinica.Paziente;
import java.util.List;

public interface PazienteDAO {

    // Salva un nuovo paziente nel database o lo aggiorna se già esiste
    void salva(Paziente paziente);

    // Aggiorna i dati di un paziente esistente
    void aggiorna(Paziente paziente);

    // Elimina un paziente dal database tramite il suo codice fiscale.
    void elimina(String codiceFiscale);

    // Trova un paziente specifico tramite il suo codice fiscale.
    Paziente trovaPerCodiceFiscale(String codiceFiscale);

    // Restituisce la lista di tutti i pazienti registrati.
    List<Paziente> trovaTutti();
}
