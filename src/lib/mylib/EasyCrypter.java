/*
 * @version 0.0 26.01.2008
 * @author 	Tobse F
 */
package lib.mylib;


/**
 * Bietet die beiden Methoden {@link #cryptZahl(int)} und {@link #deCrypt(String)} um Zahlen zu verschleiern 
 * und wiederr sichtbar zu machen. Um Manipulation vorzubeugen ist eine art kleiner Hash-Wert eingebaut. Wurde 
 * eine gecryptete Zahlt vor dem decrypten geändert, wird <code>0</code> zurück gegeben. Damit die einfache
 * verschlüsselung nicht bei Werten mit wenigen Stellen auffliegt, werden auf bis <code>lenght</code> stellen
 * Zufallszahlen angehängt.</br>
 * <b>Achtung:</b> Zahlen dürfen nicht mehr wie 8 Stellen haben! (Integer Grenze)
 * @see #cryptZahl(int)
 * @see #deCrypt(String)   
 */
public class EasyCrypter {
	private final int lenght=5;
	
	public EasyCrypter() {
	}	
	

	/**
	 * Encrypts a given String in a secence of numbers
	 * @see EasyCrypter#deCrypt(String)
	 * @param stringToCrypt
	 * @return encryptedString (eg. 01642391)
	 */
	public String enCrypt(String stringToCrypt){
		String crypted=stringToCrypt;
		crypted=convertText(crypted);
		//crypted=convertText(crypted);
		crypted=EasyCrypter2.addHash(crypted);
		if(crypted.length()<9)
			crypted=addRandom(crypted,lenght);
		crypted=flipZahl(crypted);
		EasyCrypter2 crypter2=new EasyCrypter2();
		crypted=crypter2.cryptString(crypted, true);
		//-crypted=addHash(crypted);
		return crypted;
	}
	

	/** Decrypts a with {@link #enCrypt(String)} cypted String back.
	 * @param stringToCrypt
	 * @return uncrypted String
	 */
	public String deCrypt(String stringToCrypt){
		if(!stringToCrypt.isEmpty()){
			//-stringToCrypt=removeHash(stringToCrypt);
			EasyCrypter2 crypter2=new EasyCrypter2();
			stringToCrypt=crypter2.cryptString(stringToCrypt, false);
			stringToCrypt=flipZahl(stringToCrypt);
			if(stringToCrypt.length()<9)
				stringToCrypt=removeRandom(stringToCrypt);
			stringToCrypt=EasyCrypter2.removeHash(stringToCrypt);
			stringToCrypt=reconvertdText(stringToCrypt);
			return stringToCrypt;
			}
		else return "";
	}

	/**
	 * @see #deCrypt(String)
	 */
	public String cryptZahl(int wert){
		return enCrypt(String.valueOf(wert));
	}
	
	
	public static String flipZahl(String wert){
		return new StringBuffer(wert).reverse().toString();
	}
	
	/** Hängt einer Zahl soviele Zufallsziffern an, damit der Rückgabewert <code>stellen</code> Stellen hat.
	 * An die erste Stelle wird die anzahl der uhrspünglichen Ziffern gehängt.
	 * @param wert
	 * @param stellen
	 * @return
	 * @see #removeRandom(String, int)
	 */
	public static String addRandom(String wert, int stellen){
		if(stellen>9)
			 throw new IllegalArgumentException( "only 0<values<10 are acceped" );
		if(wert.length()>9)
			throw new IllegalArgumentException( "only strings with lenght <9 are acceped" );
			// throw new IllegalArgumentException( "stellen < value.lenght() isn't allowed" );
		
		int ii=wert.length();
		String zeichen=ii+wert;
		for(int i=0;i<stellen-ii;i++){
				zeichen+=""+(int)(Math.random()*(9));
		}
		return zeichen;
	}
	
	/** Entfernt die Zufallszahlen, die zuvor mit {@link #addRandom(String, int)} hinzugefügt wurden
	 * @param wert
	 * @return
	 * @see #addRandom(String, int)
	 */
	public static String removeRandom(String wert){
		//System.out.println("wert "+ wert+" "+stellen);
		int ii=Integer.parseInt(wert.toCharArray()[0]+"");
		char[] c=new char[ii];
		wert.getChars(1, ii+1, c, 0);
		return String.valueOf(c);
	}
	
	/**
	 * Zahl wird mit der Quersumme ummantelt. Bei einer Quersumme kleiner 10 kann 
	 * keine zweite Stelle hinzugefügt werden. 
	 * @param zahl
	 * @see #removeHash(String)
	 */
	public static String addHash(String zahl){
		String wert=zahl;
		char[] c=wert.toCharArray();
		int hash=0;
		for(char i:c){
			hash+=Integer.parseInt(i+"");
		}
		String hashed=hash+"";
		c=hashed.toCharArray();
		if(hash>10)
			hashed=c[0]+""+zahl+""+c[1]+"";
		else
			hashed=c[0]+""+zahl+"";
		return hashed;
	}
	
	/**
	 * Prüft die zuvor mit {@link #addHash(String)} gehashte Zahl auf richtigkeit. Der Hash wird dabei entfernt. 
	 * @param zahl
	 * @see #addHash(String)
	 */
	public static String removeHash(String zahl){
		int lenght=zahl.length();
		char[] c1=new char[lenght-2];
		zahl.getChars(1,lenght-1, c1, 0);
		int hash=0;
		
		//Prüfung bei Hash>9
		int quer=Integer.parseInt(zahl.toCharArray()[0]+""+zahl.toCharArray()[lenght-1]);
		for(char i:c1){
			hash+=Integer.parseInt(i+"");
		}
		if(hash==quer)
			zahl=String.valueOf(c1);
		else
			{
			//Prüfung bei Hash<9
			quer=Integer.parseInt(""+zahl.toCharArray()[0]);
			c1=new char[lenght-1];
			zahl.getChars(1,lenght, c1, 0);
			hash=0;
			for(char i:c1){
				hash+=Integer.parseInt(i+"");
				}
			if(hash==quer)
				zahl=String.valueOf(c1);
			else
				zahl="0";
			}
		return zahl;
	}
	
	/** Wandelt einen Text in ein Folge aus ASCII Zeichen um
	 * @param text
	 * @return cryptText
	 */
	public static String convertText(String text){
		char[] c=text.toCharArray();
		text="";
		for(char i:c){
			String adst=""+((int)i);
			//auf drei Stellen auffüllen
			if(adst.length()==1)adst="00"+adst;
			if(adst.length()==2)adst="0"+adst;
			text=text+adst;
		}
		return text;
	}	
	
	
	/** Wandelt einen mit {@link #convertText(String)} convertierten Text wieder zurück in Reintext um
	 * @see #convertText(String)
	 */
	public static String reconvertdText(String cryptedText){
		String[] text=new String[cryptedText.length()/3];
		for(int i=0;i<text.length;i++){
			text[i]=cryptedText.subSequence(i*3, i*3+3)+"";
		}
		char[] c=new char[text.length];
		String detext="";
		for(int i=0;i<c.length;i++){
			c[i]=(char)(Integer.parseInt(text[i]));
		}
		detext=String.valueOf(c);
		return detext;
	}
}
