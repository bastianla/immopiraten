package de.immoPiraten.search;

public enum SearchType {
	City(0), PostCode(1);
	
	private final int id;
	
	SearchType(int id) { 
		this.id = id; 
	}
    
	public int getValue() { 
		return id; 
	}
}