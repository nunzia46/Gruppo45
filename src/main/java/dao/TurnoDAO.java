package dao;

import ospedale.personale.Turno;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Interfaccia DAO per la programmazione e il controllo dei Turni lavorativi.
 */
public interface TurnoDAO {
    void salva(Turno turno, String loginMedico);
    void elimina(Turno turno, String loginMedico);
    List<Turno> trovaTurniPerMedico(String loginMedico);

    /**
     * Restituisce i turni lavorativi programmati che ricadono o si sovrappongono
     * al periodo di assenza indicato per un dato medico.
     * @param loginMedico L'identificativo del medico assente.
     * @param inizio Inizio dell'assenza.
     * @param fine Fine dell'assenza.
     * @return Lista di turni rimasti scoperti.
     */
    List<Turno> trovaTurniScoperti(String loginMedico, LocalDateTime inizio, LocalDateTime fine);
}