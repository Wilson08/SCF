package scf.control.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionBD {
	private static ConnectionBD instance;
	private Connection conn;
	private String connectionURL = "jdbc:mariadb://localhost:330/scf";
	private String user = "root";
	private String psswrd = "";

	private ConnectionBD() {
		try {
			Class.forName("org.maria.db.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static ConnectionBD getInstance() {
		if (instance == null) {
			instance = new ConnectionBD();
		}
		return instance;
	}
	
	public Connection getConnection() throws SQLException {
		if (conn == null || conn.isClosed()) {
			conn = DriverManager.getConnection(connectionURL, user, psswrd);
		}
		return conn;
	}
}
