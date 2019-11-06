package pt.onept.mei.is1920.mybay.common.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import pt.onept.mei.is1920.mybay.common.enums.Country;
import pt.onept.mei.is1920.mybay.common.enums.ItemCategory;
import pt.onept.mei.is1920.mybay.common.enums.SearchType;
import pt.onept.mei.is1920.mybay.common.enums.SortType;

import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class SearchParameters {
	private int id;
	private List<SearchType> searchTypes;
	private SortType sortType;
	private Pair<String> priceRange;
	private Pair<Date> dateRange;
	private ItemCategory category;
	private Country country;
	private String searchQuery;
	private String userAccount;

}
