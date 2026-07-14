package implementazioneDao;

import dao.UtenteDAO;
import database_connection.ConnessioneDatabase;
import ospedale.utenti.Amministratore;
import ospedale.utenti.Medico;
import ospedale.utenti.Utente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementazione JDBC di UtenteDAO per PostgreSQL.
 * Gestisce la persistenza degli utenti nella tabella Utente.
 * Supporta i ruoli MEDICO e AMMINISTRATORE.
 */
public class UtentePostgresDAO implements UtenteDAO {

    /**
     * Inserisce un nuovo utente nel database con il ruolo specificato.
     * Il ruolo viene convertito in maiuscolo prima dell'inserimento.
     *
     * @param utente l'utente da salvare
     * @param ruolo  il ruolo dell'utente (MEDICO o AMMINISTRATORE)
     */
    @Override
    public void salva(Utente utente, String ruolo) {
        String query = "INSERT INTO Utente (login, password, ruolo) VALUES (?, ?, ?)";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, utente.getLogin());
            stmt.setString(2, utente.getPassword());
            stmt.setString(3, ruolo.toUpperCase());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Aggiorna la password di un utente esistente.
     *
     * @param utente l'utente con i dati aggiornati
     */
    @Override
    public void aggiorna(Utente utente) {
        String query = "UPDATE Utente SET password = ? WHERE login = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, utente.getPassword());
            stmt.setString(2, utente.getLogin());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina un utente dal database.
     *
     * @param login l'identificativo dell'utente da eliminare
     */
    @Override
    public void elimina(String login) {
        String query = "DELETE FROM Utente WHERE login = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, login);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recupera un utente tramite il suo login.
     * L'oggetto restituito e' istanziato in base al ruolo (Medico o Amministratore).
     *
     * @param login l'identificativo dell'utente
     * @return l'utente trovato con il ruolo corretto, oppure null se non esiste
     */
    @Override
    public Utente trovaPerLogin(String login) {
        String query = "SELECT * FROM Utente WHERE login = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return costruisciUtenteDaResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Verifica le credenziali di un utente e restituisce l'oggetto corrispondente.
     *
     * @param login    l'username inserito
     * @param password la password inserita
     * @return l'utente autenticato con il ruolo corretto, oppure null se le credenziali sono errate
     */
    @Override
    public Utente autentica(String login, String password) {
        String query = "SELECT * FROM Utente WHERE login = ? AND password = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, login);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return costruisciUtenteDaResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Costruisce l'oggetto utente corretto in base al ruolo letto dal ResultSet.
     * Se il ruolo e' MEDICO, istanzia Medico tramite costruttore.
     * Se il ruolo e' AMMINISTRATORE, istanzia Amministratore e usa i setter.
     *
     * @param rs il ResultSet posizionato sulla riga da leggere
     * @return l'oggetto Utente costruito, oppure null se il ruolo non e' riconosciuto
     * @throws SQLException in caso di errore di lettura dal ResultSet
     */
    private Utente costruisciUtenteDaResultSet(ResultSet rs) throws SQLException {
        String dbLogin = rs.getString("login");
        String dbPass = rs.getString("password");
        String ruolo = rs.getString("ruolo");

        if ("MEDICO".equalsIgnoreCase(ruolo)) {
            return new Medico(dbLogin, dbPass);

        } else if ("AMMINISTRATORE".equalsIgnoreCase(ruolo)) {
            Amministratore admin = new Amministratore();
            admin.setLogin(dbLogin);
            admin.setPassword(dbPass);
            return admin;
        }

        return null;
    }
}