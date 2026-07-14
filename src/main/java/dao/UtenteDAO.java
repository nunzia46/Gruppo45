package dao;

import ospedale.utenti.Utente;

/**
 * Interfaccia DAO per la gestione dell'entità astratta Utente (Medici e Amministratori).
 * Definisce le operazioni CRUD e i metodi di autenticazione.
 */
public interface UtenteDAO {
    /**
     * Salva un nuovo utente nel database specificandone il ruolo.
     * @param utente L'oggetto Utente (Medico o Amministratore).
     * @param ruolo La stringa che definisce il ruolo ("MEDICO" o "AMMINISTRATORE").
     */
    void salva(Utente utente, String ruolo);

    void aggiorna(Utente utente);

    void elimina(String login);

    Utente trovaPerLogin(String login);

    /**
     * Verifica le credenziali inserite nel LoginFrame.
     * @param login L'username dell'utente.
     * @param password La password dell'utente.
     * @return L'istanza concreta dell'Utente (Amministratore o Medico) se le credenziali sono valide, null altrimenti.
     */
    Utente autentica(String login, String password);
}