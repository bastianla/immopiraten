package de.immoPiraten.ImmoScout24;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.immoPiraten.APIException;
import de.immoPiraten.OAuth.OAuth;
import de.immoPiraten.realEstate.House;
import de.immoPiraten.realEstate.PurchaseType;
import de.immoPiraten.realEstate.RealEstateType;
import de.immoPiraten.search.SearchType;
import de.immoPiraten.site.Site;
import de.immoPiraten.utility.Parser;

public class Search {
	
	private static final String IMMOSCOUT_EXPOSE_BASE_URL = "https://www.immobilienscout24.de/expose/";	
	protected final static String CONTEXT = "search/";	
	
	/* CAUTION: Only supports House and Flat.*/
	public static de.immoPiraten.ImmoScout24.RealEstateType mapRealEstateType(de.immoPiraten.realEstate.RealEstateType realEstateType, PurchaseType purchaseType)
	{
		switch (realEstateType)
		{
		case House:
			if (purchaseType == PurchaseType.Buy)
				return de.immoPiraten.ImmoScout24.RealEstateType.HouseBuy;
			else
				return de.immoPiraten.ImmoScout24.RealEstateType.HouseRent;
		case Flat:
			if (purchaseType == PurchaseType.Buy)
				return de.immoPiraten.ImmoScout24.RealEstateType.ApartmentBuy;
			else
				return de.immoPiraten.ImmoScout24.RealEstateType.ApartmentRent;
		default:
			throw new IllegalArgumentException("Could not map the specified combination of real estate type and purchase type to an real estate type.");
		}
	}
	
	/* CAUTION: Only supports House and Apartment.*/
	public static de.immoPiraten.realEstate.RealEstateType mapRealEstateType(de.immoPiraten.ImmoScout24.RealEstateType realEstateType)
	{
		switch (realEstateType)
		{
		case ApartmentRent:
		case ApartmentBuy: 
			return de.immoPiraten.realEstate.RealEstateType.Flat;
		case HouseRent:
		case HouseBuy:
			return de.immoPiraten.realEstate.RealEstateType.House;
		default:
			throw new IllegalArgumentException("Could not map the specified real estate type.");
		}
	}
	
	/* CAUTION: Only supports House and Apartment.*/
	public static de.immoPiraten.realEstate.PurchaseType mapPurchaseType(de.immoPiraten.ImmoScout24.RealEstateType realEstateType)
	{
		switch (realEstateType)
		{
		case ApartmentBuy:
		case HouseBuy:
			return de.immoPiraten.realEstate.PurchaseType.Buy;
		case ApartmentRent:
		case HouseRent:
			return de.immoPiraten.realEstate.PurchaseType.Rent;
		default:
			throw new IllegalArgumentException("Could not map the specified real estate type to an purchase type.");
		}
	}
	
	/* 92756718 */
	private static String getExposeResponse(int id) throws APIException
	{		
		HttpGet request = new HttpGet(Request.BASE_URL + Search.CONTEXT + Request.VERSION + "expose/" + id);
		request.addHeader("accept", "application/json");
		HttpGet signedRequest = OAuth.Sign(request, Credentials.CONSUMER_KEY, Credentials.CONSUMER_SECRET, "getExpose");

		return Request.getResponse(signedRequest, "getExpose").toString();
	}
	
	private static String ExecuteSearch(de.immoPiraten.ImmoScout24.RealEstateType realEstateType, String entityType, String input, byte radius, Boolean freeOfCourtageOnly, String livingSpace, String price)
	{				
		GeoAutoCompletionService geoAutoCompletion = new GeoAutoCompletionService();
	    
		String entityId = geoAutoCompletion.getEntityId(entityType, input);
		GeoCode geoCode = geoAutoCompletion.getGeoCode(entityId);
		
		return Search.getSearchResult(realEstateType, geoCode, radius, freeOfCourtageOnly, livingSpace, price);
	}
	
