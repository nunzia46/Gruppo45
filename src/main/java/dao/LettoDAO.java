package dao;

import ospedale.struttura.Letto;
import java.util.List;

public interface LettoDAO {
    void salva(Letto letto);
    void elimina(String codiceIdentificativo);
    Letto trovaPerCodice(String codiceIdentificativo);
    List<Letto> trovaTutti();

    // Trova tutti i letti associati a un determinato reparto.
    List<Letto> trovaLettiPerReparto(String nomeReparto);
}