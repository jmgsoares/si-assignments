package pt.onept.mei.is1920.assignment.kafka.customers;

import com.google.gson.Gson;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import pt.onept.mei.is1920.assignment.kafka.common.type.Country;

import java.util.Properties;

public class Customers {

	public static void main(String[] args) {

		String sourceTopic = "dbinfo";

		java.util.Properties props = new Properties();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafkaShop-customers-app");
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

		StreamsBuilder builder = new StreamsBuilder();

		KStream<String, String> DbInfoKStream = builder.stream(sourceTopic);

		Gson gson = new Gson();

		DbInfoKStream.filter((k ,v) -> k.equals("\"country\"")).foreach((k, v) -> {
			Country country = gson.fromJson(v, Country.class);
			//Here we have a country to process
			System.out.println(country.toString()); //This is a debug print! it has to be removed
		});

		KafkaStreams streams = new KafkaStreams(builder.build(), props);

		streams.start();

	}
}
