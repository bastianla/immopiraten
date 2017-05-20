package de.immoPiraten.realEstate;

public enum RealEstateType {
	Flat(0), House(1), Plot(2);
	
	private final int id;
	
	RealEstateType(int id) { 
		this.id = id; 
	}
    
	public int getValue() { 
		return id; 
	}
}