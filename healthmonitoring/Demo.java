package healthmonitoring;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Demo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Connection connection = connectToDatabase();

        boolean loggedIn = false;
        while (!loggedIn) {
            System.out.println("1. Create Account");
            System.out.println("2. Log In");
            System.out.print("Enter your choice (1 or 2): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createUserAccount(connection, scanner);
                    break;
                case 2:
                    loggedIn = logIn(connection, scanner);
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1 or 2.");
            }
        }

        scanner.close();
        closeDatabaseConnection(connection);
    }

    private static Connection connectToDatabase() {
        String jdbcURL = "jdbc:postgresql://localhost:5432/Java-Final-Sprint";
        String username = "postgres";
        String password = "Amazing@2334";

        try {
            return DriverManager.getConnection(jdbcURL, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void createUserAccount(Connection connection, Scanner scanner) {
        System.out.println("\nCreate User Account: ");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter access level (1 for read-only, 2 for read-write, etc.): ");
        int accessLevel = scanner.nextInt();

        String sql = "INSERT INTO public.\"User\" (username, password, first_name, last_name, email, access_level) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, firstName);
            preparedStatement.setString(4, lastName);
            preparedStatement.setString(5, email);
            preparedStatement.setInt(6, accessLevel);
            preparedStatement.executeUpdate();
            System.out.println("User account created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean logIn(Connection connection, Scanner scanner) {
        System.out.println("\nLog In: ");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        String sql = "SELECT COUNT(*) FROM public.\"User\" WHERE username = ? AND password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            if (count > 0) {
                System.out.println("Login successful!");
                return true;
            } else {
                System.out.println("Invalid username or password. Please try again.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void closeDatabaseConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}