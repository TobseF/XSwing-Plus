/*
 * @version 0.0 16.11.2008
 * @author 	Tobse F
 */
package testcases;

import static org.junit.Assert.*;

import java.util.Random;

import lib.mylib.EasyCrypter2;

import org.junit.Test;


public class EasyCrypter2Test {

	@Test
	public void testCryptString() {
		EasyCrypter2 crypter=new EasyCrypter2();
		for(int testNr=0;testNr<1000;testNr++){
			String number=getRandomNumberString(6);
			String cryptedNumber=crypter.cryptString(number, true);
			String deCryptedNumber=crypter.cryptString(cryptedNumber, false);
			//System.out.println("error ["+number+"]"+"==>"+cryptedNumber+"==>"+" ["+deCryptedNumber+"]");
			assertEquals(number, deCryptedNumber);
		}
	}
	
	@Test
	public void testAddHash(){
		String testSTring="0123";
		String hashedString=EasyCrypter2.addHash(testSTring);
		assertEquals(hashedString, "0123006");
	}

	@Test
	public void testRemoveHash(){
		String testSTring="0123006";
		String stringWithOutHash=EasyCrypter2.removeHash(testSTring);
		assertEquals(stringWithOutHash, "0123");
	
		//Number with wrong Hash ==>0
		testSTring="0123016";//0123016
		stringWithOutHash=EasyCrypter2.removeHash(testSTring);
		System.out.println("["+stringWithOutHash+"]");
		assertEquals(stringWithOutHash, "0");
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
