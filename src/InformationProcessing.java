import java.sql.*;
import java.math.BigInteger;

public class InformationProcessing {
	Input input;
	DBManager dbManager;
	
	// Constructor to initialize Input and DBManager instances
	public InformationProcessing(Input input, DBManager dbManager) {
		this.input = input;
		this.dbManager = dbManager;
	}
	
	// Method to display and handle information processing operations
	public void selectInformationProcessingOperation() {
		
		// Display menu for information processing operations
		System.out.println("Enter choice for Information Processing");
		System.out.println("1. Add Driver");
		System.out.println("2. Update Driver");
		System.out.println("3. Delete Driver");
		System.out.println("4. Add Parking Lot");
		System.out.println("5. Update Parking Lot");
		System.out.println("6. Delete Parking Lot");
		System.out.println("7. Enter Zone (or) Assign Zone to Lot");
		System.out.println("8. Update Zone");
		System.out.println("9. Delete Zone");
		System.out.println("10. Assign Type to Space");
		System.out.println("11. Add space");
		System.out.println("12. Update space");
		System.out.println("13. Delete space");
		System.out.println("14. Go back to main menu");
		System.out.println();
		
		int ch = input.getInt();
		
		// Perform the selected operation based on user's choice
		switch(ch) {
			case 1: 
				addDriver();
				break;
			case 2:
				updateDriver();
				break;
			case 3:
				deleteDriver();
				break;
			case 4:
				addParkingLot();
				break;
			case 5:
				updateParkingLot();
				break;
			case 6:
				deleteParkingLot();
				break;
			case 7:
				assignZoneToLot();
				break;
			case 8:
				updateZone();
				break;
			case 9:
				deleteZone();
				break;
			case 10:
				assignTypeToSpace();
				break;
			case 11:
				addSpace();
				break;
			case 12:
				updateSpace();
				break;
			case 13:
				deleteSpace();
				break;
			case 14:
				break;
			default:
				System.out.println("Enter choice between 1 and 14");
		}
	}
	
