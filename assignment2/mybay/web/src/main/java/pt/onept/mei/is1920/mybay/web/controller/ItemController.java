package pt.onept.mei.is1920.mybay.web.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.onept.mei.is1920.mybay.common.contract.ItemEJBRemote;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

@Named(value = "itemController")
@RequestScoped
@Getter
@Setter
public class ItemController implements Serializable {
	private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

	private static final Cloudinary imageHandler = new Cloudinary(
			ObjectUtils.asMap("cloud_name", "onept"));

	private Part uploadedPicture;

	@EJB
	private ItemEJBRemote item;

	public Part getUploadedPicture() {
		return uploadedPicture;
	}

	public void setUploadedPicture(Part uploadedPicture) {
		this.uploadedPicture = uploadedPicture;
	}

	public void saveFile(){

		try (InputStream input = uploadedPicture.getInputStream()) {
			byte[] picture = input.readAllBytes();
			imageHandler.uploader().upload(picture,
					ObjectUtils.asMap(
							"use_filename", "false",
							"unique_filename", "true"));
		}

		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
