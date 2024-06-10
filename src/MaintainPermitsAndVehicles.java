import java.math.BigInteger;
import java.sql.*;

public class MaintainPermitsAndVehicles {
	Input input;
	DBManager dbManager;
	
	//Constructor to initialize input and dbManager.
	public MaintainPermitsAndVehicles(Input input, DBManager dbManager) {
		this.input = input;
		this.dbManager = dbManager;
	}
	
	//Method to ask for users input to select operation.
	public void selectMaintainPermitsAndvehiclesOperation() {
		
		System.out.println("Enter choice for reports");
		System.out.println("1. Enter Vehicle");
		System.out.println("2. Update Vehicle");
		System.out.println("3. Delete Vehicle");
		System.out.println("4. Assign Permit");
		System.out.println("5. Update Permit");
		System.out.println("6. Delete Permit");
		System.out.println("7. Update vehicle ownership information");
		System.out.println("8. Add vehicle to a permit");
		System.out.println("9. Delete vehicle from a permit");
		System.out.println("10. Go back to main menu");
		System.out.println();
		
		//Taking users choice.
		int ch = input.getInt();
		
		//Calling the operation based on users choice.
		switch(ch) {
			case 1: 
				enterVehicle();
				break;
			case 2:
				updateVehicle();
				break;
			case 3:
				deleteVehicle();
				break;
			case 4:
				assignPermit();
				break;
			case 5:
				updatePermit();
				break;
			case 6:
				deletePermit();
				break;
			case 7:
				updateVehicleOwnershipInformation();
				break;
			case 8:
				addVehicleToPermit();
				break;
			case 9: 
				deleteVehicleFromPermit();
				break;
			case 10:
				break;
			default:
				System.out.println("Enter choice between 1 and 8");
		}	
	}
	
	//Method to enter vehicle.
	private void enterVehicle() {
		try {	
			//Taking user input.
			System.out.println("Enter Plate Number: ");
			String plate = input.getString();
			System.out.println("Enter Car model: ");
			String model= input.getString();
			System.out.println("Enter the color of the car: ");
			String color = input.getString();
			System.out.println("Enter Manufactorer of the car: ");
			String manf= input.getString();
			System.out.println("Enter the car's year: ");
			int year = input.getInt();
			System.out.println("Enter the Driver Id: ");
			BigInteger driver_id = new BigInteger(input.getString());
	        
			//Checking if vehicle is present.
			String checkPresence = "SELECT * FROM Vehicles where NumberPlate='"+plate+"';";
			ResultSet resultSet = dbManager.executeQuery(checkPresence);
			if(resultSet.next()) {
				System.out.println("vehicle already exists");
				return;
			} System.out.println();
			
			//Executing the insert query.
			String query = "INSERT INTO Vehicles(NumberPlate, Model, Color, Manufacturer, Year, DriverId)  VALUES('"+plate+"','"+model+"','"+color+"','"+manf+"',"+year+","+driver_id+");";
			int rowsAffected = dbManager.executeUpdate(query);
			System.out.println("Number of rows affected are "+rowsAffected);
		  } catch (SQLException e) {
		        e.printStackTrace();
		    }
	}
	
