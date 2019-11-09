package pt.onept.mei.is1920.mybay.web.controller;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.onept.mei.is1920.mybay.common.converter.*;
import pt.onept.mei.is1920.mybay.common.enums.SearchFilter;
import pt.onept.mei.is1920.mybay.common.type.Item;
import pt.onept.mei.is1920.mybay.common.type.Pair;
import pt.onept.mei.is1920.mybay.common.type.SearchParameters;
import pt.onept.mei.is1920.mybay.common_business.contract.SaleEJBRemote;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Named(value = "searchController")
@RequestScoped
@Getter
@Setter
public class SearchController implements Serializable {
	private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

	private List<Item> itemList;

	private String itemName, itemCategoryString, itemCountryString, itemSearchPriceLowerBound, itemSearchPriceUpperBound,
			itemSearchResultOrdering = "descending", itemIdToView, sortByString = "date", filterByString;

	private Date itemSearchDateFrom;

	@EJB
	private SaleEJBRemote sale;

	@Inject
	private UserController user;

	public void search() {
		logger.info("Searching items");
		SearchParameters searchParameters = new SearchParameters();
		searchParameters.setSearchQuery(this.itemName);
		searchParameters.setSortBy(SortByConverter.StringToSortBy(this.sortByString));
		searchParameters.setSortOrder(SortOrderConverter.StringToSortOrder(itemSearchResultOrdering));
		if (this.filterByString != null) {
			//Search with filters
			SearchFilter searchFilter = SearchFilterConverter.StringToSearchFilter(this.filterByString);
			searchParameters.setSearchFilter(searchFilter);
			if (searchFilter != null) {
				switch (searchFilter) {
					case PRICE:
						if (itemSearchPriceLowerBound != null && itemSearchPriceUpperBound != null) {
							searchParameters.setPriceRange(
									new Pair<>(Float.parseFloat(itemSearchPriceLowerBound)
											,Float.parseFloat(itemSearchPriceUpperBound))
							);
						} else {
							searchParameters.setPriceRange(new Pair<>(0F, Float.MAX_VALUE));
						}
						break;
					case CATEGORY:
						if (itemCategoryString != null) {
							searchParameters.setCategory(ItemCategoryConverter
									.StringToItemCategory(itemCategoryString));
						}
						break;
					case COUNTRY:
						if (itemCountryString != null) {
							searchParameters.setCountry(CountryConverter
									.StringToCountry(itemCountryString));
						}
						break;
					case DATE:
						if (itemSearchDateFrom != null) {
							logger.debug(itemSearchDateFrom.toString());
							searchParameters.setDateRange(new Pair<>(itemSearchDateFrom, new Date()));
						}
						break;
				}
			}
		}

		logger.debug(searchParameters.toString());

		List<Item> itemList = sale.searchSales(searchParameters);
		if (!itemList.isEmpty()) {
			logger.debug("Got " + itemList.size() + " items");
			this.itemList = itemList;
		}
	}

	public void listMySales() {
		logger.info("Listing account sales");
		List<Item> itemList = sale.listAccountSales(user.getLoggedInAccount());
		if (itemList != null) {
			logger.debug("Got " + itemList.size() + " items");
			this.itemList = itemList;
		}
	}
}
