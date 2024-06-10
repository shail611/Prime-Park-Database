import java.sql.*;

public class DBManager {
    private Connection connection;
    private String url;
    private String username;
    private String password;

    // Constructor to initialize the database connection parameters
    public DBManager(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    // Method to create a database connection
    public Connection createConnection() {
        try {
        	// Load the MariaDB JDBC driver
            Class.forName("org.mariadb.jdbc.Driver");
            // Establish a connection to the database
            connection = DriverManager.getConnection(url, username, password);
            if (connection != null) {
                System.out.println("Connected to the database!");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    // Method to close the database connection
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to execute a SELECT query and return a ResultSet
    public ResultSet executeQuery(String query) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null; 
        }
    }

    // Method to execute an UPDATE, INSERT, or DELETE query and return the affected rows
    public int executeUpdate(String query) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; 
        }
    }
    
    // Method to begin a transaction
    public void beginTransaction() {
        try {
            connection.setAutoCommit(false);
            System.out.println("Transaction started.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to commit a transaction
    public void commitTransaction() {
        try {
            connection.commit();
            System.out.println("Transaction committed.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
                System.out.println("Auto-commit mode restored.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to rollback a transaction
    public void rollbackTransaction() {
        try {
            connection.rollback();
            System.out.println("Transaction rolled back.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
                System.out.println("Auto-commit mode restored.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

