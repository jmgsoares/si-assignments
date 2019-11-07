package pt.onept.mei.is1920.mybay.web.controller;

import lombok.Getter;
import lombok.Setter;
import pt.onept.mei.is1920.mybay.common.converter.SortByConverter;
import pt.onept.mei.is1920.mybay.common.enums.SortBy;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named(value = "sortByController")
@RequestScoped
@Getter
@Setter
public class SortByController {

	public List<String> getSortBy() {
		List<String> sortBys = new ArrayList<>();
		for (SortBy sb : SortBy.values()) {
			String sortBy = SortByConverter.SortByToString(sb);
			if(sortBy != null) sortBys.add(sortBy);
		}
		return sortBys;
	}
}
