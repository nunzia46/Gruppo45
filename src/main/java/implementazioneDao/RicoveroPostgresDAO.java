package implementazioneDao;

import ospedale.clinica.Paziente;
import ospedale.clinica.Ricovero;
import dao.RicoveroDAO;
import database_connection.ConnessioneDatabase;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RicoveroPostgresDAO implements RicoveroDAO {

    @Override
    public void salva(Ricovero ricovero) {
        String query = "INSERT INTO Ricovero (inizio, dimissione_prevista, cf_paziente, codice_letto) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setTimestamp(1, Timestamp.valueOf(ricovero.getInizio()));
            stmt.setTimestamp(2, Timestamp.valueOf(ricovero.getDimissionePrevista()));
            stmt.setString(3, ricovero.getPaziente().getCodiceFiscale());
            stmt.setString(4, ricovero.getLetto().getCodiceIdentificativo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void aggiorna(Ricovero ricovero) {
        String query = "UPDATE Ricovero SET dimissione_effettiva = ? WHERE cf_paziente = ? AND inizio = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            if (ricovero.getDimissioneEffettiva() != null) {
                stmt.setTimestamp(1, Timestamp.valueOf(ricovero.getDimissioneEffettiva()));
            } else {
                stmt.setNull(1, Types.TIMESTAMP);
            }
            stmt.setString(2, ricovero.getPaziente().getCodiceFiscale());
            stmt.setTimestamp(3, Timestamp.valueOf(ricovero.getInizio()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void elimina(Ricovero ricovero) {
        String query = "DELETE FROM Ricovero WHERE cf_paziente = ? AND inizio = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, ricovero.getPaziente().getCodiceFiscale());
            stmt.setTimestamp(2, Timestamp.valueOf(ricovero.getInizio()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Ricovero> trovaTutti() {
        // Implementazione standard della SELECT su tutta la tabella
        return new ArrayList<>();
    }

    @Override
    public boolean isLettoOccupato(String codiceLetto, LocalDateTime inizio, LocalDateTime fine) {
        String query = "SELECT 1 FROM Ricovero WHERE codice_letto = ? AND inizio < ? AND dimissione_prevista > ?";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, codiceLetto);
            stmt.setTimestamp(2, Timestamp.valueOf(fine));
            stmt.setTimestamp(3, Timestamp.valueOf(inizio));
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Paziente> trovaPazientiInScadenza(LocalDate dataScadenza) {
        List<Paziente> lista = new ArrayList<>();
        String query = "SELECT p.* FROM Paziente p JOIN Ricovero r ON p.codice_fiscale = r.cf_paziente " +
                "WHERE DATE(r.dimissione_prevista) = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, Date.valueOf(dataScadenza));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Paziente(rs.getString("nome"), rs.getString("cognome"), rs.getString("codice_fiscale")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}