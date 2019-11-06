package pt.onept.mei.is1920.mybay.common.utility;

import pt.onept.mei.is1920.mybay.common.enums.SortType;

import javax.persistence.Converter;

@Converter
public final class SortTypeConverter {

	private SortTypeConverter() { }

	public static String SortTypeToString(SortType sortType) {
		switch (sortType) {
			case ASC:
				return "ascending";
			case DES:
				return "descending";
			default:
				return null;
		}
	}

	public static SortType StringToSortType(String s) {
		if (s.equals("ascending")) return SortType.ASC;
		if (s.equals("descending")) return SortType.DES;
		else return null;
	}

}
