package de.immoPiraten.realEstate;

import java.util.Comparator;
import java.util.Date;

class RealEstatePublicationDateComparator implements Comparator<RealEstate> {

	@Override
	public int compare(RealEstate arg0, RealEstate arg1) {
		Date arg0PublicationDate = arg0.getPublicationDate();
		Date arg1PublicationDate = arg1.getPublicationDate();
		
		if (arg0PublicationDate.before(arg1PublicationDate))
			return -1;
		
		if (arg0PublicationDate.after(arg1PublicationDate))
			return 1;
		
		return 0;
	}
}
