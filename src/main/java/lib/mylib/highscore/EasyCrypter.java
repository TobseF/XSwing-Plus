/*
 * @version 0.0 26.01.2008
 * @author Tobse F
 */
package lib.mylib.highscore;

/**
 * Provides methods to encrypt and decrypt Strings. Strings are crypted in a sequence of
 * numbers. To prevent manipulation of crypted Strings, a hash is added. If string for
 * decrypting contains a wrong hash, the encryped string will be empty.
 *
 * <pre>
 * e.g.:
 * [Tim 12]==&gt;585957761164021267912
 * [12345678]==&gt;395557371384470916682147373
 * [@78! -,+/]==&gt;105647161374660996862747983431
 * [0]==&gt;645747
 * []==&gt;435==&gt;
 * </pre>
 *
 * @see #enCrypt(String)
 * @see #deCrypt(String)
 */
public class EasyCrypter implements Cryptable {

    /**
     * String which is used for en- & deCrypting. Strings can only deCrypted with the same
     * phrase theiy're enCrypted.
     */
    private static final String DEFAULT_PHRASE = "435907821934120766532197433071"
            + "237653248195479864366339872057";

    private final String phrase;

    private CryptLib cryptLib;

    /**
     * Initializes an Crypter with the default #phrase
     *
     * @see #phrase
     */
    public EasyCrypter() {
        phrase = DEFAULT_PHRASE;
        cryptLib = new CryptLib();
    }

    /**
     * Initializes an Crypter with the given #phrase
     *
     * @param phrase #phrase
     * @see #phrase
     */
    public EasyCrypter(String phrase) {
        this.phrase = phrase;

    }

    /**
     * Encrypts a given String in a sequence of numbers
     *
     * @param stringToEncrypt
     * @return encryptedString (e.g. 01642391)
     * @see EasyCrypter#deCrypt(String)
     */
    @Override
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
     * Decrypts a with {@link #enCrypt(String)} crypted String back. If an error occurs while
     * decrypting, e.g. if the hash of the given phrase was wrong, "" (an empty String) will be
     * returned
     *
     * @param stringToDeCrypt a with {@link #enCrypt(String)} crypted String
     * @return uncrypted String or "" (an empty String), if there was an error while decrypting
     */
    @Override
    public String deCrypt(String stringToDeCrypt) {
        try {
            stringToDeCrypt = cryptLib.deCryptString(stringToDeCrypt, phrase);
            stringToDeCrypt = cryptLib.reverseString(stringToDeCrypt);
            stringToDeCrypt = cryptLib.removeHash(stringToDeCrypt);
            stringToDeCrypt = cryptLib.convertASCISequenceToString(stringToDeCrypt);
        } catch (IllegalArgumentException e) {
            stringToDeCrypt = "";
        }
        return stringToDeCrypt;
    }

}