package sopra.vol.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import sopra.vol.Application;
import sopra.vol.dao.IAeroportDao;
import sopra.vol.model.Aeroport;
import sopra.vol.model.Ville;

public class AeroportDaoJdbc implements IAeroportDao {

	@Override
	public List<Aeroport> findAll() {
		List<Aeroport> aeroports = new ArrayList<Aeroport>();

		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("SELECT * FROM aeroport");
			ps2 = conn.prepareStatement("SELECT Ville_ID FROM aeroport_ville WHERE Aeroport_code = ?");

			rs = ps.executeQuery();

			while (rs.next()) {
				String code = rs.getString("code");
				String Nom = rs.getString("nom");
				Long ville_Id = rs.getLong("ville_Id");

				Aeroport aero = new Aeroport();
				aero.setCode(code);
				aero.setNom(Nom);
				List<Ville> villes = new ArrayList<Ville>();
				ps2.setString(1, code);
				rs2 = ps2.executeQuery();
				while (rs2.next()) {
					Ville ville = Application.getInstance().getVilleDao().findById(ville_Id);
					villes.add(ville);
				}
				aero.setVilles(villes);
				aeroports.add(aero);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				rs2.close();
				ps.close();
				ps2.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return aeroports;
	}

	@Override
	public Aeroport findById(String id) {
		Aeroport aero = null;

		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("SELECT * FROM aeroport WHERE id = ?");

			ps.setString(1, id);
			ps2 = conn.prepareStatement("SELECT Ville_ID FROM aeroport_ville WHERE Aeroport_code = ?");
			ps2.setString(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				String code = rs.getString("code");
				String Nom = rs.getString("nom");
				Long ville_Id = rs.getLong("ville_Id");

				aero = new Aeroport();
				aero.setCode(code);
				aero.setNom(Nom);
				List<Ville> villes = new ArrayList<Ville>();
				ps2.setString(1, code);
				rs2 = ps2.executeQuery();
				while (rs2.next()) {
					Ville ville = Application.getInstance().getVilleDao().findById(ville_Id);
					villes.add(ville);
				}
				aero.setVilles(villes);
				return aero;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				rs2.close();
				ps.close();
				ps2.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return aero;
	}

	@Override
	public void create(Aeroport obj) {
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("INSERT INTO aeroport (NOM) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
			ps = conn.prepareStatement("INSERT INTO aeroport-ville (aeroport_code,ville_id) VALUES (?)",
					Statement.RETURN_GENERATED_KEYS);
/// tu en es la
			ps.setString(1, obj.getNom());
			ps2.setString(2, obj.getCode());
			for (Ville ville : obj.getVilles()) {
				ps2.setLong(3, ville.getId());
				int rows = ps2.executeUpdate();
				if (rows == 1) {
					ResultSet keys = ps2.getGeneratedKeys();

				} else {
					throw new SQLException("Insertion en échec");
				}
			}

			int rows = ps.executeUpdate();

			if (rows == 1) {
				ResultSet keys = ps.getGeneratedKeys();

				if (keys.next()) {
					String id = keys.getString(1);
					obj.setCode(id);
				}
			} else {
				throw new SQLException("Insertion en échec");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				ps2.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(Aeroport obj) {
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("UPDATE aeroport SET NOM=? WHERE id = ?");
			ps2 = conn.prepareStatement("UPDATE aeroport-ville SET Ville_id=? WHERE id = ?");

			ps.setString(1, obj.getNom());
			ps.setString(2, obj.getCode());

			for (Ville ville : obj.getVilles()) {
				ps2.setLong(1, ville.getId());
				int rows = ps2.executeUpdate();
				if (rows == 1) {
					ResultSet keys = ps2.getGeneratedKeys();

				} else {
					throw new SQLException("Insertion en échec");
				}
			}

			int rows = ps.executeUpdate();

			if (rows != 1) {
				throw new SQLException("Mise à jour en échec");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				ps2.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void delete(Aeroport obj) {
		deleteById(obj.getCode());

	}

	@Override
	public void deleteById(String id) {
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("DELETE FROM aeroport WHERE id = ?");
			ps2 = conn.prepareStatement("DELETE FROM aeroport-ville WHERE Aeroport_code = ?");
			ps.setString(1, id);
			ps2.setString(1, id);

			int rows = ps.executeUpdate();
			int rows2 = ps2.executeUpdate();
			if (rows != 1 || rows2 != 1) {
				throw new SQLException("Suppression en échec");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				ps2.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
