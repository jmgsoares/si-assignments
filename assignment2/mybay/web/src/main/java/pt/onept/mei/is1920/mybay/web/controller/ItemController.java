package pt.onept.mei.is1920.mybay.web.controller;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.onept.mei.is1920.mybay.common.enums.ItemCategory;
import pt.onept.mei.is1920.mybay.common.enums.SearchType;
import pt.onept.mei.is1920.mybay.common.type.Item;
import pt.onept.mei.is1920.mybay.common.type.SearchParameters;
import pt.onept.mei.is1920.mybay.common.converter.ItemCategoryConverter;
import pt.onept.mei.is1920.mybay.common_business.contract.SaleEJBRemote;
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
import java.util.Map;

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
            itemSearchPriceUpperBound, itemSearchResultOrdering, itemIdToView, searchFilterString;
    private ItemCategory itemCategory;
    private SearchType searchType;
    private Date itemSearchDateFrom;
    private float itemPrice;
    private boolean itemsFromUser;

    @EJB
    private SaleEJBRemote sale;

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

        Item newItem = new Item()
                .setName(itemName)
                .setPrice(itemPrice)
                .setPublishDate(new Date())
                .setCategory(itemCategory)
                .setSellerEmail(session.getAttribute("email").toString())
                .setPhotoUrl(uploadedImageUrl)
                .setPhotoDeleteHash(uploadedImageDeleteHash);

        logger.debug(newItem.toString());

        if (sale.createSale(newItem)) {
            return "home";
        } else {
            logger.error("Failed to create new item");
            return "home";
        }
    }

    public void search() {
        logger.info("Searching items");
        List<Item> itemList = sale.searchSales(new SearchParameters());
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

    public int getParam(){
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
        return Integer.parseInt(paramMap.get("itemId"));
    }
}
