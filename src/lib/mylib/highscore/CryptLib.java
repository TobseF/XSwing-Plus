/*
 * @version 0.0 26.01.2008
 * @author Tobse F
 */
package lib.mylib.highscore;

/**
 * Provides methods to encrypt and decrypt Strings. If you want to use a finished crypt
 * routine, use the methods in the <b>EasyCrypter</b> class.<br>
 */
public class CryptLib {

	/**
	 * Crypts a given string which contains only figures, by rotating each digit. The 
	 * rotation amount is given by the phrase (also only digits).<br>
	 * 
	 * @param stringToCrypt
	 * @return cryptedString
	 */
	public String enCryptString(String stringToCrypt, String phrase) {
		return cryptString(stringToCrypt, phrase, true);
	}

	/**
	 * Enrypts a given string which contains only figures, by rotating each digit. 
	 * The rotation amount is given by the phrase (also only digits).<br>
	 * 
	 * @param cryptedString
	 * @return encryptedString
	 */
	public String deCryptString(String cryptedString, String phrase) {
		if (!isStringWithNumbers(cryptedString)) {
			throw new IllegalArgumentException("Every character have to be a digit");
		}
		return cryptString(cryptedString, phrase, false);
	}

	/**
	 * Returns a reversed String.<br> e.g. "Hallo" ==> "ollaH"
	 * 
	 * @param string
	 * @return reversedString
	 */
	public String reverseString(String string) {
		return new StringBuffer(string).reverse().toString();
	}

	/**
	 * Adds the number of digits of the given string at the end. This Hash is always three
	 * digits long. <br>eg. "01234" ==> "01234010"
	 * 
	 * @param stringWithNumbers String which contains only figures.
	 * @return stringWithHash
	 * @see #removeHash(String)
	 */
	public String addHash(String stringWithNumbers) {
		if (!isStringWithNumbers(stringWithNumbers)) {
			throw new IllegalArgumentException("Every character have to be a digit");
		}
		if (stringWithNumbers.length() > 111) {
				throw new IllegalArgumentException(
					"Strings greater than 111 digits can't be hashed");
		}
		String hashedString = stringWithNumbers;
		return hashedString += String.format("%03d", getHash(hashedString));
	}

	/**
	 * Removes a with {@link #addHash(String)} added number hash. If the hash of the 
	 * given string is wrong, an empty string will be returned. 
	 * is wrong<br>
	 * eg.<br>"01234010" ==> "01234"
	 * <br>"01234015" ==> ""
	 * @param stringWithHash
	 * @return checkedStringWithoutHash
	 * @see #addHash(String)
	 */
	public String removeHash(String stringWithHash) {
		String stringWithoutHash = stringWithHash;
		if (!isStringWithNumbers(stringWithHash)) {
			throw new IllegalArgumentException("Every character have to be a digit");
		}
		int hash = Integer.parseInt(stringWithoutHash
				.substring(stringWithoutHash.length() - 3));
		stringWithoutHash = stringWithoutHash.substring(0, stringWithoutHash.length() - 3);
		int checkedHash = getHash(stringWithoutHash);
		if (hash != checkedHash) {
			stringWithoutHash = "";
		}
		return stringWithoutHash;
	}

	/**
	 * Converts a text into a consequence of ASCII values. Each new value has three 
	 * digits.
	 * eg.<br>
	 * <br> "A" ==> "065"<br>
	 * <br> "Tim12 ==> "084105109049050"<br>
	 * 
	 * @param stringToCrypt
	 * @return sequenceOfASCIIValues
	 */
	public String convertStringToASCISequence(String stringToCrypt) {
		String covertedString = "";
		for (char i : stringToCrypt.toCharArray()) {
			covertedString += String.format("%03d", (int) i);
			;
		}
		return covertedString;
	}

	/**
	 * Converts a sequence of ASCI values back to a String. Every value have to fill three
	 * digits. <br> 
	 * eg.<br>
	 * <br> "065" ==> "A"<br>
	 * <br> "084105109049050 ==> "Tim12"<br>
	 * @param cryptedString String with ASCI values
	 * @return String enCryptedText
	 * @see #convertStringToASCISequence(String)
	 */
	public String convertASCISequenceToString(String cryptedString) {
		if (cryptedString.length() % 3 != 0) {
			throw new IllegalArgumentException("Every ASCI value has to "
					+ "be three digits long");
		}
		if (!isStringWithNumbers(cryptedString)) {
			throw new IllegalArgumentException("Every character have to be a digit");
		}
		char[] enCryptedChars = new char[cryptedString.length() / 3];
		for (int i = 0; i < cryptedString.length() / 3; i++) {
			enCryptedChars[i] = (char) (Integer.parseInt(cryptedString.substring(i * 3,
					i * 3 + 3)));
		}
		return String.valueOf(enCryptedChars);
	}

