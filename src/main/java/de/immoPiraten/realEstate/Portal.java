package de.immoPiraten.realEstate;

public enum Portal {
	None(0), ImmobilienScout24(1), Immonet(2), Immowelt(3), All(4);	
	
	private final int id;
	
	Portal(int id) { 
		this.id = id; 
	}
    
	public int getValue() { 
		return id; 
	}
}