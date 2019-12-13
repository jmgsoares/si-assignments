package pt.onept.mei.is1920.assignment.kafka.backend;

import java.sql.*;

public class DBHandler {

	public static void GetConnection() {
		try (
				Connection connection = DriverManager
						.getConnection(
								"jdbc:postgresql://localhost:5432/kafkaDb",
								"KafkaDbUs3r",
								"KafkaDbUs3rPwD")
		){

			System.out.println("Connected to PostgreSQL database!");
			Statement statement = connection.createStatement();
			System.out.println("Reading countries...");
			System.out.printf("%-30.30s  %-30.30s%n", "Model", "Price");
			ResultSet resultSet = statement.executeQuery("select name from countries order by name");
			while (resultSet.next()) {
				System.out.print(resultSet.getString("name") + "\n");
			}

		} catch (SQLException e) {
			System.out.println("Connection failure.");
			e.printStackTrace();
		}
	}
}
