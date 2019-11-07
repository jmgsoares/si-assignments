package pt.onept.mei.is1920.mybay.web.utility;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//Adaptation of https://www.geeksforgeeks.org/md5-hash-in-java/
public final class MD5Utility {

	private MD5Utility() {	}

	private static String GetMd5(String input) {
		try {

			// Static getInstance method is called with hashing MD5
			MessageDigest md = MessageDigest.getInstance("MD5");

			// digest() method is called to calculate message digest
			//  of an input digest() return array of byte
			byte[] messageDigest = md.digest(input.getBytes());

			// Convert byte array into sigNum representation
			BigInteger no = new BigInteger(1, messageDigest);

			// Convert message digest into hex value
			StringBuilder hashText = new StringBuilder(no.toString(16));
			while (hashText.length() < 32) {
				hashText.insert(0, "0");
			}
			return hashText.toString();
		}

		// For specifying wrong message digest algorithms
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static String HashPassword(String password) {
		for (int i = 0; i <= 7; i++) {
			password = MD5Utility.GetMd5(password);
		}
		return password;
	}
}
