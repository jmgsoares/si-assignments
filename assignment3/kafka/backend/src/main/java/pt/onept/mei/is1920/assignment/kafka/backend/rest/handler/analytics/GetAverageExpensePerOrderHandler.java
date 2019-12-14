package pt.onept.mei.is1920.assignment.kafka.backend.rest.handler.analytics;

import spark.Request;
import spark.Response;
import spark.Route;

public class GetAverageExpensePerOrderHandler implements Route {
	//WE should serve here both cases
	// 1 - Average expense per order by item
	// 2 - Average expense per order global

	@Override
	public Object handle(Request request, Response response) throws Exception {
		return null;
	}

}
