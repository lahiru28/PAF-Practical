package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Hospital {

	//A common method to connect to the DB 
		private Connection connect() {
			Connection con = null;
			
			try {
				 Class.forName("com.mysql.jdbc.Driver");
				 //Provide the correct details: DBServer/DBName, username, password 
				 //con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hosptal", "root", "");
				 con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hosptal?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");

				//For testing          
				 System.out.println("Successfully connected");
				 
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			return con; 
		}
		
		public String readHospital() {  
			String output = "";  
			
			try {  
				Connection con = connect();  
				if (con == null)  {   
					return "Error while connecting to the database for reading.";  
				} 

				// Prepare the html table to be displayed   
				output = "<table border='1'><tr><th>hName</th>"
						+ "<th>hAddress</th><th>hPhone</th>"
						+ "<th>hEmail</th><th>Update</th><th>Remove</th>";


				  String query = "select * from hospital";   
				  Statement stmt = con.createStatement();   
				  ResultSet rs = stmt.executeQuery(query); 

				  // iterate through the rows in the result set   
				  while (rs.next())   {  

					  	String hId = Integer.toString(rs.getInt("hId"));
						String hName = rs.getString("hName");
						String hAddress = rs.getString("hAddress");
						String hPhone = rs.getString("hPhone");
						String hEmail = rs.getString("hEmail");
						
						
					  // Add into the html table    

					  output += "<tr><td><input id='hidAppIDUpdate' name='hidAppIDUpdate' type='hidden' value='" + hId + "'>" + hName + "</td>"; 

					  output += "<td>" + hAddress + "</td>";
					  output += "<td>" + hPhone + "</td>";
						output += "<td>" + hEmail + "</td>";
						
						
						
					  
					// buttons     
					  output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
					  		+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-id='"+ hId +"'>"+"</td></tr>";

					} 
				  
				  con.close(); 

				  // Complete the html table   
				  output += "</table>"; 
				}
				catch (Exception e) {  
					output = "Error while reading the Hospital.";  
					System.err.println(e.getMessage()); 
				}

				return output;
			}
		
		//Insert hospital
		public String insertHospital(String hName, String hAddress, String hPhone, String hEmail) {
			String output = "";

			try {
				Connection con = connect();  

				if (con == null) {
					return "Error while connecting to the database";
				}

				// create a prepared statement   
				String query = " insert into hospital (hId,`hName`,`haddress`,`hPhone`,`hEmail`)"+" values (?, ?, ?, ?, ?)";

				PreparedStatement preparedStmt = con.prepareStatement(query);

				// binding values 
				preparedStmt.setInt(1, 0);
				preparedStmt.setString(2, hName);
				preparedStmt.setString(3, hAddress);
				preparedStmt.setString(4, hPhone);
				preparedStmt.setString(5,hEmail);
			;
				
				//execute the statement   
				preparedStmt.execute();   
				con.close(); 

				//Create JSON Object to show successful msg.
				String newHospital = readHospital();
				output = "{\"status\":\"success\", \"data\": \"" + newHospital + "\"}";
			}
			catch (Exception e) {  
				//Create JSON Object to show Error msg.
				output = "{\"status\":\"error\", \"data\": \"Error while Inserting Hospital.\"}";   
				System.err.println(e.getMessage());  
			} 

			 return output; 
		}
		
		//Update Hospital
		public String updateHospital(String hId, String hName, String hAddress, String hPhone, String hEmail )  {   
			String output = ""; 
		 
		  try   {   
			  Connection con = connect();
		 
			  if (con == null)    {
				  return "Error while connecting to the database for updating."; 
			  } 
		 
		   // create a prepared statement    
			   String query = "UPDATE hospital SET hName=?,hAddress=?,hPhone=?,hEmail=? WHERE hId=?";
				 
		   PreparedStatement preparedStmt = con.prepareStatement(query); 
		 
		   // binding values    
		    preparedStmt.setString(1, hName);
			preparedStmt.setString(2, hAddress);
			preparedStmt.setString(3, hPhone);
			preparedStmt.setString(4,hEmail);
			preparedStmt.setInt(5, Integer.parseInt(hId));
		   
		 
		   // execute the statement    
		   preparedStmt.execute();    
		   con.close(); 
		 
		   //create JSON object to show successful msg
		   String newHospital = readHospital();
		   output = "{\"status\":\"success\", \"data\": \"" + newHospital + "\"}";
		   }   catch (Exception e)   {    
			   output = "{\"status\":\"error\", \"data\": \"Error while Updating Hospital Details.\"}";      
			   System.err.println(e.getMessage());   
		   } 
		 
		  return output;  
		  }
		
		public String deleteHospital(String hId) {  
			String output = ""; 
		 
		 try  {   
			 Connection con = connect();
		 
		  if (con == null)   {    
			  return "Error while connecting to the database for deleting.";   
		  } 
		 
		  // create a prepared statement   
		  String query = "DELETE FROM hospital WHERE hId=?"; 
		 
		  PreparedStatement preparedStmt = con.prepareStatement(query); 
		 
		  // binding values   
		  preparedStmt.setInt(1, Integer.parseInt(hId));       
		  // execute the statement   
		  preparedStmt.execute();   
		  con.close(); 
		 
		  //create JSON Object
		  String newHospital = readHospital();
		  output = "{\"status\":\"success\", \"data\": \"" + newHospital + "\"}";
		  }  catch (Exception e)  {  
			  //Create JSON object 
			  output = "{\"status\":\"error\", \"data\": \"Error while Deleting Hospital.\"}";
			  System.err.println(e.getMessage());  
			  
		 } 
		 
		 return output; 
		 }
}
