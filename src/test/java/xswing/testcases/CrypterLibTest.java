/*
 * @version 0.0 16.11.2008
 * @author Tobse F
 */
package xswing.testcases;

import lib.mylib.highscore.CryptLib;
import org.junit.Test;

import static org.junit.Assert.*;

public class CrypterLibTest {

    private final CryptLib cryptLib;

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
        assertEquals(hashedString, "01230006");
    }

    @Test
    public void removeHash() {
        String testString = "01230006";
        String stringWithOutHash = cryptLib.removeHash(testString);
        assertEquals(stringWithOutHash, "0123");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveHashtWithWrongHash1() {
        // test text with wrong Hash
        cryptLib.removeHash(" 012abc@");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveHashtWithWrongHash2() {
        // test text with wrong Hash
        cryptLib.removeHash("0123016");
    }

    @Test
    public void reserveString() {
        String testString = "XSwing Plus";
        String reversedString = cryptLib.reverseString(testString);
        assertEquals(reversedString, "sulP gniwSX");
    }

    @Test
    public void isStringWithNumbers() {
        assertTrue(cryptLib.isStringWithNumbers("1234567890"));
        assertTrue(cryptLib.isStringWithNumbers("0"));

        assertFalse(cryptLib.isStringWithNumbers("12 34567890"));
        assertFalse(cryptLib.isStringWithNumbers("1AB456789"));
        assertFalse(cryptLib.isStringWithNumbers("123456789_"));
        assertFalse(cryptLib.isStringWithNumbers(""));
    }

    @Test
    public void convertStringOrASCISequence() {
        String plainText = "Tobse #+123-.�";
        String cryptedText = cryptLib.convertStringToASCISequence(plainText);
        String enCryptedText = cryptLib.convertASCISequenceToString(cryptedText);

        assertEquals(cryptedText, "084111098115101032035043049050051045046167");
        assertEquals(plainText, enCryptedText);
        assertEquals(plainText, "Tobse #+123-.�"); // String stays unchanged
    }

    @Test
    public void rotateDigit() {
        assertEquals(4, cryptLib.rotateDigit(6, 2, false));
        assertEquals(6, cryptLib.rotateDigit(9, 3, false));
        assertEquals(1, cryptLib.rotateDigit(1, 0, false));
        assertEquals(8, cryptLib.rotateDigit(2, 4, false));
        assertEquals(0, cryptLib.rotateDigit(2, 2, false));

        assertEquals(8, cryptLib.rotateDigit(6, 2, true));
        assertEquals(2, cryptLib.rotateDigit(9, 3, true));
        assertEquals(1, cryptLib.rotateDigit(1, 0, true));
        assertEquals(6, cryptLib.rotateDigit(2, 4, true));
    }

    @Test
    public void deCrypt() {
        String phrase = "001100220088";
        String crypted = "223322442244";
        String enCrypted = "222222222266";
        assertEquals(enCrypted, cryptLib.deCryptString(crypted, phrase));

        phrase = "00112";
        crypted = "223322442244";
        enCrypted = "222202431044";
        assertEquals(enCrypted, cryptLib.deCryptString(crypted, phrase));

        phrase = "00112";
        crypted = "223";
        enCrypted = "222";
        assertEquals(enCrypted, cryptLib.deCryptString(crypted, phrase));
    }

}