package implementazioneDao;

import dao.LettoDAO;
import ospedale.struttura.Letto;
import database_connection.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementazione JDBC di LettoDAO per PostgreSQL.
 * Gestisce la persistenza dei letti ospedalieri nella tabella Letto.
 */
public class LettoPostgresDAO implements LettoDAO {

    /**
     * Inserisce un nuovo letto nel database.
     * Se il letto esiste gia', l'inserimento viene ignorato.
     *
     * @param letto il letto da salvare
     */
    @Override
    public void salva(Letto letto) {
        String query = "INSERT INTO Letto (codice_identificativo) VALUES (?) ON CONFLICT DO NOTHING";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, letto.getCodiceIdentificativo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina un letto dal database.
     *
     * @param codiceIdentificativo il codice del letto da eliminare
     */
    @Override
    public void elimina(String codiceIdentificativo) {
        String query = "DELETE FROM Letto WHERE codice_identificativo = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, codiceIdentificativo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recupera un letto tramite il suo codice identificativo.
     *
     * @param codiceIdentificativo il codice del letto
     * @return il letto trovato, oppure null se non esiste
     */
    @Override
    public Letto trovaPerCodice(String codiceIdentificativo) {
        String query = "SELECT * FROM Letto WHERE codice_identificativo = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, codiceIdentificativo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Letto(rs.getString("codice_identificativo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Restituisce l'elenco di tutti i letti.
     *
     * @return lista di tutti i letti
     */
    @Override
    public List<Letto> trovaTutti() {
        List<Letto> letti = new ArrayList<>();
        String query = "SELECT * FROM Letto";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                letti.add(new Letto(rs.getString("codice_identificativo")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return letti;
    }

    /**
     * Trova i letti associati a un reparto tramite la relazione con la tabella Stanza.
     *
     * @param nomeReparto il nome del reparto
     * @return lista dei letti del reparto
     */
    @Override
    public List<Letto> trovaLettiPerReparto(String nomeReparto) {
        List<Letto> letti = new ArrayList<>();
        String query = "SELECT l.codice_identificativo FROM Letto l " +
                "JOIN Stanza s ON l.id_stanza = s.num_stanza " +
                "WHERE s.nome_reparto = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nomeReparto);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                letti.add(new Letto(rs.getString("codice_identificativo")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return letti;
    }
}