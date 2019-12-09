package pt.onept.mei.is1920.assignment.kafka.orders;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;

public class Orders {

	private final static String itemsTopic = "kafkaShop.items";
	private final static String countriesTopic = "kafkaShop.Countries";


	public static void main(String[] args) {

		java.util.Properties props = new Properties();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafkaShop-orders-app");
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		StreamsBuilder builder = new StreamsBuilder();

		KStream<String, String> itemsStream = builder.stream(itemsTopic);


	}
}