	// Method to add a new driver to the database
	private void addDriver() {
		try {
			System.out.println("Enter Driver Id");
			BigInteger id = new BigInteger(input.getString());
			System.out.println("Enter Driver Name");
			String driverName = input.getString();
			System.out.println("Enter Status");
			String status = input.getString();

			//Validating driver Id length for Students, Employees and Vissitors.
			if ((status.equals("S") || status.equals("E")) && id.toString().length() != 9) {
				System.out.println("Invalid Driver Id");
				return;
			}

			if (status.equals("V") && id.toString().length() != 10) {
				System.out.println("Invalid Driver Id");
				return;
			}
			
			//Checking if Driver already exists or not.
				String query1 = "SELECT * FROM Drivers WHERE Id = "+id+";";
				ResultSet resultSet = dbManager.executeQuery(query1);
				if(resultSet.next()) {
					System.out.println("Driver already exists");
					return;
				}

		    //Inserting into Drivers table.
			String query = "INSERT INTO Drivers (Id, Name, Status) VALUES (" + id + ", '" + driverName + "', '" + status + "');";
	        int rowsUpdated = dbManager.executeUpdate(query);

	        System.out.println("Number of rows updated: " + rowsUpdated);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	//Method to update Driver.
	private void updateDriver() {
		//taking inputs from user.
		System.out.println("Enter Driver Id");
		String driverId = input.getString();
		BigInteger id = new BigInteger(driverId);
		System.out.println("Enter Driver Name");
		String driverName = input.getString();
		System.out.println("Enter Status");
		String status = input.getString();
		
		try {
		// Checking if Driver Id exists or not.
		String query1 = "SELECT * FROM Drivers WHERE Id = "+id+";";
		ResultSet resultSet = dbManager.executeQuery(query1);
		if(!resultSet.next()) {
			System.out.println("Driver does not exist");
			return;
		}
		
		//Exceuting the update.
		String query2 = "UPDATE Drivers SET Name = '" + driverName + "', Status = '" + status +"' WHERE Id = "+driverId+";";
		int rowsUpdated = dbManager.executeUpdate(query2);
        System.out.println("Number of rows updated: "+rowsUpdated);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//Method to delete the Driver.
	private void deleteDriver() {
		//Taking input from user.
		System.out.println("Enter Driver Id");
		String driverId = input.getString();
		BigInteger id = new BigInteger(driverId);
		
		try {
		//checking if Driver exists.
		String query1 = "SELECT * FROM Drivers WHERE Id = "+id+";";
		ResultSet resultSet = dbManager.executeQuery(query1);
		if(!resultSet.next()) {
			System.out.println("Driver does not exist");
			return;
		}
		
		//Executing the delete query.
		String query2 = "DELETE FROM Drivers WHERE Id = "+driverId+";";
		int rowsUpdated = dbManager.executeUpdate(query2);
        System.out.println("Number of rows updated: "+rowsUpdated);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//Method to a parking lot.
	private void addParkingLot() {
		System.out.println("Enter lot Name");
		String name = input.getString();
		System.out.println("Enter Address");
		String address = input.getString();
		
		try {
			//Checking if Parking Lot already exists.
			String query1 = "SELECT * FROM ParkingLot WHERE Name = '"+name+"';";
			ResultSet resultSet = dbManager.executeQuery(query1);
			if(resultSet.next()) {
				System.out.println("Parking Lot already exists");
				return;
			}
			
			//Inserting into the table.
			String query = "INSERT INTO ParkingLot(Name, Address)"
					+ " VALUES('" + name + "', '"+ address+"');";
			int rowsUpdated = dbManager.executeUpdate(query);
			 System.out.println("Number of rows updated: "+rowsUpdated);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Method to update parking lot.
	private void updateParkingLot() {
		//Taking input from user.
		System.out.println("Enter lot Name");
		String name = input.getString();
		System.out.println("Enter Address");
		String address = input.getString();
		
		try {
			//Checking if Parking Lot exists.
			String query1 = "SELECT * FROM ParkingLot WHERE Name = '"+name+"';";
			ResultSet resultSet = dbManager.executeQuery(query1);
			if(!resultSet.next()) {
				System.out.println("Parking Lot does not exist");
				return;
			}
			
			//Executing the update query.
			String query = "UPDATE ParkingLot SET Address = '" + address +"' WHERE Name = '"+name+"';";
			int rowsUpdated = dbManager.executeUpdate(query);
			System.out.println("Number of rows updated: "+rowsUpdated);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Method to delete parking lot.
	private void deleteParkingLot() {
		System.out.println("Enter lot Name");
		String name = input.getString();
		
		try {
			//Checking if parking lot exists.
			String query1 = "SELECT * FROM ParkingLot WHERE Name = '"+name+"';";
			ResultSet resultSet = dbManager.executeQuery(query1);
			if(!resultSet.next()) {
				System.out.println("Parking Lot does not exist");
				return;
			}
			
			//Executing query to delete the Parking Lot.
			String query = "DELETE FROM ParkingLot WHERE Name = '"+name+"';";
			int rowsUpdated = dbManager.executeUpdate(query);
			 System.out.println("Number of rows updated: "+rowsUpdated);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Method to assign Zone to Lot.
	private void assignZoneToLot() {
		//Taking input from user.
		System.out.println("Enter zone Id");
		String zoneId = input.getString();
		
		System.out.println("Enter lot name");
		String lotName = input.getString();
		
		try {
			// Checking if Zone exists.
			String query1 = "SELECT * FROM Zones WHERE Id = '"+zoneId+"' AND LotName = '"+lotName+"';";
			ResultSet resultSet = dbManager.executeQuery(query1);
			if(resultSet.next()) {
				System.out.println("Zone already exist in the given parking lot");
				return;
			}
			
			//Executing the insert query.
			String query = "INSERT INTO Zones (Id, LotName" +
                ") VALUES ('" + zoneId + "','" + lotName +"');"; 
		
        	int rowsUpdated = dbManager.executeUpdate(query);
        	
        	System.out.println("Number of rows updated: "+rowsUpdated);
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}
	
	//Method to Update Zone.
	private void updateZone() {
		//taking Input from user.
		System.out.println("Enter zone Id");
		String zoneId = input.getString();
		
		System.out.println("Enter old lot name");
		String oldLotName = input.getString();
		
		System.out.println("Enter new lot name");
		String newLotName = input.getString();
		
		try {
			//Check if Zone exists.
			String query1 = "SELECT * FROM Zones WHERE Id = '"+zoneId+"' AND LotName = '"+oldLotName+"';";
			ResultSet resultSet = dbManager.executeQuery(query1);
			if(!resultSet.next()) {
				System.out.println("Zone does not exist");
				return;
			}
		
			//Executing the update query.
			String query = "UPDATE Zones SET LotName = '" +newLotName+"' WHERE Id = '"+zoneId+"' AND LotName = '"+oldLotName+"';";
		
        	int rowsUpdated = dbManager.executeUpdate(query);
        	
        	System.out.println("Number of rows updated: "+rowsUpdated);
        } catch (SQLException e) {
        	e.printStackTrace();
        }
	}
	
	//Method to delete zone.
	private void deleteZone() {
		//Taking input from user.
		System.out.println("Enter zone Id");
		String zoneId = input.getString();
		
		System.out.println("Enter lot name");
		String lotName = input.getString();
		
		try {
			//Checking if Zone exists.
			String query1 = "SELECT * FROM Zones WHERE Id = '"+zoneId+"' AND LotName = '"+lotName+"';";
			ResultSet resultSet = dbManager.executeQuery(query1);
			if(!resultSet.next()) {
				System.out.println("Zone does not exist");
				return;
			}
		
			//Executing the delete query.
			String query = "DELETE FROM Zones WHERE LotName = '" +lotName+"' AND Id = '"+zoneId+"';";
		
        	int rowsUpdated = dbManager.executeUpdate(query);
        	
        	System.out.println("Number of rows updated: "+rowsUpdated);
        } catch (SQLException e) {
        	e.printStackTrace();
        }
	}
	
	//Method to assign type to space.
	private void assignTypeToSpace() {
		//taking input from user.
		System.out.println("Enter space Id");
		int spaceId = input.getInt();
		System.out.println("Enter zone Id");
		String zoneId = input.getString();
		System.out.println("Enter lot name");
		String lotName = input.getString();
		System.out.println("Enter new space Type");
		String spaceType = input.getString();
		
		try {
			//Checking if space exists.
			String query1 = "SELECT * FROM Spaces WHERE SpaceNumber = "+spaceId+" AND ZoneId = '"+zoneId+"' AND LotName = '"+lotName+"';";
			ResultSet resultSet = dbManager.executeQuery(query1);
			if(!resultSet.next()) {
				System.out.println("space does not exist");
				return;
			}
			
			//Executing the Update query.
			String query = "UPDATE Spaces SET SpaceType = '" +spaceType+"' WHERE SpaceNumber = "+spaceId+" AND ZoneId = '"+zoneId+"' AND LotName = '"+lotName+"';";
			int rowsUpdated = dbManager.executeUpdate(query);
        	
        	System.out.println("Number of rows updated: "+rowsUpdated);
		} catch (SQLException e) {
        	e.printStackTrace();
        }
	}
	
	//Method to add space.
	private void addSpace() {
		//Taking input from user.
		System.out.println("Enter space Id");
		int spaceId = input.getInt();
		System.out.println("Enter zone Id");
		String zoneId = input.getString();
		System.out.println("Enter lot name");
		String lotName = input.getString();
		System.out.println("Enter new space Type");
		String spaceType = input.getString();
		System.out.println("Enter availability status");
		String avail_status = input.getString();
		
		try {
			//Checking if space exists.
			String query1 = "SELECT * FROM Spaces WHERE SpaceNumber = "+spaceId+" AND ZoneId = '"+zoneId+"' AND LotName = '"+lotName+"';";
			ResultSet resultSet = dbManager.executeQuery(query1);
			if(resultSet.next()) {
				System.out.println("space already exist");
				return;
			}
			
			//Executing the insert query.
			String query = "INSERT INTO Spaces (SpaceNumber, ZoneId, LotName, SpaceType, Avail_Status" +
	                ") VALUES (" + spaceId + ",'" + zoneId +"','"+lotName+"','"+spaceType+"','"+ avail_status+ "');"; 
			int rowsUpdated = dbManager.executeUpdate(query);
        	
        	System.out.println("Number of rows updated: "+rowsUpdated);
		} catch (SQLException e) {
        	e.printStackTrace();
        }
	}
	
	//Method to update space.
	private void updateSpace() {
		//taking input from user.
		System.out.println("Enter space Id");
		int spaceId = input.getInt();
		System.out.println("Enter zone Id");
		String zoneId = input.getString();
		System.out.println("Enter lot name");
		String lotName = input.getString();
		System.out.println("Enter new space Type");
		String spaceType = input.getString();
		System.out.println("Enter availability status");
		String avail_status = input.getString();
		
		try {
			//Checking if space exists.
			String query1 = "SELECT * FROM Spaces WHERE SpaceNumber = "+spaceId+" AND ZoneId = '"+zoneId+"' AND LotName = '"+lotName+"';";
			ResultSet resultSet = dbManager.executeQuery(query1);
			if(!resultSet.next()) {
				System.out.println("space does not exist");
				return;
			}
			
			//Execute the update query.
			String query = "UPDATE Spaces SET SpaceType = '" +spaceType+"', Avail_Status = '"+avail_status +"' WHERE SpaceNumber = "+spaceId+" AND ZoneId = '"+zoneId+"' AND LotName = '"+lotName+"';";
			int rowsUpdated = dbManager.executeUpdate(query);
        	
        	System.out.println("Number of rows updated: "+rowsUpdated);
		} catch (SQLException e) {
        	e.printStackTrace();
        }
	}
	
	//Method to delete space.
	private void deleteSpace() {
		//Taking input from user.
		System.out.println("Enter space Id");
		int spaceId = input.getInt();
		System.out.println("Enter zone Id");
		String zoneId = input.getString();
		System.out.println("Enter lot name");
		String lotName = input.getString();
		
		try {
			//Checking if Space exists.
			String query1 = "SELECT * FROM Spaces WHERE SpaceNumber = "+spaceId+" AND ZoneId = '"+zoneId+"' AND LotName = '"+lotName+"';";
			ResultSet resultSet = dbManager.executeQuery(query1);
			if(!resultSet.next()) {
				System.out.println("space does not exist");
				return;
			}
			//Executing the delete query.
			String query = "DELETE FROM Spaces WHERE SpaceNumber = "+spaceId+" AND ZoneId = '"+zoneId+"' AND LotName = '"+lotName+"';";
			int rowsUpdated = dbManager.executeUpdate(query);
        	
        	System.out.println("Number of rows updated: "+rowsUpdated);
		} catch (SQLException e) {
        	e.printStackTrace();
        }
	}
	
}
