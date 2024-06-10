import java.sql.*;
import java.math.BigInteger;

public class GenerateAndMaintainCitations {
	Input input;
	DBManager dbManager;
	
	//Constructor to initialise input and dbManager.
	public GenerateAndMaintainCitations(Input input, DBManager dbManager) {
		this.input = input;
		this.dbManager = dbManager;
	}
	
	//taking user input for selecting operation.
	public void selectGenerateAndMaintainCitationsOperation() {
		
		System.out.println("Enter choice for reports");
		System.out.println("1. Check if permit is valid.");
		System.out.println("2. create citation");
		System.out.println("3. update citation");
		System.out.println("4. delete citation");
		System.out.println("5. pay citation");
		System.out.println("6. Add appeal");
		System.out.println("7. Update appeal");
		System.out.println("8. Delete appeal");
		System.out.println("9. Go back to main menu");
		System.out.println();
		
		int ch = input.getInt();
		
		//Calling the operation based on user input.
		switch(ch) {
			case 1: 
				checkIfPermitIsValid();
				break;
			case 2:
				createCitation();
				break;
			case 3:
				updateCitation();
				break;
			case 4:
				deleteCitation();
				break;
			case 5:
				payCitation();
				break;
			case 6:
				addAppeal();
				break;
			case 7:
				updateAppeal();
				break;
			case 8:
				deleteAppeal();
				break;
			case 9:
				break;
			default:
				System.out.println("Enter choice between 1 and 9");
		}
	}
	
	//Method to check if permit is valid for a given space type, lot name and zone.
	private void checkIfPermitIsValid() {
		System.out.println("Enter number plate to check permit validity:");
        String numberPlate = input.getString();
        System.out.println("Enter space type:");
        String spaceType = input.getString();
        System.out.println("Enter lot name:");
        String lotName = input.getString();
        System.out.println("Enter zone ID:");
        String zoneId = input.getString();
        try {
        
        	String query = "SELECT CASE WHEN COUNT(*) > 0 THEN 'True' ELSE 'False' END AS IsValidPermit "
        	        + "FROM PermitsToVehicles pv "
        	        + "INNER JOIN Permits p ON pv.PermitId = p.PermitId "
        	        + "INNER JOIN Spaces s ON p.ZoneId = s.ZoneId AND p.LotName = s.LotName "
        	        + "WHERE pv.NumberPlate ='" + numberPlate + "' AND s.SpaceType = '" + spaceType + "' AND s.LotName = '" + lotName + "' "
        	        + "AND s.ZoneId = '" + zoneId + "' AND p.StartDate <= CURDATE() AND "
        	        + "(p.ExpireDate > CURDATE() OR (p.ExpireDate = CURDATE() AND p.ExpireTime > CURTIME()));";

         //Executing the query.
		 ResultSet resultSet = dbManager.executeQuery(query);
		 input.printResultSet(resultSet);
		 
		 resultSet.close();//Closing the resultSet.
        } catch (SQLException e) {
        	e.printStackTrace();
        }
	}
	
