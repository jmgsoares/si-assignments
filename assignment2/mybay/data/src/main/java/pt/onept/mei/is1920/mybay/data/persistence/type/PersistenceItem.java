package pt.onept.mei.is1920.mybay.data.persistence.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;
import pt.onept.mei.is1920.mybay.common.enums.ItemCategory;
import pt.onept.mei.is1920.mybay.common.type.Item;
import pt.onept.mei.is1920.mybay.common.utility.ItemCategoryConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Indexed
@Table(name = "item")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class PersistenceItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@SequenceGenerator(name = "seq_item", sequenceName = "seq_item_id", allocationSize = 1)
	@GeneratedValue(generator = "seq_item", strategy = GenerationType.SEQUENCE)
	private int id;

	@Field(index= Index.YES, analyze= Analyze.YES, store= Store.NO)
	@Column(name = "name", nullable = false)
	private String name;

	@Field(index= Index.YES, analyze= Analyze.YES, store= Store.NO)
	@Column(name = "price", nullable = false)
	private float price;

	@Field(index= Index.YES, analyze= Analyze.YES, store= Store.NO)
	@Column(name = "publish_date", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date publishDate;

	@Field(index= Index.YES, analyze= Analyze.YES, store= Store.NO)
	@Column(name = "category", nullable = false)
	@Convert(converter = ItemCategoryConverter.class)
	private ItemCategory category;

	@Column(name = "image_url", nullable = false)
	private String imageUrl;

	@Column(name = "image_delete_hash", nullable = false)
	private String imageDeleteHash;

	@IndexedEmbedded
	@ManyToOne
	private PersistenceUser seller;

	public PersistenceItem(Item item) {
		name = item.getName();
		price = item.getPrice();
		publishDate = item.getPublishDate();
		category = item.getCategory();
		seller = new PersistenceUser(item.getSeller());
		imageUrl = item.getItemImageUrl();
		imageDeleteHash = item.getItemImageDeleteHash();
	}
}
