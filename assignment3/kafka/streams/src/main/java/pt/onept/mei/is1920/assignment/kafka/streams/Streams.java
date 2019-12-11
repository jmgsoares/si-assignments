package pt.onept.mei.is1920.assignment.kafka.streams;

import com.google.gson.Gson;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import pt.onept.mei.is1920.assignment.kafka.common.type.Order;
import pt.onept.mei.is1920.assignment.kafka.common.type.Sale;

import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Streams {

	private static final String purchasesSourceTopic = "Purchases";
	private static final String salesSourceTopic = "Sales";

	private static final String resultSinkTopicsPrefix = "Results.";
	private static final String expensePerItemSinkTopic = resultSinkTopicsPrefix + "ExpensePerItem";
	private static final String totalExpenseSinkTopic = resultSinkTopicsPrefix + "TotalExpense";
	private static final String averageExpenseByItemSinkTopic = resultSinkTopicsPrefix + "AverageExpenseByItem";
	private static final String averageExpenseByOrderSinkTopic = resultSinkTopicsPrefix + "AverageExpenseByOrder";
	private static final String profitPerItemSinkTopic = resultSinkTopicsPrefix + "ProfitPerItem";
	private static final String totalProfitSinkTopic = resultSinkTopicsPrefix + "TotalProfit";
	private static final String mostProfitableItemSinkTopic = resultSinkTopicsPrefix + "MostProfitableItem";
	private static final String totalExpenseLastHourSinkTopic = resultSinkTopicsPrefix + "TotalExpenseLastHour";
	private static final String totalProfitLastHourSinkTopic = resultSinkTopicsPrefix + "TotalProfitLastHour";
	private static final String countryHighestSalesSinkTopic = resultSinkTopicsPrefix + "CountryHighestSales";
	private static final String revenuePerItemSinkTopic = resultSinkTopicsPrefix + "RevenuePerItem";
	private static final String totalRevenueSinkTopic = resultSinkTopicsPrefix + "TotalRevenue";
	private static final String totalRevenueLastHourSinkTopic = resultSinkTopicsPrefix + "TotalRevenueLastHour";
	private static final Duration windowSizeMs = Duration.ofSeconds(30);

	public static void main(String[] args) {

		java.util.Properties sourceProps = new Properties();
		//sourceProps.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafkaShop-streams-app");
		sourceProps.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafkaShop-streams-test-app-65");
		sourceProps.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		sourceProps.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Long().getClass());
		sourceProps.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		sourceProps.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 1000);
		sourceProps.put(StreamsConfig.REPLICATION_FACTOR_CONFIG, 1);

		StreamsBuilder builder = new StreamsBuilder();
		KStream<Long, String> purchasesKStream = builder.stream(purchasesSourceTopic);
		KStream<Long, String> salesKStream = builder.stream(salesSourceTopic);

		Gson gson = new Gson();

		KGroupedStream<Long, String> purchasesByKeyKGroupedStream = purchasesKStream.groupByKey();
		KGroupedStream<Long, String> salesByKeyKGroupedStream = salesKStream.groupByKey();

		KGroupedStream<Long, String> allPurchasesGroupedKGroupedStream = purchasesKStream.groupBy((k, v) -> 0L);
		KGroupedStream<Long, String> allSalesGroupedKGroupedStream = salesKStream.groupBy((k, v) -> 0L);

		KTable<Long, Long> totalPurchaseCount = allPurchasesGroupedKGroupedStream.count();
		KTable<Long, Long> purchaseCountByItem = purchasesByKeyKGroupedStream.count();

		KTable<Long, String> expensePerItemKTable = purchasesByKeyKGroupedStream.reduce((v1, v2) -> {
			Order order1 = gson.fromJson(v1, Order.class);
			Order order2 = gson.fromJson(v2, Order.class);
			return gson.toJson(new Order()
					.setItem(order1.getItem())
					.setPrice(order1.getPrice() + order2.getPrice()));
		});
		expensePerItemKTable.toStream().to(expensePerItemSinkTopic);

		KTable<Long, String> averageExpenseByItemKTable = expensePerItemKTable.join(purchaseCountByItem,
			(expensePerItem, countPerItem) -> {
				Order expense = gson.fromJson(expensePerItem, Order.class);
				return Float.toString(expense.getPrice()/countPerItem);
			});
		averageExpenseByItemKTable.toStream().to(averageExpenseByItemSinkTopic);

		KTable<Long, String> revenuePerItem = salesByKeyKGroupedStream.reduce((value1, value2) -> {
				Sale sale1 = gson.fromJson(value1, Sale.class);
				Sale sale2 = gson.fromJson(value2, Sale.class);
				float price = sale1.getPrice() + sale2.getPrice();
				return gson.toJson(new Sale()
						.setItem(sale1.getItem())
						.setPrice(price)
						.setQuantity(sale1.getQuantity() + sale2.getQuantity()));
			});
		revenuePerItem.toStream().to(revenuePerItemSinkTopic);

		KTable<Long, String> profitPerItemKTable = revenuePerItem.join(expensePerItemKTable,
			(revenue, expense) -> {
				Sale sale = gson.fromJson(revenue, Sale.class);
				Order order = gson.fromJson(expense, Order.class);
				return Float.toString(sale.getPrice() - order.getPrice());
			});
		profitPerItemKTable.toStream().to(profitPerItemSinkTopic);

		KTable<Long, String> totalRevenueKTable = allSalesGroupedKGroupedStream.reduce((v1, v2) -> {
				Sale sale1 = gson.fromJson(v1, Sale.class);
				Sale sale2 = gson.fromJson(v2, Sale.class);
				return gson.toJson(new Sale()
						.setPrice(sale1.getPrice() + sale2.getPrice())
						.setQuantity(sale1.getQuantity() + sale2.getQuantity()));
			});
		totalRevenueKTable.toStream().to(totalRevenueSinkTopic);



		// At the moment is set for one minute instead of one hour
		KTable<Windowed<Long>, String> totalRevenueLastHour = allSalesGroupedKGroupedStream
			.windowedBy(TimeWindows.of(windowSizeMs))
			.reduce((v1, v2) -> {
				Sale sale1 = gson.fromJson(v1, Sale.class);
				Sale sale2 = gson.fromJson(v2, Sale.class);
				return gson.toJson(new Sale()
						.setPrice(sale1.getPrice() + sale2.getPrice())
						.setQuantity(sale1.getQuantity() + sale2.getQuantity()));
			});
		totalRevenueLastHour.toStream((wk, v) -> wk.key()).to(totalRevenueLastHourSinkTopic);

		// At the moment is set for one minute instead of one hour
		KTable<Windowed<Long>, String> totalExpenseLastHour = allPurchasesGroupedKGroupedStream
			.windowedBy(TimeWindows.of(windowSizeMs))
			.reduce((v1, v2) -> {
				Order order1 = gson.fromJson(v1, Order.class);
				Order order2 = gson.fromJson(v2, Order.class);
				return gson.toJson(new Sale()
						.setPrice(order1.getPrice() + order2.getPrice()));
			});
		totalExpenseLastHour.toStream((wk, v) -> wk.key()).to(totalExpenseLastHourSinkTopic);

		KTable<Long, String> totalExpenseKTable = allPurchasesGroupedKGroupedStream.reduce((v1, v2) -> {
			Order order1 = gson.fromJson(v1, Order.class);
			Order order2 = gson.fromJson(v2, Order.class);
			return gson.toJson(new Order()
					.setPrice(order1.getPrice() + order2.getPrice()));
		});
		totalExpenseKTable.toStream().to(totalExpenseSinkTopic);

		KTable<Long, String> totalProfitKTable = totalRevenueKTable.join(totalExpenseKTable,
			(revenue, expense) -> {
				Sale sale = gson.fromJson(revenue, Sale.class);
				Order order = gson.fromJson(expense, Order.class);
				return Float.toString(sale.getPrice() - order.getPrice());
			});
		totalProfitKTable.toStream().to(totalProfitSinkTopic);

		//TODO This could be more elegant
		KTable<Windowed<Long>, String> totalProfitLastHour = totalRevenueKTable.toStream().join(totalExpenseKTable.toStream(),
			(revenue, expense) -> {
				Sale sale = gson.fromJson(revenue, Sale.class);
				Order order = gson.fromJson(expense, Order.class);
				return Float.toString(sale.getPrice() - order.getPrice());
			}, JoinWindows.of(Duration.ofMillis(1))).groupByKey().windowedBy(TimeWindows.of(windowSizeMs))
				.reduce((revenue, expense) -> {
					Sale sale = gson.fromJson(revenue, Sale.class);
					Order order = gson.fromJson(expense, Order.class);
					return Float.toString(sale.getPrice() - order.getPrice());
				});

		totalProfitLastHour.toStream().to(totalProfitLastHourSinkTopic);

		KTable<Long, String> averageExpenseByOrderKTable = totalExpenseKTable.join(totalPurchaseCount,
				(totalExpense, totalCount) -> {
					Order expense = gson.fromJson(totalExpense, Order.class);
					return Float.toString(expense.getPrice()/totalCount);
				}
		);
		averageExpenseByOrderKTable.toStream().to(averageExpenseByOrderSinkTopic);

		// Country with the highest sales per item and the corresponding revenue sum
		//@TODO this one + Most profitable item (if there is a tie, only one is needed)

		/*
		KSF -> Group by key
		KGS -> Agregate by country
		KT -> GroupBy ou toStream
		... -> Reduce price
		*/

		KafkaStreams streams = new KafkaStreams(builder.build(), sourceProps);
		streams.start();
	}
}
