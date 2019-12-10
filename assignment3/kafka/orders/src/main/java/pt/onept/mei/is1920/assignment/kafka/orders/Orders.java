package pt.onept.mei.is1920.assignment.kafka.orders;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;

public class Orders {

	public static void main(String[] args) {

		String itemsTopic = "kafkaShop.items";
		String countriesTopic = "kafkaShop.countries";

		java.util.Properties props = new Properties();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafkaShop-orders-app");
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

		StreamsBuilder builder = new StreamsBuilder();

		KStream<String, String> itemsStream = builder.stream(itemsTopic);

		itemsStream.foreach((s, s2) -> {
			JsonObject jsonObject = JsonParser.parseString(s2).getAsJsonObject();
			System.out.println(jsonObject.getAsJsonObject("payload").get("name").toString().replace("\"",""));
		});

		KafkaStreams streams = new KafkaStreams(builder.build(), props);

		streams.start();

	}
}
