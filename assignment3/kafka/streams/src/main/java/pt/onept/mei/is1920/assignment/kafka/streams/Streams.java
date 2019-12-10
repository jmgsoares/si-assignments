package pt.onept.mei.is1920.assignment.kafka.streams;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Streams {
	public static void main(String[] args) {

		String purchasesSourceTopic = "Purchases";
		String salesSourceTopic = "Sales";

		java.util.Properties sourceProps = new Properties();
		//sourceProps.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafkaShop-streams-app");
		sourceProps.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafkaShop-streams-test-app-111");
		sourceProps.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		sourceProps.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Long().getClass());
		sourceProps.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

		StreamsBuilder builder = new StreamsBuilder();
		KStream<Long, String> purchasesKStream = builder.stream(purchasesSourceTopic);
		KStream<Long, String> salesKStream = builder.stream(salesSourceTopic);

		purchasesKStream.foreach((k, v) -> {
			System.out.println("purchasesKStream Message: Key(" + k + ") Value(" + v + ")");
		});

		salesKStream.foreach((k, v) -> {
			System.out.println("salesKStream Message: Key(" + k + ") Value(" + v + ")");
		});

		KafkaStreams streams = new KafkaStreams(builder.build(), sourceProps);
		streams.start();

	}
}
