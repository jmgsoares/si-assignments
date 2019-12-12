package pt.onept.mei.is1920.assignment.kafka.streams;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import pt.onept.mei.is1920.assignment.kafka.streams.handler.StreamHandler;

import java.util.Properties;

public class Streams {

	private static final String purchasesSourceTopic = "Purchases";
	private static final String salesSourceTopic = "Sales";

	public static void main(String[] args) {

		java.util.Properties sourceProps = new Properties();
		sourceProps.put(StreamsConfig.APPLICATION_ID_CONFIG, "__kafkaShop-streams-app-44");
		sourceProps.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		sourceProps.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Long().getClass());
		sourceProps.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		sourceProps.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 1000);
		sourceProps.put(StreamsConfig.REPLICATION_FACTOR_CONFIG, 1);

		StreamsBuilder builder = new StreamsBuilder();
		KStream<Long, String> ordersStream = builder.stream(purchasesSourceTopic);
		KStream<Long, String> salesStream = builder.stream(salesSourceTopic);

		StreamHandler streamHandler = new StreamHandler(ordersStream, salesStream);

		streamHandler.getExpensePerItemTable();
		streamHandler.getRevenuePerItemTable();
		streamHandler.getTotalRevenueTable();
		streamHandler.getTotalExpenseTable();
		streamHandler.getAverageExpensePerItemTable();
		streamHandler.getAverageExpensePerOrderTable();
		streamHandler.getProfitPerItemTable();
		streamHandler.getTotalProfitTable();
		streamHandler.getTotalRevenueLastHourStream();
		streamHandler.getTotalExpenseLastHourStream();
		streamHandler.getTotalProfitLastHourTable();


		KafkaStreams streams = new KafkaStreams(builder.build(), sourceProps);
		streams.start();
	}
}
