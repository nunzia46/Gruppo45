package dao;

import ospedale.utenti.Medico;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Interfaccia DAO per la gestione degli utenti di tipo Medico.
 */
public interface MedicoDAO {
    void salva(Medico medico);
    void aggiorna(Medico medico);
    void elimina(String login);
    Medico trovaPerLogin(String login);
    List<Medico> trovaTutti();

    /**
     * Individua i medici sostitutivi di uno specifico reparto che non hanno turni
     * o interventi assegnati nelle fasce orarie indicate.
     * @param nomeReparto Il reparto in cui cercare i sostituti.
     * @param inizio Inizio del periodo di assenza.
     * @param fine Fine del periodo di assenza.
     * @return Lista di medici disponibili per la sostituzione.
     */
    List<Medico> trovaSostitutiDisponibili(String nomeReparto, LocalDateTime inizio, LocalDateTime fine);
}