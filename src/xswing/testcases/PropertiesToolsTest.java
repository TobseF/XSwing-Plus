/*
 * @version 0.0 18.03.2009
 * @author Tobse F
 */
package xswing.testcases;

import static org.junit.Assert.assertEquals;
import lib.mylib.util.*;
import org.junit.Test;

public class PropertiesToolsTest {

	@Test
	public void propertiesToXML() {
		Properties properties = new Properties();
		properties.setProperty("Tom", "1200");
		properties.setProperty("Tim", "200");
		properties.setProperty("Anna", "100");
		properties.setProperty("Ben", "80");

		System.out.println(properties);
		System.out.println("");

		String xml = PropertiesTools.propertiesToXMLString( properties);
		System.out.println(xml);

		Properties newProperties = PropertiesTools.xmlPropertiesStringToProperties(xml);
		System.out.println(newProperties);

		assertEquals(properties, newProperties);
	}

}
