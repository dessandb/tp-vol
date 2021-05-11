package sopra.vol.model;

public class Escale {
	private Long id;
	private Horaire hDepart;
	private Horaire hArrivee;
	private Vol vol;
	private Aeroport aeroport;

	public Escale() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Horaire gethDepart() {
		return hDepart;
	}

	public void sethDepart(Horaire hDepart) {
		this.hDepart = hDepart;
	}

	public Horaire gethArrivee() {
		return hArrivee;
	}

	public void sethArrivee(Horaire hArrivee) {
		this.hArrivee = hArrivee;
	}

	public Vol getVol() {
		return vol;
	}

	public void setVol(Vol vol) {
		this.vol = vol;
	}

	public Aeroport getAeroport() {
		return aeroport;
	}

	public void setAeroport(Aeroport aeroport) {
		this.aeroport = aeroport;
	}

}
