package de.immoPiraten.realEstate;

import javax.persistence.Entity;

@Entity
public class Plot extends RealEstate {
	private int landArea;

	public int getLandArea() {
		return landArea;
	}

	public void setLandArea(int landArea) {
		this.landArea = landArea;
	}
}