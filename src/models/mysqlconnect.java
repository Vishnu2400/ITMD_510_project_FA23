package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;


public class mysqlconnect {
	// Database URL
	static final String DB_URL = "jdbc:mysql://www.papademas.net:3307/510fp?autoReconnect=true&useSSL=false";
	// Database credentials
	static final String USER = "fp510", PASS = "510";

	public static Connection connectdb() {
		try {
			return DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Database Connection Error: " + e.getMessage());
			throw new RuntimeException("Database Connection Error", e);
		}
	}


}

