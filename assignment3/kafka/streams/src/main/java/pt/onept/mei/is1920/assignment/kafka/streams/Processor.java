package pt.onept.mei.is1920.assignment.kafka.streams;

import com.google.gson.Gson;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import pt.onept.mei.is1920.assignment.kafka.common.type.Order;
import pt.onept.mei.is1920.assignment.kafka.common.type.Sale;

import java.time.Duration;

@SuppressWarnings("DuplicatedCode")
public final class Processor {

	private static Gson gson = new Gson();

	private final KGroupedStream<Long, String> ordersByKey;
	private final KGroupedStream<Long, String> allOrders;
	private final KGroupedStream<Long, String> salesByKey;
	private final KGroupedStream<Long, String> allSales;

	public Processor(KStream<Long, String> ordersStream, KStream<Long, String> salesStream) {
		this.ordersByKey = ordersStream.groupByKey();
		this.salesByKey = salesStream.groupByKey();
		this.allSales = salesStream.groupBy((k, v) -> 0L);
		this.allOrders = ordersStream.groupBy((k, v) -> 0L);
	}

	public void expensePerItem () {
		KTable<Long, String> expensePerItemKTable = this.ordersByKey.reduce((v1, v2) -> {
			Order order1 = gson.fromJson(v1, Order.class);
			Order order2 = gson.fromJson(v2, Order.class);
			return gson.toJson(new Order()
					.setItem(order1.getItem())
					.setPrice(order1.getPrice() + order2.getPrice()));
		});
		KStream<Long, String> expensePerItemStream = expensePerItemKTable
				.toStream()
				.map(
						(k, v) -> new KeyValue<>(
								k,
								StreamConfigs.WrapKVSchema(k, Float.toString(gson.fromJson(v, Order.class).getPrice()))
						)
				);
		expensePerItemStream.to(StreamConfigs.EXPENSE_PER_ITEM_SINK_TOPIC);
	}

	public void revenuePerItem() {
		KTable<Long, String> revenuePerItem = this.salesByKey.reduce((value1, value2) -> {
			Sale sale1 = gson.fromJson(value1, Sale.class);
			Sale sale2 = gson.fromJson(value2, Sale.class);
			float price = sale1.getPrice() + sale2.getPrice();
			return gson.toJson(new Sale()
					.setItem(sale1.getItem())
					.setPrice(price)
					.setQuantity(sale1.getQuantity() + sale2.getQuantity()));
		});
		KStream<Long, String> revenuePerItemStream = revenuePerItem
				.toStream()
				.map(
						(k, v) -> new KeyValue<>(
								k,
								StreamConfigs.WrapKVSchema(k, Float.toString(gson.fromJson(v, Sale.class).getPrice()))

						)
				);
		revenuePerItemStream.to(StreamConfigs.REVENUE_PER_ITEM_SINK_TOPIC);
	}

	public void totalRevenue() {
		KTable<Long, String> totalRevenueKTable = this.allSales.reduce((v1, v2) -> {
			Sale sale1 = gson.fromJson(v1, Sale.class);
			Sale sale2 = gson.fromJson(v2, Sale.class);
			return gson.toJson(new Sale()
					.setPrice(sale1.getPrice() + sale2.getPrice()));
		});
		KStream<Long, String> totalRevenueStream = totalRevenueKTable
				.toStream()
				.map(
						(k, v) -> new KeyValue<>(
								k,
								StreamConfigs.WrapKVSchema(k, Float.toString(gson.fromJson(v, Sale.class).getPrice()))
						)
				);
		totalRevenueStream.to(StreamConfigs.TOTAL_REVENUE_SINK_TOPIC);
	}

	public void totalExpense() {
		KTable<Long, String> totalExpenseKTable = this.allOrders.reduce((v1, v2) -> {
			Order order1 = gson.fromJson(v1, Order.class);
			Order order2 = gson.fromJson(v2, Order.class);
			return gson.toJson(new Order()
					.setPrice(order1.getPrice() + order2.getPrice()));
		});
		KStream<Long, String> totalExpenseStream = totalExpenseKTable
				.toStream()
				.map(
						(k, v) -> new KeyValue<>(
								k,
								StreamConfigs.WrapKVSchema(k, Float.toString(gson.fromJson(v, Order.class).getPrice()))
						)
				);
		totalExpenseStream.to(StreamConfigs.TOTAL_EXPENSE_SINK_TOPIC);
	}

