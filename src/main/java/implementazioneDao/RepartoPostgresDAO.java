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

public class RepartoPostgresDAO implements RepartoDAO {

    @Override
    public void salva(Reparto reparto) {
        String query = "INSERT INTO Reparto (nome) VALUES (?) ON CONFLICT DO NOTHING";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, reparto.getNome());
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public void elimina(String nomeReparto) {
        String query = "DELETE FROM Reparto WHERE nome = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nomeReparto);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

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
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

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
        } catch (SQLException e) { e.printStackTrace(); }
        return reparti;
    }
}