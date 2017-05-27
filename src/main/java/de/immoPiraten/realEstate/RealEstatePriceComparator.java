package de.immoPiraten.realEstate;

import java.util.Comparator;

public class RealEstatePriceComparator implements Comparator<RealEstate>{
	
	@Override
	public int compare(RealEstate arg0, RealEstate arg1) {
		double arg0Price = arg0.getPrice();
		double arg1Price = arg1.getPrice();
		
		if (arg0Price < arg1Price)
			return -1;
		
		if (arg0Price > arg1Price)
			return 1;
		
		return 0;
	}
	
}
