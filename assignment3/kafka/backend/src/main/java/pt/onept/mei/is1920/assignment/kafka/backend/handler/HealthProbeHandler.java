package pt.onept.mei.is1920.assignment.kafka.backend.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

public class HealthProbeHandler implements Route {
	private static Logger logger = LoggerFactory.getLogger(HealthProbeHandler.class);

	@Override
	public Object handle(Request request, Response response) {
		logger.info("Handling health request status from " + request.ip() + " " + request.userAgent());
		return "alive";
	}
}
