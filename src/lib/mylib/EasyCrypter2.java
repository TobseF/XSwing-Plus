/*
 * @version 0.0 15.11.2008
 * @author 	Tobse F
 */
package lib.mylib;

public class EasyCrypter2 {
	String phrase="435907821934120766532197433071237653248195479864366339872057";
	
	EasyCrypter2(String phrase){
		this.phrase=phrase;
	}
	public EasyCrypter2() {
	}
	
	public String cryptString(String string, boolean isCrypting){
		StringBuffer cryptedString=new StringBuffer(string);
		for(int i=0;i<string.length();i++){
			int a=Integer.parseInt(cryptedString.substring(i, i	+1));
			int b=i<phrase.length()?Integer.parseInt(phrase.substring(i, i+1)):0; //strings greater than the phrase are also excepted
			cryptedString.replace(i, i+1, rotateDigit(a,b, isCrypting)+"");
		}
		return cryptedString.toString();
	}
	
	private int rotateDigit(int a,int b, boolean upWise){
		if(upWise)
			a=(a+b<10)?a+b:a+b-10;
		else
			a=(a-b>=0)?a-b:a-b+10;
		return a;
	}
	
	public static String addHash(String stringWithNumbers){
		//TODO check wether the given string is changed
		String hashedString=stringWithNumbers;
		return hashedString+=String.format("%03d",getHash(hashedString));	
	}
	
	private static int getHash(String stringWithNumbers){
		char[] stringChars=stringWithNumbers.toCharArray();
		int hash=0;
		for(char i:stringChars){
			hash+=Integer.parseInt(i+"");
		}
		return hash;
	}
	
	public static String removeHash(String stringWithHash){
		String stringWithoutHash=stringWithHash;
		int hash=Integer.parseInt(stringWithoutHash.substring(stringWithoutHash.length()-3));
		stringWithoutHash=stringWithoutHash.substring(0,stringWithoutHash.length()-3);
		int checkedHash=getHash(stringWithoutHash);
		if(hash!=checkedHash){
			stringWithoutHash="0";
		}
		return stringWithoutHash;
	}
	
	public static boolean checkNumberString(String string){
		//TODO implement!
		return false;
	}

}
