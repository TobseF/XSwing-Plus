package lib.mylib.util;

import java.io.File;

/**
 * Extended {@link Properties} which provides getter and setter for a {@link Enum} type. So you can get a property like
 * this:<br>
 * <code>
 * PropertiesEnum<Keys>	properties = new PropertiesEnum<Keys>();
 * properties.setPropertyInt(Keys.BUFFER_SIZE, 42);
 * </code><br>
 * So it's assured that valid keys are used in getX() and setX() methods.
 *
 * @param <Args>
 * @author TOF
 */
public class PropertiesEnum<Args extends Enum<?>> extends Properties {
    private static final long serialVersionUID = 1L;

    public PropertiesEnum(File file) {
        super(file);
    }

    public PropertiesEnum(java.util.Properties properties) {
        super(properties);
    }

    public String getProperty(Args args, String defaultValue) {
        return super.getProperty(args.toString(), defaultValue);
    }

    public String getProperty(Args args) {
        return super.getProperty(args.toString());
    }

    public synchronized Object setProperty(Args args, String value) {
        return super.setProperty(args.toString(), value);
    }

    public boolean getPropertyBoolean(Args args, boolean defaultValue) {
        return super.getPropertyBoolean(args.toString(), defaultValue);
    }

    public boolean getPropertyBoolean(Args args) {
        return super.getPropertyBoolean(args.toString());
    }

    public synchronized void setPropertyBoolean(Args args, boolean value) {
    }

    public int getPropertyInt(Args args, int defaultValue) {
        return super.getPropertyInt(args.toString(), defaultValue);
    }

    public int getPropertyInt(Args args) {
        return super.getPropertyInt(args.toString());
    }

    public synchronized void setPropertyInt(Args args, int value) {
        super.setPropertyInt(args.toString(), value);
    }

    public float getPropertyFloat(Args args, int defaultValue) {
        return super.getPropertyFloat(args.toString(), defaultValue);
    }

    public float getPropertyFloat(Args args) {
        return super.getPropertyInt(args.toString());
    }

    public synchronized void setPropertyFloat(Args args, int value) {
        super.setPropertyFloat(args.toString(), value);
    }

    public synchronized void setPropertyString(Args args, String value) {
        super.setProperty(args.toString(), value);
    }

    public String getPropertyString(Args args) {
        return super.getProperty(args.toString());
    }
}
