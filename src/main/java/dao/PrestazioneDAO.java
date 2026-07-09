package dao;

import ospedale.clinica.Prestazione;
import java.time.LocalDateTime;
import java.util.List;

public interface PrestazioneDAO {
    void salva(Prestazione prestazione, String loginMedico);
    void aggiorna(Prestazione prestazione);
    void elimina(Prestazione prestazione);
    List<Prestazione> trovaPrestazioniPerMedico(String loginMedico);

    // Trova le prestazioni di un medico che rimangono scoperte

    List<Prestazione> trovaPrestazioniScoperte(String loginMedico, LocalDateTime inizio, LocalDateTime fine);
}
