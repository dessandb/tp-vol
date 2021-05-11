package sopra.vol;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import sopra.vol.dao.IClientDao;
import sopra.vol.dao.IPassagerDao;
import sopra.vol.dao.jdbc.ClientDaoJdbc;
import sopra.vol.dao.jdbc.PassagerDaoJdbc;

public class Application {
	private static Application instance = null;
	
	private final IClientDao clientDao = new ClientDaoJdbc();
	private final IPassagerDao passagerDao = new PassagerDaoJdbc();

	private final String jdbcUrl = "jdbc:mysql://localhost:3306/tp_vol";
	private final String username = "root";
	private final String password = "admin";

	private Application() {
		super();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Application getInstance() {
		if (instance == null) {
			instance = new Application();
		}

		return instance;
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(jdbcUrl, username, password);
	}

	public IClientDao getClientDao() {
		return clientDao;
	}

	public IPassagerDao getPassagerDao() {
		return passagerDao;
	}

}
