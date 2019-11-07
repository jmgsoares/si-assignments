package pt.onept.mei.is1920.mybay.web.controller;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.onept.mei.is1920.mybay.common.contract.ItemEJBRemote;
import pt.onept.mei.is1920.mybay.common.enums.ItemCategory;
import pt.onept.mei.is1920.mybay.common.enums.SearchType;
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

    private List<Item> itemList;
    private Item itemToView;

    private String itemName, itemCategoryString, itemCountryString, itemSearchPriceLowerBound,
            itemSearchPriceUpperBound, itemSearchResultOrdering, itemIdToView, searchTypeString;
    private ItemCategory itemCategory;
    private SearchType searchType;
    private Date itemSearchDateFrom;
    private float itemPrice;
    private boolean itemsFromUser;

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
            uploadedImageUrl = ImgurApiUtility.GetImageUrlFromResponse(uploadResult);
            uploadedImageDeleteHash = ImgurApiUtility.GetImageDeleteHashFromResponse(uploadResult);
        } else {
            logger.debug("Invalid file format submitted");
            return "home";
        }

        User seller = new User().setEmail(session.getAttribute("email").toString());

        Item newItem = new Item(itemName, itemPrice, new Date(),
                itemCategory, seller, uploadedImageUrl, uploadedImageDeleteHash);

        logger.debug(newItem.toString());

        if (item.create(newItem)) {
            return "home";
        } else {
            logger.error("Failed to create new item");
            return "home";
        }
    }

    public void search() {
        logger.info("Searching items");
        List<Item> itemList = item.search(new SearchParameters());
        if(!itemList.isEmpty()) {
            logger.debug("Got " + itemList.size() + " items");
            this.itemList = itemList;
        }
    }

    public String read() {
        int itemId = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext()
                .getRequestParameterMap().get("selectedItemId"));

        return "home";
    }

    public String update() {
        return "home";
    }

    public String delete() {
        return "home";
    }
}
