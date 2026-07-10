package implementazioneDao;

import dao.AssenzaDAO;
import ospedale.personale.Assenza;
import database_connection.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AssenzaPostgresDAO implements AssenzaDAO {

    @Override
    public void salva(Assenza assenza, String loginMedico) {
        String query = "INSERT INTO Assenza (login_medico, inizio, fine) VALUES (?, ?, ?)";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, loginMedico);
            stmt.setTimestamp(2, Timestamp.valueOf(assenza.getInizio()));
            stmt.setTimestamp(3, Timestamp.valueOf(assenza.getFine()));
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public void elimina(Assenza assenza, String loginMedico) {
        String query = "DELETE FROM Assenza WHERE login_medico = ? AND inizio = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, loginMedico);
            stmt.setTimestamp(2, Timestamp.valueOf(assenza.getInizio()));
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public List<Assenza> trovaAssenzePerMedico(String loginMedico) {
        List<Assenza> assenze = new ArrayList<>();
        String query = "SELECT inizio, fine FROM Assenza WHERE login_medico = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, loginMedico);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                assenze.add(new Assenza(
                        rs.getTimestamp("inizio").toLocalDateTime(),
                        rs.getTimestamp("fine").toLocalDateTime()));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return assenze;
    }
}
