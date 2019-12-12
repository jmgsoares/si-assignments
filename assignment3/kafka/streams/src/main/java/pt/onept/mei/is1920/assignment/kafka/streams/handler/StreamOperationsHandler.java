package pt.onept.mei.is1920.assignment.kafka.streams.handler;

import com.google.gson.Gson;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import pt.onept.mei.is1920.assignment.kafka.common.type.Order;
import pt.onept.mei.is1920.assignment.kafka.common.type.Sale;
import pt.onept.mei.is1920.assignment.kafka.streams.StreamConfigs;

final class StreamOperationsHandler {

	private static Gson gson = new Gson();

	private final KGroupedStream<Long, String> ordersByKey;
	private final KGroupedStream<Long, String> allOrders;
	private final KGroupedStream<Long, String> salesByKey;
	private final KGroupedStream<Long, String> allSales;

	private final KStream<Long, String> salesStream;

	StreamOperationsHandler(KStream<Long, String> ordersStream, KStream<Long, String> salesStream) {
		this.salesStream = salesStream;
		this.ordersByKey = ordersStream.groupByKey();
		this.salesByKey = salesStream.groupByKey();
		this.allSales = salesStream.groupBy((k, v) -> 0L);
		this.allOrders = ordersStream.groupBy((k, v) -> 0L);
	}

	KTable<Long, Float> expensePerItem () {
		KTable<Long, Float> expensePerItemTable = this.ordersByKey
				.aggregate(
						() -> 0F,
						(aggKey, newValue, aggValue) -> gson.fromJson(newValue, Order.class).getPrice() + aggValue,
						Materialized.with(Serdes.Long(), Serdes.Float()));

		expensePerItemTable
				.toStream()
				.map((k, v) -> new KeyValue<>(k, StreamConfigs.WrapKVSchema(k, Float.toString(v))))
				.to(StreamConfigs.EXPENSE_PER_ITEM_SINK_TOPIC);
		return expensePerItemTable;
	}

	KTable<Long, Float> revenuePerItem() {
		KTable<Long, Float> revenuePerItemTable = this.salesByKey
				.aggregate(
						() -> 0F,
						(aggKey, newValue, aggValue) -> gson.fromJson(newValue, Sale.class).getPrice() + aggValue,
						Materialized.with(Serdes.Long(), Serdes.Float()));

		revenuePerItemTable
				.toStream()
				.map((k, v) -> new KeyValue<>(	k,StreamConfigs.WrapKVSchema(k, Float.toString(v))))
				.to(StreamConfigs.REVENUE_PER_ITEM_SINK_TOPIC);

	return revenuePerItemTable;
	}

	KTable<Long, Float>  totalRevenue() {
		KTable<Long, Float> totalRevenueTable = this.allSales
				.aggregate(
						() -> 0F,
						(aggKey, newValue, aggValue) -> gson.fromJson(newValue, Sale.class).getPrice() + aggValue,
						Materialized.with(Serdes.Long(), Serdes.Float()));

		totalRevenueTable
				.toStream()
				.map((k, v) -> new KeyValue<>(k, StreamConfigs.WrapKVSchema(k, Float.toString(v))))
				.to(StreamConfigs.TOTAL_REVENUE_SINK_TOPIC);

	return totalRevenueTable;
	}

	KTable<Long, Float> totalExpense() {
		KTable<Long, Float> totalExpenseTable = this.allSales
				.aggregate(
						() -> 0F,
						(aggKey, newValue, aggValue) -> gson.fromJson(newValue, Order.class).getPrice() + aggValue,
						Materialized.with(Serdes.Long(), Serdes.Float()));

		totalExpenseTable.toStream()
				.map((k, v) -> new KeyValue<>(k, StreamConfigs.WrapKVSchema(k, Float.toString(v))))
				.to(StreamConfigs.TOTAL_EXPENSE_SINK_TOPIC);

		return totalExpenseTable;
	}

	KTable<Long, Float> totalProfit(KTable<Long, Float> totalRevenueTable, KTable<Long, Float> totalExpenseTable) {

		KTable<Long, Float> totalProfitTable = totalRevenueTable.join(totalExpenseTable, Float::sum);

		totalProfitTable
				.toStream()
				.map((k, v) -> new KeyValue<>(k, StreamConfigs.WrapKVSchema(k, Float.toString(v))))
				.to(StreamConfigs.TOTAL_PROFIT_SINK_TOPIC);

		return totalProfitTable;
	}

	KTable<Long, Float> averageExpensePerItem(KTable<Long, Float> expensePerItemTable) {
		KTable<Long, Float> averageExpensePerItemTable = expensePerItemTable
				.join(this.ordersByKey.count(),(exp, cnt) -> exp/cnt);

		averageExpensePerItemTable
				.toStream()
				.map((k, v) -> new KeyValue<>(k, StreamConfigs.WrapKVSchema(k, Float.toString(v))))
				.to(StreamConfigs.AVERAGE_EXPENSE_BY_ITEM_SINK_TOPIC);

		return averageExpensePerItemTable;
	}

	KTable<Long, Float> averageExpensePerOrder(KTable<Long, Float> totalExpenseTable){
		KTable<Long, Float> averageExpenseByOrderTable = totalExpenseTable.join(allOrders.count(),
				(exp, count) -> exp/count);

		averageExpenseByOrderTable
				.toStream()
				.map((k, v) -> new KeyValue<>(k, StreamConfigs.WrapKVSchema(k, Float.toString(v))))
				.to(StreamConfigs.AVERAGE_EXPENSE_BY_ORDER_SINK_TOPIC);

		return averageExpenseByOrderTable;
	}