	public void averageExpensePerItem() {
		KTable<Long, String> expensePerItemKTable = this.ordersByKey.reduce((v1, v2) -> {
			Order order1 = gson.fromJson(v1, Order.class);
			Order order2 = gson.fromJson(v2, Order.class);
			return gson.toJson(new Order()
					.setItem(order1.getItem())
					.setPrice(order1.getPrice() + order2.getPrice()));
		});
		KTable<Long, String> averageExpenseByItemKTable = expensePerItemKTable.join(this.ordersByKey.count(),
				(exp, cnt) -> {
					Order expense = gson.fromJson(exp, Order.class);
					return Float.toString(expense.getPrice()/cnt);
				});
		KStream<Long, String> averageExpensePerItemStream = averageExpenseByItemKTable
				.toStream()
				.map((k, v) -> new KeyValue<>(k, StreamConfigs.WrapKVSchema(k, v)));

		averageExpensePerItemStream.to(StreamConfigs.AVERAGE_EXPENSE_BY_ITEM_SINK_TOPIC);
	}

	public void averageExpensePerOrder(){
		KTable<Long, String> totalExpense = this.allOrders.reduce((v1, v2) -> {
			Order order1 = gson.fromJson(v1, Order.class);
			Order order2 = gson.fromJson(v2, Order.class);
			return gson.toJson(new Order()
					.setPrice(order1.getPrice() + order2.getPrice()));
		});
		KTable<Long, String> averageExpenseByOrderKTable = totalExpense.join(allOrders.count(),
				(tExp, tCount) -> {
					Order expense = gson.fromJson(tExp, Order.class);
					return Float.toString(expense.getPrice()/tCount);
				}
		);
		KStream<Long, String> averageExpensePerItemStream = averageExpenseByOrderKTable
				.toStream()
				.map((k, v) -> new KeyValue<>(k, StreamConfigs.WrapKVSchema(k, v)));

		averageExpensePerItemStream.to(StreamConfigs.AVERAGE_EXPENSE_BY_ORDER_SINK_TOPIC);
	}

	public void profitPerItem(){
		KTable<Long, String> revenuePerItem = this.salesByKey.reduce((value1, value2) -> {
			Sale sale1 = gson.fromJson(value1, Sale.class);
			Sale sale2 = gson.fromJson(value2, Sale.class);
			float price = sale1.getPrice() + sale2.getPrice();
			return gson.toJson(new Sale()
					.setPrice(price));
		});

		KTable<Long, String> expensePerItem = this.ordersByKey.reduce((v1, v2) -> {
			Order order1 = gson.fromJson(v1, Order.class);
			Order order2 = gson.fromJson(v2, Order.class);
			return gson.toJson(new Order()
					.setPrice(order1.getPrice() + order2.getPrice()));
		});

		KTable<Long, String> profitPerItemKTable = revenuePerItem.join(expensePerItem,
				(revenue, expense) -> {
					Sale sale = gson.fromJson(revenue, Sale.class);
					Order order = gson.fromJson(expense, Order.class);
					return Float.toString(sale.getPrice() - order.getPrice());
				});

		KStream<Long, String> profitPerItem = profitPerItemKTable
				.toStream()
				.map((k, v) -> new KeyValue<>(k, StreamConfigs.WrapKVSchema(k, v)));

		profitPerItem.to(StreamConfigs.PROFIT_PER_ITEM_SINK_TOPIC);
	}

	public void totalProfit() {
		KTable<Long, String> totalRevenue = this.allSales.reduce((v1, v2) -> {
			Sale sale1 = gson.fromJson(v1, Sale.class);
			Sale sale2 = gson.fromJson(v2, Sale.class);
			return gson.toJson(new Sale()
					.setPrice(sale1.getPrice() + sale2.getPrice()));
		});
		KTable<Long, String> totalExpense = this.allOrders.reduce((v1, v2) -> {
			Order order1 = gson.fromJson(v1, Order.class);
			Order order2 = gson.fromJson(v2, Order.class);
			return gson.toJson(new Order()
					.setPrice(order1.getPrice() + order2.getPrice()));
		});

		KTable<Long, String> totalProfitKTable = totalRevenue.join(totalExpense,
				(revenue, expense) -> {
					Sale sale = gson.fromJson(revenue, Sale.class);
					Order order = gson.fromJson(expense, Order.class);
					return Float.toString(sale.getPrice() - order.getPrice());
				});

		KStream<Long, String> totalProfit = totalProfitKTable
				.toStream()
				.map((k, v) -> new KeyValue<>(k, StreamConfigs.WrapKVSchema(k, v)));

		totalProfit.to(StreamConfigs.TOTAL_PROFIT_SINK_TOPIC);
	}

