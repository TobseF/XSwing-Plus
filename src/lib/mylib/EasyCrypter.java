/*
 * @version 0.0 26.01.2008
 * @author Tobse F
 */
package lib.mylib;

/**
 * Provides methods to encrypt and decrypt Strings. Strings are crypted in a sequence of
 * numbers. To prevent manipulation of crypted Strings, a hash is added. If string for 
 * decrypting contains a wrong hash, the encryped string will be empty.
 * <pre>
 * eg.:
 * [Tim 12]==>585957761164021267912
 * [12345678]==>395557371384470916682147373
 * [@78! -,+/]==>105647161374660996862747983431
 * [0]==>645747
 * []==>435==>
 * </pre>
 * 
 * @see #enCrypt(String)
 * @see #deCrypt(String)
 */
public class EasyCrypter {
	/**
	 * String which is used for en- & deCrypting. Strings can only deCrypted with the same
	 * phrase theiy're enCrypted. 
	 */
	private String phrase = "435907821934120766532197433071"
			+ "237653248195479864366339872057";

	private CryptLib cryptLib;

	public EasyCrypter() {
		cryptLib = new CryptLib();
	}

	public EasyCrypter(String phrase) {
		this.phrase = phrase;
	}

	/**
	 * Encrypts a given String in a secence of numbers
	 * 
	 * @see EasyCrypter#deCrypt(String)
	 * @param stringToEncrypt
	 * @return encryptedString (eg. 01642391)
	 */
	public String enCrypt(String stringToEncrypt) {
		String crypted = stringToEncrypt;
		try {
			crypted = cryptLib.convertStringToASCISequence(crypted);
			crypted = cryptLib.addHash(crypted);
			crypted = cryptLib.reverseString(crypted);
			crypted = cryptLib.enCryptString(crypted, phrase);
		} catch (IllegalArgumentException e) {
			crypted = "";
		}
		return crypted;
	}

	/**
	 * Decrypts a with {@link #enCrypt(String)} cypted String back.
	 * 
	 * @param stringToCrypt
	 * @return uncrypted String
	 */
	public String deCrypt(String stringToCrypt) {
		try {
			stringToCrypt = cryptLib.deCryptString(stringToCrypt, phrase);
			stringToCrypt = cryptLib.reverseString(stringToCrypt);
			stringToCrypt = cryptLib.removeHash(stringToCrypt);
			stringToCrypt = cryptLib.convertASCISequenceToString(stringToCrypt);
		} catch (IllegalArgumentException e) {
			stringToCrypt = "";
		}
		return stringToCrypt;
	}

}