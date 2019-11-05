package pt.onept.mei.is1920.mybay.web.controller;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.onept.mei.is1920.mybay.common.contract.ItemEJBRemote;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


@Named(value = "itemController")
@RequestScoped
@Getter
@Setter
public class ItemController implements Serializable {
	private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

	private byte[] uploadedImage;

	@EJB
	private ItemEJBRemote item;

	public String saveImage() {

		URL url = null;
		try {
			url = new URL("https://api.imgur.com/3/upload");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Authorization", "Client-ID d34b5a0ca538c5b");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			StringBuilder data = new StringBuilder();
			data.append(URLEncoder.encode(new String(uploadedImage), "UTF-8"));
			connection.setRequestProperty("Content-Length",Integer.toString(data.toString().getBytes().length));
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream ());
			wr.writeBytes(data.toString());
			wr.flush();
			wr.close();
			connection.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "image";

	}

}
