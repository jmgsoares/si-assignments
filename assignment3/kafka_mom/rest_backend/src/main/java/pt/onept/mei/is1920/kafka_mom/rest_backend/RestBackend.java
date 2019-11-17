package pt.onept.mei.is1920.kafka_mom.rest_backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.onept.mei.is1920.kafka_mom.rest_backend.handler.HealthProbeHandler;

import static spark.Spark.*;

public class RestBackend {
	private static Logger logger = LoggerFactory.getLogger(RestBackend.class);

	public static void main(String[] args) {
		logger.info("Starting Backend");
		port(8080);
		get("/", (req, res) -> {
			logger.info("Handling root request from " + req.ip() + " " + req.userAgent());
			return RestBackend.class.toString();
		});
		path("/health", () -> {
			get("", new HealthProbeHandler());
		});
	}
}
