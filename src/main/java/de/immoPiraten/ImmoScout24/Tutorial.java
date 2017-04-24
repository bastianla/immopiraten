package de.immoPiraten.ImmoScout24;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

class Tutorial {
	
	/* Example from https://api.immobilienscout24.de/useful/tutorials-sdks-plugins/tutorial-java-signpost/ */
	public static String ImmoScoutTutorial() throws Exception {

		OAuthConsumer consumer = new DefaultOAuthConsumer(Credentials.CONSUMER_KEY, Credentials.CONSUMER_SECRET);

		OAuthProvider provider = new DefaultOAuthProvider(
				"http://rest.immobilienscout24.de/restapi/security/oauth/request_token",
				"http://rest.immobilienscout24.de/restapi/security/oauth/access_token",
				"http://rest.immobilienscout24.de/restapi/security/oauth/confirm_access");

		System.out.println("Fetching request token...");

		String authUrl = provider.retrieveRequestToken(consumer, "oob");

		String requestToken = consumer.getToken();

		String requestTokenSecret = consumer.getTokenSecret();

		System.out.println("Request token: " + requestToken);

		System.out.println("Token secret: " + requestTokenSecret);

		System.out.println("Now visit:\n" + authUrl + "\n... and grant this app authorization");

		System.out.println("Enter the verification code and hit ENTER when you're done:");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String verificationCode = br.readLine();

		System.out.println("Fetching access token...");

		provider.retrieveAccessToken(consumer, verificationCode.trim());

		String accessToken = consumer.getToken();

		String accessTokenSecret = consumer.getTokenSecret();

		System.out.println("Access token: " + accessToken);

		System.out.println("Token secret: " + accessTokenSecret);

		System.out.println("first call");
		requestObjectApi(consumer);

		System.out.println("second call");
		requestObjectApi(consumer);

		System.out.println("third call");
		OAuthConsumer consumer2 = new DefaultOAuthConsumer(Credentials.CONSUMER_KEY, Credentials.CONSUMER_SECRET);

		consumer2.setTokenWithSecret(accessToken, accessTokenSecret);

		return requestObjectApi(consumer2);

	}

	private static String requestObjectApi(OAuthConsumer consumer)
			throws MalformedURLException, IOException, OAuthMessageSignerException, OAuthExpectationFailedException,
			OAuthCommunicationException, UnsupportedEncodingException {

		System.out.println(
				"#################################################################################################");
		URL url = new URL("https://rest.immobilienscout24.de/restapi/api/search/v1.0/expose/92756718");

		HttpURLConnection apiRequest = (HttpURLConnection) url.openConnection();

		consumer.sign(apiRequest);

		System.out.println("Sending request...");

		apiRequest.connect();

		System.out.println("Expiration " + apiRequest.getExpiration());

		System.out.println("Timeout " + apiRequest.getConnectTimeout());

		System.out.println("URL " + apiRequest.getURL());

		System.out.println("Method " + apiRequest.getRequestMethod());

		String result = "Response: " + apiRequest.getResponseCode() + " " + apiRequest.getResponseMessage() + "/r/n"
				+ apiRequest.getContent();
		System.out.println(result);

		System.out.println(
				"#################################################################################################");

		return result;
	}
}
