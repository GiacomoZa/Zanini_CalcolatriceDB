package app.form.Calcolatrice;

import java.sql.*;

import static java.sql.DriverManager.getConnection;

public class Database {
    private Connection conn;

    public Database(String host, String DBname, String user, String password) {
        String connectionUrl = "jdbc:mysql://" + host + ":3306/" + DBname;

        try {
            conn = getConnection(connectionUrl, user, password);
            if (conn != null)
                System.out.println("Connessione avvenuta");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnessione() {
        System.out.println("Connessione restituita: " + conn);
        return conn;
    }

    public boolean login(String username, String password) {
        try {
            String query = "SELECT * FROM utente WHERE nome = ? AND password = ?";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, username);
                statement.setString(2, password);

                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean registrazione(String username, String password) {
        try {
            String query = "INSERT INTO utente (nome, password) VALUES (?, ?)";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, username);
                statement.setString(2, password);

                int rowsAffected = statement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}