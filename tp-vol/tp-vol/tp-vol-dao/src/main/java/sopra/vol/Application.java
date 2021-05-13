package sopra.vol;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import sopra.vol.dao.IAdresseDao;
import sopra.vol.dao.IClientDao;
import sopra.vol.dao.IPassagerDao;
import sopra.vol.dao.IVilleDao;
import sopra.vol.dao.jdbc.AdresseDaoJdbc;
import sopra.vol.dao.jdbc.ClientDaoJdbc;
import sopra.vol.dao.jdbc.PassagerDaoJdbc;
import sopra.vol.dao.jdbc.VilleDaoJdbc;

public class Application {
	private static Application instance = null;

	private final String jdbcUrl = "jdbc:mysql://localhost:3306/tp_vol";
	private final String username = "root";
	private final String password = "admin";
	
	private final IAdresseDao AdresseDao = new AdresseDaoJdbc();
	private final IClientDao ClientDao = new ClientDaoJdbc();
	private final IVilleDao VilleDao = new VilleDaoJdbc();
	private final IPassagerDao PassagerDao = new PassagerDaoJdbc();


	

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
	public IAdresseDao getAdresseDao() {
		return AdresseDao;
	}

	public IClientDao getClientDao() {
		return ClientDao;
	}

	public IVilleDao getVilleDao() {
		return VilleDao;
	}

	public IPassagerDao getPassagerDao() {
		return PassagerDao;
	}
	

}
