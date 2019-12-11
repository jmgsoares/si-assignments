package pt.onept.mei.is1920.assignment.kafka.streams;

import com.google.gson.Gson;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import pt.onept.mei.is1920.assignment.kafka.common.type.Country;
import pt.onept.mei.is1920.assignment.kafka.common.type.Order;
import pt.onept.mei.is1920.assignment.kafka.common.type.Sale;

import java.time.Duration;
import java.util.Properties;

public class Streams {
	public static void main(String[] args) {

		String purchasesSourceTopic = "Purchases";
		String salesSourceTopic = "Sales";

		java.util.Properties sourceProps = new Properties();
		//sourceProps.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafkaShop-streams-app");
		sourceProps.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafkaShop-streams-test-app-10");
		sourceProps.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		sourceProps.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Long().getClass());
		sourceProps.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

		StreamsBuilder builder = new StreamsBuilder();
		//KStream<Long, String> purchasesKStream = builder.stream(purchasesSourceTopic);
		KStream<Long, String> salesKStream = builder.stream(salesSourceTopic);

		/*purchasesKStream.foreach((k, v) -> {
			System.out.println("purchasesKStream Message: Key(" + k + ") Value(" + v + ")");
		});

		salesKStream.foreach((k, v) -> {
			System.out.println("salesKStream Message: Key(" + k + ") Value(" + v + ")");
		});*/

		Gson gson = new Gson();

		KTable<Long, String> revenuePerItem = salesKStream
				.groupByKey()
				.reduce((value1, value2) -> {
					Sale sale1 = gson.fromJson(value1, Sale.class);
					Sale sale2 = gson.fromJson(value2, Sale.class);
					float price = sale1.getPrice() + sale2.getPrice();
					return gson.toJson(new Sale()
							.setItem(sale1.getItem())
							.setPrice(price)
							.setQuantity(sale1.getQuantity() + sale2.getQuantity()));
				});

		revenuePerItem.toStream().to("RevenuePerItem");

		KTable<Long, String> totalRevenue = salesKStream
				.groupBy((k, v) -> 0L)
				.reduce((v1, v2) -> {
					Sale sale1 = gson.fromJson(v1, Sale.class);
					Sale sale2 = gson.fromJson(v2, Sale.class);
					return gson.toJson(new Sale()
							.setPrice(sale1.getPrice() + sale2.getPrice())
							.setQuantity(sale1.getQuantity() + sale2.getQuantity()));
				});

		totalRevenue.toStream().to("TotalRevenue");

		// At the moment is set for one minute instead of one hour
		KTable<Windowed<Long>, String> totalRevenueLastHour = salesKStream
				.groupBy((k, v) -> 0L)
				.windowedBy(TimeWindows.of(Duration.ofMinutes(1)))
				.reduce((v1, v2) -> {
					Sale sale1 = gson.fromJson(v1, Sale.class);
					Sale sale2 = gson.fromJson(v2, Sale.class);
					return gson.toJson(new Sale()
							.setPrice(sale1.getPrice() + sale2.getPrice())
							.setQuantity(sale1.getQuantity() + sale2.getQuantity()));
				});

		totalRevenueLastHour
				.toStream((wk, v) -> wk.key())
				.map((k, v) -> new KeyValue<>(k, "" + k + "->" + v))
				.to("TotalRevenueWindowed",
						Produced.with(Serdes.Long(), Serdes.String())
				);



		KafkaStreams streams = new KafkaStreams(builder.build(), sourceProps);
		streams.start();

	}
}
