package hr.fer.zemris.java.hw06.crypto;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.*;

/**
 * 
 * Program used for checking sha keys, encrypting and decrypting files. It
 * expects arguments through the command line. All files are expected to be in
 * the same directory as this program.
 * 
 * @author Lovro Matošević
 *
 */
public class Crypto {
	public static void main(String[] args) {
		if (args[0].equals("checksha")) {
			String fileName = args[1];
			Scanner sc = new Scanner(System.in);
			System.out.printf("Please provide expected sha-256 digest for %s:%n>", fileName);
			String input = sc.nextLine();
			try {
				MessageDigest sha = MessageDigest.getInstance("SHA-256");
				InputStream stream = Files
						.newInputStream(Paths.get("src/main/java/hr/fer/zemris/java/hw06/crypto/" + fileName));
				byte[] b = new byte[4096];

				int i;
				while ((i = (stream.read(b))) > 1) {
					sha.update(b, 0, i);
				}

				byte[] hash = sha.digest();
				String output = Util.bytetohex(hash);
				if (output.equals(input)) {
					System.out.printf("Digesting completed. Digest of %s matches expected digest.", fileName);
				} else {
					System.out.printf(
							"Digesting completed. Digest of %s does not match the expected digest. Digest was: %s",
							fileName, output);
				}
				stream.close();
				sc.close();
			} catch (NoSuchAlgorithmException | IOException e) {
				e.printStackTrace();
				System.out.println("Error while digesting. Exiting");
				System.exit(1);
			}

		} else if (args[0].equals("encrypt") || args[0].equals("decrypt")) {
			String inputFileName = args[1];
			String outputFileName = args[2];
			Scanner sc = new Scanner(System.in);
			System.out.printf("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):%n>");
			String password = sc.nextLine();
			System.out.printf("Please provide initialization vector as hex-encoded text (32 hex-digits):%n>");
			String vector = sc.nextLine();
			if (args[0].equals("encrypt")) {
				crypt(inputFileName, outputFileName, true, password, vector);
				System.out.printf("Encryption completed. Generated file %s based on file %s.", outputFileName,
						inputFileName);
			} else if (args[0].equals("decrypt")) {
				crypt(inputFileName, outputFileName, false, password, vector);
				System.out.printf("Decryption completed. Generated file %s based on file %s.", outputFileName,
						inputFileName);
			}
			sc.close();
		} else {
			System.out.println("Invalid command. Exiting.");
			System.exit(1);
		}
	}

	/**
	 * Helper method used for decrypting and encrypting files.
	 * 
	 * @param inputFileName  - name of the input file
	 * @param outputFileName - name of the output file
	 * @param encrypt        - parameter used to set encryption (if true) and
	 *                       decryption (if false)
	 * @param password       - password to be used
	 * @param vector         - vector to be used
	 */

	private static void crypt(String inputFileName, String outputFileName, boolean encrypt, String password,
			String vector) {

		String keyText = password;
		String ivText = vector;
		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
				| InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		InputStream inStream;
		OutputStream outStream;
		try {
			inStream = Files.newInputStream(Paths.get("src/main/java/hr/fer/zemris/java/hw06/crypto/" + inputFileName));
			outStream = Files
					.newOutputStream(Paths.get("src/main/java/hr/fer/zemris/java/hw06/crypto/" + outputFileName));

			byte[] b = new byte[4096];
			byte[] out;
			int i;
			while ((i = (inStream.read(b))) > 1) {
				out = cipher.update(b, 0, i);
				outStream.write(out);
			}
			out = cipher.doFinal();
			outStream.write(out);

			inStream.close();
			outStream.close();
		} catch (IOException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
