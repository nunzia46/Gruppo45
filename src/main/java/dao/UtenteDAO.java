package dao;

import ospedale.utenti.Utente;

public interface UtenteDAO {
    //Salva un nuovo utente nel database specificandone il ruolo.
    void salva(Utente utente, String ruolo);

    void aggiorna(Utente utente);

    void elimina(String login);

    Utente trovaPerLogin(String login);

    // Verifica le credenziali inserite nel LoginFrame.
    Utente autentica(String login, String password);
}