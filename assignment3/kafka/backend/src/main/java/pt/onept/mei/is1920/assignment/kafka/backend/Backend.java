package pt.onept.mei.is1920.assignment.kafka.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.onept.mei.is1920.assignment.kafka.backend.rest.handler.analytics.AnalyticsHandler;
import pt.onept.mei.is1920.assignment.kafka.backend.rest.handler.entities.*;

import static spark.Spark.*;

public class Backend {
	private static Logger logger = LoggerFactory.getLogger(Backend.class);

	public static void main(String[] args) {
		logger.info("Starting Backend");
		port(8080);
		get("/", (req, res) -> {
			logger.info("Handling root request from " + req.ip() + " " + req.userAgent());
			return "Welcome to kafka-shop";
		});
		path("/country", () -> {
			get("", new ListCountriesHandler());
			post("", new AddCountyHandler());
			put("/:country", new UpdateCountryHandler());
		});
		path("/item", () -> {
			get("", new ListItemsHandler());
			post("", new AddItemHandler());
			put("/:item", new UpdateItemHandler());
		});
		get("/analytics/:type", new AnalyticsHandler());
	}
}
