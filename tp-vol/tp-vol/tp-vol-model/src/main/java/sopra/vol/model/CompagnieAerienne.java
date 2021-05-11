package sopra.vol.model;

import java.util.ArrayList;
import java.util.List;

public class CompagnieAerienne {
	private String code;
	private String nom;
	private List<CompagnieAerienneVol> vols = new ArrayList<CompagnieAerienneVol>();

	public CompagnieAerienne() {
		super();
	}

	public CompagnieAerienne(String code, String nom) {
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

	public List<CompagnieAerienneVol> getVols() {
		return vols;
	}

	public void setVols(List<CompagnieAerienneVol> vols) {
		this.vols = vols;
	}

}