	public void totalRevenueLastHour() {
		KTable<Windowed<Long>, String> totalRevenueLastHour = this.allSales
				.windowedBy(TimeWindows.of(StreamConfigs.WINDOW_SIZE))
				.reduce((v1, v2) -> {
					Sale sale1 = gson.fromJson(v1, Sale.class);
					Sale sale2 = gson.fromJson(v2, Sale.class);
					return gson.toJson(new Sale()
							.setPrice(sale1.getPrice() + sale2.getPrice()));
				});

		KStream<Long, String> totalRevLastHour = totalRevenueLastHour
				.toStream()
				.map(
						(wk, v) -> new KeyValue<>(
								wk.key(),
								StreamConfigs.WrapKVSchema(wk.key(), Float.toString(gson.fromJson(v, Sale.class).getPrice()))
						)
				);

		totalRevLastHour.to(StreamConfigs.TOTAL_REVENUE_LAST_HOUR_SINK_TOPIC);
	}

	public void totalExpenseLastHour() {
		KTable<Windowed<Long>, String> totalExpenseLastHour = this.allOrders
				.windowedBy(TimeWindows.of(StreamConfigs.WINDOW_SIZE))
				.reduce((v1, v2) -> {
					Order order1 = gson.fromJson(v1, Order.class);
					Order order2 = gson.fromJson(v2, Order.class);
					return gson.toJson(new Order()
							.setPrice(order1.getPrice() + order2.getPrice()));
				});

		KStream<Long, String> totalExpLastHour = totalExpenseLastHour
				.toStream()
				.map(
						(wk, v) -> new KeyValue<>(
								wk.key(),
								StreamConfigs.WrapKVSchema(wk.key(), Float.toString(gson.fromJson(v, Order.class).getPrice()))
						)
				);

		totalExpLastHour.to(StreamConfigs.TOTAL_EXPENSE_LAST_HOUR_SINK_TOPIC);
	}

	public void totalProfitLastHour() {
		KStream<Long, String> totalRevenueStream = this.allSales
				.windowedBy(TimeWindows.of(StreamConfigs.WINDOW_SIZE))
				.reduce((v1, v2) -> {
					Sale sale1 = gson.fromJson(v1, Sale.class);
					Sale sale2 = gson.fromJson(v2, Sale.class);
					return gson.toJson(new Sale()
							.setPrice(sale1.getPrice() + sale2.getPrice()));})
				.toStream()
				.filter( (wk, v) -> keepCurrentWindow(wk, StreamConfigs.WINDOW_ADVANCE_MS) )
				.map(
						(wk, v) -> new KeyValue<>(wk.key(), Float.toString(gson.fromJson(v, Sale.class).getPrice()))
				);

		KStream<Long, String> totalExpenseStream = this.allOrders
				.windowedBy(TimeWindows.of(StreamConfigs.WINDOW_SIZE))
				.reduce((v1, v2) -> {
					Order order1 = gson.fromJson(v1, Order.class);
					Order order2 = gson.fromJson(v2, Order.class);
					return gson.toJson(new Order()
							.setPrice(order1.getPrice() + order2.getPrice())); })
				.toStream()
				.filter( (wk, v) -> keepCurrentWindow(wk, StreamConfigs.WINDOW_ADVANCE_MS) )
				.map(
						(wk, v) -> new KeyValue<>(wk.key(), Float.toString(gson.fromJson(v, Order.class).getPrice()))
				);

		KStream<Long, String> totalProfitLastHour = totalRevenueStream
				.join(totalExpenseStream,
					(revenueVal, expenseVal) ->
							Float.toString(Float.parseFloat(revenueVal) + Float.parseFloat(expenseVal)),
					JoinWindows.of(Duration.ofSeconds(15)))
				.map(
						(k, v) -> new KeyValue<>(k, StreamConfigs.WrapKVSchema(k, v))
				);

		totalProfitLastHour.to(StreamConfigs.TOTAL_PROFIT_LAST_HOUR_SINK_TOPIC);
	}

	private boolean keepCurrentWindow(Windowed<Long> window, long advanceMs) {
		long now = System.currentTimeMillis();

		return window.window().end() > now &&
				window.window().end() < now + advanceMs;
	}
}
