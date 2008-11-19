/*
 * @version 0.0 16.11.2008
 * @author Tobse F
 */
package testcases;

import static org.junit.Assert.*;

import java.util.Random;

import lib.mylib.CryptLib;
import org.junit.Test;

public class CrypterLibTest{
	private CryptLib cryptLib;

	public CrypterLibTest() {
		cryptLib = new CryptLib();
	}

	@Test
	public void random() {
		String testSTring = "012301222";
		String testStringWitRandom = cryptLib.addRandom(testSTring, 2);
		String testStringWitoutRandom = cryptLib.removeRandom(testStringWitRandom);
		assertEquals(testSTring, testStringWitoutRandom);
	}

	@Test
	public void addHash() {
		String testSTring = "0123";
		String hashedString = cryptLib.addHash(testSTring);
		assertEquals(hashedString, "0123006");
	}

	@Test
	public void removeHash() {
		String testString = "0123006";
		String stringWithOutHash = cryptLib.removeHash(testString);
		assertEquals(stringWithOutHash, "0123");

		// test Number with wrong Hash
		stringWithOutHash = cryptLib.removeHash("0123016");
		assertEquals(stringWithOutHash, "");
	}

	
	@Test(expected = IllegalArgumentException.class)
	public void testRemoveHashtWithIllegalArgument(){
		// test text with wrong Hash
		cryptLib.removeHash(" 012abc@");
	}
	
	
	@Test
	public void reserveString() {
		String testString = "XSwing Plus";
		String reversedString = cryptLib.reverseString(testString);
		assertEquals(reversedString, "sulP gniwSX");
	}

	@Test
	public void isStringWithNumbers() {
		Random random = new Random();
		for (int i = 0; i < 100; i++) {
			assertTrue(cryptLib
					.isStringWithNumbers(getRandomNumberString(random.nextInt(8) + 1)));
		}

		String notAStringWithNumbers = "31700i123123";
		assertFalse(cryptLib.isStringWithNumbers(notAStringWithNumbers));

		notAStringWithNumbers = "317000123123_";
		assertFalse(cryptLib.isStringWithNumbers(notAStringWithNumbers));

	}
	
	@Test
	public void convertStringOrASCISequence(){
		String plainText = "Tobse #+123-.§";
		String cryptedText = cryptLib.convertStringToASCISequence(plainText);
		String enCryptedText = cryptLib.convertASCISequenceToString(cryptedText);
		
		assertEquals(cryptedText, "084111098115101032035043049050051045046167");
		assertEquals(plainText, enCryptedText);
		assertEquals(plainText, "Tobse #+123-.§"); //String stays unchanged
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