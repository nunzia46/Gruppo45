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

public class PrestazionePostgresDAO implements PrestazioneDAO {

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
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public void aggiorna(Prestazione prestazione) {
        String query = "UPDATE Prestazione SET fine = ?, esito = ? WHERE inizio = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setTimestamp(1, prestazione.getFine() != null ? Timestamp.valueOf(prestazione.getFine()) : null);
            stmt.setString(2, prestazione.getEsito());
            stmt.setTimestamp(3, Timestamp.valueOf(prestazione.getInizio()));
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public void elimina(Prestazione prestazione) {
        String query = "DELETE FROM Prestazione WHERE inizio = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setTimestamp(1, Timestamp.valueOf(prestazione.getInizio()));
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

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
        } catch (SQLException e) { e.printStackTrace(); }
        return prestazioni;
    }

    @Override
    public List<Prestazione> trovaPrestazioniScoperte(String loginMedico, LocalDateTime inizio, LocalDateTime fine) {
        List<Prestazione> prestazioni = new ArrayList<>();
        // Query di intersezione temporale per le prestazioni (spesso gli interventi non hanno ancora un fine definito se programmati, si usa l'inizio come discriminante)
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
        } catch (SQLException e) { e.printStackTrace(); }
        return prestazioni;
    }
}
