import java.sql.*;
public class Reports {
	
	Input input;
	DBManager dbManager;
	
	//constructor to initialize input and dbManager.
	public Reports(Input input, DBManager dbManager) {
		this.input = input;
		this.dbManager = dbManager;
	}
	
	//Method which asks for user input to select operation.
	public void selectReportsOperation() {
		
		System.out.println("Enter choice for reports");
		System.out.println("1. Citations Report");
		System.out.println("2. Number of citations in all zones for time range");
		System.out.println("3. Zone Lot Pairs");
		System.out.println("4. Number of cars in violation");
		System.out.println("5. Number of employee permits in parking zone");
		System.out.println("6. Permit Information");
		System.out.println("7. Available space for space type in parking lot");
		System.out.println("8. Go back to main menu");
		System.out.println();
		
		//Getting user input to select an operation.
		int ch = input.getInt();
		
		//calling the method to execute the selected operation.
		switch(ch) {
			case 1: 
				getCitationsReport();
				break;
			case 2:
				getNumberOfCitationsInAllZonesForTimeRange();
				break;
			case 3:
				getZoneLotPairs();
				break;
			case 4:
				getNumberOfCarsInViolation();
				break;
			case 5:
				getNumberOfEmployeePermitsInParkingZone();
				break;
			case 6:
				getPermitInformation();
				break;
			case 7:
				getAvailableSpaceForSpaceTypeInParkingLot();
				break;
			case 8:
				break;
			default:
				System.out.println("Enter choice between 1 and 8");
		}
		
	}
		
	//Method to get the report of all citations.
	private void getCitationsReport() {
		try {
	    String query = "SELECT CitationNumber, CitationDate, CitationTime, Fee, PaymentStatus, Category, NumberPlate, LotName FROM Citations";
	    ResultSet resultSet = dbManager.executeQuery(query);
	    //calling printResultSet method of Input class to print the ResultSet.
	    input.printResultSet(resultSet); 

	    //closing the resultSet.
	    resultSet.close();
	    } catch (SQLException e) {
	        System.out.println("Error while closing result set: " + e.getMessage());
	    }
	}

	
	//Method to get Number of Citations in all Zones between a given start date and end date.
	private void getNumberOfCitationsInAllZonesForTimeRange() {
	    try {
	        System.out.println("Enter start date (YYYY-MM-DD): ");
	        String startDate = input.getString(); 

	        System.out.println("Enter end date (YYYY-MM-DD): ");
	        String endDate = input.getString(); 

	        String query = "SELECT LotName, COUNT(*) AS TotalCitations FROM Citations " +
	                       "WHERE CitationDate BETWEEN '" + startDate + "' AND '" + endDate + "' " +
	                       "GROUP BY LotName";

	        ResultSet resultSet = dbManager.executeQuery(query);
	        input.printResultSet(resultSet);

	        //closing the resultSet
	        resultSet.close();
	    } catch (SQLException e) {
	        System.out.println("An error occurred while fetching citations for the time range: " + e.getMessage());
	    }
	}

	//Method to get Zone,Lot Pairs.
	private void getZoneLotPairs() {
	    try {
	        String query = "SELECT LotName AS lot, Id AS Zone FROM Zones ORDER BY LotName, Id";
	        ResultSet resultSet = dbManager.executeQuery(query);
	        //Printing the resultSet.
	        input.printResultSet(resultSet);

	        //closing the resultSet.
	        resultSet.close();
	    } catch (SQLException e) {
	        System.out.println("An error occurred while fetching zone-lot pairs: " + e.getMessage());
	    }
	}

	//Method to get the number of cars in violation.
	private void getNumberOfCarsInViolation() {
	    try {
	        String query = "SELECT COUNT(DISTINCT NumberPlate) FROM Citations WHERE PaymentStatus = 'Incomplete'";
	        ResultSet resultSet = dbManager.executeQuery(query);
	        //Printing the resultSet.
	        input.printResultSet(resultSet);

	        //Closing the resultSet.
	        resultSet.close();
	    } catch (SQLException e) {
	        System.out.println("An error occurred while fetching number of cars in violation: " + e.getMessage());
	    }
	}

	//Method to get Number of employee permits in a given parking Zone.
	private void getNumberOfEmployeePermitsInParkingZone() {
	    try {
	    	//Getting input from user.
	        System.out.print("Enter the parking lot name: ");
	        String lotName = input.getString(); 

	        String query = "SELECT COUNT(DISTINCT DriverId) FROM Drivers " +
	                       "JOIN Vehicles ON Drivers.Id = Vehicles.DriverId " +
	                       "NATURAL JOIN PermitsToVehicles " +
	                       "NATURAL JOIN Permits " +
	                       "WHERE ZoneId = 'C' AND Status = 'E' AND lotName = '" + lotName + "'";

	        ResultSet resultSet = dbManager.executeQuery(query);
	        //Printing the resultSet.
	        input.printResultSet(resultSet);

	        //closing the resultset.
	        resultSet.close();
	    } catch (SQLException e) {
	        System.out.println("An error occurred while fetching number of employee permits in parking zone: " + e.getMessage());
	    }
	}

	//Method to get Permit Information.
	private void getPermitInformation() {
	    try {
	    	//Getting input from user.
	        System.out.print("Enter the Driver ID: ");
	        String driverId = input.getString(); 

	        String query = "SELECT PermitId, ZoneId, LotName, SpaceType, PermitType, StartDate, ExpireDate, Expiretime, NumberPlate " +
	                       "FROM Permits " +
	                       "NATURAL JOIN PermitsToVehicles " +
	                       "NATURAL JOIN Vehicles " +
	                       "WHERE DriverId = '" + driverId + "'";

	        //Executing the query.
	        ResultSet resultSet = dbManager.executeQuery(query);
	        //Printing the resultSet.
	        input.printResultSet(resultSet);

	        //Closing the resultSet.
	        resultSet.close();
	    } catch (SQLException e) {
	        System.out.println("An error occurred while fetching permit information: " + e.getMessage());
	    }
	}

	//Method to get Available Space for Space Type In a ParkingLot.
	private void getAvailableSpaceForSpaceTypeInParkingLot() {
	    try {
	    	//getting user input
	        System.out.print("Enter the space type: ");
	        String spaceType = input.getString(); 

	        System.out.print("Enter the parking lot name: ");
	        String lotName = input.getString(); 

	        String query = "SELECT SpaceNumber FROM Spaces " +
	                "WHERE SpaceType = '" + spaceType + "' AND LotName = '" + lotName + "' " +
	                "AND Avail_Status = 'YES' LIMIT 1";

	        //Executing the query.
	        ResultSet resultSet = dbManager.executeQuery(query);
	        
	        //Printing the resultSet.
	        input.printResultSet(resultSet);

	        //Closing the ResultSet.
	        resultSet.close();
	    } catch (SQLException e) {
	        System.out.println("An error occurred while fetching available space for space type in parking lot: " + e.getMessage());
	    }
	}

}
