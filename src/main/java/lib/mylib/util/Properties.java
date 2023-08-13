package lib.mylib.util;

import org.newdawn.slick.util.Log;

import java.io.*;

/**
 * Extended Properties which can also handle Integer values and stores the a
 * given or default file. If a default value will be used, the {@link #logger}
 * warns.
 *
 * @author TOF
 */
public class Properties extends java.util.Properties {
    private static final long serialVersionUID = 1L;

    /**
     * File which is used to store an load the property file
     */
    private File file = null;
    public static final String DEFAULT_FILE_NAME = "Properties.xml";
    // private static final String DEFAULT_FILE_PATH = "META-INF\\";
    /**
     * Default file which is used on {@link #loadXML()} and {@link #storeXML()}, if no
     * file was given in the constructor.
     */
    private static final File DEFAULT_FILE = new File(DEFAULT_FILE_NAME);


    public Properties(File file) {
        setFile(file);
    }

    public Properties(java.util.Properties properties) {
        super(properties);
    }

    public synchronized void setFile(File file) {
        this.file = file == null ? DEFAULT_FILE : file;
    }

    public Properties(String fileName) {
        this.file = new File(fileName);
    }

    public Properties() {
        this.file = DEFAULT_FILE;
        System.out.println(file);
        System.out.println(file.getAbsolutePath());
    }

    /**
     * Searches for the Integer with the specified key in this property list. If
     * the key is not found or if the value is not an integer, the default value
     * will be returned.
     *
     * @param key          the property key.
     * @param defaultValue default value which is used when the value could found in this
     *                     property list
     * @return The integer with the specified key in this property list.
     * @see #getPropertyInt(String)
     */
    public int getPropertyInt(String key, int defaultValue) {
        String value = getProperty(key);
        int parsedValie = value == null ? useDefault(key, defaultValue) : defaultValue;
        if (value != null) {
            try {
                parsedValie = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                useDefault(key, defaultValue);
            }
        }
        return parsedValie;
    }

    /**
     * Searches for the Integer with the specified key in this property list. If
     * the key is not found or if the value is not an integer, 0 value will be
     * returned.
     *
     * @param key the property key.
     * @return The integer with the specified key in this property list.
     * @see #getPropertyInt(String, int)
     */
    public int getPropertyInt(String key) {
        return getPropertyInt(key, 0);
    }

    /**
     * Searches for the Float with the specified key in this property list. If
     * the key is not found or if the value is not an integer, the default value
     * will be returned.
     *
     * @param key          the property key.
     * @param defaultValue default value which is used when the value could found in this
     *                     property list
     * @return The integer with the specified key in this property list.
     * @see #getPropertyInt(String)
     */
    public float getPropertyFloat(String key, float defaultValue) {
        String value = getProperty(key);
        float parsedValie = value == null ? useDefault(key, defaultValue) : defaultValue;
        if (value != null) {
            try {
                parsedValie = Float.parseFloat(value);
            } catch (NumberFormatException e) {
                useDefault(key, defaultValue);
            }
        }
        return parsedValie;
    }

    /**
     * Searches for the Integer with the specified key in this property list. If
     * the key is not found or if the value is not an integer, 0 value will be
     * returned.
     *
     * @param key the property key.
     * @return The integer with the specified key in this property list.
     * @see #getPropertyInt(String, int)
     */
    public float getPropertyFloat(String key) {
        return getPropertyFloat(key, 0);
    }


    /**
     * Sets the given key with the given value.
     *
     * @param key   the key to be placed into this property list.
     * @param value the float value corresponding to key.
     * @see Properties#getPropertyInt(String, int)
     */
    public synchronized void setPropertyFloat(String key, float value) {
        setProperty(key, String.valueOf(value));
    }

    /**
     * Sets the given key with the given value.
     *
     * @param key   the key to be placed into this property list.
     * @param value the integer value corresponding to key.
     * @see Properties#getPropertyInt(String, int)
     */
    public synchronized void setPropertyInt(String key, int value) {
        setProperty(key, String.valueOf(value));
    }

    @Override
    public String getProperty(String key, String defaultValue) {
        String value = getProperty(key);
        return (value == null) ? useDefault(key, defaultValue) : value;
    }

    /**
     * Sets the given key with the given value.
     *
     * @param key   the key to be placed into this property list.
     * @param value the boolean value corresponding to key.
     * @see Properties#getPropertyBoolean(String)
     */
    public synchronized void setPropertyBoolean(String key, boolean value) {
        setProperty(key, String.valueOf(value));
    }

    /**
     * Searches for the Boolean with the specified key in this property list. If
     * the key is not found or if the value is not "true", the default value
     * will be returned.
     *
     * @param key the property key.
     * @return The boolean with the specified key in this property list.
     * @see #getPropertyBoolean(String)
     */
    public boolean getPropertyBoolean(String key, boolean defaultValue) {
        String value = getProperty(key);
        return value == null ? useDefault(key, defaultValue) : Boolean.valueOf(value);
    }

