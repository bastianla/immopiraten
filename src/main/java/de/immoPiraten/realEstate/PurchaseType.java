package de.immoPiraten.realEstate;

public enum PurchaseType {
	Buy(0), Rent(1);
	
	private final int id;
	
	PurchaseType(int id) { 
		this.id = id; 
	}
    
	public int getValue() { 
		return id; 
	}
}