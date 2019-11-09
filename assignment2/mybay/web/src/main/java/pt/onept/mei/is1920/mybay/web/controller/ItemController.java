package pt.onept.mei.is1920.mybay.web.controller;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.onept.mei.is1920.mybay.common.converter.CountryConverter;
import pt.onept.mei.is1920.mybay.common.converter.SearchFilterConverter;
import pt.onept.mei.is1920.mybay.common.converter.SortOrderConverter;
import pt.onept.mei.is1920.mybay.common.enums.ItemCategory;
import pt.onept.mei.is1920.mybay.common.enums.SearchFilter;
import pt.onept.mei.is1920.mybay.common.enums.SortOrder;
import pt.onept.mei.is1920.mybay.common.type.Item;
import pt.onept.mei.is1920.mybay.common.type.Pair;
import pt.onept.mei.is1920.mybay.common.type.SearchParameters;
import pt.onept.mei.is1920.mybay.common.converter.ItemCategoryConverter;
import pt.onept.mei.is1920.mybay.common.type.User;
import pt.onept.mei.is1920.mybay.common_business.contract.SaleEJBRemote;
import pt.onept.mei.is1920.mybay.web.utility.ImgurApiUtility;
import pt.onept.mei.is1920.mybay.web.utility.SessionUtility;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
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
            itemSearchPriceUpperBound, itemSearchResultOrdering, itemIdToView, sortByString,
            filterByString;
    private ItemCategory itemCategory;
    private Date itemSearchDateFrom;
    private float itemPrice;

    @EJB
    private SaleEJBRemote sale;

    @Inject
    private UserController user;

    public String createSale() {
        itemCategory = ItemCategoryConverter.StringToItemCategory(itemCategoryString);

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
                .setCountry(user.getLoggedInAccount().getCountry())
                .setPublishDate(new Date())
                .setCategory(itemCategory)
                .setSellerEmail(user.getLoggedInAccount().getEmail())
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
        SearchParameters searchParameters = new SearchParameters();
        if (this.filterByString != null) {
            //Search with filters
            SearchFilter searchFilter = SearchFilterConverter.StringToSearchFilter(this.filterByString);
            if (searchFilter != null) {
                switch (searchFilter) {
                    case PRICE:
                        if (itemSearchPriceLowerBound != null && itemSearchPriceUpperBound != null) {
                            searchParameters.setPriceRange(
                                    new Pair<>(itemSearchPriceLowerBound, itemSearchPriceUpperBound)
                            );
                        } else {
                            searchParameters.setPriceRange(new Pair<>("0", "999999999999"));
                        }
                        break;
                    case CATEGORY:
                        if (itemCategoryString != null) {
                            searchParameters.setCategory(ItemCategoryConverter
                                    .StringToItemCategory(itemCategoryString));
                        }
                        break;
                    case COUNTRY:
                        if (itemCountryString != null) {
                            searchParameters.setCountry(CountryConverter
                                    .StringToCountry(itemCountryString));
                        }
                        break;
                    case DATE:
                        if (itemSearchDateFrom != null) {
                            searchParameters.setDateRange(new Pair<>(itemSearchDateFrom, new Date()));
                        }
                        break;
                }
            }
        } else {
            //Search without filters - Just by queryname
            searchParameters.setSearchQuery(this.itemName);
        }

        if (itemSearchResultOrdering != null) {
            searchParameters.setSortOrder(SortOrderConverter
                    .StringToSortOrder(itemSearchResultOrdering));
        }

        logger.debug(searchParameters.toString());


        List<Item> itemList = sale.searchSales(searchParameters);
        if (!itemList.isEmpty()) {
            logger.debug("Got " + itemList.size() + " items");
            this.itemList = itemList;
        }
    }

    public void readSale() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
        Item itemToFind = sale.listSale(new Item().setId(Integer.parseInt(paramMap.get("itemId"))));
        if (itemToFind != null) {
            this.itemToView = itemToFind;
        }
    }

    public String updateSale() {
        logger.info("Updating sale " + itemToView.getId());

        if (itemName != null) {
            logger.debug("Item name is not null, length: " + itemName.length());
            if (itemName.length() > 0) {
                itemToView.setName(itemName);
            }
        }

        if (itemPrice > 0.0f) {
            itemToView.setPrice(itemPrice);
        }

        if (itemCategoryString != null) {
            itemToView.setCategory(ItemCategoryConverter.StringToItemCategory(itemCategoryString));
        }

        if (uploadedImage != null) {
            String fileName = uploadedImage.getSubmittedFileName();
            if (fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
                ImgurApiUtility.DeleteImage(itemToView.getPhotoDeleteHash());
                JsonObject uploadResult = ImgurApiUtility.UploadImage(uploadedImage);
                itemToView.setPhotoUrl(ImgurApiUtility.GetImageUrlFromResponse(uploadResult));
                itemToView.setPhotoDeleteHash(ImgurApiUtility.GetImageDeleteHashFromResponse(uploadResult));
            } else {
                logger.debug("Invalid file format submitted");
                return "home";
            }
        }

        logger.debug(itemToView.toString());

        if (sale.updateSale(itemToView)) {
            logger.debug("Sale updated successfully");
            return "home";
        }
        logger.debug("Couldn't update sale");
        return "home";
    }

    public String deleteSale() {
        logger.info("Deleting sale " + itemToView.getId());
        try {
            if (sale.deleteSale(itemToView)) {
                logger.debug("Deleted sale successfully");
            } else {
                logger.debug("Couldn't delete sale");
                return "home";
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "home";
    }

    public void listMySales() {
        logger.info("Listing account sales");
        List<Item> itemList = sale.listAccountSales(user.getLoggedInAccount());
        if (itemList != null) {
            logger.debug("Got " + itemList.size() + " items");
            this.itemList = itemList;
        }
    }
}
