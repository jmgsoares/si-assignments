package pt.onept.mei.is1920.mybay.data.type;

import javax.persistence.*;
import java.awt.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "item")
public class Item implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    //@TODO see if this should be a string or a token
    private String countryOfOrigin;
    private float price;
    @Temporal(TemporalType.DATE)
    private Date publishDate;
    //@TODO see if this should be a string or a token
    private String category;

    //TODO Image has to be stored on the file system
    //private Image photo;

    @ManyToOne
    private User seller;

	public Item() {
		super();
	}

    public Item(int id, String name, String countryOfOrigin, float price, Date publishDate, String category, Image photo, User seller) {
        super();
        this.id = id;
        this.name = name;
        this.countryOfOrigin = countryOfOrigin;
        this.price = price;
        this.publishDate = publishDate;
        this.category = category;
        //this.photo = photo;
        this.seller = seller;
    }

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

//    public Image getPhoto() {
//        return photo;
//    }
//
//    public void setPhoto(Image photo) {
//        this.photo = photo;
//    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }
}
