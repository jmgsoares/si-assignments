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

public class UpdateCountryHandler implements Route {

	private static Logger logger = LoggerFactory.getLogger(UpdateCountryHandler.class);

	@Override
	public Object handle(Request request, Response response) {
		String countyToUpdate = request.params(":country");
		String newName = request.queryParams("name");

		logger.info("Request to update country (oldName: " + countyToUpdate + ", newName: " + newName + ")");

		try {
			Connection conn = DBHandler.GetConnection();
			PreparedStatement ps = conn.prepareStatement("update countries set name=(?) where name=(?)");
			ps.setString(1, newName);
			ps.setString(2, countyToUpdate);
			logger.info("Query " + ps.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			response.status(500);
			return e.getMessage();
		}
		logger.info("Country updated successfully");
		return "";
	}
}
