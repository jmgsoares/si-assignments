package pt.onept.mei.is1920.mybay.data.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import pt.onept.mei.is1920.mybay.common.enums.Country;
import pt.onept.mei.is1920.mybay.common.enums.ItemCategory;
import pt.onept.mei.is1920.mybay.data.converter.CountryConverter;
import pt.onept.mei.is1920.mybay.data.converter.ItemCategoryConverter;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
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

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "country", nullable = false)
	@Convert(converter = CountryConverter.class)
	private Country country;

	@Column(name = "price", nullable = false)
	private float price;

	@Column(name = "publish_date", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date publishDate;

	@Column(name = "category", nullable = false)
	@Convert(converter = ItemCategoryConverter.class)
	private ItemCategory category;

	@Column(name = "image_url", nullable = false)
	private String imageUrl;

	@Column(name = "image_delete_hash", nullable = false)
	private String imageDeleteHash;

	@ManyToOne
	@OnDelete( action = OnDeleteAction.CASCADE )
	private PersistenceUser seller;

}
