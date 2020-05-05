package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Appointment {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/appointmentservice?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertAppointment(String number, String type, String date, String des, String did, String hid, String pid)  
	{   
		String output = ""; 
	 
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{return "Error while connecting to the database for inserting."; } 
	 
			// create a prepared statement    
			String query = " insert into appointment (`id`,`number`,`type`,`date`,`des`,`did`,`hid`,`pid`)" + " values (?, ?, ?, ?, ?, ?, ?, ?)"; 
	 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, number);
			preparedStmt.setString(3, type);
			preparedStmt.setString(4, date);
			preparedStmt.setString(5, des);
			preparedStmt.setString(6, did);
			preparedStmt.setString(7, hid);
			preparedStmt.setString(8, pid);

			
			// execute the statement    
			preparedStmt.execute();    
			con.close(); 
	   
			String newAppointments = readAppointments(); 
			output =  "{\"status\":\"success\", \"data\": \"" +        
							newAppointments + "\"}";    
		}   
		catch (Exception e)   
		{    
			output =  "{\"status\":\"error\", \"data\": \"Error while inserting the appointment.\"}";  
			System.err.println(e.getMessage());   
		} 
		
	  return output;  
	} 
	
	public String readAppointments()  
	{   
		String output = ""; 
	
		try   
		{    
			Connection con = connect(); 
		
			if (con == null)    
			{
				return "Error while connecting to the database for reading."; 
			} 
	 
			// Prepare the html table to be displayed    
			output = "<table border=\'1\'><tr><th>Appointment Number</th><th>Appointment Type</th><th>Appointment Date</th><th>Appointment Description</th><th>Doctor ID</th><th>Hospital ID</th><th>Patient ID</th><th>Update</th><th>Remove</th></tr>";
	 
			String query = "select * from appointment";    
			Statement stmt = con.createStatement();    
			ResultSet rs = stmt.executeQuery(query); 
	 
			// iterate through the rows in the result set    
			while (rs.next())    
			{     
				String id = Integer.toString(rs.getInt("id"));
				String number = Integer.toString(rs.getInt("number"));
				String type = rs.getString("type");
				String date = rs.getString("date");
				String des = rs.getString("des");
				String did = Integer.toString(rs.getInt("did"));
				String hid = Integer.toString(rs.getInt("hid"));
				String pid = Integer.toString(rs.getInt("pid"));

			
	 
				// Add into the html table 
				output += "<tr><td><input id=\'hidAppointmentIDUpdate\' name=\'hidAppointmentIDUpdate\' type=\'hidden\' value=\'" + id + "'>" 
							+ number + "</td>";      
				output += "<td>" + type + "</td>";     
				output += "<td>" + date + "</td>";     
				output += "<td>" + des + "</td>";
				output += "<td>" + did + "</td>"; 
				output += "<td>" + hid + "</td>"; 
				output += "<td>" + pid + "</td>"; 
	 
				// buttons     
//				output += "<td><input name=\'btnUpdate\' type=\'button\' value=\'Update\' class=\' btnUpdate btn btn-secondary\'></td>"
//						//+ "<td><form method=\"post\" action=\"appointments.jsp\">      "
//						+ "<input name=\'btnRemove\' type=\'submit\' value=\'Remove\' class=\'btn btn-danger\'> "
//						+ "<input name=\"hidAppointmentIDDelete\" type=\"hidden\" value=\"" + appointmentID + "\">" + "</form></td></tr>"; 
				output +="<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"       
							+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-appointmentid='" + id + "'>" + "</td></tr>"; 
			} 
	 
			con.close(); 
	 
			// Complete the html table    
			output += "</table>";   
		}   
		catch (Exception e)   
		{    
			output = "Error while reading the appointments.";    
			System.err.println(e.getMessage());   
		} 
	 
		return output;  
	}
	 
	
	public String updateAppointment(String id, String number, String type, String date, String des,  String did, String hid, String pid)  
	{   
		String output = ""; 
	 
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{return "Error while connecting to the database for updating."; } 
	 
			// create a prepared statement    
			String query = "UPDATE appointment SET number=?,type=?,date=?,des=?,did=?,hid=?,pid=? " + "WHERE id=?";
	 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			preparedStmt.setInt(1, Integer.parseInt(number));
			preparedStmt.setString(2, type);
			preparedStmt.setString(3, date);
			preparedStmt.setString(4, des);
			preparedStmt.setInt(5, Integer.parseInt(did));
			preparedStmt.setInt(6, Integer.parseInt(hid));
			preparedStmt.setInt(7, Integer.parseInt(pid));
			preparedStmt.setInt(8, Integer.parseInt(id));

	 
			// execute the statement    
			preparedStmt.execute();    
			con.close(); 
	 
			String newAppointments = readAppointments();    
			output = "{\"status\":\"success\", \"data\": \"" +        
									newAppointments + "\"}";    
		}   
		catch (Exception e)   
		{    
			output =  "{\"status\":\"error\", \"data\": \"Error while updating the appointment.\"}";   
			System.err.println(e.getMessage());   
		} 
	 
	  return output;  
	} 
	
	public String deleteAppointment(String id)  
	{   
		String output = ""; 
	 
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{return "Error while connecting to the database for deleting."; } 
	 
			// create a prepared statement    
			String query = "delete from appointment where id=?"; 
	 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			preparedStmt.setInt(1, Integer.parseInt(id)); 
	 
			// execute the statement    
			preparedStmt.execute();    
			con.close(); 
	 
			String newAppointments = readAppointments();    
			output = "{\"status\":\"success\", \"data\": \"" +       
								newAppointments + "\"}";    
		}   
		catch (Exception e)   
		{    
			output = "Error while deleting the appointment.";    
			System.err.println(e.getMessage());   
		} 
	 
		return output;  
	}
	 
	 
}



