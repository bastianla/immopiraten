package de.immoPiraten.ImmoScout24;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.immoPiraten.APIException;

public class Request {

	protected final static String BASE_URL = "https://rest.immobilienscout24.de/restapi/api/";
	protected final static String VERSION = "v1.0/";
	
	protected static String getResponse(HttpGet signedRequest, String caption)
	{
		HttpClient client = HttpClientBuilder.create().build();		
		try {
			return client.execute(signedRequest, new BasicResponseHandler());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new APIException("An error occured during sending the request: " + caption, e);
		}
	}
	
	@SuppressWarnings("unchecked")
	protected static ArrayList<Object> getArrayList(String response)
	{
		ArrayList<Object> result = null;
		try {
			result = new ObjectMapper().readValue(response, ArrayList.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, Object> getLinkedHashMap(String json)
	{
		LinkedHashMap<String, Object> result = null;		
		try {
			result = new ObjectMapper().readValue(json, LinkedHashMap.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}	
}
