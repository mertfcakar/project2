import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Manages the database connection
class DatabaseConnectionManager {
    private Connection connection; // Holds the active database connection

    // Constructor establishes a connection when the object is created
    public DatabaseConnectionManager(String dbURL, String dbUsername, String dbPassword) {
        try {
            connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword); // Connects to the database
            System.out.println("Database connection established successfully.");
        } catch (SQLException e) { // Handles errors during connection
            System.err.println("Error establishing database connection: " + e.getMessage());
        }
    }

    // Returns the current database connection
    public Connection getConnection() {
        return connection;
    }

    // Closes the active database connection
    public void closeConnection() {
        if (connection != null) { // Ensures there is an active connection
            try {
                connection.close(); // Closes the connection
                System.out.println("Database connection closed successfully.");
            } catch (SQLException e) { // Handles errors during closure
                System.err.println("Error closing database connection: " + e.getMessage());
            }
        }
    }
}

// Authenticates users by validating credentials against the database
class AuthenticationSystem {
    private Connection dbConnection; // Database connection provided during initialization

    // Constructor receives an active database connection
    public AuthenticationSystem(Connection dbConnection) {
        this.dbConnection = dbConnection; // Assigns the connection to the class variable
    }

    // Authenticates a user by username and password
    public boolean authenticate(String username, String password) {
        String query = "SELECT role, name, surname FROM employees WHERE username = ? AND password = ?"; // SQL query for validation
        try {
            PreparedStatement stmt = dbConnection.prepareStatement(query); // Prepares the query
            stmt.setString(1, username); // Sets the username parameter
            stmt.setString(2, password); // Sets the password parameter
            ResultSet rs = stmt.executeQuery(); // Executes the query and gets the results

            if (rs.next()) { // If a matching record is found
                String role = rs.getString("role"); // Retrieves the user's role
                String name = rs.getString("name"); // Retrieves the user's name
                String surname = rs.getString("surname"); // Retrieves the user's surname
                System.out.println("Login successful!");
                System.out.println("Welcome, " + name + " " + surname + " (" + role + ")"); // Personalized welcome message
                return true; // Returns true to indicate successful authentication
            } else {
                System.out.println("Invalid username or password."); // Message for invalid credentials
                return false; // Returns false to indicate failed authentication
            }
        } catch (SQLException e) { // Handles SQL-related errors
            System.err.println("Error during authentication: " + e.getMessage());
            return false; // Returns false if an error occurs
        }
    }
}

// Main class to execute the program
// Please integrate into main whoever is responsible
public class Main {
    public static void main(String[] args) {
        // Database connection details
        String dbURL = "jdbc:mysql://localhost:3306/FirmManagement"; // Database URL
        String dbUsername = "root"; // Database username
        String dbPassword = "password"; // Database password

        // Step 1: Initialize the DatabaseConnectionManager
        DatabaseConnectionManager dbManager = new DatabaseConnectionManager(dbURL, dbUsername, dbPassword);

        // Step 2: Get the database connection
        Connection connection = dbManager.getConnection();

        if (connection != null) { // Ensures the connection is established
            // Step 3: Initialize the AuthenticationSystem
            AuthenticationSystem authSystem = new AuthenticationSystem(connection);

            // Step 4: Simulate login
            String username = "manager1"; // Example username
            String password = "defaultPassword1"; // Example password

            // Step 5: Authenticate the user
            boolean isAuthenticated = authSystem.authenticate(username, password);

            if (isAuthenticated) { // If authentication is successful
                System.out.println("Proceeding to the system...");
            } else { // If authentication fails
                System.out.println("Access denied.");
            }

            // Step 6: Close the database connection
            dbManager.closeConnection();
        } else {
            System.err.println("Failed to establish a database connection. Exiting."); // Error message for failed connection
        }
    }
}
