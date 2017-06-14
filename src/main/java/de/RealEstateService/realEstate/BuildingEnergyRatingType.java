package de.RealEstateService.realEstate;

public enum BuildingEnergyRatingType {
	NO_INFORMATION(0),
	ENERGY_REQUIRED(1),
	ENERGY_CONSUMPTION(2);
	
	private final int id;
	
	BuildingEnergyRatingType(int id) { 
		this.id = id; 
	}
	   
	public int getValue() { 
		return id; 
	}	
}