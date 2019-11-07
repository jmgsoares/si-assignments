package pt.onept.mei.is1920.mybay.common.converter;

import pt.onept.mei.is1920.mybay.common.enums.SearchType;

public final class SearchTypeConverter {

	private SearchTypeConverter() { }

	public static String SearchTypeToString(SearchType searchType) {
		switch (searchType) {
			case ALL:
				return "all";
			case USER:
				return "user";
			default:
				return null;
		}
	}

	public static SearchType StringToSearchType(String s) {
		if (s.equals("all")) return SearchType.ALL;
		if (s.equals("user")) return SearchType.USER;
		else return null;
	}
}
