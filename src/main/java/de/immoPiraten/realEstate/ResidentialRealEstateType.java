package de.immoPiraten.realEstate;

public enum ResidentialRealEstateType {
	NO_INFORMATION(0),
	SINGLE_FAMILY_HOUSE(1),
	MID_TERRACE_HOUSE(2),
	END_TERRACE_HOUSE(3),
	MULTI_FAMILY_HOUSE(4),
	BUNGALOW(5),
	FARMHOUSE(6),
	SEMIDETACHED_HOUSE(7),
	VILLA(8),
	CASTLE_MANOR_HOUSE(9),
	SPECIAL_REAL_ESTATE(10),
	ROOF_STOREY(11),
	LOFT(12),
	MAISONETTE(13),
	PENTHOUSE(14),
	TERRACED_FLAT(15),
	GROUND_FLOOR(16),
	APARTMENT(17),
	RAISED_GROUND_FLOOR(18),
	HALF_BASEMENT(19),
	OTHER(20);
	
	private final int id;
	
	ResidentialRealEstateType(int id) { 
		this.id = id; 
	}
	   
	public int getValue() { 
		return id; 
	}	
}
