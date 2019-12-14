package pt.onept.mei.is1920.mybay.common.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import pt.onept.mei.is1920.mybay.common.enums.*;

import java.util.Date;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class SearchParameters {
	private SearchFilter searchFilter;
	private SortOrder sortOrder;
	private SortBy sortBy;
	private Pair<Float> priceRange;
	private Pair<Date> dateRange;
	private ItemCategory category;
	private Country country;
	private String searchQuery;

}
