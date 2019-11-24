package pt.onept.mei.is1920.assignment.kafka.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.onept.mei.is1920.assignment.kafka.backend.handler.HealthProbeHandler;

import static spark.Spark.*;

public class Backend {
	private static Logger logger = LoggerFactory.getLogger(Backend.class);

	public static void main(String[] args) {
		logger.info("Starting Backend");
		port(8080);
		get("/", (req, res) -> {
			logger.info("Handling root request from " + req.ip() + " " + req.userAgent());
			return Backend.class.toString();
		});
		path("/health", () -> {
			get("", new HealthProbeHandler());
		});
	}
}
