package lib.mylib.properties;

import java.util.*;

public class ObjectConfig implements Cloneable {
    private String objectName;
    private Integer x;
    private Integer y;
    private Integer height;
    private Integer width;
    private Map<String, String> sounds;
    private Map<String, String> images;
    private Map<String, String> properties;
    private Collection<String> fonts;
    private String image;
    private Boolean visible;

    public ObjectConfig() {
    }

    public ObjectConfig(String objectName) {
        this.objectName = objectName;
    }

    public ObjectConfig(Class<?> objectClass) {
        this(objectClass.getSimpleName());
    }

    public ObjectConfig(Class<?> objectClass, int x, int y) {
        this(objectClass);
        this.x = x;
        this.y = y;
    }

    public ObjectConfig(Class<?> objectClass, int x, int y, int height, int width) {
        this(objectClass, x, y);
        this.height = height;
        this.width = width;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setBounds(int x, int y, int width, int height) {
        setPosition(x, y);
        setSize(width, height);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void addSound(String name, String soundRessource) {
        if (sounds == null) {
            sounds = new HashMap<String, String>();
        }
        sounds.put(name, soundRessource);
    }

    public void addImage(String name, String imageRessource) {
        if (images == null) {
            images = new HashMap<String, String>();
        }
        images.put(name, imageRessource);
    }

    public String getSound(String name) {
        return sounds == null ? null : sounds.get(name);
    }

    public String getImage(String name) {
        return images == null ? null : images.get(name);
    }

    public String getPropertyString(String name) {
        return properties == null ? null : properties.get(name);
    }

    public boolean getPropertyBoolean(String name) {
        return properties != null && Boolean.parseBoolean(properties.get(name));
    }

    public int getPropertyInt(String name) {
        return properties == null ? 0 : Integer.parseInt(properties.get(name));
    }

    public double getPropertyDouble(String name) {
        return properties == null ? 0 : Double.parseDouble(properties.get(name));
    }

    public void setPropery(String key, String value) {
        if (properties == null) {
            properties = new HashMap<String, String>();
        }
        properties.put(key, value);
    }

    public void setProperyInt(String key, int value) {
        setPropery(key, String.valueOf(value));
    }

    public void setProperyBoolean(String key, boolean value) {
        setPropery(key, String.valueOf(value));
    }

    public void setProperyDouble(String key, boolean value) {
        setPropery(key, String.valueOf(value));
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public boolean isSetX() {
        return x != null;
    }

    public boolean isSetY() {
        return y != null;
    }

    public boolean isSetImage() {
        return image != null;
    }

    public boolean isSetWidth() {
        return width != null;
    }

    public boolean isSetHeight() {
        return height != null;
    }

    public boolean isSetVisible() {
        return visible != null;
    }

    public Map<String, String> getSounds() {
        return sounds;
    }

    public Map<String, String> getImages() {
        return images;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public Boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public boolean objectConfig() {
        return image != null;
    }

    public boolean isSetSounds() {
        return sounds != null;
    }

    public boolean isSetImages() {
        return images != null;
    }

    public boolean isSetProperties() {
        return properties != null;
    }

    public boolean isSetFonts() {
        return fonts != null;
    }

    public void setFonts(Collection<String> fonts) {
        this.fonts = fonts;
    }


    public Collection<String> getFonts() {
        return fonts;
    }

    public void addFont(String font) {
        if (fonts == null) {
            fonts = new LinkedList<String>();
        }
        fonts.add(font);
    }

    @Override
    public String toString() {
        return "ObjectConfig [objectName=" + objectName + ", x=" + x + ", y=" + y + ", height=" + height + ", width=" + width + ", sounds="
                + sounds + ", images=" + images + ", properties=" + properties + ", image=" + image + ", visible=" + visible + "]";
    }

    @Override
    public ObjectConfig clone() {
        ObjectConfig cloned = new ObjectConfig();
        cloned.objectName = objectName;
        cloned.image = image;
        if (isSetX()) {
            cloned.x = x;
        }
        if (isSetY()) {
            cloned.y = y;
        }
        if (isSetWidth()) {
            cloned.width = width;
        }
        if (isSetHeight()) {
            cloned.height = height;
        }
        if (isSetVisible()) {
            cloned.visible = visible;
        }
        if (isSetImages()) {
            cloned.images = new HashMap<String, String>(images);
        }
        if (isSetSounds()) {
            cloned.sounds = new HashMap<String, String>(sounds);
        }
        if (isSetProperties()) {
            cloned.properties = new HashMap<String, String>(properties);
        }
        if (isSetFonts()) {
            cloned.fonts = new ArrayList<String>(fonts);
        }
        return cloned;
    }
}
