/*
 * @version 0.0 20.09.2009
 * @author Tobse F
 */
package lib.mylib.highscore;

/**
 * @author Tobse Can encrypt & decrypt strings without size limits. This have to be valid:
 * plainText == deCrypt(enCrypt(plainText))
 */
public interface Cryptable {

    /**
     * Encrypt the given String with it's own encrypt algorithm. This have to be valid:
     * string == deCrypt(enCrypt(string))
     *
     * @param stringToEncrypt
     * @return encrypted String which can be decrypted with deCrypt(String stringToDeCrypt)
     * @see enCrypt(String stringToEncrypt)
     */
    String enCrypt(String stringToEncrypt);

    /**
     * Decrypts a with enCrypt crypted String back to plain text. This have to be valid:
     * plainText == deCrypt(cryptedText)
     *
     * @param stringToDeCrypt a with enCrypt crypted String
     * @return encrypted String which can be decypted with deCrypt(String stringToDeCrypt)
     * @see deCrypt(String stringToDeCrypt)
     */
    String deCrypt(String stringToDeCrypt);
}
