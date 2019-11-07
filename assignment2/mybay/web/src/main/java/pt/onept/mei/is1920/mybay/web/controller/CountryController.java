package pt.onept.mei.is1920.mybay.web.controller;

import lombok.Getter;
import lombok.Setter;
import pt.onept.mei.is1920.mybay.common.enums.Country;
import pt.onept.mei.is1920.mybay.common.converter.CountryConverter;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named(value = "countryController")
@RequestScoped
@Getter
@Setter
public class CountryController {

	public List<String> getCountries() {
		List<String> countries = new ArrayList<>();
		for (Country c : Country.values())
			countries.add(CountryConverter.CountryToString(c));
		return countries;
	}
}
