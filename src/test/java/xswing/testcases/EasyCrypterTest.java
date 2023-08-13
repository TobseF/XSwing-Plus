/*
 * @version 0.0 28.11.2008
 * @author Tobse F
 */
package xswing.testcases;

import lib.mylib.highscore.EasyCrypter;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class EasyCrypterTest {

    private final EasyCrypter easyCrypter;

    public EasyCrypterTest() {
        easyCrypter = new EasyCrypter();
    }

    /**
     * Tests 1000 random score values
     */
    @Test
    public void cryptRadnomNumbers() {
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
    public void cryptStrings() {
        cryptString("Tobse16");
        cryptString("Tim 12");
        cryptString("Tobse 16");
        cryptString(" 0Tobse 16 ");
        cryptString("");
        cryptString("0");
        cryptString("@78! -,+/");
        cryptString("12345678");
        cryptString("123456789");
        cryptString("1234567890");
    }

    @Test
    public void cryptLongStrings() {
        // (180 digit String)
        cryptString("m23eoijrotkdqm23sjiom34cj29348u0923r2jdoqjpd1jejr290uj34cftjqoiejtc0qjt03ajct0j3tjg3wiojo03jcj3wvc09jgwvqotev9wj349ohwm4b5zvj4oszjmvo8ae4vzjucdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvkcgop1");
        // (10kb String, 107120 digits)
        cryptString("m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1m23eoijrotkdqm23sjiom34cjdxrmiq3j4octmj3q4m9oqj3mtcq9p3uvcgjmq3zkcgop1");
    }

    /**
     * Tests a wrong hash, what should return an empty string
     */
    @Test
    public void testWrongHashes() {
        // plus leading 0 ('Tobse16' Hash)
        assertEquals(easyCrypter.deCrypt("07959428159442718455432354"), "");

        // plus trading 0 ('Tobse16' Hash)
        assertEquals(easyCrypter.deCrypt("79594281594427184554323540"), "");

        // changed middle fugure 0 ('Tobse16' Hash)
        assertEquals(easyCrypter.deCrypt("7959428159442818455432354"), "");

        // no figures
        assertEquals(easyCrypter.deCrypt("W@ 13,-"), "");

        // empty String
        assertEquals(easyCrypter.deCrypt(""), "");
    }

    private void cryptString(String name) {
        EasyCrypter crypter = new EasyCrypter();

        String cryptedNumber = crypter.enCrypt(name);
        String deCryptedNumber = crypter.deCrypt(cryptedNumber);

        assertEquals(name, deCryptedNumber);
        System.out.println("[" + name + "]" + "==>" + cryptedNumber + "==>" + " ["
                + deCryptedNumber + "]");

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