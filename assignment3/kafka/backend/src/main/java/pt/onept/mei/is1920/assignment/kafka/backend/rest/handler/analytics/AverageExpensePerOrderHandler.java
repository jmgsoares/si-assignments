package pt.onept.mei.is1920.assignment.kafka.backend.rest.handler.analytics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.onept.mei.is1920.assignment.kafka.backend.db.DBHandler;
import pt.onept.mei.is1920.assignment.kafka.backend.util.ResultSetToJsonMapper;
import spark.Request;
import spark.Response;
import spark.Route;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class AverageExpensePerOrderHandler implements Route {

	private static Logger logger = LoggerFactory.getLogger(AverageExpensePerOrderHandler.class);

	@Override
	public Object handle(Request request, Response response) {

		logger.info("Request to get Average Expense per Order");

		try {
			Connection conn = DBHandler.GetConnection();
			PreparedStatement ps = conn.prepareStatement("select value from \"Results\".\"AverageExpenseByOrder\"");
			logger.info("Query " + ps.toString());
			ResultSet rs = ps.executeQuery();
			conn.close();
			return ResultSetToJsonMapper.MapResultSet(rs);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			response.status(500);
			return e.getMessage();
		}
	}
}
