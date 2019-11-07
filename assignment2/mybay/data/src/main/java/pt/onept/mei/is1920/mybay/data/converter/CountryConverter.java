package pt.onept.mei.is1920.mybay.data.converter;

import pt.onept.mei.is1920.mybay.common.enums.Country;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class CountryConverter implements AttributeConverter<Country, String> {

	@Override
	public String convertToDatabaseColumn(Country c) {
		return pt.onept.mei.is1920.mybay.common.converter.CountryConverter.CountryToString(c);
	}

	@Override
	public Country convertToEntityAttribute(String s) {
		return pt.onept.mei.is1920.mybay.common.converter.CountryConverter.StringToCountry(s);
	}
}
