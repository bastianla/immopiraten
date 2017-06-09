package de.immoPiraten.realEstate;

public enum ConditionType {
	FIRST_TIME_USE(0),
	FIRST_TIME_USE_AFTER_REFURBISHMENT(1),
	MINT_CONDITION(2),
	REFURBISHED(3),
	MODERNIZED(4),
	FULLY_RENOVATED(5),
	WELL_KEPT(6),
	NEED_OF_RENOVATION(7),
	NEGOTIABLE(8),
	RIPE_FOR_DEMOLITION(9);
		
	private final int id;
	
	ConditionType(int id) { 
		this.id = id; 
	}
	   
	public int getValue() { 
		return id; 
	}
}