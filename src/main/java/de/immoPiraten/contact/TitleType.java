package de.immoPiraten.contact;

public enum TitleType {
	NONE(0),
	MALE(1),
	FEMALE(2),
	COMPANY(3);
	
	private final int id;
	
	TitleType(int id) { 
		this.id = id; 
	}
	   
	public int getValue() { 
		return id; 
	}		
}
