package dao;

import ospedale.personale.Assenza;
import java.util.List;

public interface AssenzaDAO {
    // Registra un nuovo periodo di assenza per un medico.
    void salva(Assenza assenza, String loginMedico);

    void elimina(Assenza assenza, String loginMedico);

    List<Assenza> trovaAssenzePerMedico(String loginMedico);
}
