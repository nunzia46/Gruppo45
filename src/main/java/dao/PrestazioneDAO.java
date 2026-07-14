package dao;

import ospedale.clinica.Prestazione;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Interfaccia DAO per le Prestazioni (visite o interventi chirurgici).
 */
public interface PrestazioneDAO {
    void salva(Prestazione prestazione, String loginMedico);
    void aggiorna(Prestazione prestazione);
    void elimina(Prestazione prestazione);
    List<Prestazione> trovaPrestazioniPerMedico(String loginMedico);

    /**
     * Trova le prestazioni (interventi programmati) di un medico che rimangono scoperte
     * a causa di un periodo di assenza.
     * @param loginMedico Il medico assente.
     * @param inizio Inizio dell'assenza.
     * @param fine Fine dell'assenza.
     * @return Lista di prestazioni scoperte.
     */
    List<Prestazione> trovaPrestazioniScoperte(String loginMedico, LocalDateTime inizio, LocalDateTime fine);
}
