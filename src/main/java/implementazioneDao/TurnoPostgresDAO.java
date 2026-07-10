package implementazioneDao;

import dao.TurnoDAO;
import ospedale.personale.Turno;
import database_connection.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TurnoPostgresDAO implements TurnoDAO {

    @Override
    public void salva(Turno turno, String loginMedico) {
        String query = "INSERT INTO Turno (login_medico, inizio, fine) VALUES (?, ?, ?)";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, loginMedico);
            stmt.setTimestamp(2, Timestamp.valueOf(turno.getInizio()));
            stmt.setTimestamp(3, Timestamp.valueOf(turno.getFine()));
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public void elimina(Turno turno, String loginMedico) {
        String query = "DELETE FROM Turno WHERE login_medico = ? AND inizio = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, loginMedico);
            stmt.setTimestamp(2, Timestamp.valueOf(turno.getInizio()));
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public List<Turno> trovaTurniPerMedico(String loginMedico) {
        List<Turno> turni = new ArrayList<>();
        String query = "SELECT inizio, fine FROM Turno WHERE login_medico = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, loginMedico);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                turni.add(new Turno(
                        rs.getTimestamp("inizio").toLocalDateTime(),
                        rs.getTimestamp("fine").toLocalDateTime()));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return turni;
    }

    @Override
    public List<Turno> trovaTurniScoperti(String loginMedico, LocalDateTime inizio, LocalDateTime fine) {
        List<Turno> turni = new ArrayList<>();
        // Un turno è scoperto se si accavalla con il periodo di assenza.
        // Accavallamento: inizio_turno < fine_assenza AND fine_turno > inizio_assenza
        String query = "SELECT inizio, fine FROM Turno WHERE login_medico = ? AND inizio < ? AND fine > ?";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, loginMedico);
            stmt.setTimestamp(2, Timestamp.valueOf(fine));
            stmt.setTimestamp(3, Timestamp.valueOf(inizio));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                turni.add(new Turno(
                        rs.getTimestamp("inizio").toLocalDateTime(),
                        rs.getTimestamp("fine").toLocalDateTime()));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return turni;
    }
}