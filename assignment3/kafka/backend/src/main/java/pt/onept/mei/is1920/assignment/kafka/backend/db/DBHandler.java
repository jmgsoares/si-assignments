package pt.onept.mei.is1920.assignment.kafka.backend.db;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;

public class DBHandler {

	private static HikariConfig config = new HikariConfig();
	private static HikariDataSource ds;

	static {
		config.setJdbcUrl("jdbc:postgresql://localhost:5432/kafkaDb");
		config.setUsername("KafkaDbUs3r");
		config.setPassword("KafkaDbUs3rPwD");
		ds = new HikariDataSource(config);
	}

	public static Connection GetConnection() throws SQLException {
		return ds.getConnection();
	}

	private DBHandler() { }
}

