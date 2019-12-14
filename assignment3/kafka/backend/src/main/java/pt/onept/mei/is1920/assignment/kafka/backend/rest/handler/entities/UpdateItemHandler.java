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

public class UpdateItemHandler implements Route {

	private static Logger logger = LoggerFactory.getLogger(UpdateItemHandler.class);

	@Override
	public Object handle(Request request, Response response) {
		String itemToUpdate = request.params(":item");
		String newName = request.queryParams("name");

		logger.info("Update item (oldName: " + itemToUpdate + ", newName: " + newName + ") request");
		try {
			Connection conn = DBHandler.GetConnection();
			PreparedStatement ps = conn.prepareStatement("update items set name=(?) where name=(?)");
			ps.setString(1, newName);
			ps.setString(2, itemToUpdate);
			logger.info("Query " + ps.toString());
			ps.executeUpdate();
			conn.close();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			response.status(500);
			return e.getMessage();
		}
		logger.info("Item updated successfully");

		return "";
	}
}
