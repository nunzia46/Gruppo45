package dao;

import ospedale.utenti.Medico;
import java.time.LocalDateTime;
import java.util.List;

public interface MedicoDAO {
    void salva(Medico medico);
    void aggiorna(Medico medico);
    void elimina(String login);
    Medico trovaPerLogin(String login);
    List<Medico> trovaTutti();

    // Individua i medici sostitutivi di uno specifico reparto che non hanno turni
    List<Medico> trovaSostitutiDisponibili(String nomeReparto, LocalDateTime inizio, LocalDateTime fine);
}
