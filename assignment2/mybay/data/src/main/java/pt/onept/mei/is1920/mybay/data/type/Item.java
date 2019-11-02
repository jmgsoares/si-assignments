package pt.onept.mei.is1920.mybay.data.type;

import pt.onept.mei.is1920.mybay.common.enums.ItemCategory;
import pt.onept.mei.is1920.mybay.common.util.ItemCategoryConverter;

import javax.persistence.*;
import java.awt.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "item")
public class Item implements Serializable {
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
    private User seller;

	public Item() {
		super();
	}

    public Item(int id, String name, String countryOfOrigin, float price, Date publishDate, ItemCategory category, Image photo, User seller) {
        super();
        this.id = id;
        this.name = name;
        this.price = price;
        this.publishDate = publishDate;
        this.category = category;
        this.seller = seller;
    }

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public ItemCategory getCategory() {
        return category;
    }

    public void setCategory(ItemCategory category) {
        this.category = category;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

}
