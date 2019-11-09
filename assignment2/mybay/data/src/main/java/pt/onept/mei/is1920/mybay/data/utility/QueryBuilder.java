package pt.onept.mei.is1920.mybay.data.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.onept.mei.is1920.mybay.common.converter.CountryConverter;
import pt.onept.mei.is1920.mybay.common.converter.ItemCategoryConverter;
import pt.onept.mei.is1920.mybay.common.type.SearchParameters;

import java.text.SimpleDateFormat;

public final class QueryBuilder {
	private static final Logger logger = LoggerFactory.getLogger(QueryBuilder.class);

	private static final String PREFIX = "SELECT i FROM PersistenceItem i WHERE i.name LIKE CONCAT('%";

	public static String BuildQuery(SearchParameters searchParameters) {

		String query = QueryBuilder.PREFIX +  searchParameters.getSearchQuery() + "%')";

		if (searchParameters.getSearchFilter() != null) {
			switch (searchParameters.getSearchFilter()) {
				case PRICE:
					query = query
							+ " AND i.price BETWEEN '"
							+ searchParameters.getPriceRange().from
							+ "' AND '"
							+ searchParameters.getPriceRange().to +"'";
					break;
				case CATEGORY:
					query = query
							+ " AND i.category = '"
							+ ItemCategoryConverter.ItemCategoryToString(searchParameters.getCategory())
							+"'";
					break;
				case COUNTRY:
					query = query
							+ " AND i.country = '"
							+ CountryConverter.CountryToString(searchParameters.getCountry())
							+"'";
					break;
				case DATE:
					query = query
							+ " AND i.publishDate > '"
							+ new SimpleDateFormat("yyyy-MM-dd").format(searchParameters.getDateRange().from)
							+"'";
					break;
			}
		}
		switch (searchParameters.getSortBy()) {
			case NAME:
				query = query
						+ " ORDER BY i.name ";
				break;
			case PRICE:
				query = query
						+ " ORDER BY i.price ";
				break;
			case DATE:
				query = query
						+ " ORDER BY i.publishDate ";
				break;
		}
		switch (searchParameters.getSortOrder()) {
			case ASC:
				query = query + "ASC";
				break;
			case DES:
				query = query + "DESC";
				break;
		}

		logger.debug("Resulting query: " + query);
		return query;

	}
}
