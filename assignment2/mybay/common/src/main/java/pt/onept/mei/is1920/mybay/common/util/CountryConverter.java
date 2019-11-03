package pt.onept.mei.is1920.mybay.common.util;

import pt.onept.mei.is1920.mybay.common.enums.Country;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class CountryConverter implements AttributeConverter <Country, String> {

	@Override
	public String convertToDatabaseColumn(Country country) {
		return CountryConverter.CountryToString(country);
	}

	@Override
	public Country convertToEntityAttribute(String s) {
		return CountryConverter.StringToCountry(s);
	}

	public static String CountryToString(Country country) {
		switch (country) {
			case PT:
				return "Portugal";
			case S:
				return "Spain";
			case F:
				return "France";
			case GB:
				return "Great Britain";
			case B:
				return "Belgium";
			case CH:
				return "Switzerland";
			default:
				return null;
		}
	}

	public static Country StringToCountry(String s) {
		if (s.equals("Portugal")) return Country.PT;
		if (s.equals("Spain")) return Country.S;
		if (s.equals("France")) return Country.F;
		if (s.equals("Great Britain")) return Country.GB;
		if (s.equals("Belgium")) return Country.B;
		if (s.equals("Switzerland")) return Country.CH;
		else return null;
	}
}
