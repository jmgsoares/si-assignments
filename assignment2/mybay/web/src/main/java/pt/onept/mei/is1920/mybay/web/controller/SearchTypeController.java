package pt.onept.mei.is1920.mybay.web.controller;

import lombok.Getter;
import lombok.Setter;
import pt.onept.mei.is1920.mybay.common.enums.Country;
import pt.onept.mei.is1920.mybay.common.enums.SearchType;
import pt.onept.mei.is1920.mybay.common.utility.CountryConverter;
import pt.onept.mei.is1920.mybay.common.utility.SearchTypeConverter;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named(value = "searchTypeController")
@RequestScoped
@Getter
@Setter
public class SearchTypeController {

	public List<String> getSearchTypes() {
		List<String> searchTypes = new ArrayList<>();
		for (SearchType c : SearchType.values())
			searchTypes.add(SearchTypeConverter.SearchTypeToString(c));
		return searchTypes;
	}
}
