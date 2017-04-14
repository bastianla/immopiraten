package de.immoPiraten.realEstate;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class FlatService {

	public List<Flat> getItems(String postCode, String city, int priceFrom, int priceTill, short livingAreaFrom,
			short livingAreaTill, short roomFrom, short roomTill, short constructionYearFrom,
			short constructionYearTill, boolean balcony, boolean terrace, boolean garden, boolean garage,
			boolean commission) {
		return null;
	}
}