	//Method to add Citation.
	private void createCitation() {
		
		try {
			//Taking inputs from user.
			System.out.println("Enter CitationNumber");
			String citationNumber = input.getString();
			System.out.println("Enter Citation Date [ YYYY-MM-DD");
			String citationDate = input.getString();
			System.out.println("Enter Citation Time [HH:MM:SS]");
			String citationTime = input.getString();
			System.out.println("Enter category of violation");
			String category = input.getString();
			System.out.println("Enter Number Plate");
			String plate = input.getString();
			System.out.println("Enter payment status");
			String paymentStatus = input.getString();
			System.out.println("Enter the lot Name in which violation occured");
			String lotName1 = input.getString();
			System.out.println("Is handicap user[yes/no]");
			Boolean isHandicapUser = input.getString().equals("yes")? true:false;
			int fee = 0;
			if(category.equals("Invalid Permit")) {
				fee = 25;
			} else if (category.contentEquals("Expired Permit")) {
				fee = 30;
			} else {
				fee = 40;
			}
			//giving 50% discount to handicap users.
			if(isHandicapUser) {
				fee = (int)(0.5 * fee);
			}
			
			//Checking if citation already exists.
			String query = "SELECT * FROM Citations WHERE CitationNumber = '"+citationNumber+"';";
			ResultSet resultSet = dbManager.executeQuery(query);
			if(resultSet.next()) {
				System.out.println("Citation Number already exists");
				return;
			}
			
			//Executing the insert query.
			String query1 = "INSERT INTO Citations (CitationNumber, CitationDate, CitationTime, Fee, " +
	                "PaymentStatus, Category, NumberPlate, LotName) VALUES ('" + citationNumber + "','" + citationDate + "', '" + citationTime + "'," + fee + ",'" + paymentStatus + "','" + category + "','" + plate + "','" + lotName1 + "');"; 
	         int rowsAffected = dbManager.executeUpdate(query1);
	         System.out.println ("Number of rows which got affected are:" + rowsAffected);
	         resultSet.close();
	            
	    } catch (Exception e) {
	            e.printStackTrace();
	    }
	}
	
