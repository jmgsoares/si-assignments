package pt.onept.mei.is1920.mybay.common.converter;

import pt.onept.mei.is1920.mybay.common.enums.SearchFilter;

public final class SearchFilterConverter {

	private SearchFilterConverter() { }

	public static String SearchFilterToString(SearchFilter searchFilter) {
		switch (searchFilter) {
			case PRICE:
				return "price";
			case CATEGORY:
				return "category";
			case COUNTRY:
				return "country";
			case DATE:
				return "date";
			default:
				return null;
		}
	}

	public static SearchFilter StringToSearchFilter(String s) {
		if (s.equals("price")) return SearchFilter.PRICE;
		if (s.equals("category")) return SearchFilter.CATEGORY;
		if (s.equals("country")) return SearchFilter.COUNTRY;
		if (s.equals("date")) return SearchFilter.DATE;
		else return null;
	}
}
