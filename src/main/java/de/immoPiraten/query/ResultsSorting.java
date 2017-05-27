package de.immoPiraten.query;

public enum ResultsSorting {
	PublicationDateDESC(0), PublicationDateASC(1), PriceDESC(2), PriceASC(3);
	
	private final int id;
	
	ResultsSorting(int id) { 
		this.id = id; 
	}
    
	public int getValue() { 
		return id; 
	}
}