package de.immoPiraten.realEstate;

import javax.persistence.Entity;

@Entity
public class ResidentialRealEstate extends RealEstate {

	private double room;
	private double livingArea;
	private HeaterType heater;
	private boolean energyCertificate;
	private double energyConsumption;
	private float additionalCosts;
	private boolean balcony;
	private boolean terrace;
	private boolean garden;
	private boolean garage;
	private int construction;
	
	public double getRoom() {
		return room;
	}

	public void setRoom(double room) {
		this.room = room;
	}

	public double getLivingArea() {
		return livingArea;
	}

	public void setLivingArea(double livingArea) {
		this.livingArea = livingArea;
	}

	public HeaterType getHeater() {
		return heater;
	}

	public void setHeater(HeaterType heater) {
		this.heater = heater;
	}

	public boolean isEnergyCertificate() {
		return energyCertificate;
	}

	public void setEnergyCertificate(boolean energyCertificate) {
		this.energyCertificate = energyCertificate;
	}

	public double getEnergyConsumption() {
		return energyConsumption;
	}

	public void setEnergyConsumption(double energyConsumption) {
		this.energyConsumption = energyConsumption;
	}

	public float getAdditionalCosts() {
		return additionalCosts;
	}

	public void setAdditionalCosts(float additionalCosts) {
		this.additionalCosts = additionalCosts;
	}

	public boolean isBalcony() {
		return balcony;
	}

	public void setBalcony(boolean balcony) {
		this.balcony = balcony;
	}

	public boolean isTerrace() {
		return terrace;
	}

	public void setTerrace(boolean terrace) {
		this.terrace = terrace;
	}

	public boolean isGarden() {
		return garden;
	}

	public void setGarden(boolean garden) {
		this.garden = garden;
	}

	public boolean isGarage() {
		return garage;
	}

	public void setGarage(boolean garage) {
		this.garage = garage;
	}

	public int getConstruction() {
		return construction;
	}

	public void setConstruction(int construction) {
		this.construction = construction;
	}
}