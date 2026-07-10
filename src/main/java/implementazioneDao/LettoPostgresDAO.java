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

public class LettoPostgresDAO implements LettoDAO {

    @Override
    public void salva(Letto letto) {
        // N.B: Si presuppone che nel DB la tabella Letto abbia una chiave esterna per la Stanza/Reparto
        String query = "INSERT INTO Letto (codice_identificativo) VALUES (?) ON CONFLICT DO NOTHING";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, letto.getCodiceIdentificativo());
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public void elimina(String codiceIdentificativo) {
        String query = "DELETE FROM Letto WHERE codice_identificativo = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, codiceIdentificativo);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

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
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

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
        } catch (SQLException e) { e.printStackTrace(); }
        return letti;
    }

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
        } catch (SQLException e) { e.printStackTrace(); }
        return letti;
    }
}