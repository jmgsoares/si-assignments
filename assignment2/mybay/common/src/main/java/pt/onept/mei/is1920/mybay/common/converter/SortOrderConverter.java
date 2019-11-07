package pt.onept.mei.is1920.mybay.common.converter;

import pt.onept.mei.is1920.mybay.common.enums.SortOrder;

public final class SortOrderConverter {

	private SortOrderConverter() { }

	public static String SortOrderToString(SortOrder sortOrder) {
		switch (sortOrder) {
			case ASC:
				return "ascending";
			case DES:
				return "descending";
			default:
				return null;
		}
	}

	public static SortOrder StringToSortOrder(String s) {
		if (s.equals("ascending")) return SortOrder.ASC;
		if (s.equals("descending")) return SortOrder.DES;
		else return null;
	}

}
