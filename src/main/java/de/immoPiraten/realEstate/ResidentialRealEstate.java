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
	
	private Short floor;
	private Short numberOfFloors;
	private Short numberOfBedRooms;
	private Short numberOfBathRooms;
	private Short numberOfParkingSpaces;
	private Float heatingCosts;
	private Boolean heatingCostsInAdditionalCosts;
	private String deposit;
	private Float thermalCharacteristic;
	private String locationNote;
	private String otherNote;
	private String furnishingNote;	
	private String energyEfficiencyClass;
	
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

	public Short getFloor() {
		return floor;
	}

	public void setFloor(Short floor) {
		this.floor = floor;
	}

	public Short getNumberOfFloors() {
		return numberOfFloors;
	}

	public void setNumberOfFloors(Short numberOfFloors) {
		this.numberOfFloors = numberOfFloors;
	}

	public Short getNumberOfBedRooms() {
		return numberOfBedRooms;
	}

	public void setNumberOfBedRooms(Short numberOfBedRooms) {
		this.numberOfBedRooms = numberOfBedRooms;
	}

	public Short getNumberOfBathRooms() {
		return numberOfBathRooms;
	}

	public void setNumberOfBathRooms(Short numberOfBathRooms) {
		this.numberOfBathRooms = numberOfBathRooms;
	}

	public Short getNumberOfParkingSpaces() {
		return numberOfParkingSpaces;
	}

	public void setNumberOfParkingSpaces(Short numberOfParkingSpaces) {
		this.numberOfParkingSpaces = numberOfParkingSpaces;
	}

	public Float getHeatingCosts() {
		return heatingCosts;
	}

	public void setHeatingCosts(Float heatingCosts) {
		this.heatingCosts = heatingCosts;
	}

	public Boolean getHeatingCostsInAdditionalCosts() {
		return heatingCostsInAdditionalCosts;
	}

	public void setHeatingCostsInAdditionalCosts(Boolean heatingCostsInAdditionalCosts) {
		this.heatingCostsInAdditionalCosts = heatingCostsInAdditionalCosts;
	}

	public String getDeposit() {
		return deposit;
	}

	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}

	public Float getThermalCharacteristic() {
		return thermalCharacteristic;
	}

	public void setThermalCharacteristic(Float thermalCharacteristic) {
		this.thermalCharacteristic = thermalCharacteristic;
	}

	public String getLocationNote() {
		return locationNote;
	}

	public void setLocationNote(String locationNote) {
		this.locationNote = locationNote;
	}

	public String getOtherNote() {
		return otherNote;
	}

	public void setOtherNote(String otherNote) {
		this.otherNote = otherNote;
	}

	public String getFurnishingNote() {
		return furnishingNote;
	}

	public void setFurnishingNote(String furnishingNote) {
		this.furnishingNote = furnishingNote;
	}

	public String getEnergyEfficiencyClass() {
		return energyEfficiencyClass;
	}

	public void setEnergyEfficiencyClass(String energyEfficiencyClass) {
		this.energyEfficiencyClass = energyEfficiencyClass;
	}
}