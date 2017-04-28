package de.immoPiraten.ImmoScout24;

import java.io.IOException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;

import de.immoPiraten.APIException;
import de.immoPiraten.OAuth.OAuth;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

public class Geohierarchy {

	protected final static String CONTEXT = "gis";

	public static Object getContinent(int id) {
		
		String caption = "getContinent";
		
		HttpGet request = new HttpGet(Request.BASE_URL + Geohierarchy.CONTEXT + Request.VERSION + "continent/" + id);

		HttpGet signedRequest = OAuth.Sign(request, Credentials.CONSUMER_KEY, Credentials.CONSUMER_SECRET,
				caption);

		return Request.getResponse(signedRequest, caption);
	}

	public static Object getCountry(int continentId, int countryId) {
		
		String caption = "getCountry";
		
		HttpGet request = new HttpGet(Request.BASE_URL + Geohierarchy.CONTEXT + Request.VERSION + "continent/"
				+ continentId + "/country/" + countryId);

		HttpGet signedRequest = OAuth.Sign(request, Credentials.CONSUMER_KEY, Credentials.CONSUMER_SECRET,
				caption);

		return Request.getResponse(signedRequest, caption);
	}

	public static Object getRegion(int continentId, int countryId, int regionId) {
		
		String caption = "getRegion";
		
		HttpGet request = new HttpGet(Request.BASE_URL + Geohierarchy.CONTEXT + Request.VERSION + "continent/"
				+ continentId + "/country/" + countryId + "/region/" + regionId);

		HttpGet signedRequest = OAuth.Sign(request, Credentials.CONSUMER_KEY, Credentials.CONSUMER_SECRET,
				caption);

		return Request.getResponse(signedRequest, caption);
	}
	
	public static Object getCity(int continentId, int countryId, int regionId, int cityId) {
		
		String caption = "getCity";
		
		HttpGet request = new HttpGet(Request.BASE_URL + Geohierarchy.CONTEXT + Request.VERSION + "continent/"
				+ continentId + "/country/" + countryId + "/region/" + regionId + "/city/" + cityId);

		HttpGet signedRequest = OAuth.Sign(request, Credentials.CONSUMER_KEY, Credentials.CONSUMER_SECRET,
				caption);

		return Request.getResponse(signedRequest, caption);
	}
	
	public static Object getQuarter(int continentId, int countryId, int regionId, int cityId, int quarterId) {
		
		String caption = "getQuarter";
		
		HttpGet request = new HttpGet(Request.BASE_URL + Geohierarchy.CONTEXT + Request.VERSION + "continent/"
				+ continentId + "/country/" + countryId + "/region/" + regionId + "/city/" + cityId + "/quarter/" + quarterId);

		HttpGet signedRequest = OAuth.Sign(request, Credentials.CONSUMER_KEY, Credentials.CONSUMER_SECRET,
				caption);

		return Request.getResponse(signedRequest, caption);
	}
}
