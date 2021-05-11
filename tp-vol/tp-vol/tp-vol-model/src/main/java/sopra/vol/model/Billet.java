package sopra.vol.model;

public class Billet {
	private Long id;
	private String numeroPlace;
	private String classe;
	private float prix;
	private int ordre;
	private Reservation reservation;
	private Vol vol;

	public Billet() {
		super();
	}

	public Billet(String numeroPlace, String classe, float prix, int ordre) {
		super();
		this.numeroPlace = numeroPlace;
		this.classe = classe;
		this.prix = prix;
		this.ordre = ordre;
	}

	public Billet(Long id, String numeroPlace, String classe, float prix, int ordre) {
		super();
		this.id = id;
		this.numeroPlace = numeroPlace;
		this.classe = classe;
		this.prix = prix;
		this.ordre = ordre;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroPlace() {
		return numeroPlace;
	}

	public void setNumeroPlace(String numeroPlace) {
		this.numeroPlace = numeroPlace;
	}

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public float getPrix() {
		return prix;
	}

	public void setPrix(float prix) {
		this.prix = prix;
	}

	public int getOrdre() {
		return ordre;
	}

	public void setOrdre(int ordre) {
		this.ordre = ordre;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public Vol getVol() {
		return vol;
	}

	public void setVol(Vol vol) {
		this.vol = vol;
	}

}