	//Method to update vehicle.
	private void updateVehicle() {
		try
		{
			String plate,model,color, manf;
		    int year;  
		    
		    //taking user input.
	        System.out.println("Enter Plate Number: ");
	        plate = input.getString();
	        System.out.println("Enter Car model: ");
	        model= input.getString();
	        System.out.println("Enter the color of the car: ");
	        color = input.getString();
	        System.out.println("Enter Manufactorer of the car: ");
	        manf= input.getString();
	        System.out.println("Enter the car's year: ");
	        year = input.getInt();
	        
	        //Checking if vehicle exists.
	        String checkPresence = "SELECT * FROM Vehicles where NumberPlate = '"+plate+"';";
	        ResultSet resultSet = dbManager.executeQuery(checkPresence);
	        if(!resultSet.next()) {
	        	System.out.println("No such vehicle found");
	        	return;
	        } System.out.println();
	        
	        //Executing the update query.
	        String query = "UPDATE Vehicles SET Year = "+year+" , Model = '"+model+"' , Color = '"+color+"' , Manufacturer = '"+manf+"' WHERE NumberPlate= '"+plate+"'";
	        int rowsAffected = dbManager.executeUpdate(query);
			System.out.println("Number of rows affected are "+rowsAffected);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	
	//Method to delete vehicle.
	private void deleteVehicle() {
		try
		{   
			//taking user input.
	        System.out.println("Enter Plate Number: ");
	        String plate = input.getString();
	        
	        //checking if vehicle exists.
	        String checkPresence = "SELECT * FROM Vehicles where NumberPlate = '"+plate+"';" ;
	        ResultSet resultSet = dbManager.executeQuery(checkPresence);
	        if(!resultSet.next()) {
	        	System.out.println("No such vehicle found");
	        	return;
	        } System.out.println();
	        
	        //executing query to delete vehicle.
	        String query = "DELETE FROM Vehicles WHERE NumberPlate = '"+plate+"'";
	        int rowsAffected = dbManager.executeUpdate(query);
			System.out.println("Number of rows affected are "+rowsAffected);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}	
	}
	
	//method to assign permit.
	private void assignPermit() {
		try
		{
			dbManager.beginTransaction();// starting transaction
			//taking user input
			System.out.println("Enter permit id");
			String permitId = input.getString();
			System.out.println("Enter permit type");
			String permitType = input.getString();
			System.out.println("Enter start date");
			String startDate = input.getString();
			System.out.println("Enter expire date");
			String expireDate = input.getString();
			System.out.println("Enter expire time");
			String expireTime = input.getString();
			System.out.println("Enter space type");
			String spaceType = input.getString();
			System.out.println("Enter zone Id");
			String zoneId = input.getString();
			System.out.println("Enter lot name");
			String lotName = input.getString();
			System.out.println("Enter vehicle number plate");
			String numberPlate = input.getString();
			System.out.println("Enter driver id");
			BigInteger driverId = new BigInteger(input.getString());
			
			//Check is Permit exists.
			String checkPermitPresence = "SELECT * FROM Permits WHERE PermitId = '"+permitId+"';";
			
			ResultSet resultSet = dbManager.executeQuery(checkPermitPresence);
			if(resultSet.next()) {
				System.out.println("Permit already exists. Rolling back the transaction");
				dbManager.rollbackTransaction(); // roll back the transaction.
				return;
			}
			
			//Checking if driver id is present in drivers table.
			String driverPresence = "SELECT * FROM Drivers WHERE Id = "+driverId+";";
			ResultSet resultSet1 = dbManager.executeQuery(driverPresence);
			if(!resultSet1.next()) {
	        	System.out.println("The given driver does not exist. Please insert driver details using add driver operation first");
	        	System.out.println("Rolling back the transaction");
	        	dbManager.rollbackTransaction();//rollback the transaction.
	        	return;
	        } System.out.println();
	        
	        String checkVehiclePresence = "SELECT * FROM Vehicles WHERE NumberPlate = '"+numberPlate+"';";
	        
	        //Checking if vehicle is present in vehicles table.
	        ResultSet resultSet3 = dbManager.executeQuery(checkVehiclePresence);
	        if(!resultSet3.next()) {
	        	System.out.println("The given vehicle does not exist. Please insert vehicle details using add vehcile operation first");
	        	System.out.println("Rolling back the transaction");
	        	dbManager.rollbackTransaction();//rollback the transaction.
	        		return;
	        } System.out.println();
			
	        //Checking if driver is the owner of the vehicle.
			String checkVehicleDriverPresence = "SELECT * FROM Vehicles WHERE NumberPlate = '"+numberPlate+"' AND DriverId = "+driverId+";";
			ResultSet resultSet2 = dbManager.executeQuery(checkVehicleDriverPresence);
			if(!resultSet2.next()) {
	        	System.out.println("The given driver is not the owner of the vehicle");
	        	System.out.println("Rolling back the transaction");
	        	dbManager.rollbackTransaction(); // roll back the transaction.
	        	return;
	        } System.out.println();
	        
	        //Check the number of current permits.
	        String currentPermitsQuery = "SELECT COUNT(*) AS NumPermits " +
                    "FROM PermitsToVehicles AS PV " +
                    "JOIN Vehicles AS V ON PV.NumberPlate = V.NumberPlate " +
                    "JOIN Permits AS P ON PV.PermitId = P.PermitId " +
                    "WHERE V.DriverId = " + driverId + " " +
                    "  AND (STR_TO_DATE(ExpireDate, '%Y-%m-%d') > CURDATE() " +
                    "OR (STR_TO_DATE(ExpireDate, '%Y-%m-%d') = CURDATE() AND STR_TO_DATE(ExpireTime, '%H:%i:%s') > CURTIME()));";
	        
	        ResultSet resultSet4 = dbManager.executeQuery(currentPermitsQuery);
	        if(resultSet4 == null) {
	        	System.out.println("Error executing query");
	        	System.out.println("Rolling back the transaction");
	        	dbManager.rollbackTransaction();
	        	return;
	        }
	        int numCurPermits = 0;
	        if(resultSet4.next()) {
	        	numCurPermits = resultSet4.getInt("NumPermits");
	        }
	        
	        //Get driver status.
	        String driverStatusQuery = "SELECT Status AS DriverStatus FROM Drivers WHERE Id = "+driverId+";";
	        ResultSet resultSet5 = dbManager.executeQuery(driverStatusQuery);
	     
	        if(resultSet5 == null) {
	        	System.out.println("Error executing query");
	        	System.out.println("Rolling back the transaction");
	        	dbManager.rollbackTransaction(); //rollback the transaction.
	        	return;
	        }
	        String driverStatus = null;
	        if(resultSet5.next()) {
	        	driverStatus = resultSet5.getString("DriverStatus");
	        }
	        
	        //Check max permit limit.
	        if(("V".equals(driverStatus) && numCurPermits == 1)) {
	        	System.out.println("Max permits already exists. Cannot give new Permit");
	        	System.out.println("Rolling back the transaction");
	        	dbManager.rollbackTransaction();
	        	return;
	        }
	    
	        if(("S".equals(driverStatus) && numCurPermits == 1) || ("E".equals(driverStatus) && numCurPermits == 2)) {
	        	if(!permitType.equals("special event")) {
	        		System.out.println("Max permits already exists. Cannot give new Permit");
		        	System.out.println("Rolling back the transaction");
		        	dbManager.rollbackTransaction();
		        	return;
	        	}
	        }
	        
	        //Check if given zone is valid for the driver.
	        if(driverStatus.equals("V") && !zoneId.equals("V")) {
	        	System.out.println("Not allowed to take permit in this zone");
	        	System.out.println("rolling back the transaction");
	        	dbManager.rollbackTransaction();
	        	return;
	        }
	        if(driverStatus.equals("S") && !(zoneId.equals("AS") || zoneId.equals("BS") || zoneId.equals("CS") || zoneId.equals("DS"))) {
	        	System.out.println("Not allowed to take permit in this zone");
	        	System.out.println("rolling back the transaction");
	        	dbManager.rollbackTransaction();
	        	return;
	        }
	        
	        if(driverStatus.equals("E") && !(zoneId.equals("A") || zoneId.equals("B") || zoneId.equals("C") || zoneId.equals("D"))) {
	        	System.out.println("Not allowed to take permit in this zone");
	        	System.out.println("rolling back the transaction");
	        	dbManager.rollbackTransaction();
	        	return;
	        }
	        
	        //Check if there is an available space.
	        int space = -1;
	        String query = "SELECT SpaceNumber AS SpaceNumber FROM Spaces " +
	                "WHERE SpaceType = '" + spaceType + "' AND ZoneId = '"+zoneId+"' AND LotName = '" + lotName + "' " +
	                "AND Avail_Status = 'YES' LIMIT 1";
	        
	        ResultSet resultSet6 = dbManager.executeQuery(query);
	        
	        if(resultSet6 == null) {
	        	System.out.println("Error executing query");
	        	System.out.println("Rolling back the transaction");
	        	dbManager.rollbackTransaction();
	        	return;
	        }
	        if(!resultSet6.next()) {
	        	System.out.println("No available space for the given space type, zone and lot");
	        	System.out.println("Rolling back the transaction");
	        	dbManager.rollbackTransaction();
	        	return;
	        } else {
	        	space = resultSet6.getInt("SpaceNumber");
	        }
	        
	        //Query to uodate the availability status of random space to NO to handle the permit overflow for a zone , space type and parking lot.
	        String query1 = "UPDATE Spaces SET Avail_Status = 'NO' WHERE SpaceNumber = "+space+"  AND ZoneId = '"+zoneId+"' AND LotName = '" + lotName + "';";
	        
	        //Queries to insert into PermitsTovehicles and permits tables.
	        String query2 = "INSERT INTO Permits VALUES('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')";
	        String query3 = "INSERT INTO PermitsToVehicles VALUES('%s' , '%s')";

	        query2 = String.format(query2, permitId, zoneId, lotName, spaceType, permitType, startDate, expireDate, expireTime);
	        query3 = String.format(query3, permitId, numberPlate);
	        
	        //Executing the queries.
	        int ra1 = dbManager.executeUpdate(query2);
	        if(ra1 == -1) {
	        	System.out.println("Error inserting into Permits");
	        	System.out.println("Rolling back the transaction");
	        	dbManager.rollbackTransaction();// roll back the transaction.
	        	return;
	        }
	        
	        int ra2 = dbManager.executeUpdate(query3);
	        if(ra2 == -1) {
	        	System.out.println("Error inserting into PermitsToVehicles");
	        	System.out.println("Rolling back the transaction");
	        	dbManager.rollbackTransaction();//roll back the transaction.
	        	return;
	        }
	        int ra3 = dbManager.executeUpdate(query1);
	        if(ra3 == -1) {
	        	System.out.println("Error updating availability status");
	        	System.out.println("Rolling back the transaction");
	        	dbManager.rollbackTransaction();//roll back the transaction.
	        	return;
	        }
	        
	        System.out.println("Permit added successfully. Commiting the transaction");
	     
	        dbManager.commitTransaction();//commit transaction.
	        
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception occured. Rolling back the transaction.");
			dbManager.rollbackTransaction();//roll back the transaction.
		}
	}
	
	//Method to add vehicle to a permit.
	private void addVehicleToPermit() {
		try {
			//taking user input.
			System.out.println("Enter permit Id");
			String permitId = input.getString();
			
			//Check if permit exists.
			String query = "SELECT * FROM Permits WHERE PermitId = '"+permitId+"';";
			
			ResultSet resultSet = dbManager.executeQuery(query);
			if(!resultSet.next()) {
				System.out.println("Permit does not exist");
				return;
			}
			
			//Get the status of the driver.
			String sql = "SELECT D.Status AS status " +
                    "FROM PermitsToVehicles PV " +
                    "JOIN Vehicles V ON PV.NumberPlate = V.NumberPlate " +
                    "JOIN Drivers D ON V.DriverID = D.Id " +
                    "WHERE PV.PermitId = '" +permitId+"' "+
                    "LIMIT 1";
			
			ResultSet resultSet1 = dbManager.executeQuery(sql);
			String status = null;
			if(resultSet1.next()) {
				status = resultSet1.getString("status");
			}
			
			//Checking max vehicle constraint on a permit based on driver status.
			if(!status.equals("E")) {
				System.out.println("Only 1 vehicle permitted for Students and Visitors. Not allowed to add vehcile");
				return;
			}
			else
			{
				 String q = "select count(*) as vehicleCount FROM PermitsToVehicles as pv JOIN Permits as p ON pv.PermitId = p.PermitId "
				 		+ "WHERE p.PermitId ='"+permitId+"' AND (STR_TO_DATE(p.ExpireDate, '%Y-%m-%d') > CURDATE() "
				 		+ "                    OR (STR_TO_DATE(p.ExpireDate, '%Y-%m-%d') = CURDATE() AND STR_TO_DATE(p.ExpireTime, '%H:%i:%s') > CURTIME()));";
				ResultSet qset = dbManager.executeQuery(q);
				int count = 0;
				if(qset.next()) {
					count = qset.getInt("vehicleCount");
				}
				if(count>1) {System.out.println("Employee already has max vehicles on this permit");return;} 
			

			}
			System.out.println("Enter vehcile Id to add");
			String numberPlate = input.getString();
			
			//Executing the insert query.
			String query1 = "INSERT INTO PermitsToVehicles VALUES('"+permitId+"', '"+numberPlate+"');";
			int rowsAffected = dbManager.executeUpdate(query1);
			System.out.println("Number of rows affected: "+rowsAffected);
			
			resultSet.close();
			resultSet1.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//Method to delete vehicle from a permit.
	private void deleteVehicleFromPermit() {
		try {
			//Take user input
			System.out.println("Enter permit Id");
			String permitId = input.getString();
			
			System.out.println("Enter vehcile Id to delete");
			String numberPlate = input.getString();
			
			//Checking if permit exists.
			String query = "SELECT * FROM Permits WHERE PermitId = '"+permitId+"';";
			
			ResultSet resultSet = dbManager.executeQuery(query);
			if(!resultSet.next()) {
				System.out.println("Permit does not exist");
				return;
			}
			
			//Checking if vehicle is there on permit.
			String sql = "SELECT * FROM PermitsToVehicles WHERE PermitId ='"+permitId+"' AND NumberPlate = '"+numberPlate+"';";
			ResultSet resultSet1 = dbManager.executeQuery(sql);
			if(!resultSet1.next()) {
				System.out.println("The given vehcile is not there on the permit");
				return;
			}
			
			//Counting the number of vehicles on permit.
			String countQuery = "SELECT COUNT(*) AS vehicleCount FROM PermitsToVehicles WHERE PermitId = '" + permitId + "';";
	        ResultSet countResultSet = dbManager.executeQuery(countQuery);
	        int vehicleCount = 0;
	        if (countResultSet.next()) {
	            vehicleCount = countResultSet.getInt("vehicleCount");
	        }

	        // Delete vehicle association
	        String deleteVehicleQuery = "DELETE FROM PermitsToVehicles WHERE PermitId = '" + permitId + "' AND NumberPlate = '" + numberPlate + "';";
	        int rowsAffected = dbManager.executeUpdate(deleteVehicleQuery);
	        System.out.println("Number of rows affected: " + rowsAffected);

	        // If there's only one vehicle, delete the entire permit
	        if (vehicleCount == 1) {
	            String deletePermitQuery = "DELETE FROM Permits WHERE PermitId = '" + permitId + "';";
	            int permitRowsAffected = dbManager.executeUpdate(deletePermitQuery);
	            System.out.println("Permit deleted. Number of rows affected: " + permitRowsAffected);
	        }
			
			resultSet.close();
			resultSet1.close();
			countResultSet.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//Method to update permit.
	private void updatePermit() {
		try
		{
			//take user input.
			System.out.println("Enter permit id");
			String permitId = input.getString();
			System.out.println("Enter permit type");
			String permitType = input.getString();
			System.out.println("Enter start date");
			String startDate = input.getString();
			System.out.println("Enter expire date");
			String expireDate = input.getString();
			System.out.println("Enter expire time");
			String expireTime = input.getString();
			System.out.println("Enter space type");
			String spaceType = input.getString();
			System.out.println("Enter zone Id");
			String zoneId = input.getString();
			System.out.println("Enter lot name");
			String lotName = input.getString();
	        
			//Check if Permit exists.
	        String checkPresence = "SELECT * FROM Permits where PermitId='"+permitId+"';" ;
	        ResultSet resultSet = dbManager.executeQuery(checkPresence);
	        if(!resultSet.next()) {
	        	System.out.println("No such permit found");
	        	return;
	        } System.out.println();
	        
	        //Execute update query.
	       String query = "UPDATE Permits SET ZoneId= '"+zoneId+"', LotName='"+lotName+"' , SpaceType='"+spaceType+"' , PermitType='"+permitType+"' ,StartDate='"+startDate+"' , ExpireDate='"+expireDate+"' , ExpireTime='"+expireTime+"'  WHERE PermitId='"+permitId+"';";
	       int rowsAffected = dbManager.executeUpdate(query);
		   System.out.println("Number of rows affected are "+rowsAffected);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	
	//Method to delete permit.
	private void deletePermit() {
		try
		{
	        dbManager.beginTransaction();//start transaction.
	        System.out.println("Enter permitId: ");
	        String permitId = input.getString();
	        
	        //Check Permit exists.
	        String checkPresence = "SELECT * FROM Permits where PermitId='"+permitId+"';" ;
	        ResultSet resultSet = dbManager.executeQuery(checkPresence);
	        if(!resultSet.next()) {
	        	System.out.println("No such permit found");
	        	dbManager.rollbackTransaction();
	        	return;
	        } System.out.println();
	        //Query to delete from PermitsTovehicles.
	        String query1 = "DELETE FROM PermitsToVehicles WHERE PermitId = '"+permitId+"'";
	        
	        //Query to delte from Permits.
	        String query2 = "DELETE FROM Permits WHERE PermitId = '"+permitId+"'";
	        
	        //Executing the delete queries.
	        int rowsAffected1 = dbManager.executeUpdate(query1);
	        if(rowsAffected1 == -1) {
	        	System.out.println("Error executing the query 1.. Hence rolling back the transaction");
	        	dbManager.rollbackTransaction();
	        	return;
	        }
	        int rowsAffected2 = dbManager.executeUpdate(query2);
	        if(rowsAffected2 == -1) {
	        	System.out.println("Error executing the query 2.. Hence rolling back the transaction");
	        	dbManager.rollbackTransaction();
	        	return;
	        }
			System.out.println("Number of rows affected in PermitToVehicles are "+rowsAffected1);
			System.out.println("Number of rows affected in Permits are "+rowsAffected2);
			System.out.println("Permit deleted successfully. Hence committing the transaction");
			dbManager.commitTransaction();
			resultSet.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println("Exception occured. Rolling back the transaction.");
			dbManager.rollbackTransaction();//roll back the transaction.
		}
		
	}
	
	//Method to update vehicle ownership information.
	private void updateVehicleOwnershipInformation() {
		try
		{
			String plate;
			BigInteger driverId;
	        
			//Take user input.
	        System.out.println("Enter plate number: ");
	        plate = input.getString();
	        
	        System.out.println("Enter driver id: ");
	        driverId = new BigInteger(input.getString());
	        
	        //Check if Vehicle exists.
	        String checkPresence = "SELECT * FROM Vehicles where NumberPlate='"+plate+"';" ;
	        ResultSet resultSet = dbManager.executeQuery(checkPresence);
	        if(!resultSet.next()) {
	        	System.out.println("No such vehicle found");
	        	return;
	        } System.out.println();
	        
	        //Execute update query.
	        String query = "UPDATE Vehicles SET DriverId = "+driverId+" WHERE NumberPlate='"+plate+"'";
	        int rowsAffected = dbManager.executeUpdate(query);
			System.out.println("Number of rows affected are "+rowsAffected);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}
}