package pt.onept.mei.is1920.assignment.kafka.orders;

import com.google.gson.Gson;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import pt.onept.mei.is1920.assignment.kafka.common.type.Item;

import java.util.Properties;

public class Orders {

	public static void main(String[] args) {

		String sourceTopic = "DBInfo";

		java.util.Properties props = new Properties();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafkaShop-orders-app");
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

		StreamsBuilder builder = new StreamsBuilder();

		KStream<String, String> DbInfoKStream = builder.stream(sourceTopic);

		Gson gson = new Gson();

		DbInfoKStream.filter((k ,v) -> k.equals("\"item\"")).foreach((k, v) -> {
			Item item = gson.fromJson(v, Item.class);
			//Here we have an item to process
			System.out.println(item.toString()); //This is a debug print! it has to be removed
		});

		KafkaStreams streams = new KafkaStreams(builder.build(), props);

		streams.start();

	}
}
