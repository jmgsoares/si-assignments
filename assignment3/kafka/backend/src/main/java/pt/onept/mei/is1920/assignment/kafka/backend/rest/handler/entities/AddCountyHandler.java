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

public class AddCountyHandler implements Route {

	private static Logger logger = LoggerFactory.getLogger(AddCountyHandler.class);

	@Override
	public Object handle(Request request, Response response) {
		String countryToAdd = request.queryParams("name");

		logger.info("Request to add country (" + countryToAdd + ")");


		try {
			Connection conn = DBHandler.GetConnection();
			PreparedStatement ps = conn.prepareStatement("insert into countries (name) values (?)");
			ps.setString(1, countryToAdd);
			logger.info("Query " + ps.toString());
			ps.executeUpdate();
			conn.close();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			response.status(500);
			return e.getMessage();
		}
		logger.info("Country (" + countryToAdd + ") added successfully");

		return "";
	}
}
