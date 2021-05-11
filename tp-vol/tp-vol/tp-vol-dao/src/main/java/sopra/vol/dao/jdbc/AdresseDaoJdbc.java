package sopra.vol.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import sopra.vol.Application;
import sopra.vol.dao.IAdresseDao;
import sopra.vol.dao.IClientDao;
import sopra.vol.model.Adresse;
import sopra.vol.model.Client;

public class AdresseDaoJdbc implements IAdresseDao {

	@Override
	public List<Adresse> findAll() {
		List<Adresse> adresses = new ArrayList<Adresse>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("SELECT id, rue, complement, code_postal, ville, pays, client_id FROM adresse");

			rs = ps.executeQuery();

			while (rs.next()) {
				Long id = rs.getLong("id");
				String rue = rs.getString("rue");
				String complement = rs.getString("complement");
				String code_postal = rs.getString("code_postal");
				String ville = rs.getString("ville");
				String pays = rs.getString("pays");
				Long clientId = rs.getLong("client_id");


				Adresse adresse = new Adresse(id, rue, complement, code_postal, ville, pays);
				
				if (clientId != null) {
					IClientDao clientDao = Application.getInstance().getClientDao();

					Client client = clientDao.findById(clientId);
					adresse.setClient(client);
				}

				adresses.add(adresse);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return adresses;
	}

	@Override
	public Adresse findById(Long id) {
		Adresse adresse = null;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("SELECT rue, complement, code_postal, ville, pays, client_id FROM adresse WHERE id = ?");

			ps.setLong(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				String rue = rs.getString("rue");
				String complement = rs.getString("complement");
				String code_postal = rs.getString("code_postal");
				String ville = rs.getString("ville");
				String pays = rs.getString("pays");
				Long clientId = rs.getLong("client_id");


				adresse = new Adresse(id, rue, complement, code_postal, ville, pays);
				
				if (clientId != null) {
					IClientDao clientDao = Application.getInstance().getClientDao();

					Client client = clientDao.findById(clientId);
					adresse.setClient(client);
				}
			
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return adresse;
	}

	@Override
	public void create(Adresse adresse) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("INSERT INTO adresse (rue, complement, code_postal, ville, pays, client_id) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, adresse.getRue());
			ps.setString(2, adresse.getComplement());
			ps.setString(3, adresse.getCodePostal());
			ps.setString(4, adresse.getVille());
			ps.setString(5, adresse.getPays());
			
			if (adresse.getClient() != null && adresse.getClient().getId() != null) {
				ps.setLong(6, adresse.getClient().getId());
			} else {
				ps.setNull(6, Types.INTEGER);
			}

			int rows = ps.executeUpdate();

			if (rows == 1) {
				ResultSet keys = ps.getGeneratedKeys();

				if (keys.next()) {
					Long id = keys.getLong(1);
					adresse.setId(id);
				}
			} else {
				throw new SQLException("Insertion en échec");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(Adresse adresse) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("UPDATE adresse SET rue = ?, complement = ?, code_postal = ?, ville = ?, pays = ?, client_id = ? WHERE id = ?");

			ps.setString(1, adresse.getRue());
			ps.setString(2, adresse.getComplement());
			ps.setString(3, adresse.getCodePostal());
			ps.setString(4, adresse.getVille());
			ps.setString(5, adresse.getPays());
			
			if (adresse.getClient() != null && adresse.getClient().getId() != null) {
				ps.setLong(6, adresse.getClient().getId());
			} else {
				ps.setNull(6, Types.INTEGER);
			}
			
			ps.setLong(7, adresse.getId());
			
			int rows = ps.executeUpdate();

			if (rows != 1) {
				throw new SQLException("Mise à jour en échec");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void delete(Adresse adresse) {
		deleteById(adresse.getId());
	}

	@Override
	public void deleteById(Long id) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("DELETE FROM adresse WHERE id = ?");

			ps.setLong(1, id);

			int rows = ps.executeUpdate();

			if (rows != 1) {
				throw new SQLException("Suppression en échec");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
