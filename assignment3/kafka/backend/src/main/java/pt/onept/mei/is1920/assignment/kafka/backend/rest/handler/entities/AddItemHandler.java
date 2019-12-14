package pt.onept.mei.is1920.assignment.kafka.backend.rest.handler.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.onept.mei.is1920.assignment.kafka.backend.db.DBHandler;
import spark.Request;
import spark.Response;
import spark.Route;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddItemHandler implements Route {

	private static Logger logger = LoggerFactory.getLogger(AddItemHandler.class);

	@Override
	public Object handle(Request request, Response response) {
		String itemToAdd = request.queryParams("name");

		logger.info("Request to add item (" + itemToAdd + ")");

		try {
			Connection conn = DBHandler.GetConnection();
			PreparedStatement ps = conn.prepareStatement("insert into items (name) values (?)");
			ps.setString(1, itemToAdd);
			logger.info("Query " + ps.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			response.status(500);
			return e.getMessage();
		}
		logger.info("Item (" + itemToAdd + ") added successfully");

		return "";
	}
}
