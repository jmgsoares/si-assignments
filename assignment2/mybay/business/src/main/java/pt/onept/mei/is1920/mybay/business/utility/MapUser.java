package pt.onept.mei.is1920.mybay.business.utility;

import pt.onept.mei.is1920.mybay.common.type.User;
import pt.onept.mei.is1920.mybay.data.persistence.type.PersistenceUser;

public final class MapUser {

	private MapUser() { }

	public static User MapPersistenceUserToUser(PersistenceUser persistedUser) {
		return new User().setEmail(persistedUser.getEmail())
							  .setName(persistedUser.getName())
							  .setCountry(persistedUser.getCountry())
							  .setPassword(null);
	}

	//TODO Implement this
	public static PersistenceUser MapUserToPersistenceUser(User user) {
		return null;
	}
}
