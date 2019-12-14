package pt.onept.mei.is1920.assignment.kafka.common.util;

import pt.onept.mei.is1920.assignment.kafka.common.enums.AnalyticsType;

public class AnalyticsTypeMapperUtility {
	private static String GLOBAL = "global";
	private static String PER_ITEM = "perItem";
	private static String LAST_HOUR = "lastHour";
	private static String NA = "na";

	public static String AnalyticTypeToString(AnalyticsType at) {
		switch (at) {
			case GLOBAL:
				return GLOBAL;
			case PER_ITEM:
				return PER_ITEM;
			case LAST_HOUR:
				return LAST_HOUR;
			default:
				return NA;
		}
	}

	public static AnalyticsType StringToAnalyticType(String s) {
		if (s.equals(GLOBAL)) return AnalyticsType.GLOBAL;
		else if (s.equals(PER_ITEM)) return AnalyticsType.GLOBAL;
		else if (s.equals(LAST_HOUR)) return AnalyticsType.GLOBAL;
		else return AnalyticsType.NA;
	}
}

