package de.immoPiraten.realEstate;

public enum FiringType {
	NO_INFORMATION(0),
	GEOTHERMAL(1),
	SOLAR_HEATING(2),
	PELLET_HEATING(3),
	GAS(4),
	OIL(5),
	DISTRICT_HEATING(6),
	ELECTRICITY(7),
	COAL(8);
	
	private final int id;
	
	FiringType(int id) { 
		this.id = id; 
	}
	   
	public int getValue() { 
		return id; 
	}	
}