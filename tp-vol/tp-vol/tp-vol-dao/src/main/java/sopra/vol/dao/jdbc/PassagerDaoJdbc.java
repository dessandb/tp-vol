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
			ps = conn.prepareStatement("SELECT * FROM passager");

			rs = ps.executeQuery();

			while (rs.next()) {
				Long id = rs.getLong(1);
				String Nom = rs.getString("NOM");
				String Prenom = rs.getString("PRENOM");
				String NumeroIdent = rs.getString("NUMERO_IDENTITE");
				String TypeIdent = rs.getString("TYPE_IDENTITE");
			
				
				Passager pass=new Passager();
				pass.setId(id);
				pass.setNom(Nom);
				pass.setPrenom(Prenom);
				pass.setNumeroIdentite(NumeroIdent);
				pass.setTypeIdentite(TypeIdentite.valueOf(TypeIdent));
				
				passagers.add(pass);
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
		Passager pass = null;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("SELECT * FROM passager WHERE id = ?");

			ps.setLong(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				String Nom = rs.getString("NOM");
				String Prenom = rs.getString("PRENOM");
				String NumeroIdent = rs.getString("NUMERO_IDENTITE");
				String TypeIdent = rs.getString("TYPE_IDENTITE");
			
				
				pass=new Passager();
				pass.setId(id);
				pass.setNom(Nom);
				pass.setPrenom(Prenom);
				pass.setNumeroIdentite(NumeroIdent);
				pass.setTypeIdentite(TypeIdentite.valueOf(TypeIdent));
				return pass;
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

		return pass;
	}

	@Override
	public void create(Passager obj) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("INSERT INTO passager (NOM,PRENOM,NUMERO_IDENTITE,TYPE_IDENTITE) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, obj.getNom());
			ps.setString(2, obj.getPrenom());
			ps.setString(3, obj.getNumeroIdentite());
			ps.setString(4, obj.getTypeIdentite().toString());	
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
	public void update(Passager obj) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("UPDATE passager SET NOM=?,PRENOM=?,NUMERO_IDENTITE=?,TYPE_IDENTITE=? WHERE id = ?");

			ps.setString(1, obj.getNom());
			ps.setString(2, obj.getPrenom());
			ps.setString(3, obj.getNumeroIdentite());
			ps.setString(4, obj.getTypeIdentite().toString());	
			ps.setLong(5, obj.getId());
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
	public void delete(Passager obj) {
		deleteById(obj.getId());
		
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
