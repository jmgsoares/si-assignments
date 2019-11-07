package pt.onept.mei.is1920.mybay.common.utility;

import pt.onept.mei.is1920.mybay.common.enums.SearchType;

import javax.persistence.AttributeConverter;

public class SearchTypeConverter implements AttributeConverter<SearchType, String> {

	public static String SearchTypeToString(SearchType searchType) {
		switch (searchType) {
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

	public static SearchType StringToSearchType(String s) {
		if (s.equals("price")) return SearchType.PRICE;
		if (s.equals("category")) return SearchType.CATEGORY;
		if (s.equals("country")) return SearchType.COUNTRY;
		if (s.equals("date")) return SearchType.DATE;
		else return null;
	}

	@Override
	public String convertToDatabaseColumn(SearchType searchType) {
		return SearchTypeToString(searchType);
	}

	@Override
	public SearchType convertToEntityAttribute(String s) {
		return StringToSearchType(s);
	}
}
