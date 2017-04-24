package de.immoPiraten.OAuth;

import org.apache.http.client.methods.HttpGet;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

public class OAuth {

	public static HttpGet Sign(HttpGet request, String consumerKey, String consumerSecret) throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException
	{
		final OAuthConsumer consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
		consumer.setTokenWithSecret(null, "");
		consumer.sign(request);
		
		return request;
	}	
}
