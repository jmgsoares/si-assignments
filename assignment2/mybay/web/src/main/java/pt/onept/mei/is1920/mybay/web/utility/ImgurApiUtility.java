package pt.onept.mei.is1920.mybay.web.utility;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Part;

public final class ImgurApiUtility {

	private static final Logger logger = LoggerFactory.getLogger(ImgurApiUtility.class);

	private static final String API_URL =  "https://api.imgur.com/3/";

	private ImgurApiUtility() { }

	public static JsonObject uploadImage(Part data) {
		logger.debug("Uploading image");
		return HttpUtility.UploadHttpDataPart(data,"POST",API_URL+"upload");
	}

	public static JsonObject deleteImage(String hash) {
		logger.debug("Deleting image");
		return HttpUtility.MakeRequest("DELETE", API_URL+hash);
	}
}
