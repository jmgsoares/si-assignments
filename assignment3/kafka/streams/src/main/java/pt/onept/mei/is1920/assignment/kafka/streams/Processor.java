package pt.onept.mei.is1920.assignment.kafka.streams;

import com.google.gson.Gson;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KGroupedStream;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import pt.onept.mei.is1920.assignment.kafka.common.type.Order;
import pt.onept.mei.is1920.assignment.kafka.common.type.Sale;

@SuppressWarnings("DuplicatedCode")
public final class Processor {

	private static Gson gson = new Gson();

	private Processor() { }

	public static void RevenuePerItem() { }

	public static KTable<Long, String> ExpensePerItem (KStream<Long, String> ordersStream) {
		KGroupedStream<Long, String> orderByKey = ordersStream.groupByKey();
		KTable<Long, String> expensePerItemKTable = orderByKey.reduce((v1, v2) -> {
			Order order1 = gson.fromJson(v1, Order.class);
			Order order2 = gson.fromJson(v2, Order.class);
			return gson.toJson(new Order()
					.setItem(order1.getItem())
					.setPrice(order1.getPrice() + order2.getPrice()));
		});
		expensePerItemKTable.toStream().to(StreamConfigs.EXPENSE_PER_ITEM_SINK_TOPIC);
		return expensePerItemKTable;
	}

	public static KTable<Long, String> RevenuePerItem(KStream<Long, String> salesStream) {
		KGroupedStream<Long, String> SalesByKey = salesStream.groupByKey();
		KTable<Long, String> revenuePerItem = SalesByKey.reduce((value1, value2) -> {
			Sale sale1 = gson.fromJson(value1, Sale.class);
			Sale sale2 = gson.fromJson(value2, Sale.class);
			float price = sale1.getPrice() + sale2.getPrice();
			return gson.toJson(new Sale()
					.setItem(sale1.getItem())
					.setPrice(price)
					.setQuantity(sale1.getQuantity() + sale2.getQuantity()));
		});
		revenuePerItem.toStream().to(StreamConfigs.REVENUE_PER_ITEM_SINK_TOPIC);
		return revenuePerItem;
	}

	public static KTable<Long, String> TotalRevenue(KStream<Long, String> salesStream) {
		KGroupedStream<Long, String> allSales = salesStream.groupBy((k, v) -> 0L);
		KTable<Long, String> totalRevenueKTable = allSales.reduce((v1, v2) -> {
			Sale sale1 = gson.fromJson(v1, Sale.class);
			Sale sale2 = gson.fromJson(v2, Sale.class);
			return gson.toJson(new Sale()
					.setPrice(sale1.getPrice() + sale2.getPrice())
					.setQuantity(sale1.getQuantity() + sale2.getQuantity()));
		});
		totalRevenueKTable.toStream().to(StreamConfigs.TOTAL_REVENUE_SINK_TOPIC);
		return totalRevenueKTable;
	}

	public static KTable<Long, String> TotalExpense(KStream<Long, String> ordersStream) {
		KGroupedStream<Long, String> allOrders = ordersStream.groupBy((k, v) -> 0L);
		KTable<Long, String> totalExpenseKTable = allOrders.reduce((v1, v2) -> {
			Order order1 = gson.fromJson(v1, Order.class);
			Order order2 = gson.fromJson(v2, Order.class);
			return gson.toJson(new Order()
					.setPrice(order1.getPrice() + order2.getPrice()));
		});
		totalExpenseKTable.toStream().to(StreamConfigs.TOTAL_EXPENSE_SINK_TOPIC);
		return totalExpenseKTable;
	}

	public static KTable<Long, String> AverageExpensePerItem(KStream<Long, String> ordersStream) {
		KTable<Long, String> expensePerItem = Processor.ExpensePerItem(ordersStream);
		KTable<Long, Long> purchaseCountByItem = ordersStream.groupByKey().count();
		KTable<Long, String> averageExpenseByItemKTable = expensePerItem.join(purchaseCountByItem,
				(exp, cnt) -> {
					Order expense = gson.fromJson(exp, Order.class);
					return Float.toString(expense.getPrice()/cnt);
				});
		averageExpenseByItemKTable.toStream().to(StreamConfigs.AVERAGE_EXPENSE_BY_ITEM_SINK_TOPIC);
		return averageExpenseByItemKTable;
	}

	public static KTable<Long, String> AverageExpensePerOrder(KStream<Long, String> ordersStream){
		KTable<Long, String> totalExpense = Processor.TotalExpense(ordersStream);
		KTable<Long, Long> totalPurchaseCount = ordersStream.groupBy((k, v) -> 0L).count();
		KTable<Long, String> averageExpenseByOrderKTable = totalExpense.join(totalPurchaseCount,
				(tExp, tCount) -> {
					Order expense = gson.fromJson(tExp, Order.class);
					return Float.toString(expense.getPrice()/tCount);
				}
		);
		averageExpenseByOrderKTable.toStream().to(StreamConfigs.AVERAGE_EXPENSE_BY_ORDER_SINK_TOPIC);
		return averageExpenseByOrderKTable;
	}

	public static KTable<Long, String> ProfitPerItem(KStream<Long, String> ordersStream, KStream<Long, String> salesStream){
		KTable<Long, String> revenuePerItem = Processor.RevenuePerItem(salesStream);
		KTable<Long, String> expensePerItem = Processor.ExpensePerItem(ordersStream);
		KTable<Long, String> profitPerItemKTable = revenuePerItem.join(expensePerItem,
				(revenue, expense) -> {
					Sale sale = gson.fromJson(revenue, Sale.class);
					Order order = gson.fromJson(expense, Order.class);
					return Float.toString(sale.getPrice() - order.getPrice());
				});
		profitPerItemKTable.toStream().to(StreamConfigs.PROFIT_PER_ITEM_SINK_TOPIC);
		return profitPerItemKTable;
	}

	public static KTable<Long, String> TotalProfitPerItem(KStream<Long, String> ordersStream, KStream<Long, String> salesStream) {
		KTable<Long, String> totalProfitKTable = totalRevenueKTable.join(totalExpenseKTable,
				(revenue, expense) -> {
					Sale sale = gson.fromJson(revenue, Sale.class);
					Order order = gson.fromJson(expense, Order.class);
					return Float.toString(sale.getPrice() - order.getPrice());
				});
		totalProfitKTable.toStream().to(totalProfitSinkTopic);
	}




	}
