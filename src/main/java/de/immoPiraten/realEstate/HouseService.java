package de.immoPiraten.realEstate;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.immoPiraten.ImmoScout24.Request;
import de.immoPiraten.ImmoScout24.Search;
import de.immoPiraten.query.ResultsSorting;
import de.immoPiraten.site.Site;
import de.immoPiraten.utility.Parser;

@Service
public class HouseService {

	private static final String IMMOSCOUT_EXPOSE_BASE_URL = "https://www.immobilienscout24.de/expose/";

	@SuppressWarnings("unchecked")
	public House getExpose(Portal portal, int id) {
		
		if (portal == Portal.Immonet){
			return de.immoPiraten.ownPortal.Search.getExpose(id);
		}
		else{		
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
	public List<House> Search(Portal portal, RealEstateType realEstateType, PurchaseType purchaseType, String entityType, String input,
			byte radius, Boolean freeOfCommission, Double livingAreaFrom, Double livingAreaTill, Integer priceFrom,
			Integer priceTill, ResultsSorting sorting) {
		List<House> results = new ArrayList<House>();

		if (portal == Portal.ImmobilienScout24 || portal == Portal.All){		
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
		}
		
		if (portal == Portal.Immonet || portal == Portal.All){
			try {
				results.addAll(de.immoPiraten.ownPortal.Search.Execute(realEstateType, purchaseType, entityType, input,
						radius, freeOfCommission, livingAreaFrom, livingAreaTill, priceFrom, priceTill));
			} catch (Exception e) {
				e.printStackTrace();
			};
		}
				
		results.sort(this.getRealEstateComparator(sorting));
		
		return results;
	}

	private Comparator<RealEstate> getRealEstateComparator(ResultsSorting sorting)
	{
		if (sorting == null)
			sorting = ResultsSorting.PublicationDateDESC;
					
		switch (sorting)
		{
			case PublicationDateDESC:
				return Collections.reverseOrder(RealEstate.REAL_ESTATE_PUBLICATION_DATE_COMPARATOR);
				
			case PublicationDateASC:
				return RealEstate.REAL_ESTATE_PUBLICATION_DATE_COMPARATOR;
				
			case PriceDESC:
				return Collections.reverseOrder(RealEstate.REAL_ESTATE_PRICE_COMPARATOR);
				
			case PriceASC:
				return RealEstate.REAL_ESTATE_PRICE_COMPARATOR;
				
			default:
				return Collections.reverseOrder(RealEstate.REAL_ESTATE_PUBLICATION_DATE_COMPARATOR);
		}
	}
	
	@SuppressWarnings("unchecked")
	private House getHouse(Object json) throws ParseException {
		House house = new House();
		LinkedHashMap<String, Object> properties = (LinkedHashMap<String, Object>) json;

		// sets the publication date
		String rawDate = properties.get("@publishDate").toString();

		DateTimeZone zone = DateTimeZone.UTC;
		DateTime dt = new DateTime(rawDate, zone);
		house.setPublicationDate(dt.toDate());

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
				
		// sets the price
		LinkedHashMap<String, Object> price = (LinkedHashMap<String, Object>) realEstate.get("price");
		if (price != null)
			house.setPrice(this.parseDouble(price, "value", 0));

		// sets a value indicating whether a energy certificate is available
		house.setEnergyCertificate(Boolean.parseBoolean(
				this.getJsonValueOrDefault(realEstate, "energyPerformanceCertificate", false).toString()));

		// sets a value indicating whether a commission has to be paid by the
		// customer
		LinkedHashMap<String, Object> courtage = (LinkedHashMap<String, Object>) realEstate.get("courtage");

		if (courtage != null)
			house.setCommission(
					Boolean.parseBoolean(this.getJsonValueOrDefault(courtage, "hasCourtage", false).toString()));

		// sets a value indicating whether an energy performance certificate is
		// available
		house.setEnergyCertificate(Boolean.parseBoolean(
				this.getJsonValueOrDefault(realEstate, "energyPerformanceCertificate", false).toString()));

		// sets the availability date
		Object freeFrom = realEstate.get("freeFrom");

		if (freeFrom != null) {
			// house.setAvailabilityDate(this.parseDate(X, ));
			DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
			try {
				house.setAvailabilityDate(df.parse(freeFrom.toString()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw e;
			}
		}		

		house.setDescription(Parser.parseString(realEstate.get("descriptionNote")));
		house.setLivingArea(this.parseDouble(realEstate, "livingSpace", 0));
		house.setLandArea(this.parseDouble(realEstate, "plotArea", 0));
		house.setRoom(this.parseDouble(realEstate, "numberOfRooms", 0));
		house.setSite(this.getSite(realEstate));
		house.setAdditionalCosts(this.parseDouble(realEstate, "ServiceCharge", 0));
		house.setImage(this.getImageUrl(realEstate));
				
		return house;
	}

	@SuppressWarnings("unchecked")
	private String getImageUrl(LinkedHashMap<String, Object> realEstate) {
		LinkedHashMap<String, Object> titlePicture = (LinkedHashMap<String, Object>) realEstate.get("titlePicture");

		if (titlePicture != null) {
			Object href = titlePicture.get("@xlink.href");
			if (href != null) {
				return titlePicture.get("@xlink.href").toString();
			} else {
				ArrayList<LinkedHashMap<String, Object>> pictureUrlsContainer = (ArrayList<LinkedHashMap<String, Object>>) titlePicture
						.get("urls");
				ArrayList<LinkedHashMap<String, Object>> pictureUrls = (ArrayList<LinkedHashMap<String, Object>>) pictureUrlsContainer
						.get(0).get("url");
				return pictureUrls.get(0).get("@href").toString();
			}
		}

		return null;
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

	@SuppressWarnings("unchecked")
	private Site getSite(LinkedHashMap<String, Object> realEstate) {

		Object address = realEstate.get("address");

		if (address != null) {
			LinkedHashMap<String, Object> addressMap = (LinkedHashMap<String, Object>) address;

			Site site = new Site("Deutschland");
			site.setStreet(Parser.parseString(addressMap.get("street")));
			site.setHouseNumber(Parser.parseString(addressMap.get("houseNumber")));
			site.setPostCode(Parser.parseString(addressMap.get("postcode")));
			site.setCity(Parser.parseString(addressMap.get("city")));
			return site;
		}
		return null;
	}

	private double parseDouble(LinkedHashMap<String, Object> map, String key, double defaultValue) {
		Object value = this.getJsonValueOrDefault(map, key, defaultValue);

		Object property = map.get(key);
		if (property != null) {
			if (value instanceof Integer) {
				int intValue = ((Integer) value).intValue();
				return (double) intValue;
			} else {
				return (double) value;
			}
		}

		return defaultValue;
	}
}
