package pt.onept.mei.is1920.mybay.web.utility;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public final class HttpUtility {
	private static final Logger logger = LoggerFactory.getLogger(HttpUtility.class);

	private HttpUtility() {}

	static JsonObject UploadHttpDataPart(Part data, String method, String url) {
		logger.debug("Processing upload part request");
		HttpURLConnection conn = GetHttpConnection(method, url);
		try {
			assert conn != null;
			WriteToConnection(conn, "image=" + ToBase64(data.getInputStream()));
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return GetResponse(conn);
	}

	static JsonObject MakeRequest(String method, String url) {
		logger.debug("Processing request");
		HttpURLConnection conn = GetHttpConnection(method, url);
		assert conn != null;
		return GetResponse(conn);
	}

	private static String ToBase64(InputStream data) {
		logger.debug("Encoding data to send");
		try	{
			return URLEncoder.encode(DatatypeConverter.printBase64Binary(data.readAllBytes()), StandardCharsets.UTF_8);
		}
		catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	private static HttpURLConnection GetHttpConnection(String method, String url) {
		HttpURLConnection conn;
		try {
			logger.debug("Getting connection url:" + url + " method:" + method);
			conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod(method);
			conn.setRequestProperty("Authorization", "Client-ID d34b5a0ca538c5b");
			conn.setReadTimeout(100000);
			conn.connect();
			return conn;
		}
		catch (IOException e) {
			logger.error(e.getMessage(), e);		}
		return null;
	}

	private static void WriteToConnection(HttpURLConnection conn, String message) {
		logger.debug("Writing data to connection");
		OutputStreamWriter writer;
		try {
			writer = new OutputStreamWriter(conn.getOutputStream());
			writer.write(message);
			writer.flush();
			writer.close();
		}
		catch (IOException e) {
			logger.error(e.getMessage(), e);		}
	}

	private static JsonObject GetResponse(HttpURLConnection conn) {
		logger.debug("Getting response");
		StringBuilder str = new StringBuilder();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				str.append(line);
			}
			reader.close();
		}
		catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		logger.debug("Parsing response to json object");
		JsonElement jsonElement = new JsonParser().parse(str.toString());
		JsonObject response = jsonElement.getAsJsonObject().get("data").getAsJsonObject();
		logger.debug("Parsed response: " + response.toString());
		return response;
	}
}
