/*
 * @version 0.0 16.11.2008
 * @author 	Tobse F
 */
package testcases;

import static org.junit.Assert.*;

import java.util.Random;

import lib.mylib.EasyCrypter;

import org.junit.Test;


public class EasyCrypterTest {

	@Test
	public void testCryptZahlString() {
		EasyCrypter crypter=new EasyCrypter();
		for(int testNr=0;testNr<1000;testNr++){
			String number=getRandomNumberString(6);
			String cryptedNumber=crypter.enCrypt(number);
			String deCryptedNumber=crypter.deCrypt(cryptedNumber);
			assertEquals(number, deCryptedNumber);
			
			if(!number.equals(deCryptedNumber))
				System.out.println("error ["+number+"]"+"==>"+cryptedNumber+"==>"+" ["+deCryptedNumber+"]");
		}
		
		String wrongCryptedText="795352761035631556643577";
		String deCryptedText=crypter.deCrypt(wrongCryptedText);
		assertEquals(deCryptedText, "");
	}
	
	@Test
	public void convertText() {
		cryptNameTest("Tobse16");
		cryptNameTest("Tobse 16");
		cryptNameTest(" 0Tobse 16 ");
		cryptNameTest("");
		cryptNameTest("0");
		cryptNameTest("@78! -,+/");
		cryptNameTest("12345678");
		cryptNameTest("123456789");
		cryptNameTest("1234567890");
	}
	
	private void cryptNameTest(String name){
		EasyCrypter crypter=new EasyCrypter();
		for(int testNr=0;testNr<100;testNr++){
			String cryptedNumber=crypter.enCrypt(name);
			String deCryptedNumber=crypter.deCrypt(cryptedNumber);
			
			assertEquals(name, deCryptedNumber);
			
			if(testNr%100==0)
				System.out.println("["+name+"]"+"==>"+cryptedNumber+"==>"+" ["+deCryptedNumber+"]");
		}
	}
	
	@Test
	public void testRandom(){
		String testSTring="012301222";
		String testStringWitRandom=EasyCrypter.addRandom(testSTring,2);
		String testStringWitoutRandom=EasyCrypter.removeRandom(testStringWitRandom);
		assertEquals(testSTring, testStringWitoutRandom);
	}

	private String getRandomNumberString(int maxSize){
		String randomNumberString="";
		Random random=new Random();
		maxSize=random.nextInt(maxSize);
		for(int j=0;j<maxSize;j++){
			randomNumberString+=random.nextInt(10);
		}
		return randomNumberString;
	}

}
