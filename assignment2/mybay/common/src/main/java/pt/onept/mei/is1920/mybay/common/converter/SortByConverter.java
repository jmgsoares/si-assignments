package pt.onept.mei.is1920.mybay.common.converter;

import pt.onept.mei.is1920.mybay.common.enums.SortBy;

public final class SortByConverter {

	private SortByConverter() { }

	public static String SortByToString(SortBy sb) {
		switch (sb) {
			case NAME:
				return "name";
			case PRICE:
				return "price";
			case DATE:
				return "date";
			default:
				return null;
		}
	}

	public static SortBy StringToSortBy(String s) {
		if(s.equals("name")) return SortBy.NAME;
		if(s.equals("price")) return SortBy.PRICE;
		if(s.equals("date")) return SortBy.DATE;
		else return null;
	}

}