	private static String getSearchResult(de.immoPiraten.ImmoScout24.RealEstateType realEstateType, GeoCode geoCode, byte radius, Boolean freeOfCourtageOnly, String livingSpace, String price)
	{		
		// example uri:
		// https://rest.immobilienscout24.de/restapi/api/search/v1.0/search/region?realestatetype=apartmentrent&geocodes=1276003001046
		
		// example result:
		// {"resultlist.resultlist":{"paging":{"next":{"@xlink.href":"https:\/\/rest.immobilienscout24.de\/restapi\/api\/search\/v1.0\/search\/region?realestatetype=HouseBuy&geocodes=1276010001&pagenumber=2"},"pageNumber":1,"pageSize":20,"numberOfPages":7,"numberOfHits":139,"numberOfListings":139},"resultlistEntries":[{"@numberOfHits":"139","@realEstateType":"3","resultlistEntry":[{"@xlink.href":"https:\/\/rest.immobilienscout24.de\/restapi\/api\/search\/v1.0\/expose\/93066284","@id":"93066284","@modification":"2017-05-02T11:59:37.487+02:00","@creation":"2017-01-31T14:34:04.000+01:00","@publishDate":"2017-01-31T14:34:04.000+01:00","realEstateId":93066284,"resultlist.realEstate":{"@xsi.type":"search:HouseBuy","@id":"93066284","title":"FRIEDERICH: Südviertel - Freist. Energieeffizienzhaus \/ Zweifamilienhaus in ruhiger Lage","address":{"postcode":"52066","city":"Aachen","quarter":"Burtscheider Abtei"},"companyWideCustomerId":"001.268614","titlePicture":{"@xlink.href":"https:\/\/pic.immobilienscout24.de\/pic\/orig04\/N\/569\/917\/659\/569917659-0.jpg\/ORIG\/resize\/60x60%3E\/extent\/60x60\/format\/jpg\/quality\/50","@id":"93066284","@modification":"2017-05-06T18:21:50.631+02:00","@creation":"2017-05-06T18:21:50.631+02:00","@publishDate":"2017-05-06T18:21:50.631+02:00","floorplan":"false","titlePicture":"true","urls":[{"url":[{"@scale":"SCALE_118x118","@href":"https:\/\/pic.immobilienscout24.de\/pic\/orig04\/N\/569\/917\/659\/569917659-0.jpg\/ORIG\/resize\/118x118%3E\/extent\/118x118\/format\/jpg\/quality\/50"},{"@scale":"SCALE_210x210","@href":"https:\/\/pic.immobilienscout24.de\/pic\/orig04\/N\/569\/917\/659\/569917659-0.jpg\/ORIG\/resize\/210x210%3E\/format\/jpg\/quality\/50"},{"@scale":"SCALE","@href":"https:\/\/pic.immobilienscout24.de\/pic\/orig04\/N\/569\/917\/659\/569917659-0.jpg\/ORIG\/resize\/%WIDTH%x%HEIGHT%%3E\/format\/jpg\/quality\/50"},{"@scale":"SCALE_AND_CROP","@href":"https:\/\/pic.immobilienscout24.de\/pic\/orig04\/N\/569\/917\/659\/569917659-0.jpg\/ORIG\/legacy_thumbnail\/%WIDTH%x%HEIGHT%\/format\/jpg\/quality\/50"},{"@scale":"WHITE_FILLING","@href":"https:\/\/pic.immobilienscout24.de\/pic\/orig04\/N\/569\/917\/659\/569917659-0.jpg\/ORIG\/resize\/%WIDTH%x%HEIGHT%%3E\/extent\/%WIDTH%x%HEIGHT%\/format\/jpg\/quality\/50"}]}]},"floorplan":"false","streamingVideo":"false","listingType":"XL","showcasePlacementColor":"#343434","privateOffer":"false","realtorLogo":"https:\/\/pic.immobilienscout24.de\/pic\/orig02\/L\/411\/851\/877\/411851877-0.png","realtorLogoForResultList":{"@xsi.type":"common:Picture","@xlink.href":"https:\/\/pic.immobilienscout24.de\/pic\/orig02\/L\/411\/851\/877\/411851877-0.png","floorplan":"false","titlePicture":"false","urls":[{"url":[{"@scale":"SCALE","@href":"https:\/\/pic.immobilienscout24.de\/pic\/orig02\/L\/411\/851\/877\/411851877-0.png\/ORIG\/resize\/%WIDTH%x%HEIGHT%%3E\/quality\/50"},{"@scale":"SCALE_AND_CROP","@href":"https:\/\/pic.immobilienscout24.de\/pic\/orig02\/L\/411\/851\/877\/411851877-0.png\/ORIG\/legacy_thumbnail\/%WIDTH%x%HEIGHT%\/format\/jpg\/quality\/50"},{"@scale":"WHITE_FILLING","@href":"https:\/\/pic.immobilienscout24.de\/pic\/orig02\/L\/411\/851\/877\/411851877-0.png\/ORIG\/resize\/%WIDTH%x%HEIGHT%%3E\/extent\/%WIDTH%x%HEIGHT%\/format\/jpg\/quality\/50"}]}]},"price":{"value":1095000,"currency":"EUR","marketingType":"PURCHASE","priceIntervalType":"ONE_TIME_CHARGE"},"livingSpace":311,"plotArea":728,"numberOfRooms":10,"energyPerformanceCertificate":"true","courtage":{"hasCourtage":"YES"},"guestToilet":"true","cellar":"true","isBarrierFree":"false"}},{"@xlink.href":"https:\/\/rest.immobilienscout24.de\/restapi\/api\/search\/v1.0\/expose\/94646591","@id":"94646591","@modification":"2017-05-02T14:54:33.829+02:00","@creation":"2017-04-14T14:12:35.000+02:00","@publishDate":"2017-04-14T14:12:35.000+02:00","realEstateId":94646591,"resultlist.realEstate":{"@xsi.type":"search:HouseBuy","@id":"94646591","title":"Einzigartiger Bungalow mit Garten in Bestlage von Aachen City","address":{"street":"Gut Steeg","houseNumber":"458","postcode":"52062","city":"Aachen","quarter":"Hangeweiher","wgs84Coordinate":{"latitude":50.75006483510182,"longitude":6.066972966867468},"preciseHouseNumber":"true"},"companyWideCustomerId":"001.12858801","titlePicture":{"@xlink.href":"https:\/\/pic.immobilienscout24.de\/pic\/orig04\/N\/574\/765\/939\/574765939-0.jpg\/ORIG\/resize\/60x60%3E\/extent\/60x60\/format\/jpg\/quality\/50","@id":"94646591","@modification":"2017-05-06T18:21:50.631+02:00","@creation":"2017-05-06T18:21:50.631+02:00","@publishDate":"2017-05-06T18:21:50.631+02:00","floorplan":"false","titlePicture":"true","urls":[{"url":[{"@scale":"SCALE_118x118","@href":"https:\/\/pic.immobilienscout24.de\/pic\/orig04\/N\/574\/765\/939\/574765939-0.jpg\/ORIG\/resize\/118x118%3E\/extent\/118x118\/format\/jpg\/quality\/50"},{"@scale":"SCALE_210x210","@href":"https:\/\/pic.immobilienscout24.de\/pic\/orig04\/N\/574\/765\/939\/574765939-0.jpg\/ORIG\/resize\/210x210%3E\/format\/jpg\/quality\/50"},{"@scale":"SCALE","@href":"https:\/\/pic.immobilienscout24.de\/pic\/orig04\/N\/574\/765\/939\/574765939-0.jpg\/ORIG\/resize\/%WIDTH%x%HEIGHT%%3E\/format\/jpg\/quality\/50"},{"@scale":"SCALE_AND_CROP","@href":"https:\/\/pic.immobilienscout24.de\/pic\/orig04\/N\/574\/765\/939\/574765939-0.jpg\/ORIG\/legacy_thumbnail\/%WIDTH%x%HEIGHT%\/format\/jpg\/quality\/50"},{"@scale":"WHITE_FILLING","@href":"https:\/\/pic.immobilienscout24.de\/pic\/orig04\/N\/574\/765\/939\/574765939-0.jpg\/ORIG\/resize\/%WIDTH%x%HEIGHT%%3E\/extent\/%WIDTH%x%HEIGHT%\/format\/jpg\/quality\/50"}]}]},"floorplan":"true","streamingVideo":"false","listingType":"L","privateOffer":"false","realtorLogo":"https:\/\/pic.immobilienscout24.de\/pic\/orig04\/L\/397\/274\/479\/397274479-0.jpg","realtorLogoForResultList":{"@xsi.type":"common:Picture","@xlink.href":"https:\/\/pic.immobilienscout24.de\/pic\/orig04\/L\/397\/274\/479\/397274479-0.jpg","floorplan":"false","titlePicture":"false","urls":[{"url":[{"@scale":"SCALE","@href":"https:\/\/pic.immobilienscout24.de\/pic\/orig04\/L\/397\/274\/479\/397274479-0.jpg\/ORIG\/resize\/%WIDTH%x%HEIGHT%%3E\/quality\/50"},{"@scale":"SCALE_AND_CROP","@href":"https:\/\/pic.immobilienscout24.de\/pic\/orig04\/L\/397\/274\/479\/397274479-0.jpg\/ORIG\/legacy_thumbnail\/%WIDTH%x%HEIGHT%\/format\/jpg\/quality\/50"},{"@scale":"WHITE_FILLING","@href":"https:\/\/pic.immobilienscout24.de\/pic\/orig04\/L\/397\/274\/479\/397274479-0.jpg\/ORIG\/resize\/%WIDTH%x%HEIGHT%%3E\/extent\/%WIDTH%x%HEIGHT%\/format\/jpg\/quality\/50"}]}]},"price":{"value":1550000,"currency":"EUR","marketingType":"PURCHASE","priceIntervalType":"ONE_TIME_CHARGE"},"livingSpace":400,"plotArea":700,"numberOfRooms":8,"courtage":{"hasCourtage":"YES"},"guestToilet":"true","cellar":"false","isBarrierFree":"false"}},{"@xlink.href":"https:\/\/rest.immobilienscout24.de\/restapi\/api\/search\/v1.0\/expose\/94944343","@id":"94944343","@modification":"2017-04-27T12:04:39.614+02:00","@creation":"2016-10-24T12:11:20.000+02:00","@publishDate":"2016-10-24T12:11:20.000+02:00","realEstateId":94944343,"resultlist.realEstate":{"@xsi.type":"search:HouseBuy","@id":"94944343","title":"Courté, energetisch hochwertige Doppelhaushälfte in kinderfreundlicher Umgebung!","address":{"postcode":"52078","city":"Aachen","quarter":"Stadtbezirk Aachen-Brand"},"companyWideCustomerId":"001.590254","titlePicture":{"@xlink.href":"https:\/\/pic.immobilienscout24.de\/pic\/orig03\/N\/578\/605\/918\/578605918-0.jpg\/ORIG\/resize\/60x60%3E\/extent\/60x60\/format\/jpg\/quality\/50","@id":"94944343","@modification":"2017-05-06T18:21:50.632+02:00","@creation":"2017-05-06T18:21:50.632+02:00","@publishDate":"2017-05-06T18:21:50.632+02:00","floorplan":"false","titlePicture":"true","urls":[{"url":[{"@scale":"SCALE_118x118","@href":"https:\/\/pic.immobilienscout24.de\/pic\/orig03\/N\/578\/605\/918\/578605918-0.jpg\/ORIG\/resize\/118x118%3E\/extent\/118x118\/format\/jpg\/quality\/50"},{"@scale":"SCALE_210x210","@href":"https:\/\/pic.immobilienscout24.de\/pic\/orig03\/N\/578\/605\/918\/578605918-0.jpg\/ORIG\/resize\/210x210%3E\/format\/jpg\/quality\/50"},{"@scale":"SCALE","@href":"https:\/\/pic.immobilienscout24.de\/pic\/orig03\/N\/578\/605\/918\/578605918-0.jpg\/ORIG\/resize\/%WIDTH%x%HEIGHT%%3E\/format\/jpg\/quality\/50"},{"@scale":"SCALE_AND_CROP","@href":"https:\/\/pic.immobilienscout24.de\/pic\/orig03\/N\/578\/605\/918\/578605918-0.jpg\/ORIG\/legacy_thumbnail\/%WIDTH%x%HEIGHT%\/format\/jpg\/quality\/50"},{"@scale":"WHITE_FILLING","@href":"https:\/\/pic.immobilienscout24.de\/pic\/orig03\/N\/578\/605\/918\/578605918-0.jpg\/ORIG\/resize\/%WIDTH%x%HEIGHT%%3E\/extent\/%WIDTH%x%HEIGHT%\/format\/jpg\/quality\/50"}]}]},"floorplan":"true","streamingVideo":"false","listingType":"L","privateOffer":"false","realtorLogo":"https:\/\/pic.immobilienscout24.de\/pic\/orig02\/L\/302\/175\/65\/302175065-0.png","realtorLogoForResultList":{"@xsi.type":"common:Picture","@xlink.href":"https:\/\/pic.immobilienscout24.de\/pic\/orig02\/L\/302\/175\/65\/302175065-0.png","floorplan":"false","titlePicture":"false","urls":[{"url":[{"@scale":"SCALE","@href":"https:\/\/pic.immobilienscout24.de\/pic\/orig02\/L\/302\/175\/65\/302175065-0.png\/ORIG\/resize\/%WIDTH%x%HEIGHT%%3E\/quality\/50"},{"@scale":"SCALE_AND_CROP","@href":"https:\/\/pic.immobilienscout24.de\/pic\/orig02\/L\/302\/175\/65\/302175065-0.png\/ORIG\/legacy_thumbnail\/%WIDTH%x%HEIGHT%\/format\/jpg\/quality\/50"},{"@scale":"WHITE_FILLING","@href":"https:\/\/pic.immobilienscout24.de\/pic\/orig02\/L\/302\/175\/65\/302175065-0.png\/ORIG\/resize\/%WIDTH%x%HEIGHT%%3E\/extent\/%WIDTH%x%HEIGHT%\/format\/jpg\/quality\/50"}]}]},"price":{"value":550000,"currency":"EUR","marketingType":"PURCHASE","priceIntervalType":"ONE_TIME_CHARGE"},"livingSpace":227,"plotArea":382,"numberOfRooms":7,"energyPerformanceCertificate":"true","courtage":{"hasCourtage":"YES"},"guestToilet":"true","cellar":"true","isBarrierFree":"false"}},{"@xlink.href":"https:\/\/rest.immobilienscout24.de\/restapi\/api\/search\/v1.0\/e... 
		
		// gets the uri builder to build the request
		URIBuilder uriBuilder = null;
		try {
			uriBuilder = new URIBuilder(Request.BASE_URL + Search.CONTEXT + Request.VERSION + Search.CONTEXT + "/radius");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// converts the required geo coordinates for the request into a string
		String geoCoordinates = geoCode.getLatitude() + ";" + geoCode.getLongitude() + ";" + radius;
		
		// adds required parameters
		uriBuilder.addParameter("realestatetype", realEstateType.name());
		uriBuilder.addParameter("geocoordinates", geoCoordinates);
		
		// adds the optional page size, default is 20
		uriBuilder.addParameter("pagesize", "50");
		
		// adds optional query parameters
		if (livingSpace != null)
			uriBuilder.addParameter("livingSpace", livingSpace);
		if (price != null)
			uriBuilder.addParameter("price", price);
		if (freeOfCourtageOnly != null)
			uriBuilder.addParameter("freeofcourtageonly", freeOfCourtageOnly.toString());
		
		// gets the signed request
		HttpGet request = new HttpGet(uriBuilder.toString());
		request.addHeader("accept", "application/json");
		HttpGet signedRequest = OAuth.Sign(request, Credentials.CONSUMER_KEY, Credentials.CONSUMER_SECRET, "getSearchResults");
			
		// executes the request and returns the response
		return Request.getResponse(signedRequest, "searchRegion").toString();
	}
	
	@SuppressWarnings("unchecked")
	private static House getHouse(Object json) throws ParseException {
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
		house.setLink(Search.IMMOSCOUT_EXPOSE_BASE_URL.concat(exposeId));

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
			house.setPrice(Search.parseDouble(price, "value", 0));

		// sets a value indicating whether a energy certificate is available
		house.setEnergyCertificate(Boolean.parseBoolean(
				Search.getJsonValueOrDefault(realEstate, "energyPerformanceCertificate", false).toString()));

		// sets a value indicating whether a commission has to be paid by the
		// customer
		LinkedHashMap<String, Object> courtage = (LinkedHashMap<String, Object>) realEstate.get("courtage");

		if (courtage != null)
			house.setCommission(
					Boolean.parseBoolean(Search.getJsonValueOrDefault(courtage, "hasCourtage", false).toString()));

		// sets a value indicating whether an energy performance certificate is
		// available
		house.setEnergyCertificate(Boolean.parseBoolean(
				Search.getJsonValueOrDefault(realEstate, "energyPerformanceCertificate", false).toString()));

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
		house.setLivingArea(Search.parseDouble(realEstate, "livingSpace", 0));
		house.setLandArea(Search.parseDouble(realEstate, "plotArea", 0));
		house.setRoom(Search.parseDouble(realEstate, "numberOfRooms", 0));
		house.setSite(Search.getSite(realEstate));
		house.setAdditionalCosts(Search.parseDouble(realEstate, "ServiceCharge", 0));
		house.setImage(Search.getImageUrl(realEstate));
				
		return house;
	}

	@SuppressWarnings("unchecked")
	private static String getImageUrl(LinkedHashMap<String, Object> realEstate) {
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

	private static Object getJsonValueOrDefault(LinkedHashMap<String, Object> map, String key, Object defaultValue) {
		Object property = map.get(key);
		if (property != null) {
			if (property instanceof Integer)
				return ((Integer) property).intValue();
			return property;
		}

		return defaultValue;
	}

	@SuppressWarnings("unchecked")
	private static Site getSite(LinkedHashMap<String, Object> realEstate) {

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

	private static double parseDouble(LinkedHashMap<String, Object> map, String key, double defaultValue) {
		Object value = Search.getJsonValueOrDefault(map, key, defaultValue);

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
	
	// returns an immoScout24 range, if a from or a till value is specified,
	// otherwise null
	private static String getRange(Object from, Object till) {
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
	public static House getExpose(int id) {
		String response = Search.getExposeResponse(id);
		
		try {
			LinkedHashMap<String, Object> result = new ObjectMapper().readValue(response, LinkedHashMap.class);

			LinkedHashMap<String, Object> expose = (LinkedHashMap<String, Object>) result.get("expose.expose");
			return Search.getHouse(expose);

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
	
	@SuppressWarnings("unchecked")	
	public static List<House> Execute(RealEstateType realEstateType, PurchaseType purchaseType, SearchType searchType, String input,
			Byte radius, Boolean freeOfCommission, Double livingAreaFrom, Double livingAreaTill, Integer priceFrom,
			Integer priceTill){
		
		String entityType = Search.mapGeoType(searchType);	
		
		List<House> results = new ArrayList<House>();		
		
		// maps the real estate type on the immoScout24 real estate type
		de.immoPiraten.ImmoScout24.RealEstateType immoScoutRealEstateType = Search.mapRealEstateType(realEstateType, purchaseType);

		// converts the living area for the immoScout24 requirements, they call
		// it living space
		String livingSpace = Search.getRange(livingAreaFrom, livingAreaTill);

		// converts the price for the immoScout24 requirements
		String price = Search.getRange(priceFrom, priceTill);

		String response = Search.ExecuteSearch(immoScoutRealEstateType, entityType, input, radius, freeOfCommission,
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
					results.add(Search.getHouse(exposes.get(index)));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		} else {
			if (numberOfHits == 1) {
				try {
					results.add(Search.getHouse(entry.get("resultlistEntry")));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return results;
	}
	
	private static String mapGeoType(SearchType searchType)
	{
		String geoType = null;
		
		switch(searchType){
		case PostCode:
			geoType= GeoAutoCompletionService.ENTITY_TYPE_POSTCODE;
			break;
		default:
			geoType= GeoAutoCompletionService.ENTITY_TYPE_CITY;
		}
		
		return geoType;
	}
}