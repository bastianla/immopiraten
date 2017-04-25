package de.immoPiraten.OAuth;

import org.apache.http.client.methods.HttpGet;

import de.immoPiraten.APIException;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

public class OAuth {

	public static HttpGet Sign(HttpGet request, String consumerKey, String consumerSecret)
			throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException {
		final OAuthConsumer consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
		consumer.setTokenWithSecret(null, "");
		consumer.sign(request);

		return request;
	}

	public static HttpGet Sign(HttpGet request, String consumerKey, String consumerSecret, String requestCaption) {
		HttpGet signedRequest;
		try {
			signedRequest = OAuth.Sign(request, consumerKey, consumerSecret);
		} catch (OAuthMessageSignerException | OAuthExpectationFailedException | OAuthCommunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new APIException("An error occured during signing the request: " + requestCaption, e);
		}
		return signedRequest;
	}
}
