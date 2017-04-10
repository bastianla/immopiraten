package de.immoPiraten.realEstate;

import javax.persistence.Entity;

@Entity
public class Plot {
	private int landArea;

	public int getLandArea() {
		return landArea;
	}

	public void setLandArea(int landArea) {
		this.landArea = landArea;
	}
}