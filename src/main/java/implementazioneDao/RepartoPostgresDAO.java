package implementazioneDao;

import dao.RepartoDAO;
import ospedale.struttura.Reparto;
import database_connection.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementazione JDBC di RepartoDAO per PostgreSQL.
 * Gestisce la persistenza dei reparti nella tabella Reparto.
 */
public class RepartoPostgresDAO implements RepartoDAO {

    /**
     * Inserisce un nuovo reparto nel database.
     * Se il reparto esiste gia', l'inserimento viene ignorato.
     *
     * @param reparto il reparto da salvare
     */
    @Override
    public void salva(Reparto reparto) {
        String query = "INSERT INTO Reparto (nome) VALUES (?) ON CONFLICT DO NOTHING";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, reparto.getNome());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina un reparto dal database.
     *
     * @param nomeReparto il nome del reparto da eliminare
     */
    @Override
    public void elimina(String nomeReparto) {
        String query = "DELETE FROM Reparto WHERE nome = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nomeReparto);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recupera un reparto tramite il suo nome.
     *
     * @param nomeReparto il nome del reparto
     * @return il reparto trovato, oppure null se non esiste
     */
    @Override
    public Reparto trovaPerNome(String nomeReparto) {
        String query = "SELECT * FROM Reparto WHERE nome = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nomeReparto);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Reparto(rs.getString("nome"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Restituisce l'elenco di tutti i reparti.
     *
     * @return lista di tutti i reparti
     */
    @Override
    public List<Reparto> trovaTutti() {
        List<Reparto> reparti = new ArrayList<>();
        String query = "SELECT * FROM Reparto";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                reparti.add(new Reparto(rs.getString("nome")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reparti;
    }
}