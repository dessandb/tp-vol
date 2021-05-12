package sopra.vol.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import sopra.vol.Application;
import sopra.vol.dao.IAdresseDao;
import sopra.vol.model.Adresse;

public class AdresseDaoJdbc implements IAdresseDao {

	@Override
	public List<Adresse> findAll() {
		List<Adresse> adresses = new ArrayList<Adresse>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("SELECT * FROM adresse");

			rs = ps.executeQuery();

			while (rs.next()) {
				Long id = rs.getLong(1);
				String Rue = rs.getString("RUE");
				String Complement = rs.getString("COMPLEMENT");
				String CodePostal = rs.getString("CODEPOSTAL");
				String Ville = rs.getString("VILLE");
				String Pays = rs.getString("PAYS");
				Long clientId = rs.getLong("CLIENT_ID");
				
				Adresse adr=new Adresse();
				adr.setId(id);
				adr.setClient(Application.getInstance().getClientDao().findById(clientId));
				adr.setCodePostal(CodePostal);
				adr.setComplement(Complement);
				adr.setPays(Pays);
				adr.setRue(Rue);
				adr.setVille(Ville);

				adresses.add(adr);
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
		Adresse adr = null;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("SELECT * FROM adresse WHERE id = ?");

			ps.setLong(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				String Rue = rs.getString("RUE");
				String Complement = rs.getString("COMPLEMENT");
				String CodePostal = rs.getString("CODEPOSTAL");
				String Ville = rs.getString("VILLE");
				String Pays = rs.getString("PAYS");
				Long clientId = rs.getLong("CLIENT_ID");
				adr = new Adresse();
				adr.setId(id);
				adr.setClient(Application.getInstance().getClientDao().findById(clientId));
				adr.setCodePostal(CodePostal);
				adr.setComplement(Complement);
				adr.setPays(Pays);
				adr.setRue(Rue);
				adr.setVille(Ville);

				return adr;
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

		return adr;
	}


	@Override
	public void create(Adresse obj) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("INSERT INTO adresse (RUE,COMPLEMENT,CODE_POSTAL,VILLE,PAYS,CLIENT_ID) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, obj.getRue());
			ps.setString(2, obj.getComplement());
			ps.setString(3, obj.getCodePostal());
			ps.setString(4, obj.getVille());
			ps.setString(5, obj.getPays());
			ps.setLong(6, obj.getClient().getId());
			
			int rows = ps.executeUpdate();

			if (rows == 1) {
				ResultSet keys = ps.getGeneratedKeys();

				if (keys.next()) {
					Long id = keys.getLong(1);
					obj.setId(id);
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
	public void update(Adresse obj) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("UPDATE adresse SET RUE=?,COMPLEMENT=?,CODE_POSTAL=?,VILLE=?,PAYS=?,CLIENT_ID=? WHERE id = ?");

			ps.setString(1, obj.getRue());
			ps.setString(2, obj.getComplement());
			ps.setString(3, obj.getCodePostal());
			ps.setString(4, obj.getVille());
			ps.setString(5, obj.getPays());
			ps.setLong(6, obj.getClient().getId());
			ps.setLong(7, obj.getId());
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
	public void delete(Adresse obj) {
		deleteById(obj.getId());
		
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
