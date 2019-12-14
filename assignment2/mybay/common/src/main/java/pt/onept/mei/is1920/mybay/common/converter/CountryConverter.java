package pt.onept.mei.is1920.mybay.common.converter;

import pt.onept.mei.is1920.mybay.common.enums.Country;


public final class CountryConverter {

	private CountryConverter() { }

	public static String CountryToString(Country country) {
		switch (country) {
			case PT:
				return "portugal";
			case S:
				return "spain";
			case F:
				return "france";
			case GB:
				return "great britain";
			case B:
				return "belgium";
			case CH:
				return "switzerland";
			default:
				return null;
		}
	}

	public static Country StringToCountry(String s) {
		if (s.equals("portugal")) return Country.PT;
		if (s.equals("spain")) return Country.S;
		if (s.equals("france")) return Country.F;
		if (s.equals("great britain")) return Country.GB;
		if (s.equals("belgium")) return Country.B;
		if (s.equals("switzerland")) return Country.CH;
		else return null;
	}

}
