package pt.onept.mei.is1920.assignment.kafka.customers;

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
import pt.onept.mei.is1920.assignment.kafka.common.type.Country;
import pt.onept.mei.is1920.assignment.kafka.common.type.Item;
import pt.onept.mei.is1920.assignment.kafka.common.type.Sale;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Customers {

	private final static Logger logger = LoggerFactory.getLogger(Customers.class);

	public static void main(String[] args) {

		String sourceTopic = "DBInfo";
		String sinkTopic = "Sales";

		Map<Long, Item> itemsMap = new HashMap<>();
		Map<Long, Country> countriesMap = new HashMap<>();

		java.util.Properties props = new Properties();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "__kafkaShop-customers-app-222132");
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

		Properties sinkProps = new Properties();
		sinkProps.put("bootstrap.servers", "localhost:9092");
		sinkProps.put("acks", "all");
		sinkProps.put("retries", 0);
		sinkProps.put("batch.size", 16384);
		sinkProps.put("linger.ms", 1);
		sinkProps.put("buffer.memory", 33554432);
		sinkProps.put("key.serializer", "org.apache.kafka.common.serialization.LongSerializer");
		sinkProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		Producer<Long, String> producer = new KafkaProducer<>(sinkProps);

		StreamsBuilder builder = new StreamsBuilder();

		KStream<String, String> DbInfoKStream = builder.stream(sourceTopic);

		Gson gson = new Gson();

		DbInfoKStream.foreach((k, v) -> {
			if (k.equals("\"item\"")) {
				Item item = gson.fromJson(v, Item.class);
				if(itemsMap.containsKey(item.getId())) {
					logger.info("Updating item " + item.getId());
					itemsMap.replace(item.getId(), item);
				}
				else {
					logger.info("Adding new item " + item.getId() + " " + item.getName());
					itemsMap.putIfAbsent(item.getId(), item);
				}
			}
			else if (k.equals("\"country\"")) {
				Country country = gson.fromJson(v, Country.class);
				//noinspection DuplicatedCode
				if(countriesMap.containsKey(country.getId())) {
					logger.info("Updating country " + country.getId());
					countriesMap.replace(country.getId(), country);
				}
				else {
					logger.info("Adding new country " + country.getId() + " " + country.getName());
					countriesMap.putIfAbsent(country.getId(), country);
				}
			}
		});

		ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
		exec.scheduleAtFixedRate(() -> {
			Random generator = new Random();
			Object[] itemIds = itemsMap.keySet().toArray();
			Object randomItem = itemIds[generator.nextInt(itemIds.length)];

			Object[] countryIds = countriesMap.keySet().toArray();
			Object randomCountry = countryIds[generator.nextInt(countryIds.length)];

			@SuppressWarnings("SuspiciousMethodCalls")
			Sale newSale = new Sale()
					.setItem(itemsMap.get(randomItem))
					.setPrice(generator.nextFloat() * Sale.PRICE_MAX)
					.setQuantity(generator.nextInt(Sale.QUANTITY_MAX))
					.setCountry(countriesMap.get(randomCountry))
					.setTimeStamp(new Date());

			producer.send(new ProducerRecord<>(sinkTopic, newSale.getItem().getId(), gson.toJson(newSale)));
			logger.info("Sending " + newSale.toString());

		}, 5, 5, TimeUnit.SECONDS);

		KafkaStreams streams = new KafkaStreams(builder.build(), props);
		streams.start();

	}
}
