package pt.onept.mei.is1920.mybay.business.ejb;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.onept.mei.is1920.mybay.common.enums.SortBy;
import pt.onept.mei.is1920.mybay.common.enums.SortOrder;
import pt.onept.mei.is1920.mybay.common.type.Item;
import pt.onept.mei.is1920.mybay.common.type.SearchParameters;
import pt.onept.mei.is1920.mybay.common_data.contract.ItemEJBRemote;
import pt.onept.mei.is1920.mybay.common_data.contract.UserEJBRemote;

import javax.ejb.*;
import java.util.List;

@Startup
@Singleton
public class EmailEJB {
	private static final Logger logger = LoggerFactory.getLogger(EmailEJB.class);

	@EJB
	private UserEJBRemote userEJBRemote;

	@EJB
	private ItemEJBRemote itemEJBRemote;


	@Schedule(dayOfWeek="Fri", hour="15")
	public void sendNewsletter() {
		logger.info("Sending newsletter to users");

		List<String> emails = userEJBRemote.getUsersEmails();

		logger.debug("Got " + emails.size() + " emails.");

		SearchParameters searchParameters = new SearchParameters()
				.setSearchQuery("")
				.setSortOrder(SortOrder.DES)
				.setSortBy(SortBy.DATE);

		List<Item> newsLetterItems = itemEJBRemote.search(searchParameters, 3);

		logger.debug("Got " + newsLetterItems.size() + " items.");

		JSONArray emailJsonArray = new JSONArray();

		for (String s: emails
		     ) {
			JSONObject dest = new JSONObject();
			dest.put("Email", s);
			dest.put("Name", s);
			emailJsonArray.put(dest);
		}

		logger.debug("Sending newsletter to: " + emailJsonArray.toString(2));

		MailjetClient client;
		MailjetRequest request;
		MailjetResponse response;
		client = new MailjetClient(System.getenv("58aa52c3891f33d6d827a86fa3ed4b73"),
				System.getenv("a9924aa9cd5731ef67a2eea9514d5cd1"), new ClientOptions("v3.1"));
		request = new MailjetRequest(Emailv31.resource)
				.property(Emailv31.MESSAGES, new JSONArray()
						.put(new JSONObject()
								.put(Emailv31.Message.FROM, new JSONObject()
										.put("Email", "mailjet@onept.pt")
										.put("Name", "JG"))
								.put(Emailv31.Message.TO, emailJsonArray)
								.put(Emailv31.Message.SUBJECT, "MYBAY NEWEST ITEMS!!")
								.put(Emailv31.Message.TEXTPART, "CHECKOUT THE HOTTEST NEWS IN TOWN")
								.put(Emailv31.Message.HTMLPART, newsLetterItems.toString())
								.put(Emailv31.Message.CUSTOMID, "AppGettingStartedTest")));
		try {
			logger.debug("Sending API request");
			logger.debug("Response code: " + client.post(request).getStatus());
		} catch (MailjetException | MailjetSocketTimeoutException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
