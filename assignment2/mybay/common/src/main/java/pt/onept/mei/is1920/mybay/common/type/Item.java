package pt.onept.mei.is1920.mybay.common.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import pt.onept.mei.is1920.mybay.common.enums.ItemCategory;

import java.util.Date;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Item {
	private int id;
	private String name;
	private float price;
	private Date publishDate;
	private ItemCategory category;
	private User seller;
	private String itemImageUrl;
	private String itemImageDeleteHash;

}
