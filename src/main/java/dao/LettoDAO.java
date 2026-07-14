package dao;

import ospedale.struttura.Letto;
import java.util.List;

/**
 * Interfaccia DAO per la gestione dei Letti all'interno delle stanze.
 */
public interface LettoDAO {
    void salva(Letto letto);
    void elimina(String codiceIdentificativo);
    Letto trovaPerCodice(String codiceIdentificativo);
    List<Letto> trovaTutti();

    /**
     * Trova tutti i letti associati a un determinato reparto.
     * @param nomeReparto Il nome del reparto di interesse.
     * @return Lista di letti presenti in quel reparto.
     */
    List<Letto> trovaLettiPerReparto(String nomeReparto);
}