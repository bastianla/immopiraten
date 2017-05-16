package de.immoPiraten.ImmoScout24;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;

import de.immoPiraten.OAuth.OAuth;

public class GeoAutoCompletionService {

	protected final static String CONTEXT = "gis/";
	protected final static String VERSION = "v2.0/";
	protected final static String PATH = "geoautocomplete/DEU";

	// defines the possible geo coordinate formats
	protected final static String GEO_CODE_FORMAT_GEO_ID = "GeoId";
	protected final static String GEO_CODE_FORMAT_LAMBERT = "Lambert";
	protected final static String GEO_CODE_FORMAT_WGS84 = "WGS84";

	public final static String ENTITY_TYPE_ALL = "ALL";
	public final static String ENTITY_TYPE_STREET = "street";
	public final static String ENTITY_TYPE_POSTCODE = "postCode";
	public final static String ENTITY_TYPE_CITY = "city";
	public final static String ENTITY_TYPE_REGION = "region";
	public final static String ENTITY_TYPE_QUARTERORTOWN = "quarterOrTown";
	public final static String ENTITY_TYPE_QUARTERWITHPOSTCODE = "quarterWithPostCode";
	public final static String ENTITY_TYPE_DISTRICT = "district";
	public final static String ENTITY_TYPE_TRAINSTATION = "trainStation";
	
	// contains all immoScout24 entity types
	private static ArrayList<String> entityTypes;

	// initializes the immoScout24 entity types
	static {
		GeoAutoCompletionService.entityTypes = new ArrayList<String>();

		// looks up in all entity types except train station
		GeoAutoCompletionService.entityTypes.add(ENTITY_TYPE_ALL);
		
		GeoAutoCompletionService.entityTypes.add(ENTITY_TYPE_STREET);
		GeoAutoCompletionService.entityTypes.add(ENTITY_TYPE_POSTCODE);
		GeoAutoCompletionService.entityTypes.add(ENTITY_TYPE_CITY); 
		GeoAutoCompletionService.entityTypes.add(ENTITY_TYPE_REGION);
		GeoAutoCompletionService.entityTypes.add(ENTITY_TYPE_QUARTERORTOWN);
		GeoAutoCompletionService.entityTypes.add(ENTITY_TYPE_QUARTERWITHPOSTCODE);
		GeoAutoCompletionService.entityTypes.add(ENTITY_TYPE_DISTRICT);
		GeoAutoCompletionService.entityTypes.add(ENTITY_TYPE_TRAINSTATION);
	}

	private URIBuilder getUriBuilder(String baseURL, String context, String version, String path) {
		URIBuilder uriBuilder = null;
		try {
			uriBuilder = new URIBuilder(baseURL + context + version + path);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return uriBuilder;
	}

	private HttpGet getSignedRequest(String uri, String caption) {
		HttpGet request = new HttpGet(uri);
		request.addHeader("accept", "application/json");

		return OAuth.Sign(request, Credentials.CONSUMER_KEY, Credentials.CONSUMER_SECRET, caption);
	}

	public GeoAutoCompletionService() {
	}
	
	@SuppressWarnings("unchecked")
	public String getEntityId(String entityType, String input)
	{
		if (!GeoAutoCompletionService.entityTypes.contains(entityType))
			throw new NoSuchElementException();
		
		String getEntityResponse = this.getEntity(entityType, input, 10);
		// String getEntityResponse = "[{\"entity\":{\"type\":\"city\",\"id\":\"1276010001\",\"label\":\"Aachen\"},\"matches\":[{\"offset\":0,\"length\":6}]}]";
				
		ArrayList<Object> searchResults = Request.getArrayList(getEntityResponse);		
		
		LinkedHashMap<String, Object> searchResult = (LinkedHashMap<String, Object>)searchResults.get(0);
		LinkedHashMap<String, Object> entity = (LinkedHashMap<String, Object>)searchResult.get("entity");
		
		return entity.get("id").toString();
	}
	
	public String getEntity(String entityType, String input, int limit) {
		if (!GeoAutoCompletionService.entityTypes.contains(entityType))
			throw new NoSuchElementException();

		URIBuilder uriBuilder = this.getUriBuilder(Request.BASE_URL, GeoAutoCompletionService.CONTEXT,
				GeoAutoCompletionService.VERSION, GeoAutoCompletionService.PATH);

		uriBuilder.addParameter("i", input);
		uriBuilder.addParameter("l", Integer.toString(limit));
		uriBuilder.addParameter("t", entityType);

		String caption = "get" + entityType;
		HttpGet signedRequest = this.getSignedRequest(uriBuilder.toString(), caption);
		return Request.getResponse(signedRequest, caption);
	}

	public GeoCode getGeoCode(String entityType, String input)
	{
		String entityId = this.getEntityId(entityType, input);
		return this.getGeoCode(entityId); 
	}
	
	@SuppressWarnings("unchecked")
	public GeoCode getGeoCode(String entityId) {
		URIBuilder uriBuilder = this.getUriBuilder(Request.BASE_URL, GeoAutoCompletionService.CONTEXT,
				GeoAutoCompletionService.VERSION, GeoAutoCompletionService.PATH + "/entity/" + entityId);

		uriBuilder.addParameter("g", GeoAutoCompletionService.GEO_CODE_FORMAT_WGS84);

		String caption = "getGeoCodes";
		HttpGet signedRequest = this.getSignedRequest(uriBuilder.toString(), caption);
		String geoCodeResponse = Request.getResponse(signedRequest, caption);
		
		LinkedHashMap<String, Object> geoCodeMap = Request.getLinkedHashMap(geoCodeResponse);
		LinkedHashMap<String, Object> geoData = (LinkedHashMap<String, Object>)geoCodeMap.get("geoData");
		
		return new GeoCode(geoData.get("lat").toString(), geoData.get("lon").toString());
	}
}
