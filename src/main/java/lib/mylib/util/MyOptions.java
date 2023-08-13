/*
 * @version 0.0 18.09.2009
 * @author Tobse F
 */
package lib.mylib.util;

import lib.mylib.options.DefaultArgs.Args;

/**
 * @author Tobse Same as {@link MyPropertys}, but adds Methods to direct use {@link Args}
 * @see MyPropertys
 * @see Args
 */
public class MyOptions extends MyPropertys {

    /**
     * Private
     */
    private MyOptions() {
    }

    /**
     * Get the String with the given name
     *
     * @param nameOfField name (key) of the value to return
     * @param default     value, which is uesed, when the String can not be found
     * @return the value with the given name (key), or defaultValue when the String can not be
     * found
     */
    public static String getString(Args nameOfField, String defaultValue) {
        return getString(nameOfField.toString(), defaultValue);
    }

    /**
     * Get the String with the given name
     *
     * @param nameOfField name (key) of the value to return
     * @return the value with the given name (key), or <code>null</code> if it's not given
     */
    public static String getString(Args nameOfField) {
        return getString(nameOfField.toString());
    }

    /**
     * Set's the string with the given name and saves it local
     *
     * @param nameOfField   name (key) of the value to save local
     * @param value         to save local
     * @param saveInstantly if the value should be saved immediately after setting it
     */
    public static void setString(Args nameOfField, String value) {
        setString(nameOfField.toString(), value);
    }

    /**
     * Get the Number with the given name
     *
     * @param nameOfField name (key) of the value to return
     * @param default     value, which is uesed, when the number can not be found
     * @return the value with the given name (key), or defaultValue when the number can not be
     * found
     */
    public static double getNumber(Args nameOfField, double defaultValue) {
        return getNumber(nameOfField.toString(), defaultValue);
    }

    /**
     * Get the Number with the given name
     *
     * @param nameOfField name (key) of the value to return
     * @return the value with the given name (key), or 0 when the number can not be found
     */
    public static double getNumber(Args nameOfField) {
        return getNumber(nameOfField.toString());
    }

    /**
     * Set's the number with the given name and saves it local
     *
     * @param nameOfField name (key) of the value to save local
     * @param value       to save local
     */
    public static void setNumber(Args nameOfField, double value) {
        setNumber(nameOfField.toString(), value);
    }

    /**
     * Get the boolean with the given name.
     *
     * @param nameOfField name (key) of the value to return
     * @param default     value, which is uesed, when the boolean can not be found
     * @return the value with the given name (key), or defaultValue when the boolean can not be
     * found
     */
    public static boolean getBoolean(Args nameOfField, boolean defaultValue) {
        return getBoolean(nameOfField.toString(), defaultValue);
    }

    /**
     * Get the boolean with the given name. <code>False</code> is the default value.
     *
     * @param nameOfField
     * @return the value with the given name (key), or <code>false</code> when the boolean can
     * not be found
     * @see #getBoolean(String, boolean)
     */
    public static boolean getBoolean(Args nameOfField) {
        return getBoolean(nameOfField.toString());
    }

    /**
     * Set's the boolean with the given name and saves it local
     *
     * @param nameOfField name (key) of the value to save local
     * @param value       to save local
     */
    public static void setBoolean(Args nameOfField, boolean value) {
        setBoolean(nameOfField.toString(), value);
    }

    /**
     * @param nameOfField key
     * @return <code>true</code>: If the given key exists and has a value.
     */
    public static boolean hasProperty(Args nameOfField) {
        return hasProperty(nameOfField.toString());
    }

}
