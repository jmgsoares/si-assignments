package pt.onept.mei.is1920.assignment.kafka.streams;

import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.Windowed;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public final class StreamConfigs {
	private StreamConfigs() { }

	public static final String PURCHASES_SOURCE_TOPIC = "Purchases";
	public static final String SALES_SOURCE_TOPIC = "Sales";

	public static final String RESULT_SINK_TOPIC_PREFIX = "Results.";
	public static final String EXPENSE_PER_ITEM_SINK_TOPIC = RESULT_SINK_TOPIC_PREFIX + "ExpensePerItem";
	public static final String TOTAL_EXPENSE_SINK_TOPIC = RESULT_SINK_TOPIC_PREFIX + "TotalExpense";
	public static final String AVERAGE_EXPENSE_BY_ITEM_SINK_TOPIC = RESULT_SINK_TOPIC_PREFIX + "AverageExpenseByItem";
	public static final String AVERAGE_EXPENSE_BY_ORDER_SINK_TOPIC = RESULT_SINK_TOPIC_PREFIX + "AverageExpenseByOrder";
	public static final String PROFIT_PER_ITEM_SINK_TOPIC = RESULT_SINK_TOPIC_PREFIX + "ProfitPerItem";
	public static final String TOTAL_PROFIT_SINK_TOPIC = RESULT_SINK_TOPIC_PREFIX + "TotalProfit";
	public static final String MOST_PROFITABLE_ITEM_SINK_TOPIC = RESULT_SINK_TOPIC_PREFIX + "MostProfitableItem";
	public static final String TOTAL_EXPENSE_LAST_HOUR_SINK_TOPIC = RESULT_SINK_TOPIC_PREFIX + "TotalExpenseLastHour";
	public static final String TOTAL_PROFIT_LAST_HOUR_SINK_TOPIC = RESULT_SINK_TOPIC_PREFIX + "TotalProfitLastHour";
	public static final String COUNTRY_HIGHEST_SALES_SINK_TOPIC = RESULT_SINK_TOPIC_PREFIX + "CountryHighestSales";
	public static final String REVENUE_PER_ITEM_SINK_TOPIC = RESULT_SINK_TOPIC_PREFIX + "RevenuePerItem";
	public static final String TOTAL_REVENUE_SINK_TOPIC = RESULT_SINK_TOPIC_PREFIX + "TotalRevenue";
	public static final String TOTAL_REVENUE_LAST_HOUR_SINK_TOPIC = RESULT_SINK_TOPIC_PREFIX + "TotalRevenueLastHour";

	public static final Duration WINDOW_SIZE_DURATION = Duration.ofSeconds(30);
	public static final Duration WINDOW_ADVANCE_DURATION = Duration.ofSeconds(30);
	public static final TimeWindows TIME_WINDOWS = TimeWindows.of(WINDOW_SIZE_DURATION).advanceBy(WINDOW_ADVANCE_DURATION);


	public static final String RESULT_SCHEMA_PREFIX = "{\"schema\":{\"type\":\"struct\",\"fields\":[{\"type\":\"string\",\"optional\":false,\"field\":\"id\"},{\"type\":\"string\",\"optional\":false,\"field\":\"value\"}],\"optional\":false},\"payload\":{\"id\":\"";
	public static final String RESULT_SCHEMA_SEP = "\",\"value\":\"";
	public static final String RESULT_SCHEMA_SUFFIX = "\"}}";

	public static boolean KeepWindow (Windowed<Long> window) {
		long now = System.currentTimeMillis();

		return window.window().end() > now &&
				window.window().end() < now + WINDOW_ADVANCE_DURATION.toMillis();
	}

	public static String WrapKVSchema(Long k, String v) {
		return StreamConfigs.RESULT_SCHEMA_PREFIX + k.toString() + RESULT_SCHEMA_SEP + v + RESULT_SCHEMA_SUFFIX;
	}
}
