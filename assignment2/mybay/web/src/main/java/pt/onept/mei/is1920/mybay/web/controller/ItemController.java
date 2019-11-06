package pt.onept.mei.is1920.mybay.web.controller;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.onept.mei.is1920.mybay.common.contract.ItemEJBRemote;
import pt.onept.mei.is1920.mybay.common.enums.ItemCategory;
import pt.onept.mei.is1920.mybay.common.type.Item;
import pt.onept.mei.is1920.mybay.common.type.SearchParameters;
import pt.onept.mei.is1920.mybay.common.type.User;
import pt.onept.mei.is1920.mybay.common.utility.ItemCategoryConverter;
import pt.onept.mei.is1920.mybay.web.utility.ImgurApiUtility;
import pt.onept.mei.is1920.mybay.web.utility.SessionUtility;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.*;
import java.util.Date;
import java.util.List;


@Named(value = "itemController")
@ViewScoped
@Getter
@Setter
public class ItemController implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    private Part uploadedImage;

    private String itemName, itemCategoryString, itemCountryString, itemSearchPriceLowerBound,
            itemSearchPriceUpperBound, itemSearchResultOrdering;
    private ItemCategory itemCategory;
    private Date itemSearchDateFrom;
    private float itemPrice;

    @EJB
    private ItemEJBRemote item;

    public String create() {
        itemCategory = ItemCategoryConverter.StringToItemCategory(itemCategoryString);
        HttpSession session = SessionUtility.getSession();

        logger.info("Uploading image");
        String uploadedImageUrl, uploadedImageDeleteHash;

        String fileName = uploadedImage.getSubmittedFileName();
        if (fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            JsonObject uploadResult = ImgurApiUtility.UploadImage(uploadedImage);
            uploadedImageUrl = uploadResult.get("link").toString()
                    .substring(1, uploadResult.get("link").toString().length() - 1);
            uploadedImageDeleteHash = uploadResult.get("deletehash").toString()
                    .substring(1, uploadResult.get("deletehash").toString().length() - 1);
            //boolean deleteResult = ImgurApiUtility.DeleteImage(uploadedImageDeleteHash);
        } else {
            logger.debug("Invalid file format submitted");
            return "home";
        }

        Date itemDate = new Date(System.currentTimeMillis());
        String email = session.getAttribute("email").toString();
        User userAddingItem = new User().setEmail(email);

        logger.debug("Item to add: " + itemName + ", price: " + itemPrice + ", date: " + itemDate
                + ", category: " + itemCategory + ", user: " + userAddingItem.getEmail()
                + ", url: " + uploadedImageUrl + ", delete url: " + uploadedImageDeleteHash);
        Item itemToAdd = new Item()
                .setName(itemName)
                .setPrice(itemPrice)
                .setPublishDate(itemDate)
                .setCategory(itemCategory)
                .setSeller(userAddingItem)
                .setItemImageUrl(uploadedImageUrl)
                .setItemImageDeleteHash(uploadedImageDeleteHash);

        if (item.create(itemToAdd)) {
            return "home";
        } else {
            logger.info("Failed to create new item");
            return "home";
        }
    }

    public String search() {
        List<Item> itemList = item.search(new SearchParameters());
        if(!itemList.isEmpty()) {
            HttpSession session = SessionUtility.getSession();
            session.setAttribute("itemList", itemList);
            return "search";
        }
        return "home";
    }

    public String viewItem() {
        int itemId = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selectedItemId"));
        if(itemId != 0) {
            // Search for the item and put on the session the Item
            List<Item> itemToView = item.search(new SearchParameters().setId(itemId));
            if(!itemToView.isEmpty()) {
                HttpSession session = SessionUtility.getSession();
                session.setAttribute("itemToView", itemToView.get(0));
                return "item";
            } else {
                return "home";
            }
        } else {
            return "home";
        }
    }
}
