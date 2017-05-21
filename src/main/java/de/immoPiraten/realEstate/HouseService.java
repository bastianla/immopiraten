package de.immoPiraten.realEstate;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.immoPiraten.ImmoScout24.Request;
import de.immoPiraten.ImmoScout24.Search;
import de.immoPiraten.site.Site;

@Service
public class HouseService {

	private static final String IMMOSCOUT_EXPOSE_BASE_URL = "https://www.immobilienscout24.de/expose/";
	
	public List<House> getItems(String postCode, String city, int priceFrom, int priceTill, double livingAreaFrom,
			double livingAreaTill, double landAreaFrom, double landAreaTill, short roomFrom, short roomTill,
			short constructionYearFrom, short constructionYearTill, boolean balcony, boolean terrace, boolean garden,
			boolean garage, boolean commission) {
		return this.getExamples();
	}

	@SuppressWarnings("unchecked")
	public House getExpose(int id) {
		String response = Search.getExpose(id).toString();

		try {
			LinkedHashMap<String, Object> result = new ObjectMapper().readValue(response, LinkedHashMap.class);

			LinkedHashMap<String, Object> expose = (LinkedHashMap<String, Object>) result.get("expose.expose");
			return this.getHouse(expose);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	// returns an immoScout24 range, if a from or a till value is specified,
	// otherwise null
	private String getRange(Object from, Object till) {
		StringBuilder rangeBuilder = new StringBuilder();	
		
		if (from != null)
			rangeBuilder.append(String.valueOf(from));

		if (till != null)
			rangeBuilder.append("-").append(String.valueOf(till));

		String range = null;
		if (rangeBuilder.length() > 0)
			range = rangeBuilder.toString();

		return range;
	}

	@SuppressWarnings("unchecked")
	public List<House> Search(RealEstateType realEstateType, PurchaseType purchaseType, String entityType, String input,
			byte radius, boolean freeOfCommission, Double livingAreaFrom, Double livingAreaTill, Integer priceFrom,
			Integer priceTill) {
		List<House> results = new ArrayList<House>();

		// maps the real estate type on the immoScout24 real estate type
		de.immoPiraten.ImmoScout24.RealEstateType immoScoutRealEstateType = Search.mapRealEstateType(realEstateType,
				purchaseType);

		// converts the living area for the immoScout24 requirements, they call
		// it living space
		String livingSpace = this.getRange(livingAreaFrom, livingAreaTill);

		// converts the price for the immoScout24 requirements
		String price = this.getRange(priceFrom, priceTill);

		String response = Search.Execute(immoScoutRealEstateType, entityType, input, radius, freeOfCommission,
				livingSpace, price);
		
		LinkedHashMap<String, Object> result = Request.getLinkedHashMap(response);
		LinkedHashMap<String, Object> resultList = (LinkedHashMap<String, Object>) result.get("resultlist.resultlist");

		ArrayList<Object> resultListEntries = (ArrayList<Object>) resultList.get("resultlistEntries");

		LinkedHashMap<String, Object> entry = (LinkedHashMap<String, Object>) resultListEntries.get(0);
		int numberOfHits = Integer.parseInt(entry.get("@numberOfHits").toString());

		if (numberOfHits > 1) {
			ArrayList<Object> exposes = (ArrayList<Object>) entry.get("resultlistEntry");

			// the result list is restricted to 20 entries
			int max = numberOfHits < 50 ? numberOfHits : 50;
			for (int index = 0; index < max; index++)
				try {
					results.add(this.getHouse(exposes.get(index)));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		} else {
			if (numberOfHits == 1) {
				try {
					results.add(this.getHouse(entry.get("resultlistEntry")));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return results;
	}

	@SuppressWarnings("unchecked")
	private House getHouse(Object json) throws ParseException {
		House house = new House();
		LinkedHashMap<String, Object> properties = (LinkedHashMap<String, Object>) json;
				
		// sets the publication date
		String rawDate = properties.get("@publishDate").toString();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS+hh:mm");
		Date parsedDate = null;
		try {
			parsedDate = df.parse(rawDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
		house.setPublicationDate(parsedDate);
				
		// sets the id
		String exposeId = properties.get("@id").toString();
		house.setId(Integer.parseInt(exposeId));

		// sets the origin portal
		house.setPortal(de.immoPiraten.realEstate.Portal.ImmobilienScout24);

		// sets the link to the origin expose
		house.setLink(HouseService.IMMOSCOUT_EXPOSE_BASE_URL.concat(exposeId));

		// sets the house properties
		LinkedHashMap<String, Object> realEstate = (LinkedHashMap<String, Object>) properties
				.get("resultlist.realEstate");

		if (realEstate == null)
			realEstate = (LinkedHashMap<String, Object>) properties.get("realEstate");

		// sets the purchase type
		String rawPurchaseType = realEstate.get("@xsi.type").toString();
		String purchaseType = rawPurchaseType.substring(rawPurchaseType.indexOf(':') + 1);
		house.setPurchaseType(Search.mapPurchaseType(de.immoPiraten.ImmoScout24.RealEstateType.valueOf(purchaseType)));

		// a real estate has always a title
		house.setTitle(realEstate.get("title").toString());

		// sets the description
		Object description = realEstate.get("descriptionNote");
		if (description != null)
			house.setDescription(description.toString());
		
		// sets the living area
		Object value = this.getJsonValueOrDefault(realEstate, "livingSpace", 0);
		if (value instanceof Integer) {
			int intValue = ((Integer) value).intValue();
			house.setLivingArea((double) intValue);
		} else {
			house.setLivingArea((double) value);
		}

		// sets the land area
		value = this.getJsonValueOrDefault(realEstate, "plotArea", 0);
		if (value instanceof Integer) {
			int intValue = ((Integer) value).intValue();
			house.setLandArea((double) intValue);
		} else {
			house.setLandArea((double) value);
		}

		// sets the number of rooms
		Object numberOfRooms = this.getJsonValueOrDefault(realEstate, "numberOfRooms", 0);
		if (numberOfRooms instanceof Integer)
			house.setRoom((double) ((int) numberOfRooms));
		else
			house.setRoom((double) numberOfRooms);

		// sets the site
		LinkedHashMap<String, Object> address = (LinkedHashMap<String, Object>) realEstate.get("address");

		Site site = new Site("Deutschland");

		value = address.get("street");
		if (value != null)
			site.setStreet(value.toString());

		value = address.get("houseNumber");
		if (value != null)
			site.setHouseNumber(value.toString());

		value = address.get("postcode");
		if (value != null)
			site.setPostCode(value.toString());

		value = address.get("city");
		if (value != null)
			site.setCity(value.toString());

		house.setSite(site);

		// sets the price
		LinkedHashMap<String, Object> price = (LinkedHashMap<String, Object>) realEstate.get("price");
		if (price != null)
		{
			Object priceValue = this.getJsonValueOrDefault(price, "value", 0.0);
			if (priceValue instanceof Integer)
				house.setPrice((double) ((int) priceValue));
			else
				house.setPrice((double) priceValue);
		}
		
		// sets the additional costs
		value = this.getJsonValueOrDefault(realEstate, "serviceCharge", 0);
		if (value instanceof Integer) {
			int intValue = ((Integer) value).intValue();
			house.setAdditionalCosts((double) intValue);
		} else {
			house.setAdditionalCosts((double) value);
		}
		
		// sets a value indicating whether a energy certificate is available
		house.setEnergyCertificate(Boolean.parseBoolean(
				this.getJsonValueOrDefault(realEstate, "energyPerformanceCertificate", false).toString()));

		// sets a value indicating whether a commission has to be paid by the
		// customer
		LinkedHashMap<String, Object> courtage = (LinkedHashMap<String, Object>) realEstate.get("courtage");

		if (courtage != null)
			house.setCommission(
					Boolean.parseBoolean(this.getJsonValueOrDefault(courtage, "hasCourtage", false).toString()));

		// sets a value indicating whether an energy performance certificate is available
		house.setEnergyCertificate(Boolean.parseBoolean(this.getJsonValueOrDefault(realEstate, "energyPerformanceCertificate", false).toString()));

		// sets the availability date
		Object freeFrom = realEstate.get("freeFrom");
		
		if (freeFrom != null)
		{
			rawDate = freeFrom.toString();
			df = new SimpleDateFormat("dd.MM.yyyy");
			parsedDate = null;
			try {
				parsedDate = df.parse(rawDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw e;
			}
			house.setAvailabilityDate(parsedDate);
		}
				
		// sets the image		
		LinkedHashMap<String, Object> titlePicture = (LinkedHashMap<String, Object>) realEstate.get("titlePicture");		
		if (titlePicture != null)
		{
			Object href = titlePicture.get("@xlink.href");
			if (href != null)
			{
				house.setImage(titlePicture.get("@xlink.href").toString());
			}
			else
			{
				ArrayList<LinkedHashMap<String, Object>> pictureUrlsContainer = (ArrayList<LinkedHashMap<String, Object>>)titlePicture.get("urls");
				ArrayList<LinkedHashMap<String, Object>> pictureUrls = (ArrayList<LinkedHashMap<String, Object>>)pictureUrlsContainer.get(0).get("url");
				house.setImage(pictureUrls.get(0).get("@href").toString());				
			}
		}
		
		return house;
	}

	private Object getJsonValueOrDefault(LinkedHashMap<String, Object> map, String key, Object defaultValue) {
		Object property = map.get(key);
		if (property != null) {
			if (property instanceof Integer)
				return ((Integer) property).intValue();
			return property;
		}

		return defaultValue;
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
