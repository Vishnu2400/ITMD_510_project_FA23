//package login;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//import javax.swing.JOptionPane;
//
//
//@SuppressWarnings("unused")
//public class mysqlconnect {
//	// Database URL
//    static final String DB_URL = "jdbc:mysql://www.papademas.net:3307/510labs?autoReconnect=true&useSSL=false";
//    // Database credentials
//    static final String USER = "db510", PASS = "510";
//
//    public Connection connect() throws SQLException {
//        // Creating a connection using the DriverManager class
//        return DriverManager.getConnection(DB_URL, USER, PASS);
//    }
//
//}
////
////OptionPane.showMessageDialog(null, "Connection Established");