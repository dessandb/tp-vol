package sopra.vol.model;

import java.util.ArrayList;
import java.util.List;

public class Aeroport {
	private String code;
	private String nom;
	private List<Ville> villes = new ArrayList<Ville>();

	public Aeroport() {
		super();
	}

	public Aeroport(String code, String nom) {
		super();
		this.code = code;
		this.nom = nom;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public List<Ville> getVilles() {
		return villes;
	}

	public void setVilles(List<Ville> villes) {
		this.villes = villes;
	}

}
