import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;


public class Input {
    private Scanner scanner;

    // Constructor to initialize the Scanner for input
    public Input() {
        scanner = new Scanner(System.in);
    }

    // Method to get an integer input from the user
    public int getInt() {
        int number = 0;
        boolean isValid = false;
        
        do {
            try {
                number = scanner.nextInt();
                scanner.nextLine();
                isValid = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.next(); // Clear the invalid input from the buffer
            }
        } while (!isValid);
        
        return number;
    }

    // Method to get a string input from the user
    public String getString() {
        String input = scanner.nextLine();
        return input.trim();
    }
    

    // Method to get a double input from the user
    public double getDouble() {
        double dbl = 0.0;
        boolean isValid = false;
        
        do {
            try {
                dbl = scanner.nextDouble();
                scanner.nextLine();
                isValid = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a double.");
                scanner.next(); 
            }
        } while (!isValid);
        
        return dbl;
    }
    
    // Method to print a ResultSet in tabular format
    public void printResultSet(ResultSet resultSet) {
    	
    	if (resultSet == null) {
            System.out.println("ResultSet is null");
            return;
        }

        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columns = metaData.getColumnCount();

            // Print column headers
            for (int i = 1; i <= columns; i++) {
                String columnName = metaData.getColumnName(i);
                System.out.printf("%-25s", columnName);
            }
            System.out.println();

            // Print a line separator
            for (int i = 1; i <= columns; i++) {
                System.out.print("__________________________");
            }
            System.out.println();

            // Print rows
            while (resultSet.next()) {
                for (int i = 1; i <= columns; i++) {
                    String value = resultSet.getString(i);
                    System.out.printf("%-25s", value);
                }
                System.out.println();
            }
            System.out.println();
            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to close the Scanner
    public void closeScanner() {
        scanner.close();
    }
}
