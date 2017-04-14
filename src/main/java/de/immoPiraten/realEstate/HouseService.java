package de.immoPiraten.realEstate;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class HouseService {

	public List<House> getItems(String postCode, String city, int priceFrom, int priceTill, short livingAreaFrom,
			short livingAreaTill, short landAreaFrom, short landAreaTill, short roomFrom, short roomTill,
			short constructionYearFrom, short constructionYearTill, boolean balcony, boolean terrace, boolean garden,
			boolean garage, boolean commission) {
		return null;
	}
}