	KTable<Long, Float> profitPerItem(KTable<Long, Float> revenuePerItemTable, KTable<Long, Float> expensePerItemTable){
		KTable<Long, Float> profitPerItemTable = revenuePerItemTable.join(expensePerItemTable,
				(revenue, expense) -> revenue - expense);

		profitPerItemTable
				.toStream()
				.map((k, v) -> new KeyValue<>(k, StreamConfigs.WrapKVSchema(k, Float.toString(v))))
				.to(StreamConfigs.PROFIT_PER_ITEM_SINK_TOPIC);

		return profitPerItemTable;
	}

	KTable<Long, String> mostProfitableItem(KTable<Long, Float> profitPerItemTable) {
		KTable<Long, String> mostProfitableItemTable = profitPerItemTable
				.toStream()
				.map((k, v) -> new KeyValue<>(k, k + " " + v))
				.groupBy((k, v) -> 0L)
				.reduce((newValue, aggValue) ->
						Float.parseFloat(aggValue.split(" ")[1]) < Float.parseFloat(newValue.split(" ")[1]) ?
								newValue : aggValue);

		mostProfitableItemTable
				.toStream()
				.map((k, v) -> new KeyValue<>(Long.parseLong(v.split(" ")[0]),
						StreamConfigs.WrapKVSchema(Long.parseLong(v.split(" ")[0]), v.split(" ")[1])))
				.to(StreamConfigs.MOST_PROFITABLE_ITEM_SINK_TOPIC);

		return mostProfitableItemTable;
	}

	KTable<Windowed<Long>, Float>totalRevenueLastHour() {
		KTable<Windowed<Long>, Float> totalRevenueLastHourWindowed = this.allSales
				.windowedBy(StreamConfigs.TIME_WINDOWS)
				.aggregate(
						() -> 0F,
						(aggKey, newValue, aggValue) -> gson.fromJson(newValue, Sale.class).getPrice() + aggValue)
				.filter((w, v) -> StreamConfigs.KeepWindow(w));

		totalRevenueLastHourWindowed
				.toStream()
				.map((wk, v) -> new KeyValue<>(wk.key(),Float.toString(v)))
				.to(StreamConfigs.TOTAL_REVENUE_LAST_HOUR_SINK_TOPIC);

		return totalRevenueLastHourWindowed;
	}

	KTable<Windowed<Long>, Float> totalExpenseLastHour() {
		KTable<Windowed<Long>, Float> totalExpenseLastHourWindowed = this.allOrders
				.windowedBy(StreamConfigs.TIME_WINDOWS)
				.aggregate(
						() -> 0F,
						(aggKey, newValue, aggValue) -> gson.fromJson(newValue, Order.class).getPrice() + aggValue)
				.filter((w, v) -> StreamConfigs.KeepWindow(w));


		totalExpenseLastHourWindowed
				.toStream()
				.map((wk, v) -> new KeyValue<>(wk.key(),Float.toString(v)))
				.to(StreamConfigs.TOTAL_EXPENSE_LAST_HOUR_SINK_TOPIC);

		return totalExpenseLastHourWindowed;
	}

	KTable<Windowed<Long>, Float> totalProfitLastHour(
		KTable<Windowed<Long>, Float> totalRevenueLastHourTable,
		KTable<Windowed<Long>, Float> totalExpenseLastHourTable
	) {

		KTable<Windowed<Long>, Float> totalProfitLastHour = totalRevenueLastHourTable
				.join(totalExpenseLastHourTable, Float::sum);

		totalExpenseLastHourTable
				.filter((w, v) -> StreamConfigs.KeepWindow(w))
				.toStream()
				.map(
						(wk, v) -> new KeyValue<>(wk.key(), StreamConfigs.WrapKVSchema(wk.key(), Float.toString(v)))
				)
				.to(StreamConfigs.TOTAL_PROFIT_LAST_HOUR_SINK_TOPIC);

		return totalProfitLastHour;
	}

	KTable<Long, String> countryHighestSales() {
		KTable<Long, String> countryHighestSalesTable = salesStream
				.groupBy((k, v) -> (k + " " + gson.fromJson(v, Sale.class).getCountry().getName()),
						Grouped.with(Serdes.String(), Serdes.String()))
				.reduce((sale1, sale2) -> {
					Sale s1 = gson.fromJson(sale1, Sale.class);
					Sale s2 = gson.fromJson(sale2, Sale.class);
					return gson.toJson(s2
							.setPrice(s1.getPrice() + s2.getPrice()));
				})
				.toStream()
				.groupBy((k, v) -> Long.parseLong(k.split(" ")[0]), Grouped.with(Serdes.Long(),
						Serdes.String()))
				.reduce((oldVal, newVal) -> {
					Sale oldS = gson.fromJson(oldVal, Sale.class);
					Sale newS = gson.fromJson(newVal, Sale.class);
					Sale resultSale = new Sale();
					if(oldS.getPrice() < newS.getPrice()){
						resultSale.setPrice(newS.getPrice())
								.setCountry(newS.getCountry());
					} else {
						resultSale.setPrice(oldS.getPrice())
								.setCountry(oldS.getCountry());
					}
					return gson.toJson(resultSale);
				});

		countryHighestSalesTable
				.toStream()
				.map(((key, value) -> new KeyValue<>(
						key,
						gson.fromJson(value, Sale.class).getCountry().getId() + " " + gson.fromJson(value, Sale.class).getPrice()
				))).to(StreamConfigs.COUNTRY_HIGHEST_SALES_SINK_TOPIC);

		return countryHighestSalesTable;
	}
}
