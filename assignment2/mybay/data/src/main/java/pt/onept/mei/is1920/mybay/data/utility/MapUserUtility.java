package pt.onept.mei.is1920.mybay.data.utility;

import pt.onept.mei.is1920.mybay.common.type.User;
import pt.onept.mei.is1920.mybay.data.type.PersistenceUser;

public final class MapUserUtility {

	private MapUserUtility() { }

	public static User MapPersistenceUserToUser(PersistenceUser persistedUser) {
		return new User().setEmail(persistedUser.getEmail())
							  .setName(persistedUser.getName())
							  .setCountry(persistedUser.getCountry())
							  .setPassword(persistedUser.getPassword());
	}

	public static PersistenceUser MapUserToPersistenceUser(User user) {
		return new PersistenceUser().setEmail(user.getEmail())
									.setName(user.getName())
									.setPassword(user.getPassword())
									.setCountry(user.getCountry());
	}
}
