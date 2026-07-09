package dao;

import ospedale.struttura.Reparto;
import java.util.List;

public interface RepartoDAO {
    void salva(Reparto reparto);
    void elimina(String nomeReparto);
    Reparto trovaPerNome(String nomeReparto);
    List<Reparto> trovaTutti();
}