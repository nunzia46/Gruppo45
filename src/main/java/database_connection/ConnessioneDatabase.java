package database_connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe Singleton per la gestione della connessione al database PostgreSQL.
 * <p>
 * Fornisce un'unica istanza di connessione al database "ospedale" e garantisce
 * che la connessione venga ricreata se risulta chiusa o non inizializzata.
 */
public class ConnessioneDatabase {

    /** Unica istanza della classe (pattern Singleton). */
    private static ConnessioneDatabase instance;

    /** Connessione attiva al database PostgreSQL. */
    private final Connection connection;

    /**
     * Costruttore privato che inizializza il driver PostgreSQL e stabilisce
     * la connessione al database.
     *
     * @throws SQLException se il driver non viene trovato o la connessione fallisce
     */
    private ConnessioneDatabase() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/ospedale";
            String username = "postgres";
            String password = "pass1234";
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException ex) {
            throw new SQLException("Driver PostgreSQL non trovato.", ex);
        }
    }

    /**
     * Restituisce l'unica istanza della classe {@code ConnessioneDatabase}.
     * <p>
     * Se l'istanza non esiste o la connessione associata è chiusa, ne viene creata una nuova.
     *
     * @return l'istanza Singleton di {@code ConnessioneDatabase}
     * @throws SQLException in caso di errore durante la creazione della connessione
     */
    public static ConnessioneDatabase getInstance() throws SQLException {
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new ConnessioneDatabase();
        }
        return instance;
    }

    /**
     * Restituisce l'oggetto {@link Connection} attualmente in uso.
     *
     * @return la connessione al database
     */
    public Connection getConnection() {
        return connection;
    }
}