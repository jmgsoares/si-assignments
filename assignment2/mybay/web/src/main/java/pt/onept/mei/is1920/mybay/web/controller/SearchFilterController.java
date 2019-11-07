package pt.onept.mei.is1920.mybay.web.controller;

import lombok.Getter;
import lombok.Setter;
import pt.onept.mei.is1920.mybay.common.enums.SearchFilter;
import pt.onept.mei.is1920.mybay.common.converter.SearchFilterConverter;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named(value = "searchFilterController")
@RequestScoped
@Getter
@Setter
public class SearchFilterController {

	public List<String> getSearchFilters() {
		List<String> searchFilters = new ArrayList<>();
		for (SearchFilter c : SearchFilter.values()) {
			String searchFilter = SearchFilterConverter.SearchFilterToString(c);
			if(searchFilter != null) searchFilters.add(searchFilter);
		}
		return searchFilters;
	}
}
