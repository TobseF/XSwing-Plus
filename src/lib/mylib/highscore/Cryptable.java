/*
 * @version 0.0 20.09.2009
 * @author Tobse F
 */
package lib.mylib.highscore;

/**
 * @author Tobse Can encrypt & decrypt strings without size limits. This have to be valid:
 *         plainText == deCrypt(enCrypt(plainText))
 */
public interface Cryptable {

	/**
	 * Ecrypt the given String with it's own encryptopn algotithm. This have to be valid:
	 * string == deCrypt(enCrypt(string))
	 * 
	 * @param stringToEncrypt
	 * @return encrypted String wich can be decypted bach with deCrypt(String stringToDeCrypt)
	 * @see enCrypt(String stringToEncrypt)
	 */
	public String enCrypt(String stringToEncrypt);

	/**
	 * Decrypts a with enCrypt crypted String back to plaintext. This have to be valid:
	 * plainText == deCrypt(cryptedText)
	 * 
	 * @param stringToDeCrypt a with enCrypt crypted String
	 * @return encrypted String wich can be decypted bach with deCrypt(String stringToDeCrypt)
	 * @see deCrypt(String stringToDeCrypt)
	 */
	public String deCrypt(String stringToDeCrypt);
}
