package pt.onept.mei.is1920.mybay.common.contract;

import pt.onept.mei.is1920.mybay.common.type.SearchParameters;

import java.util.List;

public interface Searchable<T> {
	List<T> search(SearchParameters searchParameters);
}
