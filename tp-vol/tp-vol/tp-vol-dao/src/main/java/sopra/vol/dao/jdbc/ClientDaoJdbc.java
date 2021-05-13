package sopra.vol.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sun.tools.javac.code.Types;

import sopra.vol.Application;
import sopra.vol.dao.IClientDao;
import sopra.vol.model.Adresse;
import sopra.vol.model.Client;
import sopra.vol.model.Entreprise;
import sopra.vol.model.Particulier;
import sopra.vol.model.StatutJuridique;
import sopra.vol.model.Ville;


public class ClientDaoJdbc implements IClientDao {

	@Override
	public List<Client> findAll() {
		
		List<Client> clients = new ArrayList<Client>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps2 = null;
		//ResultSet rs2 = null;
		

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("SELECT id, type, nom, prenom, siret, numero_TVA,statut_juridique, adresse_id FROM client");
			//ps2 = conn.prepareStatement("SELECT * FROM adresse where Adresse_ID = ?"); A faire dans adresse

			rs = ps.executeQuery();

			while (rs.next()) {
				Long id = rs.getLong(1);
				String type = rs.getString("type");
				String nom = rs.getString("nom");
				Long adresse_id = rs.getLong("adresse_id");
				
				// on peux pas créer de clients directement car classe abstraite
				if (type.equals("E")) {
					Entreprise entreprise = new Entreprise(id, nom);
					String siret = rs.getString("siret");
					String numero_Tva = rs.getString("numero_TVA");
					String statut_juridique = rs.getString("statut_juridique");
					entreprise.setNumeroTVA(numero_Tva);
					entreprise.setSiret(siret);					
					entreprise.setStatutJuridique(StatutJuridique.valueOf(statut_juridique));
					ps2.setLong(1, adresse_id);
					//rs2 = ps2.executeQuery();
					List<Adresse> adresses = new ArrayList<Adresse>();
					if (rs.next()) {
						String rue = rs.getString("rue");
						String complement = rs.getString("complement");
						String code_postal = rs.getString("code_postal");
						String ville = rs.getString("ville");
						String pays = rs.getString("pays");
						
						Adresse adr = new Adresse();
						adr.setCodePostal(code_postal);
						adr.setComplement(complement);
						adr.setPays(pays);
						adr.setRue(rue);
						adr.setVille(ville);
						adresses.add(adr);
						// entreprise.setAdresse(adr)
						
					}
					
					entreprise.setAdresses(adresses);
					clients.add(entreprise);
					
				}
				
				else if (type.equals("P")) {
					Particulier particulier = new Particulier();
					
					String prenom = rs.getString("prenom");
					
					particulier.setPrenom(prenom);			
					particulier.setId(id);
					particulier.setNom(nom);					
					
					ps2.setLong(1, adresse_id);
					rs2 = ps2.executeQuery();
					List<Adresse> adresses = new ArrayList<Adresse>();
										
					while (rs2.next()) {
						String rue = rs2.getString("rue");
						String complement = rs2.getString("complement");
						String code_postal = rs2.getString("code_postal");
						String ville = rs2.getString("ville");
						String pays = rs2.getString("pays");
						
						Adresse adr = new Adresse();
						adr.setCodePostal(code_postal);
						adr.setComplement(complement);
						adr.setPays(pays);
						adr.setRue(rue);
						adr.setVille(ville);
						adresses.add(adr);
						
					}
					
					particulier.setAdresses(adresses);
					clients.add(particulier);
				}

				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				rs2.close();
				ps2.close();
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
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("SELECT type, nom, prenom, siret, numero_TVA,statut_juridique, adresse_id FROM client WHERE id = ?");
			ps2 = conn.prepareStatement("SELECT * FROM adresse where Adresse_ID = ?");

			ps.setLong(1, id);
			rs = ps.executeQuery();
			

			if (rs.next()) {
				String type = rs.getString("type");
				String nom = rs.getString("nom");
				Long adresse_id = rs.getLong("adresse_id");
				
				if( type.equals("E")) {
				Entreprise entreprise = new Entreprise(id, nom);
				String siret = rs.getString("siret");
				String numero_Tva = rs.getString("numero_TVA");
				String statut_juridique = rs.getString("statut_juridique");
				entreprise.setNumeroTVA(numero_Tva);
				entreprise.setSiret(siret);					
				entreprise.setStatutJuridique(StatutJuridique.valueOf(statut_juridique));
				ps2.setLong(1, adresse_id);
				rs2 = ps2.executeQuery();
				List<Adresse> adresses = new ArrayList<Adresse>();
				while (rs2.next()) {
					String rue = rs2.getString("rue");
					String complement = rs2.getString("complement");
					String code_postal = rs2.getString("code_postal");
					String ville = rs2.getString("ville");
					String pays = rs2.getString("pays");
					
					Adresse adr = new Adresse();
					adr.setCodePostal(code_postal);
					adr.setComplement(complement);
					adr.setPays(pays);
					adr.setRue(rue);
					adr.setVille(ville);
					adresses.add(adr);
					
				}
				
				entreprise.setAdresses(adresses);
				return entreprise;
					
					
				}
				
				else if (type.equals("P")){
					Particulier particulier = new Particulier();
					
					String prenom = rs.getString("prenom");
					
					particulier.setPrenom(prenom);			
					particulier.setId(id);
					particulier.setNom(nom);					
					
					ps2.setLong(1, adresse_id);
					rs2 = ps2.executeQuery();
					List<Adresse> adresses = new ArrayList<Adresse>();
										
					while (rs2.next()) {
						String rue = rs2.getString("rue");
						String complement = rs2.getString("complement");
						String code_postal = rs2.getString("code_postal");
						String ville = rs2.getString("ville");
						String pays = rs2.getString("pays");
						
						Adresse adr = new Adresse();
						adr.setCodePostal(code_postal);
						adr.setComplement(complement);
						adr.setPays(pays);
						adr.setRue(rue);
						adr.setVille(ville);
						adresses.add(adr);
						
					}
					
					particulier.setAdresses(adresses);
					return particulier;
				}
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				rs2.close();
				ps2.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;

	}


	@Override
	public void create(Client obj) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("INSERT INTO client (type,nom, prenom, siret,numero_Tva, statut_juridique, adresse_Id) VALUES (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

			if (obj.getClass().getName().equals("Entreprise")) {
				ps.setString(1, "E");
				ps.setNull(3, java.sql.Types.VARCHAR);
				ps.setString(4, ((Entreprise) obj).getSiret());
				ps.setString(5, ((Entreprise) obj).getNumeroTVA());
				ps.setString(6, ((Entreprise) obj).getStatutJuridique().toString());
			}
			
			else if(obj.getClass().getName().equals("Particulier")) {
				ps.setString(1, "P");
				ps.setString(3, ((Particulier) obj).getPrenom());
				ps.setNull(4, java.sql.Types.VARCHAR);
				ps.setNull(5, java.sql.Types.VARCHAR);
				ps.setNull(6, java.sql.Types.VARCHAR);				
			}
			
			ps.setString(2, obj.getNom());
			// if plus ! = not la condition
			if (!(obj.getAdresses().isEmpty())) {
				for (Adresse adr:obj.getAdresse()) {
					ps.setLong(obj.getId(), adr.getId());
				}
			}
			

			int rows = ps.executeUpdate();

			if (rows == 1) {
				ResultSet keys = ps.getGeneratedKeys();

				if (keys.next()) {
					Long id = keys.getLong(1);
					ville.setId(id);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Client obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}
	
	

}
