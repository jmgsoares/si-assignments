package pt.onept.mei.is1920.assignment.kafka.orders;

import com.google.gson.Gson;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.onept.mei.is1920.assignment.kafka.common.type.Item;
import pt.onept.mei.is1920.assignment.kafka.common.type.Order;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Orders {

	private final static Logger logger = LoggerFactory.getLogger(Orders.class);

	public static void main(String[] args) {

		String sourceTopic = "DBInfo";
		String sinkTopic = "Purchases";

		Map<Long, Item> itemsMap = new HashMap<>();

		java.util.Properties sourceProps = new Properties();
		//sourceProps.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafkaShop-orders-app");
		sourceProps.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafkaShop-orders-test-app-4");
		sourceProps.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		sourceProps.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		sourceProps.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

		Properties sinkProps = new Properties();
		sinkProps.put("bootstrap.servers", "localhost:9092");
		sinkProps.put("acks", "all");
		sinkProps.put("retries", 0);
		sinkProps.put("batch.size", 16384);
		sinkProps.put("linger.ms", 1);
		sinkProps.put("buffer.memory", 33554432);
		sinkProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		sinkProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		Producer<String, String> producer = new KafkaProducer<>(sinkProps);

		StreamsBuilder builder = new StreamsBuilder();

		KStream<String, String> DbInfoKStream = builder.stream(sourceTopic);

		Gson gson = new Gson();

		DbInfoKStream.filter((k ,v) -> k.equals("\"item\"")).foreach((k, v) -> {
			Item item = gson.fromJson(v, Item.class);
			if(itemsMap.containsKey(item.getId())) {
				logger.info("Updating item " + item.getId());
				itemsMap.replace(item.getId(), item);
			}
			else {
				logger.info("Adding new item " + item.getId() + " " + item.getName());
				itemsMap.putIfAbsent(item.getId(), item);
			}
		});

		ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
		exec.scheduleAtFixedRate(() -> {
			Random generator = new Random();
			Object[] itemsIds = itemsMap.keySet().toArray();
			Object randomValue = itemsIds[generator.nextInt(itemsIds.length)];
			//noinspection SuspiciousMethodCalls
			Order newOrder = new Order()
					.setItem(itemsMap.get(randomValue))
					.setQuantity(generator.nextInt(Order.QUANTITY_MAX))
					.setPrice(generator.nextFloat() * Order.PRICE_MAX);

			producer.send(new ProducerRecord<>(sinkTopic, "purchase", gson.toJson(newOrder)));
			logger.info("Sending " + newOrder.toString());

		}, 5, 5, TimeUnit.SECONDS);

		KafkaStreams streams = new KafkaStreams(builder.build(), sourceProps);
		streams.start();
	}
}
