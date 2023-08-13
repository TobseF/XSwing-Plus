/*
 * @version 0.0 21.09.2009
 * @author Tobse F
 */
package lib.mylib.highscore;

/**
 * Public crypter implementation which does nothing wiht the strings. So nobody can check my
 * brilliant cryping algoriths an fake the highscore -But he can compile the Game
 *
 * @author Tobse
 */
public class UselessCrypter implements Cryptable {

    @Override
    public String deCrypt(String stringToDeCrypt) {
        return stringToDeCrypt;
    }

    @Override
    public String enCrypt(String stringToEncrypt) {
        return stringToEncrypt;
    }

}
