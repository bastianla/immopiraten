package de.immoPiraten.realEstate;

public enum HeatingType {
	NO_INFORMATION(0),
	SELF_CONTAINED_CENTRAL_HEATING(1),
	STOVE_HEATING(2),
	CENTRAL_HEATING(3),
	COMBINED_HEAT_AND_POWER_PLANT(4),
	ELECTRIC_HEATING(5),
	DISTRICT_HEATING(6),
	FLOOR_HEATING(7),
	GAS_HEATING(8),
	WOOD_PELLET_HEATING(9),
	NIGHT_STORAGE_HEATER(10),
	OIL_HEATING(11),
	SOLAR_HEATING(12),
	HEAT_PUMP(13);
	
	private final int id;
	
	HeatingType(int id) { 
		this.id = id; 
	}
	   
	public int getValue() { 
		return id; 
	}	
}