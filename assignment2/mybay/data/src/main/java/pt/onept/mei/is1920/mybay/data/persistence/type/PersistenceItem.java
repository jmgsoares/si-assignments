package pt.onept.mei.is1920.mybay.data.persistence.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import pt.onept.mei.is1920.mybay.common.enums.ItemCategory;
import pt.onept.mei.is1920.mybay.common.utility.ItemCategoryConverter;

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

	@Column(name = "price", nullable = false)
	private float price;

	@Column(name = "publish_date", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date publishDate;

	@Column(name = "category", nullable = false)
	@Convert(converter = ItemCategoryConverter.class)
	private ItemCategory category;

	@ManyToOne
	private PersistenceUser seller;

}
