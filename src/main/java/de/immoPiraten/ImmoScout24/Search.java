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

public class Search {

	protected final static String BASE_URL = "https://rest.immobilienscout24.de/restapi/api/";
	protected final static String CONTEXT = "search/";
	protected final static String VERSION = "v1.0/";
	
	/* 92756718 */
	public static Object getExpose(int id) throws APIException
	{		
		HttpGet request = new HttpGet(Search.BASE_URL + Search.CONTEXT + Search.VERSION + "expose/" + id);
		
		HttpGet signedRequest;
		try {
			signedRequest = OAuth.Sign(request, Credentials.CONSUMER_KEY, Credentials.CONSUMER_SECRET);
		} catch (OAuthMessageSignerException | OAuthExpectationFailedException | OAuthCommunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new APIException("An error occured during signing the getExpose request.", e);
		}
		
		HttpClient client = HttpClientBuilder.create().build();
		
		try {
			return client.execute(signedRequest, new BasicResponseHandler());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new APIException("An error occured during sending the getExpose request.", e);
		}
	}
	
}
