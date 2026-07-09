package database_connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnessioneDatabase {
    private static ConnessioneDatabase instance;
    private Connection connection;

    private final String url = "jdbc:postgresql://localhost:3467/ospedale";
    private final String username = "postgres";
    private final String password = "pass1234";

    private ConnessioneDatabase() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException ex) {
            throw new SQLException("Driver PostgreSQL non trovato.", ex);
        }
    }

    public static ConnessioneDatabase getInstance() throws SQLException {
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new ConnessioneDatabase();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