    /**
     * Prints a warn message, that the default value will be used, and returns
     * the given default value.
     *
     * @param <T>
     * @param key          of the default value
     * @param defaultValue which will me returned
     * @return defaultValue
     */
    private <T> T useDefault(String key, T defaultValue) {
        Log.warn("Could't find key '" + key + "'. Default value '" + defaultValue + "' will be used.");
        return defaultValue;
    }

    /**
     * Searches for the Boolean with the specified key in this property list. If
     * the key is not found or if the value is not "true", the default value
     * false will be returned.
     *
     * @param key the property key.
     * @return The boolean with the specified key in this property list.
     * @see #getPropertyBoolean(String, boolean)
     */
    public boolean getPropertyBoolean(String key) {
        return getPropertyBoolean(key, false);
    }

    /**
     * Saves this Properties list in the given {@link File} as INI.
     *
     * @param comment in the header of the saved file.
     * @param file
     * @see #storeXML()
     * @see #loadXML()
     * @see #Properties(File)
     */
    public synchronized void storeINI(String comment, File file) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            store(fileOutputStream, comment);
        } catch (IOException e) {
            Log.warn("Could't store in file: " + e.getMessage());
        } finally {
            try {
                fileOutputStream.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * Saves this Properties list in the given {@link File} as INI.
     *
     * @param comment in the header of the saved file.
     * @see #storeXML()
     * @see #loadXML()
     * @see #Properties(File)
     */
    public synchronized void storeINI(String comment) {
        storeINI(comment, file);
    }

    /**
     * Saves this Properties list in the given {@link File} in XML.
     *
     * @see #storeXML()
     * @see #loadXML()
     * @see #Properties(File)
     */
    public synchronized void storeINI() {
        storeINI("");
    }

    /**
     * Saves this Properties list in the given {@link File} in XML.
     *
     * @param comment in the header of the saved file.
     * @param file
     * @see #storeXML()
     * @see #loadXML()
     * @see #Properties(File)
     */
    public synchronized void storeXML(String comment, File file) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            storeToXML(fileOutputStream, comment);
        } catch (IOException e) {
            Log.warn("Could't store in file: " + e.getMessage());
        } finally {
            try {
                fileOutputStream.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * Saves this Properties list in the given {@link File} in XML.
     *
     * @param comment in the header of the saved file.
     * @see #storeXML()
     * @see #loadXML()
     * @see #Properties(File)
     */
    public synchronized void storeXML(String comment) {
        storeXML(comment, file);
    }

    /**
     * Saves this Properties list in the given {@link File} in XML.
     *
     * @see #storeXML()
     * @see #loadXML()
     * @see #Properties(File)
     */
    public synchronized void storeXML(File file) {
        storeXML("", file);
    }

    /**
     * Saves this Properties list in the given {@link File} in XML.
     *
     * @see #storeXML()
     * @see #loadXML()
     * @see #Properties(File)
     */
    public synchronized void storeXML() {
        storeXML("");
    }

    /**
     * Reads this Properties list out of the given XML {@link File}.
     *
     * @see #storeXML()
     * @see #Properties(File)
     */
    public synchronized void loadINI() {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            Log.warn("Couldn't load from file: " + file.getAbsolutePath() + " " + e.getMessage());
        }
    }

    /**
     * Reads this Properties list out of the given XML {@link File}.
     *
     * @see #storeXML()
     * @see #Properties(File)
     */
    public synchronized void loadXML() {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            loadFromXML(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            Log.warn("Couldn't load from file: " + file.getAbsolutePath() + " " + e.getMessage());
        }
    }

    /**
     * Returns this {@link Properties} as XML String
     *
     * @param comment a description of the property list, or null if no comment is
     *                desired.
     * @return this {@link Properties} as XML String
     */
    public String propertiesToXMLString(String comment) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            storeToXML(stream, comment);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stream.toString();
    }

    public void xmlPropertiesStringToProperties(String xmlProperties) {
        Properties properties = new Properties();
        byte[] bArray = xmlProperties.getBytes();
        ByteArrayInputStream bais = new ByteArrayInputStream(bArray);
        try {
            properties.loadFromXML(bais);
        } catch (Exception e) {
            Log.warn("Couldn't load Properties from XML with: " + xmlProperties + "because " + e.getMessage());
        }
        setProperties(properties);
    }


    public void setProperties(java.util.Properties properties) {
        putAll(properties);
    }


    /**
     * Prints this property list as line separated String. This method is useful
     * for debugging.
     *
     * @return properties as line separated String
     */
    public String list() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(stream, true);
        list(out);
        return stream.toString();
    }

    public File getFile() {
        return file;
    }

    @Override
    public synchronized boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public synchronized int hashCode() {
        return super.hashCode();
    }

}
