package dao;

import ospedale.personale.Assenza;
import java.util.List;

/**
 * Interfaccia DAO per la registrazione degli stati di "Malattia" / Assenza del personale.
 */
public interface AssenzaDAO {
    /**
     * Registra un nuovo periodo di assenza per un medico.
     * @param assenza L'oggetto assenza contenente data inizio e fine.
     * @param loginMedico L'identificativo del medico.
     */
    void salva(Assenza assenza, String loginMedico);

    void elimina(Assenza assenza, String loginMedico);

    List<Assenza> trovaAssenzePerMedico(String loginMedico);
}