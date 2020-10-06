package hr.fer.zemris.java.hw15.model.util;

import java.security.MessageDigest;

import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * 
 * Class used to hash the {@link BlogUser} passwords.
 * 
 * @author Lovro Matošević
 *
 */
public class Hashing {

	/**
	 * Takes a hex-encoded String and returns an appropriate byte[].
	 * 
	 * @param keyText - hex encoded String
	 * @return a byte[] which is the result of converting the input String to a
	 *         byte[]
	 */
	public static byte[] hextobyte(String keyText) {
		if (keyText.length() % 2 != 0) {
			throw new IllegalArgumentException("Invalid argument length");
		}
		byte[] bytearray = new byte[keyText.length() / 2];

		for (int i = 0; i < keyText.length() / 2; i++) {
			int num1 = extractInt(keyText.charAt(2 * i));
			int num2 = extractInt(keyText.charAt(2 * i + 1));

			int result = num1 * 16 + num2;
			bytearray[i] = (byte) result;
		}

		return bytearray;
	}

	/**
	 * Extracts an integer from a hexadecimal character.
	 * 
	 * @param chr - hexadecimal character to be converted to integer
	 * @return an integer that is the result of conversion
	 */

	private static int extractInt(char chr) {
		if (Character.isDigit(chr)) {
			return Integer.parseInt(String.valueOf(chr));
		} else if (Character.isAlphabetic(chr)) {
			chr = Character.toLowerCase(chr);
			switch (chr) {
			case 'a':
				return 10;
			case 'b':
				return 11;
			case 'c':
				return 12;
			case 'd':
				return 13;
			case 'e':
				return 14;
			case 'f':
				return 15;
			default:
				throw new IllegalArgumentException("Invalid character.");
			}
		} else {
			throw new IllegalArgumentException("Invalid character.");
		}
	}

	/**
	 * Converts a byte array to a hex-encoded String.
	 * 
	 * @param bytearray - the array to be converted
	 * @return a string that is the result of conversion
	 */

	public static String bytetohex(byte[] bytearray) {
		if (bytearray.length == 0) {
			return "";
		}
		char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		StringBuilder sb = new StringBuilder();
		StringBuilder temp;

		for (byte b : bytearray) {
			temp = new StringBuilder();
			int num = b & 0xFF;
			if (num == 0) {
				sb.append("00");
			}
			while (num != 0) {
				int remainder = num % 16;
				if (remainder >= 0) {
					temp.insert(0, String.valueOf(digits[remainder]));
				} else {
					temp.insert(0, String.valueOf(digits[16 + remainder]));
				}
				num = num / 16;
			}
			if (temp.toString().length() == 1) {
				temp.insert(0, "0");
			}
			sb.append(temp.toString());
		}
		return sb.toString();
	}

	/**
	 * Generates the hashed password from the provided string.
	 * 
	 * @param password - password to be hashed
	 * @return hashedPassword - resulting hashed password
	 */
	public static String generateHashedPassword(String password) {

		MessageDigest md;

		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());
			return bytetohex(md.digest());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}

	}

	/**
	 * Compares the provided password to the stored hashed password.
	 * 
	 * @param input  - provided password
	 * @param stored - stored hashed password
	 * @return result - true if the passwords match and false else
	 */
	public static boolean validatePassword(String input, String stored) {

		String hashed = Hashing.generateHashedPassword(input);

		if (hashed.equals(stored)) {
			return true;
		}

		return false;

	}
}
