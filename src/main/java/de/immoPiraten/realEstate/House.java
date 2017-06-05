package de.immoPiraten.realEstate;

import javax.persistence.Entity;

@Entity
public class House extends ResidentialRealEstate {

	private short landArea;

	public short getLandArea() {
		return landArea;
	}

	public void setLandArea(short landArea) {
		this.landArea = landArea;
	}
}