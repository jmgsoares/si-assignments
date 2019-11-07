package pt.onept.mei.is1920.mybay.web.controller;

import lombok.Getter;
import lombok.Setter;
import pt.onept.mei.is1920.mybay.common.enums.SearchType;
import pt.onept.mei.is1920.mybay.common.converter.SearchTypeConverter;

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
		for (SearchType c : SearchType.values()) {
			String searchType = SearchTypeConverter.SearchTypeToString(c);
			if(searchType != null) searchTypes.add(searchType);
		}
		return searchTypes;
	}
}
