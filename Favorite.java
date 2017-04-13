package de.immoPiraten.favorite;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import de.immoPiraten.realEstate.RealEstate;

// Test
@Entity
public class Favorite {

	@Id
	@GeneratedValue	
	private int id;
	@OneToOne
	private RealEstate realEstate;
	private Date creationDate;
	
	public Favorite() {
	}
	
	public Favorite(RealEstate realEstate, Date creationDate) {
		super();
		this.realEstate = realEstate;
		this.creationDate = creationDate;
	}
	
	public RealEstate getRealEstate() {
		return realEstate;
	}
	public void setRealEstate(RealEstate realEstate) {
		this.realEstate = realEstate;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}