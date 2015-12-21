package common;

import com.fasterxml.jackson.databind.JsonNode;
import play.Logger;
import play.libs.F.Function;
import play.libs.Json;
import play.libs.WS.Response;
import play.mvc.Result;
import play.mvc.Results;

public class Functions
{
	public static Function<Response, JsonNode> responseToJson = new Function<Response, JsonNode>()
	{
		public JsonNode apply(Response s)
		{
			return s.asJson();
		}
	};
	
	public static Function<JsonNode, Result> jsonToResult = new Function<JsonNode, Result>()
	{
		public Result apply(JsonNode s)
		{
			return Results.ok(s);
		}
	};
	
	public static Function<Throwable, JsonNode> fetchUserError = new Function<Throwable, JsonNode>()
	{
		@Override
		public JsonNode apply(Throwable t)// throws Throwable
		{
			Logger.error("Failed to fetch profile", t);
			return Json.parse("{\"error\": \"failed to fetch the profile of the user\"}");
		}
	};

	public static Function<JsonNode, String> findTextElement(final String path)
	{
		return new Function<JsonNode, String>()
		{
			public String apply(JsonNode s)
			{
				return s.findPath(path).asText();
			}
		};
	}
	
	public static Function<Throwable, JsonNode> searchError = new Function<Throwable, JsonNode>()
	{
		@Override
		public JsonNode apply(Throwable t)// throws Throwable
		{
			Logger.error("Failed to execute search", t);
			return Json.parse("{\"error\": \"failed to execute query search\"}");
		}
	};
}
