package de.immoPiraten.favorite;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import de.immoPiraten.realEstate.RealEstate;
import de.immoPiraten.userprofile.Userprofile;

// Test
@Entity
public class Favorite {

	@Id
	@GeneratedValue	
	private int id;
	@OneToOne
	private RealEstate realEstate;
	private Date creationDate;
	/*
	@Column(name="USERPROFILE_ID")
	private int userprofileId;
	*/
	@ManyToOne(optional=false)
	@JoinColumn(name="USERPROFILE_ID", referencedColumnName="USERPROFILE_ID")
	private Userprofile userprofile;
	
	public Favorite() {
	}
	
	public Favorite(RealEstate realEstate, Date creationDate) {
		super();
		this.realEstate = realEstate;
		this.creationDate = creationDate;
	}
	
	public void setUserprofile(Userprofile userprofile){
		this.userprofile = userprofile;
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