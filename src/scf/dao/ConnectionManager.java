package scf.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

	private static ConnectionManager instancia;
	private Connection connect;
	private String connectionURL = "jdbc:hsqldb:hsql://localhost/scf";
	private String user = "SA";
	private String pass = "";

	private ConnectionManager() {
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			System.out.println("Driver Carregado");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static ConnectionManager getInstance() {
		if (instancia == null) {
			instancia = new ConnectionManager();
		}
		return instancia;
	}

	public Connection getConnection() throws SQLException {
		if (connect == null || connect.isClosed()) {
			connect = DriverManager.getConnection(connectionURL, user, pass);
			System.out.println("Gerada uma nova conexão");
		} else {
			System.out.println("Reusando uma conexão existente");
		}
		return connect;
	}

}