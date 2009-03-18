/*
 * @version 0.0 05.12.2008
 * @author 	Tobse F
 */
package tests;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EnumsTest {
	Set<String> sound = new HashSet<String>();
	enum options {EXPL,SHRINK,FLOW};
	Map<options, String> sounds2 = new EnumMap<options, String>(options.class);
	
	public EnumsTest() {
		sounds2.put(options.EXPL, "Gigant Explosion");
		sounds2.put(options.FLOW, "Bla ahjksd");
		
		System.out.println(sounds2);
	}
	
	public static void main(String[] args) {
		new EnumsTest();
	}

}
