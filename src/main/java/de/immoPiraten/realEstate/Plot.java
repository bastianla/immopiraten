package de.immoPiraten.realEstate;

import javax.persistence.Entity;

@Entity
public class Plot extends RealEstate {
	private short landArea;

	public short getLandArea() {
		return landArea;
	}

	public void setLandArea(short landArea) {
		this.landArea = landArea;
	}
}