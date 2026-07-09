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

public class UtentePostgresDAO implements UtenteDAO {

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

    @Override
    public Utente autentica(String login, String password) {
        String query = "SELECT * FROM Utente WHERE login = ? AND password = ?";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, login);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Le credenziali sono corrette, restituiamo l'oggetto istanziato
                return costruisciUtenteDaResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Credenziali errate o utente non trovato
    }


    private Utente costruisciUtenteDaResultSet(ResultSet rs) throws SQLException {
        String dbLogin = rs.getString("login");
        String dbPass = rs.getString("password");
        String ruolo = rs.getString("ruolo");

        if ("MEDICO".equalsIgnoreCase(ruolo)) {
            // Utilizza il costruttore definito nella tua classe Medico
            return new Medico(dbLogin, dbPass);

        } else if ("AMMINISTRATORE".equalsIgnoreCase(ruolo)) {
            // Utilizza il costruttore vuoto e i setter definiti nella tua classe Amministratore/Utente
            Amministratore admin = new Amministratore();
            admin.setLogin(dbLogin);
            admin.setPassword(dbPass);
            return admin;
        }

        return null;
    }
}