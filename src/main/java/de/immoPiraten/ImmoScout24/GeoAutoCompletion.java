package de.immoPiraten.ImmoScout24;

import java.net.URISyntaxException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;

import de.immoPiraten.OAuth.OAuth;

public class GeoAutoCompletion {

	protected final static String CONTEXT = "gis/";
	protected final static String VERSION = "v2.0/";

	private static URIBuilder getUriBuilder(String baseURL, String context, String version, String path) {
		URIBuilder uriBuilder = null;
		try {
			uriBuilder = new URIBuilder(baseURL + context + version + path);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return uriBuilder;
	}

	/* except train station */
	public static Object getAll(String input, int limit) {
		String entityType = "ALL";
		String caption = "getAll";

		URIBuilder uriBuilder = GeoAutoCompletion.getUriBuilder(Request.BASE_URL, GeoAutoCompletion.CONTEXT,
				GeoAutoCompletion.VERSION, "geoautocomplete/DEU");

		uriBuilder.addParameter("i", input);
		uriBuilder.addParameter("l", Integer.toString(limit));
		uriBuilder.addParameter("t", entityType);

		HttpGet request = new HttpGet(uriBuilder.toString());
		HttpGet signedRequest = OAuth.Sign(request, Credentials.CONSUMER_KEY, Credentials.CONSUMER_SECRET, caption);

		return Request.getResponse(signedRequest, caption);
	}

	public static Object getRegion(String input, int limit) {
		String entityType = "region";
		String caption = "getRegion";

		URIBuilder uriBuilder = GeoAutoCompletion.getUriBuilder(Request.BASE_URL, GeoAutoCompletion.CONTEXT,
				Request.VERSION, "geoautocomplete/DEU");

		uriBuilder.addParameter("i", input);
		uriBuilder.addParameter("l", Integer.toString(limit));
		uriBuilder.addParameter("t", entityType);

		HttpGet request = new HttpGet(uriBuilder.toString());
		HttpGet signedRequest = OAuth.Sign(request, Credentials.CONSUMER_KEY, Credentials.CONSUMER_SECRET, caption);

		return Request.getResponse(signedRequest, caption);
	}

	public static Object getCity(String input, int limit) {
		String entityType = "city";
		String caption = "getCity";

		URIBuilder uriBuilder = GeoAutoCompletion.getUriBuilder(Request.BASE_URL, GeoAutoCompletion.CONTEXT,
				Request.VERSION, "geoautocomplete/DEU");

		uriBuilder.addParameter("i", input);
		uriBuilder.addParameter("l", Integer.toString(limit));
		uriBuilder.addParameter("t", entityType);

		HttpGet request = new HttpGet(uriBuilder.toString());
		HttpGet signedRequest = OAuth.Sign(request, Credentials.CONSUMER_KEY, Credentials.CONSUMER_SECRET, caption);

		return Request.getResponse(signedRequest, caption);
	}

	public static Object getDistrict(String input, int limit) {
		String entityType = "district";
		String caption = "getDistrict";

		URIBuilder uriBuilder = GeoAutoCompletion.getUriBuilder(Request.BASE_URL, GeoAutoCompletion.CONTEXT,
				Request.VERSION, "geoautocomplete/DEU");

		uriBuilder.addParameter("i", input);
		uriBuilder.addParameter("l", Integer.toString(limit));
		uriBuilder.addParameter("t", entityType);

		HttpGet request = new HttpGet(uriBuilder.toString());
		HttpGet signedRequest = OAuth.Sign(request, Credentials.CONSUMER_KEY, Credentials.CONSUMER_SECRET, caption);

		return Request.getResponse(signedRequest, caption);
	}

	public static Object getQuarterOrTown(String input, int limit) {
		String entityType = "quarterOrTown";
		String caption = "getQuarterOrTown";

		URIBuilder uriBuilder = GeoAutoCompletion.getUriBuilder(Request.BASE_URL, GeoAutoCompletion.CONTEXT,
				Request.VERSION, "geoautocomplete/DEU");

		uriBuilder.addParameter("i", input);
		uriBuilder.addParameter("l", Integer.toString(limit));
		uriBuilder.addParameter("t", entityType);

		HttpGet request = new HttpGet(uriBuilder.toString());
		HttpGet signedRequest = OAuth.Sign(request, Credentials.CONSUMER_KEY, Credentials.CONSUMER_SECRET, caption);

		return Request.getResponse(signedRequest, caption);
	}

	public static Object getPostCode(int input, int limit) {
		String entityType = "quarterOrTown";
		String caption = "getQuarterOrTown";

		URIBuilder uriBuilder = GeoAutoCompletion.getUriBuilder(Request.BASE_URL, GeoAutoCompletion.CONTEXT,
				Request.VERSION, "geoautocomplete/DEU");

		uriBuilder.addParameter("i", Integer.toString(input));
		uriBuilder.addParameter("l", Integer.toString(limit));
		uriBuilder.addParameter("t", entityType);

		HttpGet request = new HttpGet(uriBuilder.toString());
		HttpGet signedRequest = OAuth.Sign(request, Credentials.CONSUMER_KEY, Credentials.CONSUMER_SECRET, caption);

		return Request.getResponse(signedRequest, caption);
	}

	public static Object getStreet(String input, int limit) {
		String entityType = "street";
		String caption = "getStreet";

		URIBuilder uriBuilder = GeoAutoCompletion.getUriBuilder(Request.BASE_URL, GeoAutoCompletion.CONTEXT,
				Request.VERSION, "geoautocomplete/DEU");

		uriBuilder.addParameter("i", input);
		uriBuilder.addParameter("l", Integer.toString(limit));
		uriBuilder.addParameter("t", entityType);

		HttpGet request = new HttpGet(uriBuilder.toString());
		HttpGet signedRequest = OAuth.Sign(request, Credentials.CONSUMER_KEY, Credentials.CONSUMER_SECRET, caption);

		return Request.getResponse(signedRequest, caption);
	}

	public static Object getPostCodeWithQuarter(String input, int limit) {
		String entityType = "postcodeWithQuarter";
		String caption = "getPostCodeWithQuarter";

		URIBuilder uriBuilder = GeoAutoCompletion.getUriBuilder(Request.BASE_URL, GeoAutoCompletion.CONTEXT,
				Request.VERSION, "geoautocomplete/DEU");

		uriBuilder.addParameter("i", input);
		uriBuilder.addParameter("l", Integer.toString(limit));
		uriBuilder.addParameter("t", entityType);

		HttpGet request = new HttpGet(uriBuilder.toString());
		HttpGet signedRequest = OAuth.Sign(request, Credentials.CONSUMER_KEY, Credentials.CONSUMER_SECRET, caption);

		return Request.getResponse(signedRequest, caption);
	}

	public static Object getTrainStation(String input, int limit) {
		String entityType = "trainStation";
		String caption = "getTraiStation";

		URIBuilder uriBuilder = GeoAutoCompletion.getUriBuilder(Request.BASE_URL, GeoAutoCompletion.CONTEXT,
				Request.VERSION, "geoautocomplete/DEU");

		uriBuilder.addParameter("i", input);
		uriBuilder.addParameter("l", Integer.toString(limit));
		uriBuilder.addParameter("t", entityType);

		HttpGet request = new HttpGet(uriBuilder.toString());
		HttpGet signedRequest = OAuth.Sign(request, Credentials.CONSUMER_KEY, Credentials.CONSUMER_SECRET, caption);

		return Request.getResponse(signedRequest, caption);
	}

}
