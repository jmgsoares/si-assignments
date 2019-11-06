package pt.onept.mei.is1920.mybay.web.controller;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.onept.mei.is1920.mybay.common.contract.ItemEJBRemote;
import pt.onept.mei.is1920.mybay.web.utility.ImgurApiUtility;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.io.*;
import java.util.Date;


@Named(value = "itemController")
@RequestScoped
@Getter
@Setter
public class ItemController implements Serializable {
	private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

	private Part uploadedImage;

	private String itemName, itemCategoryString, itemCountryString, itemSearchPriceLowerBound,
			itemSearchPriceUpperBound, itemSearchResultOrdering;
	private Date itemSearchDateFrom;
	private float itemPrice;

	@EJB
	private ItemEJBRemote item;

	public String saveImage() {

		logger.info("Uploading image");

		String fileName = uploadedImage.getSubmittedFileName();
		if (fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
			JsonObject uploadResult = ImgurApiUtility.UploadImage(uploadedImage);
			String uploadedImageUrl = uploadResult.get("link").toString()
					.substring(1,uploadResult.get("link").toString().length()-1);
			String uploadedImageDeleteHash = uploadResult.get("deletehash").toString()
					.substring(1, uploadResult.get("deletehash").toString().length()-1);

			boolean deleteResult = ImgurApiUtility.DeleteImage(uploadedImageDeleteHash);

		}

		else {
			logger.debug("Invalid file format submitted");
		}

		return "image";
	}

	public String create() {
		return "home";
	}

	public String search() {
		return "home";
	}
}
