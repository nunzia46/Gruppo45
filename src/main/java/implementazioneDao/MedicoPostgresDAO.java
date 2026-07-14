package implementazioneDao;

import dao.MedicoDAO;
import ospedale.utenti.Medico;
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
 * Implementazione JDBC di MedicoDAO per PostgreSQL.
 * Gestisce la persistenza dei medici nella tabella Utente con ruolo MEDICO.
 */
public class MedicoPostgresDAO implements MedicoDAO {

    /**
     * Inserisce un nuovo medico nella tabella Utente.
     * Il ruolo viene impostato automaticamente a MEDICO.
     * Se il login esiste gia', l'inserimento viene ignorato (ON CONFLICT DO NOTHING).
     *
     * @param medico il medico da salvare
     */
    @Override
    public void salva(Medico medico) {
        String query = "INSERT INTO Utente (login, password, ruolo) VALUES (?, ?, 'MEDICO') ON CONFLICT (login) DO NOTHING";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, medico.getLogin());
            stmt.setString(2, medico.getPassword());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Aggiorna la password di un medico esistente.
     *
     * @param medico il medico con i dati aggiornati
     */
    @Override
    public void aggiorna(Medico medico) {
        String query = "UPDATE Utente SET password = ? WHERE login = ? AND ruolo = 'MEDICO'";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, medico.getPassword());
            stmt.setString(2, medico.getLogin());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina un medico dalla tabella Utente.
     *
     * @param login l'identificativo del medico da eliminare
     */
    @Override
    public void elimina(String login) {
        String query = "DELETE FROM Utente WHERE login = ? AND ruolo = 'MEDICO'";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, login);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recupera un medico tramite il suo login.
     *
     * @param login l'identificativo del medico
     * @return il medico trovato, oppure null se non esiste
     */
    @Override
    public Medico trovaPerLogin(String login) {
        String query = "SELECT login, password FROM Utente WHERE login = ? AND ruolo = 'MEDICO'";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Medico(rs.getString("login"), rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Restituisce l'elenco di tutti i medici registrati.
     *
     * @return lista di tutti i medici
     */
    @Override
    public List<Medico> trovaTutti() {
        List<Medico> medici = new ArrayList<>();
        String query = "SELECT login, password FROM Utente WHERE ruolo = 'MEDICO'";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                medici.add(new Medico(rs.getString("login"), rs.getString("password")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medici;
    }

    /**
     * Trova i medici sostitutivi disponibili in un reparto per un dato intervallo.
     * Vengono esclusi i medici che hanno turni o prestazioni sovrapposte al periodo richiesto.
     *
     * @param nomeReparto il reparto in cui cercare i sostituti
     * @param inizio      l'inizio dell'intervallo temporale
     * @param fine        la fine dell'intervallo temporale
     * @return lista di medici disponibili come sostituti
     */
    @Override
    public List<Medico> trovaSostitutiDisponibili(String nomeReparto, LocalDateTime inizio, LocalDateTime fine) {
        List<Medico> sostituti = new ArrayList<>();
        String query = "SELECT u.login, u.password FROM Utente u " +
                "WHERE u.ruolo = 'MEDICO' AND u.nome_reparto = ? " +
                "AND u.login NOT IN (SELECT login_medico FROM Turno WHERE inizio < ? AND fine > ?) " +
                "AND u.login NOT IN (SELECT login_medico FROM Prestazione WHERE inizio < ? AND fine > ?)";
        try (Connection conn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            Timestamp tFine = Timestamp.valueOf(fine);
            Timestamp tInizio = Timestamp.valueOf(inizio);

            stmt.setString(1, nomeReparto);
            stmt.setTimestamp(2, tFine);
            stmt.setTimestamp(3, tInizio);
            stmt.setTimestamp(4, tFine);
            stmt.setTimestamp(5, tInizio);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                sostituti.add(new Medico(rs.getString("login"), rs.getString("password")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sostituti;
    }

}