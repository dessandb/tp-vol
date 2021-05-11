package sopra.vol.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Client {
	private Long id;
	private String nom;
	private Adresse adresse;
	private List<Reservation> reservations = new ArrayList<Reservation>();

	public Client() {
		super();
	}

	public Client(String nom) {
		super();
		this.nom = nom;
	}
	
	public Client(Long id, String nom) {
		super();
		this.id = id;
		this.nom = nom;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

}
