package dao;

import ospedale.struttura.Reparto;
import java.util.List;

/**
 * Interfaccia DAO per la gestione dei Reparti ospedalieri.
 */
public interface RepartoDAO {
    void salva(Reparto reparto);
    void elimina(String nomeReparto);
    Reparto trovaPerNome(String nomeReparto);
    List<Reparto> trovaTutti();
}