	/**
	 * Removes the with {@link #addRandom(String, int)} added digits.
	 * 
	 * @param string
	 * @return String without random digits
	 * @see #addRandom(String, int)
	 */
	public String removeRandom(String string) {
		int numberOfRandomDigits = Integer.parseInt(string.substring(0, 1));
		return string.substring(1, numberOfRandomDigits + 1);
	}

	/**
	 * Adds a random <code>numberOfDigits</code> long number sequence to a string. The first
	 * wich is added, contains <code>numberOfDigits</code>. If the String is longer than
	 * <code>numberOfDigits</code> nothing will be added.<br> eg.:<br> addRandom("16",4)==>
	 * "22116" <br> addRandom("16",4)==> "23316"
	 * 
	 * @param string
	 * @param numberOfDigits
	 * @return StringFilledWithRandom
	 * @see #removeRandom(String, int)
	 */
	public String addRandom(String string, int numberOfDigits) {
		if (numberOfDigits > 9) {
			throw new IllegalArgumentException("only 0<values<10 are acceped");
		}
		if (string.length() > 9) {
			throw new IllegalArgumentException("only strings lenght <9 are acceped");
		}
		String sringWithRandom = string.length() + string;
		for (int i = 0; i < numberOfDigits - string.length(); i++) {
			sringWithRandom += "" + (int) (Math.random() * (9));
		}
		return sringWithRandom;
	}

	/**
	 * Checks wether a given string is only a sequence of numbers (true).<br> e.g.<br>
	 * "01349823"==> true <br> "0S349823"==> false <br>
	 * 
	 * @param string
	 * @return containsOnlyNumbers
	 */
	public boolean isStringWithNumbers(String string) {
		boolean containsOnlyNumbers = true;
		for (Character character : string.toCharArray()) {
			if (!Character.isDigit(character)) {
				containsOnlyNumbers = false;
			}
		}
		return containsOnlyNumbers;
	}

	/**
	 * Crypts a given string which contains only figures, by rotating each digit. The rotation
	 * amount is given by the phrase (also only digits).<br> Wether the string will be en- or.
	 * deCypted is controlled by <code>isCrypting</code>.
	 * 
	 * @param stringToCrypt (contains only figures)
	 * @param phrase follwing of digits which regulates the number rotation
	 * @param isCrypting if true: string will be deCrypted with phrase <br> if false: string
	 *            will be enCrypted with phrase
	 * @return enCrypted or deCrypted string (relying on <code>isCrypting</code>)
	 * @see #enCryptString(String, String)
	 * @see #deCryptString(String, String)
	 */
	private String cryptString(String stringToCrypt, String phrase, boolean isCrypting) {
		if (!isStringWithNumbers(stringToCrypt) || !isStringWithNumbers(phrase)) {
			throw new IllegalArgumentException("Every character have to be a digit");
		}
		StringBuffer cryptedString = new StringBuffer(stringToCrypt);
		for (int i = 0; i < stringToCrypt.length(); i++) {
			int a = Integer.parseInt(cryptedString.substring(i, i + 1));
			// strings greater than the phrase are also excepted
			int b = i < phrase.length() ? Integer.parseInt(phrase.substring(i, i + 1)) : 0;
			cryptedString.replace(i, i + 1, rotateDigit(a, b, isCrypting) + "");
		}
		return cryptedString.toString();
	}

	/**
	 * Rotates a given one digit number with the ammount of b.<br> e.g.:<br> (8, 4, true) ==> 2
	 * (upWise)<br> (8, 4, false) ==> 4 (downWise)<br>
	 * 
	 * @param a figure to rotate
	 * @param b amount of rotation (0 <= b <= 9)
	 * @param upWise rotationDirection
	 * @return rotatedOneDigitNumber
	 */
	private int rotateDigit(int a, int b, boolean upWise) {
		if (a > 9 || b > 9) {
			throw new IllegalArgumentException("only numbers with one digit and "
					+ "0 >= rotation <=9 can be rotated");
		}
		if (upWise) {
			a = (a + b < 10) ? a + b : a + b - 10;
		} else {
			a = (a - b >= 0) ? a - b : a - b + 10;
		}
		return a;
	}

	/**
	 * Returns the sum of the digits of a given String (which have to consits only out of
	 * numbers). <br> eg. "01234" ==> "10"
	 * 
	 * @param stringWithNumbers
	 * @return sumOfTheDigits
	 */
	private int getHash(String stringWithNumbers) {
		char[] stringChars = stringWithNumbers.toCharArray();
		int hash = 0;
		for (char i : stringChars) {
			hash += Integer.parseInt(i + "");
		}
		return hash;
	}
}