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
import de.immoPiraten.contact.Contact;
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
	
	private static String executeSearch(de.immoPiraten.ImmoScout24.RealEstateType realEstateType, String entityType, String input,
			byte radius, Boolean freeOfCourtageOnly, String livingSpace, String price, String ground, String numberOfRooms,
			String constructionyear, Boolean balcony, Boolean garden)
	{				
		GeoAutoCompletionService geoAutoCompletion = new GeoAutoCompletionService();
	    
		String entityId = geoAutoCompletion.getEntityId(entityType, input);
		GeoCode geoCode = geoAutoCompletion.getGeoCode(entityId);
		
		return Search.getSearchResult(realEstateType, geoCode, radius, freeOfCourtageOnly, livingSpace, price, ground, numberOfRooms,
				constructionyear, balcony, garden);
	}
	
	private static String getSearchResult(de.immoPiraten.ImmoScout24.RealEstateType realEstateType, GeoCode geoCode, byte radius, 
			Boolean freeOfCourtageOnly, String livingSpace, String price, String ground, String numberOfRooms, String constructionyear,
			Boolean balcony, Boolean garden)
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
		if (ground != null)
			uriBuilder.addParameter("ground", ground);
		if (numberOfRooms != null)
			uriBuilder.addParameter("numberofrooms", numberOfRooms);
		if (constructionyear != null)
			uriBuilder.addParameter("constructionyear", constructionyear);		
		if (balcony != null)
			uriBuilder.addParameter("balcony", balcony.toString());
		if (garden != null)
			uriBuilder.addParameter("garden", garden.toString());
		
		// TODO Implement terrace and garage
		
		// gets the signed request
		HttpGet request = new HttpGet(uriBuilder.toString());
		request.addHeader("accept", "application/json");
		HttpGet signedRequest = OAuth.Sign(request, Credentials.CONSUMER_KEY, Credentials.CONSUMER_SECRET, "getSearchResults");
			
		// executes the request and returns the response
		return Request.getResponse(signedRequest, "searchRegion").toString();
	}
	
	@SuppressWarnings("unchecked")
	private static House getHouse(Object json, int imageWidth, int imageHeight) throws ParseException {
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
		LinkedHashMap<String, Object> realEstate = (LinkedHashMap<String, Object>) properties.get("resultlist.realEstate");
		
		if (realEstate == null)
			realEstate = (LinkedHashMap<String, Object>) properties.get("realEstate");

		// sets the purchase type
		String rawPurchaseType = realEstate.get("@xsi.type").toString();
		String purchaseType = rawPurchaseType.substring(rawPurchaseType.indexOf(':') + 1);
		house.setPurchaseType(Search.mapPurchaseType(de.immoPiraten.ImmoScout24.RealEstateType.valueOf(purchaseType)));
		
		// a real estate has always a title
		house.setTitle(realEstate.get("title").toString());

		// sets the price
		// (Important: In the expose response a rent object includes the price in the baseRent property
		//			   and in a search response at a price-value property)
		Double price = null;
		LinkedHashMap<String, Object> priceMap = (LinkedHashMap<String, Object>) realEstate.get("price");
		if (priceMap != null)
			price = Parser.parseDouble(Search.getJsonValue(priceMap, "value"));
		else
			price = Parser.parseDouble(Search.getJsonValue(realEstate, "baseRent"));		
		
		if (price != null)
			house.setPrice(price.intValue());

		// sets a value indicating whether a energy certificate is available
		Boolean energyCertificate = Parser.parseBoolean(Search.getJsonValue(realEstate, "energyPerformanceCertificate"));
		if (energyCertificate != null)
			house.setEnergyCertificate(energyCertificate);		
		
		// sets a value indicating whether a commission has to be paid by the customer		
		Boolean courtage = null;
		LinkedHashMap<String, Object> courtageMap = (LinkedHashMap<String, Object>)Search.getJsonValue(realEstate, "courtage");		
		if (courtageMap != null)
			courtage = Parser.parseBoolean(Search.getJsonValue(realEstate, "hasCourtage"));
		
		if (courtage != null)
			house.setCommission(courtage);

		// sets the availability date
		Object freeFrom = realEstate.get("freeFrom");
		if (freeFrom != null) {
			DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
			try {
				house.setAvailabilityDate(df.parse(freeFrom.toString()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
			}
		}

		String description = Parser.parseString(realEstate.get("descriptionNote"));
		if (description != null)
			house.setDescription(description);
		
		Float livingArea = Parser.parseFloat(Search.getJsonValue(realEstate, "livingSpace"));
		if (livingArea != null)
			house.setLivingArea(livingArea.shortValue());
		
		Float landArea = Parser.parseFloat(Search.getJsonValue(realEstate, "plotArea"));
		if (landArea != null)
			house.setLandArea(landArea.shortValue());
		
		Float room = Parser.parseFloat(Search.getJsonValue(realEstate, "numberOfRooms"));
		if (room != null)
			house.setRoom(room);

		Short construction = Parser.parseShort(Search.getJsonValue(realEstate, "constructionYear"));
		if (construction != null)
			house.setConstruction(construction);
		
		Boolean balcony = Parser.parseBoolean(Search.getJsonValue(realEstate, "balcony"));
		if (balcony != null)
			house.setBalcony(balcony);
		
		Boolean garden = Parser.parseBoolean(Search.getJsonValue(realEstate, "garden"));
		if(garden != null)
			house.setGarden(garden);			
		
		house.setSite(Search.getSite(realEstate));
		
		// side cost at rent real estate; house money at condominium
		Float additionalCosts = Parser.parseFloat(Search.getJsonValue(realEstate, "ServiceCharge"));
		if (additionalCosts == null)
			additionalCosts = Parser.parseFloat(Search.getJsonValue(realEstate, "rentSubsidy"));
		
		if (additionalCosts != null)
			house.setAdditionalCosts(additionalCosts);
		
		house.setImage(Search.getImageUrl(realEstate, imageWidth, imageHeight));
				
		Short floor = Parser.parseShort(Search.getJsonValue(realEstate, "floor"));
		if (floor != null)
			house.setFloor(floor);
		
		Short numberOfFloors = Parser.parseShort(Search.getJsonValue(realEstate, "numberOfFloors"));
		if (numberOfFloors != null)
			house.setNumberOfFloors(numberOfFloors);		
		
		Short numberOfBedRooms = Parser.parseShort(Search.getJsonValue(realEstate, "numberOfBedRooms"));
		if (numberOfBedRooms != null)
			house.setNumberOfBedRooms(numberOfBedRooms);
		
		Short numberOfBathRooms = Parser.parseShort(Search.getJsonValue(realEstate, "numberOfBathRooms"));
		if (numberOfBathRooms != null)
			house.setNumberOfBathRooms(numberOfBathRooms);
		
		Short numberOfParkingSpaces = Parser.parseShort(Search.getJsonValue(realEstate, "numberOfParkingSpaces"));
		if (numberOfParkingSpaces != null)
			house.setNumberOfParkingSpaces(numberOfParkingSpaces);		
		
		Float heatingCosts = Parser.parseFloat(Search.getJsonValue(realEstate, "heatingCosts"));
		if (heatingCosts != null)
			house.setHeatingCosts(heatingCosts);
		
		Boolean heatingCostsInAdditionalCosts = Parser.parseBoolean(Search.getJsonValue(realEstate, "heatingCostsInServiceCharge"));
		if (heatingCostsInAdditionalCosts != null)
			house.setHeatingCostsInAdditionalCosts(heatingCostsInAdditionalCosts);
		
		String deposit = Parser.parseString(Search.getJsonValue(realEstate, "deposit"));
		if (deposit != null)
			house.setDeposit(deposit);		
		
		Float thermalCharacteristic = Parser.parseFloat(Search.getJsonValue(realEstate, "thermalCharacteristic"));
		if (thermalCharacteristic != null)
			house.setThermalCharacteristic(thermalCharacteristic);		
		
		String locationNote = Parser.parseString(Search.getJsonValue(realEstate, "locationNote"));
		if (locationNote != null)
			house.setLocationNote(locationNote);
		
		String otherNote = Parser.parseString(Search.getJsonValue(realEstate, "otherNote"));
		if (otherNote != null)
			house.setOtherNote(otherNote);
		
		String furnishingNote = Parser.parseString(Search.getJsonValue(realEstate, "furnishingNote"));
		if (furnishingNote != null)
			house.setFurnishingNote(furnishingNote);		
		
		String energyEfficiencyClass = null;
		LinkedHashMap<String, Object> energyCertificateMap = (LinkedHashMap<String, Object>)Search.getJsonValue(realEstate, "energyCertificate");
		if (energyCertificateMap != null)
			energyEfficiencyClass = Parser.parseString(Search.getJsonValue(energyCertificateMap, "energyEfficiencyClass"));
		
		if (energyEfficiencyClass != null)
			house.setEnergyEfficiencyClass(energyEfficiencyClass);		
		
		// important: JPA can't persist the property name condition. Therefore the property was renamed from condition to objectState.   
		de.immoPiraten.realEstate.ConditionType condition = Parser.parseEnum(Search.getJsonValue(realEstate, "condition"), de.immoPiraten.realEstate.ConditionType.class);		
		if (condition != null)
			house.setObjectstate(condition);
		
		de.immoPiraten.realEstate.InteriorQualityType interiorQuality = Parser.parseEnum(Search.getJsonValue(realEstate, "interiorQuality"), de.immoPiraten.realEstate.InteriorQualityType.class);		
		if (interiorQuality != null)
			house.setInteriorQuality(interiorQuality);		
		
		de.immoPiraten.realEstate.BuildingEnergyRatingType buildingEnergyRating = Parser.parseEnum(Search.getJsonValue(realEstate, "buildingEnergyRatingType"), de.immoPiraten.realEstate.BuildingEnergyRatingType.class);		
		if (buildingEnergyRating != null)
			house.setBuildingEnergyRating(buildingEnergyRating);
		
		de.immoPiraten.realEstate.HeatingType heating = Parser.parseEnum(Search.getJsonValue(realEstate, "heatingType"), de.immoPiraten.realEstate.HeatingType.class);
		if (heating == null)
			heating  = Parser.parseEnum(Search.getJsonValue(realEstate, "heatingTypeEnev2014"), de.immoPiraten.realEstate.HeatingType.class);
		
		if (heating != null)
			house.setHeating(heating);	
				
		try
		{
			Object firing = null;			
			// de.immoPiraten.realEstate.FiringType[] firingArray = (de.immoPiraten.realEstate.FiringType[])Search.getJsonValue(realEstate, "firingTypes");
			ArrayList<de.immoPiraten.realEstate.FiringType> firingArray = (ArrayList<de.immoPiraten.realEstate.FiringType>)Search.getJsonValue(realEstate, "firingTypes");

			if ((firingArray != null) && !firingArray.isEmpty())
				firing = firingArray.get(0);
			
			// if (firing != null)
				// house.setFiring(firing);			
		}catch(Exception e)
		{}
				
		de.immoPiraten.realEstate.ResidentialRealEstateType residentialRealEstate = Parser.parseEnum(Search.getJsonValue(realEstate, "apartmentType"), de.immoPiraten.realEstate.ResidentialRealEstateType.class);
		if (residentialRealEstate == null)
			residentialRealEstate = Parser.parseEnum(Search.getJsonValue(realEstate, "buildingType"), de.immoPiraten.realEstate.ResidentialRealEstateType.class);
		
		if (residentialRealEstate != null)
			house.setResidentialRealEstate(residentialRealEstate);
		
		Contact contact = Search.getContact(properties);
		if (contact != null)
			house.setContact(contact);
		
		return house;
	}

	@SuppressWarnings("unchecked")
	private static String getImageUrl(LinkedHashMap<String, Object> realEstate, int width, int height) {
		LinkedHashMap<String, Object> titlePicture = (LinkedHashMap<String, Object>) realEstate.get("titlePicture");

		String url = null;
		
		if (titlePicture != null) {
			Object href = titlePicture.get("@xlink.href");
			if (href != null) {
				url = titlePicture.get("@xlink.href").toString();
			} else {
				ArrayList<LinkedHashMap<String, Object>> pictureUrlsContainer = (ArrayList<LinkedHashMap<String, Object>>) titlePicture
						.get("urls");
				ArrayList<LinkedHashMap<String, Object>> pictureUrls = (ArrayList<LinkedHashMap<String, Object>>) pictureUrlsContainer
						.get(0).get("url");
				url = pictureUrls.get(0).get("@href").toString();
			}
		}
		
		if (url != null)
		{
			Integer resWidth = Parser.parseInteger(width);
			Integer resHeight = Parser.parseInteger(height);
			
			String targetResolution = resWidth.toString().concat("x").concat(resHeight.toString());
			url = url.replaceAll("(\\d+)x(\\d+)", targetResolution);
		}

		return url;
	}

	private static Object getJsonValue(LinkedHashMap<String, Object> map, String key) {
		return Search.getJsonValueOrDefault(map, key, null);
	}	
	
	private static Object getJsonValueOrDefault(LinkedHashMap<String, Object> map, String key, Object defaultValue) {
		try{
			Object property = map.get(key);
			if (property != null) {
				if (property instanceof Integer)
					return ((Integer) property).intValue();
				return property;
			}			
		}
		catch(Exception e){
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

	@SuppressWarnings("unchecked")
	private static Contact getContact(LinkedHashMap<String, Object> realEstate) {

		try{
			
			LinkedHashMap<String, Object> contactDetailsMap = (LinkedHashMap<String, Object>)realEstate.get("contactDetails");
	
			if (contactDetailsMap != null) {
	
				Contact contact = new Contact();
				
				String firstName = Parser.parseString(Search.getJsonValue(contactDetailsMap, "firstname"));
				if (firstName != null)
					contact.setFirstName(firstName);
				
				String lastName = Parser.parseString(Search.getJsonValue(contactDetailsMap, "lastname"));
				if (lastName != null)
					contact.setLastName(lastName);

				String telephone = Parser.parseString(Search.getJsonValue(contactDetailsMap, "faxNumber"));
				if (telephone != null)
					contact.setTelephone(telephone);			

				String company = Parser.parseString(Search.getJsonValue(contactDetailsMap, "company"));
				if (company != null)
					contact.setCompany(company);							
				
				de.immoPiraten.contact.TitleType title = Parser.parseEnum(Search.getJsonValue(contactDetailsMap, "salutation"), de.immoPiraten.contact.TitleType.class);
				if (title != null)
					contact.setTitle(title);

				Site site = Search.getSite(contactDetailsMap);
				if (site != null)
					contact.setSite(site);
				
				return contact;
			}
		}catch(Exception e){
		}
		
		return null;
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
			return Search.getHouse(expose, 740, 560);

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
	public static List<House> execute(RealEstateType realEstateType, PurchaseType purchaseType, SearchType searchType, String input,
			Byte radius, Boolean freeOfCommission, Short livingAreaFrom, Short livingAreaTill, Integer priceFrom, Integer priceTill,
			Short constructionYearFrom, Short constructionYearTill, Float roomsFrom, Float roomsTill, Short landAreaFrom, Short landAreaTill,
			Boolean balcony, Boolean terrace, Boolean garden, Boolean garage){
		
		String entityType = Search.mapGeoType(searchType);	
		
		List<House> results = new ArrayList<House>();		
		
		// maps the real estate type on the immoScout24 real estate type
		de.immoPiraten.ImmoScout24.RealEstateType immoScoutRealEstateType = Search.mapRealEstateType(realEstateType, purchaseType);

		// converts the living area for the immoScout24 requirements, they call
		// it living space
		String livingSpace = Search.getRange(livingAreaFrom, livingAreaTill);

		// converts the price for the immoScout24 requirements
		String price = Search.getRange(priceFrom, priceTill);

		// converts the landArea for the immoScout24 requirements
		String ground = Search.getRange(landAreaFrom, landAreaTill);
		
		// converts the rooms for the immoScout24 requirements
		String numberOfRooms = Search.getRange(roomsFrom, roomsTill);		
		
		// converts the construction for the immoScout24 requirements
		String constructionyear = Search.getRange(constructionYearFrom, constructionYearTill);		
		
		// TODO Implement terrace and garage	
		
		String response = Search.executeSearch(immoScoutRealEstateType, entityType, input, radius, freeOfCommission,
				livingSpace, price, ground, numberOfRooms, constructionyear, balcony, garden);

		LinkedHashMap<String, Object> result = Request.getLinkedHashMap(response);
		LinkedHashMap<String, Object> resultList = (LinkedHashMap<String, Object>) result.get("resultlist.resultlist");

		ArrayList<Object> resultListEntries = (ArrayList<Object>) resultList.get("resultlistEntries");

		LinkedHashMap<String, Object> entry = (LinkedHashMap<String, Object>) resultListEntries.get(0);
		int numberOfHits = Integer.parseInt(entry.get("@numberOfHits").toString());

		int imageWidth = 281;
		int imageHeight = 211;
		
		if (numberOfHits > 1) {
			ArrayList<Object> exposes = (ArrayList<Object>) entry.get("resultlistEntry");

			// the result list is restricted to 20 entries
			int max = numberOfHits < 50 ? numberOfHits : 50;
			for (int index = 0; index < max; index++)
				try {
					results.add(Search.getHouse(exposes.get(index), imageWidth, imageHeight));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		} else {
			if (numberOfHits == 1) {
				try {
					results.add(Search.getHouse(entry.get("resultlistEntry"), imageWidth, imageHeight));
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