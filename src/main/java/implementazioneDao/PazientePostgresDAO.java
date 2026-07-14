package implementazioneDao;

import ospedale.clinica.Paziente;
import dao.PazienteDAO;
import database_connection.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementazione JDBC di PazienteDAO per PostgreSQL.
 * Gestisce la persistenza dei pazienti nella tabella Paziente.
 */
public class PazientePostgresDAO implements PazienteDAO {

    /**
     * Inserisce un nuovo paziente nel database.
     *
     * @param paziente il paziente da salvare
     */
    @Override
    public void salva(Paziente paziente) {
        String query = "INSERT INTO Paziente (codice_fiscale, nome, cognome) VALUES (?, ?, ?)";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, paziente.getCodiceFiscale());
            stmt.setString(2, paziente.getNome());
            stmt.setString(3, paziente.getCognome());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Aggiorna nome e cognome di un paziente esistente.
     *
     * @param paziente il paziente con i dati aggiornati
     */
    @Override
    public void aggiorna(Paziente paziente) {
        String query = "UPDATE Paziente SET nome = ?, cognome = ? WHERE codice_fiscale = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, paziente.getNome());
            stmt.setString(2, paziente.getCognome());
            stmt.setString(3, paziente.getCodiceFiscale());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina un paziente dal database.
     *
     * @param codiceFiscale il codice fiscale del paziente da eliminare
     */
    @Override
    public void elimina(String codiceFiscale) {
        String query = "DELETE FROM Paziente WHERE codice_fiscale = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, codiceFiscale);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recupera un paziente tramite il suo codice fiscale.
     *
     * @param codiceFiscale il codice fiscale del paziente
     * @return il paziente trovato, oppure null se non esiste
     */
    @Override
    public Paziente trovaPerCodiceFiscale(String codiceFiscale) {
        String query = "SELECT * FROM Paziente WHERE codice_fiscale = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, codiceFiscale);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Paziente(rs.getString("nome"), rs.getString("cognome"), rs.getString("codice_fiscale"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Restituisce l'elenco di tutti i pazienti registrati.
     *
     * @return lista di tutti i pazienti
     */
    @Override
    public List<Paziente> trovaTutti() {
        List<Paziente> lista = new ArrayList<>();
        String query = "SELECT * FROM Paziente";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Paziente(rs.getString("nome"), rs.getString("cognome"), rs.getString("codice_fiscale")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}