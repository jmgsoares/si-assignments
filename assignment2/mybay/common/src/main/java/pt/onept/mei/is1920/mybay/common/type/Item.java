package pt.onept.mei.is1920.mybay.common.type;

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
public class Item {
	private int id;
	private String name;
	private float price;
	private Date publishDate;
	private ItemCategory category;
	private User seller;
	private String itemImageUrl;
	private String itemImageDeleteHash;

	public Item(String name, float price, Date publishDate, ItemCategory category,
	            User seller, String itemImageUrl, String itemImageDeleteHash) {
		this.name = name;
		this.price = price;
		this.publishDate = publishDate;
		this.category = category;
		this.seller = seller;
		this.itemImageUrl = itemImageUrl;
		this.itemImageDeleteHash = itemImageDeleteHash;
	}
}
