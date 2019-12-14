package pt.onept.mei.is1920.assignment.kafka.common.util;

import pt.onept.mei.is1920.assignment.kafka.common.enums.Analytics;

public final class AnalyticsMapperUtility {

	private static String averageExpenseByItem = "r11";
	private static String averageExpenseByOrder = "r12";
	private static String countryHighestSalesPerItem = "r17";
	private static String expensePerItem = "r6";
	private static String mostProfitableItem = "r13";
	private static String profitPerItem = "r7";
	private static String revenuePerItem = "r5";
	private static String totalExpense = "r9";
	private static String totalExpenseLastHour = "r15";
	private static String totalProfit = "r10";
	private static String totalProfitLastHour = "r16";
	private static String totalRevenue = "r8";
	private static String totalRevenueLastHour = "r14";


	private AnalyticsMapperUtility () { }

	public static String AnalyticToString(Analytics a) {
		switch (a) {
			case AVERAGE_EXPENSE_BY_ITEM:
				return averageExpenseByItem;
			case AVERAGE_EXPENSE_BY_ORDER:
				return averageExpenseByOrder;
			case COUNTRY_HIGHEST_SALES_PER_ITEM:
				return countryHighestSalesPerItem;
			case EXPENSE_PER_ITEM:
				return expensePerItem;
			case MOST_PROFITABLE_ITEM:
				return mostProfitableItem;
			case PROFIT_PER_ITEM:
				return profitPerItem;
			case REVENUE_PER_ITEM:
				return revenuePerItem;
			case TOTAL_EXPENSE:
				return totalExpense;
			case TOTAL_EXPENSE_LAST_HOUR:
				return totalExpenseLastHour;
			case TOTAL_PROFIT:
				return totalProfit;
			case TOTAL_PROFIT_LAST_HOUR:
				return totalProfitLastHour;
			case TOTAL_REVENUE:
				return totalRevenue;
			case TOTAL_REVENUE_LAST_HOUR:
				return totalRevenueLastHour;
			default:
				return "NA";
		}
	}

	public static Analytics StringToAnalytic(String s) {
		if(s.equals(averageExpenseByItem)) return Analytics.AVERAGE_EXPENSE_BY_ITEM;
		if(s.equals(averageExpenseByOrder)) return Analytics.AVERAGE_EXPENSE_BY_ORDER;
		if(s.equals(countryHighestSalesPerItem)) return Analytics.COUNTRY_HIGHEST_SALES_PER_ITEM;
		if(s.equals(expensePerItem)) return Analytics.EXPENSE_PER_ITEM;
		if(s.equals(mostProfitableItem)) return Analytics.MOST_PROFITABLE_ITEM;
		if(s.equals(profitPerItem)) return Analytics.PROFIT_PER_ITEM;
		if(s.equals(revenuePerItem)) return Analytics.REVENUE_PER_ITEM;
		if(s.equals(totalExpense)) return Analytics.TOTAL_EXPENSE;
		if(s.equals(totalExpenseLastHour)) return Analytics.TOTAL_EXPENSE_LAST_HOUR;
		if(s.equals(totalProfit)) return Analytics.TOTAL_PROFIT;
		if(s.equals(totalProfitLastHour)) return Analytics.TOTAL_PROFIT_LAST_HOUR;
		if(s.equals(totalRevenue)) return Analytics.TOTAL_REVENUE;
		if(s.equals(totalRevenueLastHour)) return Analytics.TOTAL_REVENUE_LAST_HOUR;
		else return null;
	}
}
