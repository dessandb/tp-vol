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
import sopra.vol.dao.IClientDao;
import sopra.vol.model.Client;
import sopra.vol.model.Entreprise;
import sopra.vol.model.Particulier;
import sopra.vol.model.StatutJuridique;




public class ClientDaoJdbc implements IClientDao {

	@Override
	public List<Client> findAll() {
		List<Client> clients = new ArrayList<Client>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("SELECT * FROM client");

			rs = ps.executeQuery();

			while (rs.next()) {
				Long id = rs.getLong("id");
				String nom = rs.getString("nom");
				String type =rs.getString("TYPE");
				
				if(type.equals("E")) {
					String siret = rs.getString("SIRET");
					StatutJuridique stat = StatutJuridique.valueOf(rs.getString("STATUT_JURIDIQUE"));
					String numtva = rs.getString("NUMERO_TVA");
					Entreprise ent= new Entreprise(id,nom);
					ent.setNumeroTVA(numtva);
					ent.setSiret(siret);
					ent.setStatutJuridique(stat);
					clients.add(ent);
				}
				else if(type.equals("P")) {
					String prenom=rs.getString("PRENOM");
					Particulier part = new Particulier();
					part.setId(id);
					part.setNom(nom);
					part.setPrenom(prenom);
					clients.add(part);
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

		return clients;
	}

	@Override
	public Client findById(Long id) {
		Client client = null;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("SELECT * FROM client WHERE id = ?");

			ps.setLong(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				String nom = rs.getString("nom");
				String type =rs.getString("TYPE");
				
				if(type.equals("E")) {
					String siret = rs.getString("SIRET");
					StatutJuridique stat = StatutJuridique.valueOf(rs.getString("STATUT_JURIDIQUE"));
					String numtva = rs.getString("NUMERO_TVA");
					Entreprise ent= new Entreprise(id,nom);
					ent.setNumeroTVA(numtva);
					ent.setSiret(siret);
					ent.setStatutJuridique(stat);
					return ent;
				}
				else if(type.equals("P")) {
					String prenom=rs.getString("PRENOM");
					Particulier part = new Particulier();
					part.setId(id);
					part.setNom(nom);
					part.setPrenom(prenom);
					return part;
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

		return client;
	}

	@Override
	public void create(Client obj) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("INSERT INTO client (TYPE,NOM,PRENOM,SIRET,NUMERO_TVA,STATUT_JURIDIQUE) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

			ps.setString(2, obj.getNom());
			if(obj.getClass().getName().equals("Particulier")) {
				ps.setString(1, "P");
				ps.setString(3, ((Particulier) obj).getPrenom());
				ps.setNull(4, Types.VARCHAR);
				ps.setNull(5, Types.VARCHAR);
				ps.setNull(6, Types.VARCHAR);
			}
			else if(obj.getClass().getName().equals("Particulier")) {
				ps.setString(1, "E");
				ps.setNull(3,Types.VARCHAR);
				ps.setString(4, ((Entreprise) obj).getSiret());
				ps.setString(5, ((Entreprise) obj).getNumeroTVA());
				ps.setString(6, ((Entreprise) obj).getStatutJuridique().toString());
			}

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
	public void update(Client obj) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("UPDATE client SET TYPE=?,NOM=?,PRENOM=?,SIRET=?,NUMERO_TVA=?,STATUT_JURIDIQUE=? WHERE id = ?");

			ps.setString(2, obj.getNom());
			if(obj.getClass().getName().equals("Particulier")) {
				ps.setString(1, "P");
				ps.setString(3, ((Particulier) obj).getPrenom());
				ps.setNull(4, Types.VARCHAR);
				ps.setNull(5, Types.VARCHAR);
				ps.setNull(6, Types.VARCHAR);
			}
			else if(obj.getClass().getName().equals("Particulier")) {
				ps.setString(1, "E");
				ps.setNull(3,Types.VARCHAR);
				ps.setString(4, ((Entreprise) obj).getSiret());
				ps.setString(5, ((Entreprise) obj).getNumeroTVA());
				ps.setString(6, ((Entreprise) obj).getStatutJuridique().toString());
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
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public void delete(Client obj) {
		deleteById(obj.getId());		
	}

	@Override
	public void deleteById(Long id) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("DELETE FROM client WHERE id = ?");

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
