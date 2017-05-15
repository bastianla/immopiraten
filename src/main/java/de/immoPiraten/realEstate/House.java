package de.immoPiraten.realEstate;

import javax.persistence.Entity;

@Entity
public class House extends ResidentialRealEstate {

	private double landArea;

	public double getLandArea() {
		return landArea;
	}

	public void setLandArea(double landArea) {
		this.landArea = landArea;
	}
}