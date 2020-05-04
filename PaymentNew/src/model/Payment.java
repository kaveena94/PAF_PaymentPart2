package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Payment {
	// A common method to connect to the DB
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/payment2", "root", "root");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String readPay()
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
			output = "<table border=\\\"1\\\"><th>Pay User Name</th><th>Pay Amount</th><th>Pay Date</th><th>Pay CardType</th><th>Pay CardNo</th><th>Update</th><th>Remove</th></tr>";
			
			String query = "select * from payment2";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
	 
			// iterate through the rows in the result set
			while (rs.next())
			{
				String payID = Integer.toString(rs.getInt("payID"));
				String payName = rs.getString("payName");
				String payAmount = Double.toString(rs.getDouble("payAmount"));
				String payDate = rs.getString("payDate");		
				String payCardType = rs.getString("payCardType"); 
				String payCardNo = rs.getString("payCardNo"); 
	
				// Add into the html table
				output += "<tr><td><input id='hidPaymentIDUpdate'name='hidPaymentIDUpdate' type='hidden'value='" + payID + "'>" + payName + "</td>";
				output += "<td>" + payAmount + "</td>";
				output += "<td>" + payDate + "</td>";
				output += "<td>" + payCardType + "</td>";
				output += "<td>" + payCardNo + "</td>";
	
				// buttons
				output += "<td><input name='btnUpdate' type='button'value='Update' class='btnUpdate btn btn-secondary'></td><td><input name='btnRemove' type='button'value='Remove'class='btnRemove btn btn-danger' data-payid='"
						+ payID + "'>" + "</td></tr>";
			}
	 
			con.close();
	 
			// Complete the html table
	 
			output += "</table>";
	 }
	catch (Exception e)
	 {
	 output = "Error while reading the payment.";
	 System.err.println(e.getMessage());
	 }
	return output;
	}
	
	public String insertPay(String name, String amount, String date, String cardType, String cardNo) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into payment2(payID, payName, payAmount, payDate, payCardType, payCardNo)"
					+ " values (?, ?, ?, ?, ?, ?)";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, name);
			preparedStmt.setDouble(3, Double.parseDouble(amount));
			preparedStmt.setString(4, date);	
			preparedStmt.setString(5, cardType);
			preparedStmt.setString(6, cardNo);
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			//output = "Inserted successfully";
			
			String newPayment = readPay();
			 output = "{\"status\":\"success\", \"data\": \"" +
					 newPayment + "\"}"; 
			 
			 
		} catch (Exception e) {
		//	output = "Error while inserting the payment.";
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the peyment.\"}"; 
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String updatePay(String id, String name, String amount, String date, String cardType, String cardNo) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE payment2 SET payName=?,payAmount=?,payDate=?,payCardType=?,payCardNo=? WHERE payID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, name);
			preparedStmt.setDouble(2, Double.parseDouble(amount));
			preparedStmt.setString(3, date);
			preparedStmt.setString(4, cardType);
			preparedStmt.setString(5, cardNo);
			preparedStmt.setInt(6, Integer.parseInt(id));
			// execute the statement
			preparedStmt.execute();
			con.close();
			//output = "Updated successfully";
			String newPayment = readPay();
			 output = "{\"status\":\"success\", \"data\": \"" +
			 newPayment + "\"}"; 
		} catch (Exception e) {
			//output = "Error while updating the payment.";
			output = "{\"status\":\"error\", \"data\": \"Error while updating the payment.\"}"; 
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deletePay(String id) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from payment2 where payID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(id));
			// execute the statement
			preparedStmt.execute();
			con.close();
			//output = "Deleted successfully";
			String newPayment = readPay();
			 output = "{\"status\":\"success\", \"data\": \"" +
			 newPayment + "\"}"; 
		} catch (Exception e) {
			//output = "Error while deleting the payment.";
			output = "{\"status\":\"error\", \"data\": \"Error while deleting the payment.\"}"; 
			System.err.println(e.getMessage());
		}
		return output;
	}
}
