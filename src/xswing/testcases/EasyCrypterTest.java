/*
 * @version 0.0 28.11.2008
 * @author Tobse F
 */
package xswing.testcases;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import lib.mylib.highscore.EasyCrypter;

import org.junit.Test;

public class EasyCrypterTest {
	private EasyCrypter easyCrypter;

	public EasyCrypterTest() {
		easyCrypter = new EasyCrypter();
	}

	/**
	 * Tests 1000 random score values
	 */
	@Test
	public void cryptStringWithDigits() {
		boolean showOutput = false;
		for (int testNr = 0; testNr < 1000; testNr++) {
			String randomNumber = getRandomNumberString(6);
			String cryptedNumber = easyCrypter.enCrypt(randomNumber);
			String deCryptedNumber = easyCrypter.deCrypt(cryptedNumber);
			assertEquals(randomNumber, deCryptedNumber);

			if (showOutput) {
				System.out.println("[" + randomNumber + "]" + "==>" + cryptedNumber + "==>"
						+ " [" + deCryptedNumber + "]");
			}
		}
	}

	/**
	 * Tests a wrong hash, what shoul return an empty string
	 */
	@Test
	public void testWrongInput() {
		String deCryptedText = easyCrypter.deCrypt("795352761035631556643577");
		assertEquals(deCryptedText, "");

		deCryptedText = easyCrypter.deCrypt("79535276103563155664357");
		assertEquals(deCryptedText, "");

		deCryptedText = easyCrypter.deCrypt("W@ 13,-");
		assertEquals(deCryptedText, "");
	}

	@Test
	public void testInformationHiding() {
		String testString = " 012456Test@";
		@SuppressWarnings("unused")
		String tempString = easyCrypter.enCrypt(testString);
		assertEquals(testString, " 012456Test@");

		tempString = easyCrypter.deCrypt(testString);
		assertEquals(testString, " 012456Test@");
	}

	/**
	 * Crypts serverals names
	 */
	@Test
	public void cryptNames() {
		cryptNames("Tobse16");
		cryptNames("Tim 12");
		cryptNames("Tobse 16");
		cryptNames(" 0Tobse 16 ");
		cryptNames("");
		cryptNames("0");
		cryptNames("@78! -,+/");
		cryptNames("12345678");
		cryptNames("123456789");
		cryptNames("1234567890");
	}

	private void cryptNames(String name) {
		EasyCrypter crypter = new EasyCrypter();
		// every cryption is tested 100 times
		for (int testNr = 0; testNr < 100; testNr++) {
			String cryptedNumber = crypter.enCrypt(name);
			String deCryptedNumber = crypter.deCrypt(cryptedNumber);

			assertEquals(name, deCryptedNumber);

			// prints only the first of 100 tests
			if (testNr % 100 == 0) {
				System.out.println("[" + name + "]" + "==>" + cryptedNumber + "==>" + " ["
						+ deCryptedNumber + "]");
			}
		}
	}

	/**
	 * Help method for testing. Reruns random numbers (can also start with a 0)!
	 * 
	 * @param maxSize
	 * @return randomNumberAsString
	 */
	private String getRandomNumberString(int maxSize) {
		String randomNumberString = "";
		Random random = new Random();
		maxSize = random.nextInt(maxSize);
		for (int j = 0; j < maxSize; j++) {
			randomNumberString += random.nextInt(10);
		}
		return randomNumberString;
	}
}