package pt.onept.mei.is1920.mybay.data;

import javax.persistence.*;
import java.awt.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Item implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    //@TODO see if this should be a string or a token
    private String contryOfOrigin;
    private float price;
    @Temporal(TemporalType.DATE)
    private Date publishDate;
    //@TODO see if this should be a string or a token
    private String category;

    private Image photo;

    @ManyToOne
    private User seller;

    public Item(int id, String name, String contryOfOrigin, float price, Date publishDate, String category, Image photo, User seller) {
        super();
        this.id = id;
        this.name = name;
        this.contryOfOrigin = contryOfOrigin;
        this.price = price;
        this.publishDate = publishDate;
        this.category = category;
        this.photo = photo;
        this.seller = seller;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContryOfOrigin() {
        return contryOfOrigin;
    }

    public void setContryOfOrigin(String contryOfOrigin) {
        this.contryOfOrigin = contryOfOrigin;
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

    public Image getPhoto() {
        return photo;
    }

    public void setPhoto(Image photo) {
        this.photo = photo;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }
}
