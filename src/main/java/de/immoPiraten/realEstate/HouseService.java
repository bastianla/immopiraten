package de.immoPiraten.realEstate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import de.immoPiraten.ImmoScout24.Search;

@Service
public class HouseService {

	public List<House> getItems(String postCode, String city, int priceFrom, int priceTill, short livingAreaFrom,
			short livingAreaTill, short landAreaFrom, short landAreaTill, short roomFrom, short roomTill,
			short constructionYearFrom, short constructionYearTill, boolean balcony, boolean terrace, boolean garden,
			boolean garage, boolean commission) {
		return this.getExamples();
	}
	
	public String getExpose(int id) {
		return Search.getExpose(id).toString();
	}
	
	private List<House> getExamples() {

		List<House> examples = new ArrayList<House>();

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2017);
		cal.set(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date lowerBound = cal.getTime();
		
		cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2017);
		cal.set(Calendar.MONTH, 9);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date upperBound = cal.getTime();

		for (int i = 0; i < 10; i++) {
			House example = new House();
			example.setTitle("BeispielImmobilie Nr. " + i);
			example.setDescription("Dies ist eine beispielhafte Beschreibung von " + example.getTitle());
			example.setLink("http://www.immobeispiele.org/beispiele?id=123");
			example.setPortal(Portal.values()[i % 3]);
			example.setPrice(1000 + i);
			example.setCommission(i % 2 == 1);
			example.setAvailabilityDate(
					new Date(ThreadLocalRandom.current().nextLong(lowerBound.getTime(), upperBound.getTime())));
			example.setPurchaseType(PurchaseType.Rent);
			example.setPublicationDate(
					new Date(ThreadLocalRandom.current().nextLong(lowerBound.getTime(), new Date().getTime())));

			examples.add(example);
		}

		return examples;
	}
}
