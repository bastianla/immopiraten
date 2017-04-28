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
			return RealEstateType.HouseBuy;
		}
	}
	
	/* 92756718 */
	public static Object getExpose(int id) throws APIException
	{		
		HttpGet request = new HttpGet(Request.BASE_URL + Search.CONTEXT + Request.VERSION + "expose/" + id);
		
		HttpGet signedRequest = OAuth.Sign(request, Credentials.CONSUMER_KEY, Credentials.CONSUMER_SECRET, "getExpose");

		return Request.getResponse(signedRequest, "getExpose");
	}
	
	
	public static Object searchRegion(RealEstateType realEstateType, String input)
	{				
		URIBuilder uriBuilder = null;
		try {
			uriBuilder = new URIBuilder(Request.BASE_URL + Search.CONTEXT + Request.VERSION + "region");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		uriBuilder.addParameter("realestatetype", realEstateType.name());
		
		Object matches = GeoAutoCompletion.getAll(input, 5);
		
		uriBuilder.addParameter("geocodes", "");
		
		HttpGet request = new HttpGet(uriBuilder.toString());
		HttpGet signedRequest = OAuth.Sign(request, Credentials.CONSUMER_KEY, Credentials.CONSUMER_SECRET, "getExpose");

		return Request.getResponse(signedRequest, "searchRegion");
	}
}
