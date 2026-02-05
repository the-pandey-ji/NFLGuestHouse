package com.DB;
import java.sql.Connection;

public class DBConnect {
	
	private static Connection conn;
	
	public static Connection getConnection() {
		
		  try { Class.forName("oracle.jdbc.driver.OracleDriver"); 
		  String url = "jdbc:oracle:thin:@//10.3.111.120:1521/ORCL";  // Use service name format
	        conn = java.sql.DriverManager.getConnection(url, "PERSONNEL", "PERSONNEL");

		
		 System.out.println("Connection Established Successfully");
		 
		  } catch (Exception e) { e.printStackTrace(); }
		 

		
		
		return conn;
	}

}
