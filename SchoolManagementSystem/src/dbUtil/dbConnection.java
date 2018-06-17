package dbUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * This class will connect to my database
 */
public class dbConnection {

	// you'll need these for MYSQL
	//private static final String USERNAME = "dbuser";
	//private static final String PASSWORD = "dbpassword";
	//private static final String CONN = "jbdc:mysql://localhost/login";
	
	// this is all we need for SQLITE
	private static final String SQCONN = "jdbc:sqlite:schoolsystem.sqlite";
	
	/*
	 * This is the class that will help with the
	 * connection to the Database.
	 */
	public static Connection getConnection() throws SQLException {
		
		try {
			
			Class.forName("org.sqlite.JDBC");
			return DriverManager.getConnection(SQCONN);
			
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
}
