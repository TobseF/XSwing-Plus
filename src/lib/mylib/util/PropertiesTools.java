/*
 * @version 0.0 19.02.2009
 * @author Tobse F
 */
package lib.mylib.util;

import java.io.*;
import java.util.*;

public class PropertiesTools {

	public static Properties convertArgsToLinkedHashMap(String[] args) {
		Properties properties = new Properties();
		for (String string : args) {
			String[] keyAndValue = string.split("=");
			if (keyAndValue.length == 2) {
				properties.setProperty(keyAndValue[0], keyAndValue[1]);
			}
		}
		return properties;
	}

	public static String propertiesToXMLString(Properties properties, String comment) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try {
			properties.storeToXML(stream, comment);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String(stream.toByteArray());
	}

	public static String propertiesToXMLString(Properties properties) {
		return propertiesToXMLString(properties, "");
	}

	public static Properties xmlPropertiesStringToProperties(String xmlProperties) {
		Properties properties = new Properties();
		byte[] bArray = xmlProperties.getBytes();
		ByteArrayInputStream bais = new ByteArrayInputStream(bArray);
		try {
			properties.loadFromXML(bais);
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

}