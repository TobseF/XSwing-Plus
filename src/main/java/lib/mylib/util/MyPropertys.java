/*
 * @version 0.0 17.09.2009
 * @author Tobse F
 */
package lib.mylib.util;

import lib.mylib.options.DefaultArgs;
import org.newdawn.slick.SavedState;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

/**
 * @author Tobse Overlay to easily access a {@link SavedState} without catching exceptions and
 * with <code>boolean</code> support. Local data is loaded when
 * {@link #setFile(String)} is called and will be saved with every set function.
 * Loading or saving exceptions will only trigger {@link Log} warnings.
 */
public class MyPropertys {

    /**
     * It's used to store the options local.
     */
    private static SavedState options;

    /**
     * File name of the {@link SavedState} file for checking filename changes on
     * {@link #setFile(Class)} or {@link #setFile(String)}
     */
    private static String fileNameTemp = null;

    /**
     * List of default options names
     *
     * @see #isRegistredArg(String)
     */
    private static HashSet<String> defaultArgs = null;

    /**
     * Stores string values which are set before calling {@link #setFile(Class)} or
     * {@link #setFile(String)}
     */
    private static Properties entriesBeforeSettingFile = new Properties();

    /**
     * Indicates if field names should be checked for default with {@link #checkForDefaults}
     */
    private static boolean checkForDefaults = false;

    /**
     * Saves the Options automatically on every set method
     */
    private static final boolean autoSave = true;

    /**
     * Loads the Options automatically on every get method
     */
    private static final boolean autoLoad = true;

    /**
     * Indicates when {@link #checkForDefaults} is <code>true</code> and
     * {@link #checkArg(String)} gets a not resisted arg, if then also an exertion should be
     * thrown.
     */
    private static boolean throwExeptonOnArgsCheking = false;

    /**
     * Private static class
     */
    public MyPropertys() {
    }

    /**
     * Sets a Class which provides the {@link File} Name in which the options should be stored
     * local and loads them for the get functions. On loading problems, the option file will be
     * cleared.
     *
     * @param fileName name (without path) in which the options should be stored local
     * @see #setFile(String)
     */
    public static void setFile(Class<?> className) {
        setFile(className.getSimpleName());
    }

    /**
     * Sets the {@link File}(Name) in which the options should be stored local and loads them
     * for the get functions. On loading problems, the option file will be cleared.
     *
     * @param fileName name (without path) in which the options should be stored local
     * @see #setFile(Class)
     */
    public static void setFile(String fileName) {
        if (!fileName.equals(fileNameTemp)) {
            if (fileNameTemp != null) {
                Log.warn("Changed options file location during process: " + fileNameTemp
                        + " to " + fileName);
            } else {
                Log.info("Using property file: " + fileName);
                fileNameTemp = fileName;
            }
        }
        try {
            options = new SavedState(fileName);
        } catch (SlickException e) {
            Log.warn("Could not crate options file");
        }
        try {
            options.load();
            setStrings(entriesBeforeSettingFile);
            entriesBeforeSettingFile = new Properties();
        } catch (IOException e1) {
            Log
                    .warn("Could not load options file. They will be cleread and only default values will be returned");
            options.clear();
            try {
                options.save();
            } catch (IOException e2) {
                Log.warn("Could not clear options file");
            }
        }
    }

    /**
     * Get the String with the given name
     *
     * @param nameOfField name (key) of the value to return
     * @param default     value, which is used, when the String can not be found
     * @return the value with the given name (key), or defaultValue when the String can not be
     * found
     */
    public static String getString(String nameOfField, String defaultValue) {
        checkArg(nameOfField);
        if (autoLoad) {
            load();
        }
        return options != null ? options.getString(nameOfField, defaultValue)
                : entriesBeforeSettingFile.getProperty(nameOfField, defaultValue);
    }

    /**
     * Get the String with the given name
     *
     * @param nameOfField name (key) of the value to return
     * @return the value with the given name (key), or <code>null</code> if it's not given
     */
    public static String getString(String nameOfField) {
        checkArg(nameOfField);
        if (autoLoad) {
            load();
        }
        return getString(nameOfField, null);
    }

    /**
     * Set's the string with the given name and saves it local
     *
     * @param nameOfField   name (key) of the value to save local
     * @param value         to save local
     * @param saveInstantly if the value should be saved immediately after setting it
     */
    private static void setString(String nameOfField, String value, boolean saveInstantly) {
        checkArg(nameOfField);
        if (options != null) {
            options.setString(nameOfField, value);
            if (saveInstantly) {
                save();
            }
        } else {
            entriesBeforeSettingFile.setProperty(nameOfField, value);
            Log.warn("setValue before setting property file");
        }
    }

    /**
     * Set's the string with the given name and saves it local
     *
     * @param nameOfField name (key) of the value to save local
     * @param value       to save local
     */
    public static void setString(String nameOfField, String value) {
        checkArg(nameOfField);
        setString(nameOfField, value, autoSave);
    }

    /**
     * Sets and saves the complete {@link Properties} list
     *
     * @param properties which should be stored
     */
    public static void setStrings(Properties properties) {
        if (properties != null && !properties.isEmpty()) {
            for (String key : properties.stringPropertyNames()) {
                setString(key, properties.getProperty(key), false);
            }
            save();
        }
    }

    /**
     * Sets and saves an args list
     *
     * @param args splitted in: "key=value <newLine> key2=value2 ..."
     * @see PropertiesTools#convertArgsToProperties(String[])
     */
    public static void setStrings(String[] args) {
        setStrings(PropertiesTools.convertArgsToProperties(args));
    }

