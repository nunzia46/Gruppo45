package dao;

import ospedale.personale.Turno;
import java.time.LocalDateTime;
import java.util.List;

public interface TurnoDAO {
    void salva(Turno turno, String loginMedico);
    void elimina(Turno turno, String loginMedico);
    List<Turno> trovaTurniPerMedico(String loginMedico);

    // Restituisce i turni lavorativi programmati che ricadono o si sovrappongono al periodo di assenza per un medico.
    List<Turno> trovaTurniScoperti(String loginMedico, LocalDateTime inizio, LocalDateTime fine);
}