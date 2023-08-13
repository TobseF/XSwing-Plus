/*
 * @version 0.0 19.02.2009
 * @author Tobse F
 */
package lib.mylib.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;

public class PropertiesTools {

    /**
     * Converts an args list into a property table, provided it's in the following syntax:
     * </br>[key1=value1][key2=value2][...] </br> Useful to evaluate the
     * <code>void main(String[] args)</code> properties. If <code>args==null</code> an empty
     * {@link Properties} list will be returned.
     *
     * @param args which are converted in a {@link Properties} table
     * @return {@link Properties} table out of the given args list
     */
    public static Properties convertArgsToProperties(String[] args) {
        Properties properties = new Properties();
        if (args != null) {
            for (String string : args) {
                String[] keyAndValue = string.split("=");
                if (keyAndValue.length == 2) {
                    properties.setProperty(keyAndValue[0], keyAndValue[1]);
                }
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
        return stream.toString();
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