import java.sql.*;
public class Main {
    public static void main(String[] args) {
    	
    	System.out.println("Enter database username:");
        String username = new Input().getString();

        System.out.println("Enter database password:");
        String password = new Input().getString();
        
    	// Creating an instance of DBManager with database connection details
        DBManager dbManager = new DBManager("jdbc:mariadb://classdb2.csc.ncsu.edu:3306/"+username, username, password);
        
        // Creating an instance of Input for user input
        Input input = new Input();

        // Initializing a Connection object to manage the database connection
        Connection connection = null;

        try {
        	//Establishing connection to data base.
            connection = dbManager.createConnection();
            
         // Check if the connection is successful
            if(connection == null) {
                System.out.println("Failed to establish a connection to the database. Please check your credentials or database URL.");
                return; // Exit the program as the attempt to get connection failed.
            }
            
            System.out.println("**** Welcome to Wolf Parking Management System ****");

            int choice = 0;

            //Asking user choice to navigate to sub menu for operations.
            while (choice != 5) {
                System.out.println("Select the type of operation you would like to perform");
                System.out.println("1. Information processing");
                System.out.println("2. Maintaining permits and vehicle information for each driver");
                System.out.println("3. Generating and maintaining citations");
                System.out.println("4. Reports");
                System.out.println("5. Exit");
                System.out.println();

                // Get the user's choice
                choice = input.getInt();

                // Perform the selected operation based on user's choice
                switch (choice) {
                    case 1:
                        InformationProcessing infoPro = new InformationProcessing(input, dbManager);
                        infoPro.selectInformationProcessingOperation();
                        break;
                    case 2:
                        MaintainPermitsAndVehicles mpv = new MaintainPermitsAndVehicles(input, dbManager);
                        mpv.selectMaintainPermitsAndvehiclesOperation();
                        break;
                    case 3:
                        GenerateAndMaintainCitations gmc = new GenerateAndMaintainCitations(input, dbManager);
                        gmc.selectGenerateAndMaintainCitationsOperation();
                        break;
                    case 4:
                        Reports reports = new Reports(input, dbManager);
                        reports.selectReportsOperation();
                        break;
                    case 5:
                        break;
                    default:
                        System.out.println("Choose an option between 1 to 5");
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	// Close the database connection and the scanner for user input
            dbManager.closeConnection();
            input.closeScanner();
        }
    }
}
