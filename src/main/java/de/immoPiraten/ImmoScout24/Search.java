package de.immoPiraten.ImmoScout24;

import java.net.URISyntaxException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;

import de.immoPiraten.APIException;
import de.immoPiraten.OAuth.OAuth;
import de.immoPiraten.realEstate.PurchaseType;

public class Search {
	
	protected final static String CONTEXT = "search/";	
	
	/* CAUTION: Only supports House and Flat.*/
	public static RealEstateType mapRealEstateType(de.immoPiraten.realEstate.RealEstateType realEstateType, PurchaseType purchaseType)
	{
		switch (realEstateType)
		{
		case House:
			if (purchaseType == PurchaseType.Buy)
				return RealEstateType.HouseBuy;
			else
				return RealEstateType.HouseRent;
		case Flat:
			if (purchaseType == PurchaseType.Buy)
				return RealEstateType.ApartmentBuy;
			else
				return RealEstateType.ApartmentRent;
		default:
			throw new IllegalArgumentException("Could not map the specified combination of real estate type and purchase type to an real estate type.");
		}
	}
	
	/* CAUTION: Only supports House and Apartment.*/
	public static de.immoPiraten.realEstate.RealEstateType mapRealEstateType(RealEstateType realEstateType)
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
	public static de.immoPiraten.realEstate.PurchaseType mapPurchaseType(RealEstateType realEstateType)
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
	public static String getExpose(int id) throws APIException
	{		
		HttpGet request = new HttpGet(Request.BASE_URL + Search.CONTEXT + Request.VERSION + "expose/" + id);
		request.addHeader("accept", "application/json");
		HttpGet signedRequest = OAuth.Sign(request, Credentials.CONSUMER_KEY, Credentials.CONSUMER_SECRET, "getExpose");

		return Request.getResponse(signedRequest, "getExpose").toString();
	}
		
	public static String Execute(RealEstateType realEstateType, String entityType, String input, byte radius, Boolean freeOfCourtageOnly, String livingSpace, String price)
	{				
		GeoAutoCompletionService geoAutoCompletion = new GeoAutoCompletionService();
	    
		String entityId = geoAutoCompletion.getEntityId(entityType, input);
		GeoCode geoCode = geoAutoCompletion.getGeoCode(entityId);
		
		return Search.getSearchResult(realEstateType, geoCode, radius, freeOfCourtageOnly, livingSpace, price);
	}
	
	private static String getSearchResult(RealEstateType realEstateType, GeoCode geoCode, byte radius, Boolean freeOfCourtageOnly, String livingSpace, String price)
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
}
