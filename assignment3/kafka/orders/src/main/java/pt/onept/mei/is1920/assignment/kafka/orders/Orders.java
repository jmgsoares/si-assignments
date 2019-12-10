package pt.onept.mei.is1920.assignment.kafka.orders;

import com.google.gson.Gson;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.onept.mei.is1920.assignment.kafka.common.type.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Orders {

	private final static Logger logger = LoggerFactory.getLogger(Orders.class);

	public static void main(String[] args) {

		String sourceTopic = "DBInfo";

		Map<Long, String> itemsMap = new HashMap<>();

		java.util.Properties props = new Properties();
		//props.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafkaShop-orders-app");
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafkaShop-orders-test-app-1");
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

		StreamsBuilder builder = new StreamsBuilder();

		KStream<String, String> DbInfoKStream = builder.stream(sourceTopic);

		Gson gson = new Gson();

		DbInfoKStream.filter((k ,v) -> k.equals("\"item\"")).foreach((k, v) -> {
			Item item = gson.fromJson(v, Item.class);
			if(itemsMap.containsKey(item.getId())) {
				logger.info("Updating item " + item.getId());
				itemsMap.replace(item.getId(), item.getName());
			}
			else {
				logger.info("Adding new item " + item.getId() + " " + item.getName());
				itemsMap.putIfAbsent(item.getId(), item.getName());
			}
		});

		ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
		exec.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				//Here we need to now generate the random purchase thingy...
				System.out.println(itemsMap.size());
			}
		}, 5, 5, TimeUnit.SECONDS);

		KafkaStreams streams = new KafkaStreams(builder.build(), props);

		streams.start();

	}
}
