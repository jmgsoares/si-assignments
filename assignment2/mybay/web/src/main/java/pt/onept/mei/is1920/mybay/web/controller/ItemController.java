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


@Named(value = "itemController")
@RequestScoped
@Getter
@Setter
public class ItemController implements Serializable {
	private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

	private Part uploadedImage;

	@EJB
	private ItemEJBRemote item;

	public String saveImage() {

		logger.info("Uploading image");

		String fileName = uploadedImage.getSubmittedFileName();
		if (fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
			JsonObject result = ImgurApiUtility.uploadImage(uploadedImage);
			String imageUrl = result.get("link").toString()
					.substring(1,result.get("link").toString().length()-1);
			String imageDeleteHash = result.get("deletehash").toString()
					.substring(1, result.get("deletehash").toString().length()-1);

		}
		else {
			logger.debug("Invalid file format submitted");
		}

		return "image";
	}
}
