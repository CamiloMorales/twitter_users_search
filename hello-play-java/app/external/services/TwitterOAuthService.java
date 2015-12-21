package external.services;

import play.libs.F.Function;
import play.libs.F.Promise;
import play.libs.F.Tuple;
import play.libs.OAuth;
import play.libs.OAuth.OAuthCalculator;
import play.libs.OAuth.ConsumerKey;
import play.libs.OAuth.RequestToken;
import play.libs.OAuth.ServiceInfo;
import play.libs.WS;
import play.libs.WS.Response;
import play.libs.WS.WSRequestHolder;

import com.fasterxml.jackson.databind.JsonNode;
import com.ning.http.util.Base64;
import common.Functions;

public class TwitterOAuthService implements OAuthService
{
	private final String consumerKey;
	private final String consumerSecret;
	private final ConsumerKey key;
	private final OAuth oauthHelper;
	
	public TwitterOAuthService(String consumerKey, String consumerSecret)
	{
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
		this.key = new ConsumerKey(consumerKey, consumerSecret);
		this.oauthHelper = new OAuth(new ServiceInfo(
				"https://api.twitter.com/oauth/request_token",
				"https://api.twitter.com/oauth/access_token",
				"https://api.twitter.com/oauth/authorize",
				 this.key
				));
	}
	
	@Override
	public Tuple<String, RequestToken> retrieveRequestToken(String callbackUrl)
	{
		RequestToken rt = oauthHelper.retrieveRequestToken(callbackUrl);				
		return new Tuple<String, RequestToken>(oauthHelper.redirectUrl(rt.token), rt);
	}

	@Override
	public Promise<JsonNode> registeredUserProfile(RequestToken token, String authVerifier)
	{
		RequestToken accessToken = oauthHelper.retrieveAccessToken(token, authVerifier);
		WSRequestHolder request = WS.url("https://api.twitter.com/1.1/account/settings.json");
		
		WSRequestHolder request_1 = request.sign(new OAuthCalculator(key, accessToken));		
		Promise<Response> result = request_1.get();
		Promise<JsonNode> promiseOfJson = result.map(Functions.responseToJson);
		Promise<String> screenName = promiseOfJson.map(Functions.findTextElement("screen_name"));
		return screenName.flatMap(userProfile);
	}
	
	public Function<String, Promise<JsonNode>> userProfile = new Function<String, Promise<JsonNode>>()
	{
		public Promise<JsonNode> apply(final String screenName)
		{
			Promise<JsonNode> promiseOfJson = authenticateApplication().map(Functions.responseToJson);
			Promise<String> accessToken = promiseOfJson.map(Functions.findTextElement("access_token"));
			return accessToken.flatMap(fetchProfile(screenName)).recover(Functions.fetchUserError);
		}
	};
	
	private static Function<String, Promise<JsonNode>> fetchProfile(final String screenName)
	{
		return new Function<String, Promise<JsonNode>>()
		{
			public Promise<JsonNode> apply(String accessToken)
			{
				WSRequestHolder req = WS.url("https://api.twitter.com/1.1/users/show.json")
						                .setQueryParameter("screen_name", screenName)
						                .setHeader("Authorization","Bearer "+accessToken);
				Promise<Response> promise = req.get();
				return promise.map((Function<Response, JsonNode>) Functions.responseToJson);
			}
		};
	}
	
	private Promise<Response> authenticateApplication()
	{
		WSRequestHolder req = WS.url("https://api.twitter.com/oauth2/token")
				                .setHeader("Authorization", "Basic " + bearerToken())
				                .setContentType("application/x-www-form-urlencoded;charset=UTF-8");
		return req.post("grant_type=client_credentials");
	}

	private String bearerToken()
	{
		return Base64.encode((consumerKey + ":" + consumerSecret).getBytes());
	}

	@Override
	public Promise<JsonNode> getSearchResults(RequestToken token, String authVerifier, String query_string)
	{
		RequestToken accessToken = oauthHelper.retrieveAccessToken(token, authVerifier);
		WSRequestHolder request = WS.url("https://api.twitter.com/1.1/users/search.json?q="+query_string.replace(" ", "%20")) //This is a bug in the play-java-ws API, thats why I have to include manually the query on the query.
					                .setQueryParameter("q", query_string); //This seems not to work because of the bug in play-java-ws although it doesnt throw any errors.

		WSRequestHolder request_authenticated = request.sign(new OAuthCalculator(key, accessToken));

		Promise<Response> result = request_authenticated.get();

		Promise<JsonNode> promiseOfJson = result.map(Functions.responseToJson);
		
		//System.out.println("RESULTS: "+promiseOfJson.get());
		
		return promiseOfJson;	
	}
	
	private static Function<String, Promise<JsonNode>> searchResults(String query_string)
	{
		return new Function<String, Promise<JsonNode>>()
		{
			public Promise<JsonNode> apply(String accessToken)
			{
				WSRequestHolder req = WS.url("https://api.twitter.com/1.1/users/search.json")
						                .setQueryParameter("q", query_string)
						                .setHeader("Authorization","Bearer "+accessToken);
				Promise<Response> promise = req.get();
				
				System.out.println("RESULTS 1: "+promise.get().getBody());
				
				return promise.map((Function<Response, JsonNode>) Functions.responseToJson);
			}
		};
	}
}