	//Method for updating citation.
	private void updateCitation() {
		
		try {
			//Taking input from user.
			System.out.println("Enter CitationNumber to update");
			String citationNumber = input.getString();
			System.out.println("Enter Citation Date [YYYY-MM-DD]");
			String citationDate = input.getString();
			System.out.println("Enter Citation Time [HH:MM:SS]");
			String citationTime = input.getString();
			System.out.println("Enter category of violation");
			String category = input.getString();
			System.out.println("Enter Number Plate");
			String plate = input.getString();
			System.out.println("Enter payment status");
			String paymentStatus = input.getString();
			System.out.println("Enter the lot Name in which violation occurred");
			String lotName1 = input.getString();
			System.out.println("Enter fee");
			int fee = input.getInt();
			
			//Checking if Citation already exists.
			String checkPresence = "SELECT * FROM Citations WHERE CitationNumber = '"+citationNumber+"';";
			
			ResultSet resultSet = dbManager.executeQuery(checkPresence);
			
			if(!resultSet.next()) {
				System.out.println("Citation does not exist");
				return;
			}

			//Executing the update query.
			String query = "UPDATE Citations SET Fee = " + fee + ", CitationDate = '" + citationDate + "', CitationTime = '" + citationTime
	            + "', PaymentStatus = '" + paymentStatus + "', Category = '" + category + "', NumberPlate = '" + plate
	            + "', LotName = '" + lotName1 + "' WHERE CitationNumber = '" + citationNumber + "';";
	        int rowsAffected = dbManager.executeUpdate(query);
	        System.out.println("Number of rows which got affected are:" + rowsAffected);
	        
	        resultSet.close();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	
	//Method to delete citation.
	private void deleteCitation() {
		
		//taking user input.
		System.out.println("Enter citation number to delete the citation:");
        String citationToDelete = input.getString();
		String query = "DELETE FROM Citations WHERE CitationNumber = '" + citationToDelete+"';";
        try {
        	
        	//Checking if Citation exists.
        	String checkPresence = "SELECT * FROM Citations WHERE CitationNumber = '"+citationToDelete+"';";
			ResultSet resultSet = dbManager.executeQuery(checkPresence);
			
			if(!resultSet.next()) {
				System.out.println("Citation does not exist");
				return;
			}
			
			//Executing the delete query.
        	int rowsAffected = dbManager.executeUpdate(query);
	         System.out.println ("Number of rows which got affected are:" + rowsAffected);
	         
	         //Closing the resultSet.
	         resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}
	
	//Method to payCitation.
	private void payCitation() {
		
		//taking user input.
		 System.out.println("Enter citation number to pay:");
         String citationToPay = input.getString();
         
         String query = "UPDATE Citations SET PaymentStatus = 'Complete' WHERE CitationNumber = '" + citationToPay+"';";
	        try {
	        	
	        	//Checking if Citation already exists.
	        	String checkPresence = "SELECT * FROM Citations WHERE CitationNumber = '"+citationToPay+"';";
				ResultSet resultSet = dbManager.executeQuery(checkPresence);
				
				if(!resultSet.next()) {
					System.out.println("Citation does not exist");
					return;
				}
				
				//Executing the Payment query.
	        	int rowsAffected = dbManager.executeUpdate(query);
		        System.out.println ("Number of rows which got affected are:" + rowsAffected);
	        	
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		
	}
	
	//Method to add appeal.
	private void addAppeal() {
		//Taking input from user.
		System.out.println("Enter driver id");
		int driverId = input.getInt();
		System.out.println("Enter citation number");
		String citationNum = input.getString();
		System.out.println("Enter description");
		String description = input.getString();
		System.out.println("Enter status");
		String status = input.getString();
    
		String query = "INSERT INTO Appeals (DriverId, CitationNumber, Description, Status) VALUES (" + driverId + ",'" + citationNum + "','" + description + "','" + status + "')";
		try {
			//Checking if Appeal exists.
			String checkPresence = "SELECT * FROM Appeals WHERE CitationNumber = '"+citationNum+"';";
			ResultSet resultSet = dbManager.executeQuery(checkPresence);
			
			if(resultSet.next()) {
				System.out.println("Citation already exists");
				return;
			}
			
			//Executing the insert query.
			int rowsAffected = dbManager.executeUpdate(query);
			System.out.println ("Number of rows which got affected are:" + rowsAffected);
			
			resultSet.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Method to update appeal.
	private void updateAppeal() {
		 
		try {
			//Taking input from user.
			System.out.println("Enter citation number for which you want to update the appeal");
			String citationNum = input.getString();
			System.out.println("Enter driver id");
			BigInteger driverId = new BigInteger(input.getString());
			System.out.println("Enter description");
			String description = input.getString();
			System.out.println("Enter status");
			String status = input.getString();
			
			//Checking if Appeal exists.
			String checkPresence = "SELECT * FROM Appeals WHERE CitationNumber = '"+citationNum+"';";
			ResultSet resultSet = dbManager.executeQuery(checkPresence);
			
			if(!resultSet.next()) {
				System.out.println("Appeal does not exist");
				return;
			}
		
			//Executing the update query.
			String query = "UPDATE Appeals SET Status = '" + status + "', Description = '" + description + "', DriverId = " + driverId + " WHERE CitationNumber = '" + citationNum + "';";
			int rowsAffected = dbManager.executeUpdate(query);
			System.out.println ("Number of rows which got affected are:" + rowsAffected);
			
			//Closing the resultSet.
			resultSet.close();
	     } catch (Exception e) {
	        e.printStackTrace();
	     }
	}
	
	//Method to delete appeal.
	private void deleteAppeal() {
		 try { 
			 //taking input from user.
			 System.out.println("Enter citation number for which you want to delete the appeal for");
			 String citationToDeleteAppeal = input.getString();
			 
			 //Checking if Appeal exists.
			 String checkPresence = "SELECT * FROM Appeals WHERE CitationNumber = '"+citationToDeleteAppeal+"';";
			 ResultSet resultSet = dbManager.executeQuery(checkPresence);
				
			if(!resultSet.next()) {
				System.out.println("Appeal does not exist");
				return;
			}
				
			 //Executing the delete query.
			 String query = "DELETE FROM Appeals WHERE CitationNumber = '" + citationToDeleteAppeal+"';";
			 
			 int rowsAffected = dbManager.executeUpdate(query);
			 System.out.println ("Number of rows which got affected are:" + rowsAffected);
			 
			 resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
