package implementazioneDao;

import dao.PrestazioneDAO;
import ospedale.clinica.Prestazione;
import database_connection.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementazione JDBC di PrestazioneDAO per PostgreSQL.
 * Gestisce la persistenza delle prestazioni cliniche nella tabella Prestazione.
 */
public class PrestazionePostgresDAO implements PrestazioneDAO {

    /**
     * Inserisce una nuova prestazione nel database.
     * Il campo fine puo' essere null per prestazioni non ancora completate.
     *
     * @param prestazione la prestazione da salvare
     * @param loginMedico il medico che ha eseguito la prestazione
     */
    @Override
    public void salva(Prestazione prestazione, String loginMedico) {
        String query = "INSERT INTO Prestazione (inizio, fine, esito, login_medico) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setTimestamp(1, Timestamp.valueOf(prestazione.getInizio()));
            stmt.setTimestamp(2, prestazione.getFine() != null ? Timestamp.valueOf(prestazione.getFine()) : null);
            stmt.setString(3, prestazione.getEsito());
            stmt.setString(4, loginMedico);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Aggiorna fine ed esito di una prestazione esistente.
     * La chiave di ricerca e' il timestamp di inizio.
     *
     * @param prestazione la prestazione con i dati aggiornati
     */
    @Override
    public void aggiorna(Prestazione prestazione) {
        String query = "UPDATE Prestazione SET fine = ?, esito = ? WHERE inizio = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setTimestamp(1, prestazione.getFine() != null ? Timestamp.valueOf(prestazione.getFine()) : null);
            stmt.setString(2, prestazione.getEsito());
            stmt.setTimestamp(3, Timestamp.valueOf(prestazione.getInizio()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina una prestazione dal database.
     * La chiave di ricerca e' il timestamp di inizio.
     *
     * @param prestazione la prestazione da eliminare
     */
    @Override
    public void elimina(Prestazione prestazione) {
        String query = "DELETE FROM Prestazione WHERE inizio = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setTimestamp(1, Timestamp.valueOf(prestazione.getInizio()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recupera tutte le prestazioni di un medico.
     *
     * @param loginMedico l'identificativo del medico
     * @return lista delle prestazioni del medico
     */
    @Override
    public List<Prestazione> trovaPrestazioniPerMedico(String loginMedico) {
        List<Prestazione> prestazioni = new ArrayList<>();
        String query = "SELECT * FROM Prestazione WHERE login_medico = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, loginMedico);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Prestazione p = new Prestazione(rs.getTimestamp("inizio").toLocalDateTime(), null);
                if (rs.getTimestamp("fine") != null) p.setFine(rs.getTimestamp("fine").toLocalDateTime());
                p.setEsito(rs.getString("esito"));
                prestazioni.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestazioni;
    }

    /**
     * Trova le prestazioni programmate di un medico in un dato intervallo.
     * Considera le prestazioni il cui inizio cade tra le date specificate.
     *
     * @param loginMedico l'identificativo del medico
     * @param inizio      l'inizio dell'intervallo
     * @param fine        la fine dell'intervallo
     * @return lista delle prestazioni scoperte nel periodo
     */
    @Override
    public List<Prestazione> trovaPrestazioniScoperte(String loginMedico, LocalDateTime inizio, LocalDateTime fine) {
        List<Prestazione> prestazioni = new ArrayList<>();
        String query = "SELECT * FROM Prestazione WHERE login_medico = ? AND inizio >= ? AND inizio <= ?";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, loginMedico);
            stmt.setTimestamp(2, Timestamp.valueOf(inizio));
            stmt.setTimestamp(3, Timestamp.valueOf(fine));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Prestazione p = new Prestazione(rs.getTimestamp("inizio").toLocalDateTime(), null);
                if (rs.getTimestamp("fine") != null) p.setFine(rs.getTimestamp("fine").toLocalDateTime());
                p.setEsito(rs.getString("esito"));
                prestazioni.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestazioni;
    }
}