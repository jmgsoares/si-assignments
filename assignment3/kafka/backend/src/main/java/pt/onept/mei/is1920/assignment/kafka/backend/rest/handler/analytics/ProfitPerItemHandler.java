package pt.onept.mei.is1920.assignment.kafka.backend.rest.handler.analytics;

import com.google.gson.JsonArray;
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

public class ProfitPerItemHandler implements Route {
	private static Logger logger = LoggerFactory.getLogger(ProfitPerItemHandler.class);

	@Override
	public Object handle(Request request, Response response) {
		logger.info("Request to get Profit per item");

		try {
			Connection conn = DBHandler.GetConnection();
			PreparedStatement ps = conn.prepareStatement("" +
					"select i.name as item, r.value as value from \"Results\".\"ProfitPerItem\" r" +
					" inner join items i" +
					" on r.id = i.id" +
					" order by value desc");
			logger.info("Query " + ps.toString());
			ResultSet rs = ps.executeQuery();
			JsonArray jsonArray = ResultSetToJsonMapper.MapResultSet(rs);
			conn.close();
			return jsonArray;
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			response.status(500);
			return e.getMessage();
		}
	}
}
