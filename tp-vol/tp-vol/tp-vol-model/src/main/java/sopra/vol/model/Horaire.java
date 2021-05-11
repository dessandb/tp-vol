package sopra.vol.model;

import java.util.Date;

public class Horaire {
	private Long id;
	private Date dateHeure;

	public Horaire() {
		super();
	}

	public Horaire(Date dateHeure) {
		super();
		this.dateHeure = dateHeure;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateHeure() {
		return dateHeure;
	}

	public void setDateHeure(Date dateHeure) {
		this.dateHeure = dateHeure;
	}

}
