package pt.onept.mei.is1920.assignment.kafka.backend.rest.handler.entities;

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

public class ListItemsHandler implements Route {

	private static Logger logger = LoggerFactory.getLogger(ListItemsHandler.class);

	@Override
	public Object handle(Request request, Response response) {
		logger.info("Request to list items");

		try {
			Connection conn = DBHandler.GetConnection();
			PreparedStatement ps = conn.prepareStatement("select name from items order by name");
			ResultSet rs = ps.executeQuery();
			logger.info("Query " + ps.toString());
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
