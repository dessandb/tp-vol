package sopra.vol.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import sopra.vol.Application;
import sopra.vol.dao.IPassagerDao;
import sopra.vol.model.Passager;
import sopra.vol.model.TypeIdentite;

public class PassagerDaoJdbc implements IPassagerDao {

	@Override
	public List<Passager> findAll() {
		List<Passager> passagers = new ArrayList<Passager>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("SELECT id, nom, prenom, numero_identite, type_identite FROM passager");

			rs = ps.executeQuery();

			while (rs.next()) {
				Long id = rs.getLong("id");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String numeroIdentite = rs.getString("numero_identite");
				TypeIdentite typeIdentite = TypeIdentite.valueOf(rs.getString("type_identite"));

				Passager passager = new Passager(id, nom, prenom, numeroIdentite, typeIdentite);

				passagers.add(passager);
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

		return passagers;
	}

	@Override
	public Passager findById(Long id) {
		Passager passager = null;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("SELECT nom, prenom, numero_identite, type_identite FROM passager WHERE id = ?");

			ps.setLong(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String numero_identite = rs.getString("numero_identite");
				TypeIdentite type_identite = TypeIdentite.valueOf(rs.getString("type_identite"));

				passager = new Passager(nom, prenom, numero_identite, type_identite);
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

		return passager;
	}

	@Override
	public void create(Passager passager) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("INSERT INTO passager (nom, prenom, numero_identite, type_identite) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, passager.getNom());
			ps.setString(2, passager.getPrenom());
			ps.setString(3, passager.getNumeroIdentite());
			ps.setString(4, ((Passager) passager).getTypeIdentite().toString());

			int rows = ps.executeUpdate();

			if (rows == 1) {
				ResultSet keys = ps.getGeneratedKeys();

				if (keys.next()) {
					Long id = keys.getLong(1);
					passager.setId(id);
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
	public void update(Passager passager) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("UPDATE passager SET nom = ?, prenom = ?, numero_identite = ?, type_identite = ? WHERE id = ?");

			ps.setString(1, passager.getNom());
			ps.setString(2, passager.getPrenom());
			ps.setString(3, passager.getNumeroIdentite());
			ps.setString(4, ((Passager) passager).getTypeIdentite().toString());
			

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
	public void delete(Passager passager) {
		deleteById(passager.getId());
	}

	@Override
	public void deleteById(Long id) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("DELETE FROM passager WHERE id = ?");

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
