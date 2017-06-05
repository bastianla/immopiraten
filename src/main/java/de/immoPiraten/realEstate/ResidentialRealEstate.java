package de.immoPiraten.realEstate;

import javax.persistence.Entity;

@Entity
public class ResidentialRealEstate extends RealEstate {

	private float room;
	private short livingArea;
	private HeaterType heater;
	private boolean energyCertificate;
	private float energyConsumption;
	private float additionalCosts;
	private boolean balcony;
	private boolean terrace;
	private boolean garden;
	private boolean garage;
	private short construction;
	
	public float getRoom() {
		return room;
	}

	public void setRoom(float room) {
		this.room = room;
	}

	public short getLivingArea() {
		return livingArea;
	}

	public void setLivingArea(short livingArea) {
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

	public float getEnergyConsumption() {
		return energyConsumption;
	}

	public void setEnergyConsumption(float energyConsumption) {
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

	public short getConstruction() {
		return construction;
	}

	public void setConstruction(short construction) {
		this.construction = construction;
	}
}