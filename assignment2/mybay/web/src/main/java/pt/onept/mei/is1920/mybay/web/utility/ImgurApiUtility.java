package pt.onept.mei.is1920.mybay.web.utility;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Part;

public final class ImgurApiUtility {

	private static final Logger logger = LoggerFactory.getLogger(ImgurApiUtility.class);

	private static final String API_URL =  "https://api.imgur.com/3/";

	private ImgurApiUtility() { }

	public static JsonObject UploadImage(Part data) {
		logger.debug("Uploading image");
		JsonObject response = HttpUtility.UploadHttpDataPart(data,"POST",API_URL+"upload");
		JsonObject parsedResponse = response.get("data").getAsJsonObject();
		logger.debug("Parsed response: " + parsedResponse.toString());
		return parsedResponse;
	}

	public static boolean DeleteImage(String hash) {
		logger.debug("Deleting image");
		JsonObject response =  HttpUtility.MakeRequest("DELETE", API_URL+"image/"+hash);
		boolean responseSuccess = response.get("success").getAsBoolean();
		logger.debug("Parsed response success:" + responseSuccess);
		return responseSuccess;
	}

	public static String GetImageUrlFromResponse(JsonObject response) {
		return response.get("link").toString()
				.substring(1, response.get("link").toString().length() - 1);
	}

	public static String GetImageDeleteHashFromResponse(JsonObject response) {
		return  response.get("deletehash").toString()
				.substring(1, response.get("deletehash").toString().length() - 1);
	}

}