    /**
     * Get the Number with the given name
     *
     * @param nameOfField name (key) of the value to return
     * @param default     value, which is used, when the number can not be found
     * @return the value with the given name (key), or defaultValue when the number can not be
     * found
     */
    public static double getNumber(String nameOfField, double defaultValue) {
        checkArg(nameOfField);
        if (autoLoad) {
            load();
        }
        return options != null ? options.getNumber(nameOfField, defaultValue) : defaultValue;
    }

    /**
     * Get the Number with the given name
     *
     * @param nameOfField name (key) of the value to return
     * @return the value with the given name (key), or 0 when the number can not be found
     */
    public static double getNumber(String nameOfField) {
        if (autoLoad) {
            load();
        }
        checkArg(nameOfField);
        return getNumber(nameOfField, 0);
    }

    /**
     * Set's the number with the given name and saves it local
     *
     * @param nameOfField name (key) of the value to save local
     * @param value       to save local
     */
    public static void setNumber(String nameOfField, double value) {
        checkArg(nameOfField);
        if (options != null) {
            options.setNumber(nameOfField, value);
            if (autoSave) {
                save();
            }
        } else {
            entriesBeforeSettingFile.setProperty(nameOfField, String.valueOf(value));
            Log.warn("setValue before setting property file");
        }
    }

    /**
     * Get the boolean with the given name.
     *
     * @param nameOfField name (key) of the value to return
     * @param default     value, which is used, when the boolean can not be found
     * @return the value with the given name (key), or defaultValue when the boolean can not be
     * found
     */
    public static boolean getBoolean(String nameOfField, boolean defaultValue) {
        checkArg(nameOfField);
        if (autoLoad) {
            load();
        }
        String value = options != null ? options.getString(nameOfField) : null;
        if (value == null || value.isEmpty()) {
            return defaultValue;
        } else {
            return Boolean.valueOf(value);
        }
    }

    /**
     * Get the boolean with the given name. <code>False</code> is the default value.
     *
     * @param nameOfField
     * @return the value with the given name (key), or <code>false</code> when the boolean can
     * not be found
     * @see #getBoolean(String, boolean)
     */
    public static boolean getBoolean(String nameOfField) {
        checkArg(nameOfField);
        if (autoLoad) {
            load();
        }
        return getBoolean(nameOfField, false);
    }

    /**
     * @param nameOfField key
     * @return <code>true</code>: If the given key exists and has a value.
     */
    public static boolean hasProperty(String nameOfField) {
        return getString(nameOfField) != null && !nameOfField.isEmpty();
    }

    /**
     * Set's the boolean with the given name and saves it local
     *
     * @param nameOfField name (key) of the value to save local
     * @param value       to save local
     */
    public static void setBoolean(String nameOfField, boolean value) {
        checkArg(nameOfField);
        if (options != null) {
            options.setString(nameOfField, String.valueOf(value));
            if (autoSave) {
                save();
            }
        } else {
            entriesBeforeSettingFile.setProperty(nameOfField, String.valueOf(value));
            Log.warn("setValue before setting property file");
        }
    }

    /**
     * Saves the options local, without throwing an exception
     */
    public static void save() {
        try {
            if (options != null) {
                options.save();
            } else {
                new IOException();
            }
        } catch (IOException e) {
            Log.warn("Option file could not be saved");
        }
    }

    /**
     * Loads the options local, without throwing an exception
     */
    public static void load() {
        try {
            if (options != null) {
                options.load();
            } else {
                new IOException();
            }
        } catch (IOException e) {
            Log.warn("Option file could not be loaded");
        }
    }

    /**
     * Clears all options
     */
    public static void clear() {
        entriesBeforeSettingFile.clear();
        if (options != null) {
            options.clear();
        }
    }

    /**
     * Checks if the arg is a registered option of {@link DefaultArgs} or if set of
     * {@link #defaultArgs}. So only intended values can be set and returned.
     *
     * @param arg to check if it's available
     * @return if the arg is a registered option
     * @see #setDefaultArgs(HashSet)
     * @see #defaultArgs
     */
    private static boolean isRegistredArg(String arg) {
        if (defaultArgs != null) {
            return defaultArgs.contains(arg);
        } else {
            return true;
        }
    }

    /**
     * Prints a Log warning if {@link #checkForDefaults} is <code>true</code> and arg is not
     * registered and if {@link #throwExeptonOnArgsCheking} is <code>true</code>, also an
     * Exception will be thrown.
     *
     * @param arg to check if it's available
     * @see #isRegistredArg(String)
     */
    public static void checkArg(String arg) {
        if (checkForDefaults && !isRegistredArg(arg)) {
            Log.warn("Argument [" + arg + "] is not a registered option");
            if (throwExeptonOnArgsCheking) {
                throw new IllegalArgumentException("Argument [" + arg
                        + "] is not a registered option");
            }
        }
    }

    /**
     * Sets the list of default options names
     *
     * @param defaultArgs list with default options names
     * @see #isRegistredArg(String)
     */
    public static void setDefaultArgs(HashSet<String> defaultArgs) {
        MyPropertys.defaultArgs = defaultArgs;
    }

    /**
     * {@link #checkForDefaults}
     *
     * @param checkForDefaults <code>true</code> if default values should be checked
     * @see #checkArg(String)
     */
    public static void setCheckForDefaults(boolean checkForDefaults) {
        MyPropertys.checkForDefaults = checkForDefaults;
    }

    /**
     * {@link #throwExeptonOnArgsCheking}
     *
     * @param throwExeptonOnArgsCheking <code>true</code> if not resisted args should be also
     *                                  throw an exception in {@link #checkForDefaults} -not only a Log waring
     * @see #checkArg(String)
     */
    public static void setThrowExeptonOnArgsCheking(boolean throwExeptonOnArgsCheking) {
        MyPropertys.throwExeptonOnArgsCheking = throwExeptonOnArgsCheking;
    }

}
