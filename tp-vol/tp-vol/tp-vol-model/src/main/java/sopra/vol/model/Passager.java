package sopra.vol.model;

public class Passager {
	private Long id;
	private String nom;
	private String prenom;
	private String numeroIdentite;
	private TypeIdentite typeIdentite;
	private Reservation reservation;

	public Passager() {
		super();
	}
	
	public Passager(String nom, String prenom, String numeroIdentite, TypeIdentite typeIdentite) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.numeroIdentite = numeroIdentite;
		this.typeIdentite = typeIdentite;
	}

	public Passager(Long id, String nom, String prenom, String numeroIdentite, TypeIdentite typeIdentite) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.numeroIdentite = numeroIdentite;
		this.typeIdentite = typeIdentite;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroIdentite() {
		return numeroIdentite;
	}

	public void setNumeroIdentite(String numeroIdentite) {
		this.numeroIdentite = numeroIdentite;
	}

	public TypeIdentite getTypeIdentite() {
		return typeIdentite;
	}

	public void setTypeIdentite(TypeIdentite typeIdentite) {
		this.typeIdentite = typeIdentite;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

}
