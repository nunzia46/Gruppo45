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

/**
 * Implementazione JDBC di AssenzaDAO per PostgreSQL.
 * Gestisce la persistenza dei periodi di assenza nella tabella Assenza.
 */
public class AssenzaPostgresDAO implements AssenzaDAO {

    /**
     * Inserisce un nuovo periodo di assenza per un medico.
     *
     * @param assenza     il periodo di assenza da salvare
     * @param loginMedico il medico assente
     */
    @Override
    public void salva(Assenza assenza, String loginMedico) {
        String query = "INSERT INTO Assenza (login_medico, inizio, fine) VALUES (?, ?, ?)";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, loginMedico);
            stmt.setTimestamp(2, Timestamp.valueOf(assenza.getInizio()));
            stmt.setTimestamp(3, Timestamp.valueOf(assenza.getFine()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina un periodo di assenza specifico di un medico.
     * La chiave di ricerca e' il login e il timestamp di inizio.
     *
     * @param assenza     il periodo di assenza da eliminare
     * @param loginMedico il medico assente
     */
    @Override
    public void elimina(Assenza assenza, String loginMedico) {
        String query = "DELETE FROM Assenza WHERE login_medico = ? AND inizio = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, loginMedico);
            stmt.setTimestamp(2, Timestamp.valueOf(assenza.getInizio()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recupera tutte le assenze di un medico.
     *
     * @param loginMedico l'identificativo del medico
     * @return lista delle assenze del medico
     */
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assenze;
    }
}