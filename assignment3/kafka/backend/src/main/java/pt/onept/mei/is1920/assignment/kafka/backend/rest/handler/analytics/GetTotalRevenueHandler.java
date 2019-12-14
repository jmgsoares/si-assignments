package pt.onept.mei.is1920.assignment.kafka.backend.rest.handler.analytics;

import spark.Request;
import spark.Response;
import spark.Route;

public class GetTotalRevenueHandler implements Route {
	// Here we have to serve both cases
	// Total global or just in the last hour

	@Override
	public Object handle(Request request, Response response) throws Exception {
		return null;
	}

}
