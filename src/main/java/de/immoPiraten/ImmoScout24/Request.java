package de.immoPiraten.ImmoScout24;

import java.io.IOException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;

import de.immoPiraten.APIException;

public class Request {

	protected final static String BASE_URL = "https://rest.immobilienscout24.de/restapi/api/";
	protected final static String VERSION = "v1.0/";
	
	protected static Object getResponse(HttpGet signedRequest, String caption)
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
	
	
	
}
