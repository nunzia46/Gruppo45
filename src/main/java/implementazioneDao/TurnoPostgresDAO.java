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

/**
 * Implementazione JDBC di TurnoDAO per PostgreSQL.
 * Gestisce la persistenza dei turni lavorativi nella tabella Turno.
 */
public class TurnoPostgresDAO implements TurnoDAO {

    /**
     * Inserisce un nuovo turno nel database.
     *
     * @param turno       il turno da salvare
     * @param loginMedico il medico assegnato al turno
     */
    @Override
    public void salva(Turno turno, String loginMedico) {
        String query = "INSERT INTO Turno (login_medico, inizio, fine) VALUES (?, ?, ?)";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, loginMedico);
            stmt.setTimestamp(2, Timestamp.valueOf(turno.getInizio()));
            stmt.setTimestamp(3, Timestamp.valueOf(turno.getFine()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina un turno specifico di un medico.
     * La chiave di ricerca e' il login e il timestamp di inizio.
     *
     * @param turno       il turno da eliminare
     * @param loginMedico il medico assegnato al turno
     */
    @Override
    public void elimina(Turno turno, String loginMedico) {
        String query = "DELETE FROM Turno WHERE login_medico = ? AND inizio = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, loginMedico);
            stmt.setTimestamp(2, Timestamp.valueOf(turno.getInizio()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recupera tutti i turni di un medico.
     *
     * @param loginMedico l'identificativo del medico
     * @return lista dei turni del medico
     */
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return turni;
    }

    /**
     * Trova i turni che si sovrappongono a un periodo di assenza.
     * Un turno e' scoperto se inizio_turno < fine_assenza e fine_turno > inizio_assenza.
     *
     * @param loginMedico l'identificativo del medico
     * @param inizio      l'inizio del periodo di assenza
     * @param fine        la fine del periodo di assenza
     * @return lista dei turni scoperti
     */
    @Override
    public List<Turno> trovaTurniScoperti(String loginMedico, LocalDateTime inizio, LocalDateTime fine) {
        List<Turno> turni = new ArrayList<>();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return turni;
    }
}