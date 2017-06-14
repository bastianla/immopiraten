package de.RealEstateService.realEstate;

public enum InteriorQualityType {
	NO_INFORMANTION(0),
	LUXURY(1),
	SOPHISTICATED(2),
	NORMAL(3),
	SIMPLE(4);
	
	private final int id;
	
	InteriorQualityType(int id) { 
		this.id = id; 
	}
	   
	public int getValue() { 
		return id; 
	}	
}