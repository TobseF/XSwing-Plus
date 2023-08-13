package xswing.properties;

import lib.mylib.object.SObject;
import lib.mylib.options.Paths;
import lib.mylib.properties.ObjectConfig;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class ConfigToObjectMapper {

    public static void map(Iterable<Object> objects, ObjectConfig config) throws SlickException {
        for (Object object : objects) {
            map(object, config);
        }
    }

    public static void map(Object object, ObjectConfig config) throws SlickException {

        if (object instanceof SObject) {
            SObject sobject = (SObject) object;
            if (config.isSetX()) {
                sobject.setX(config.getX());
            }
            if (config.isSetY()) {
                sobject.setY(config.getY());
            }
            if (config.isSetWidth()) {
                sobject.setWidth(config.getWidth());
            }

            if (config.isSetHeight()) {
                sobject.setHeight(config.getHeight());
            }

            if (config.isSetVisible()) {
                sobject.setVisible(config.isVisible());
            }
            if (config.isSetImage()) {
                sobject.setImage(new Image(Paths.RES_DIR + config.getImage()));
            }

            for (Field field : getDeclaredFields(object)) {
                String fieldName = field.getName();
                if ((config.isSetProperties() && config.getProperties().containsKey(fieldName))
                        || (config.isSetSounds() && config.getSounds().containsKey(fieldName)) ||
                        config.isSetImages() && config.getImages().containsKey(fieldName)) {
                    try {
                        field.setAccessible(true);
                        Class<?> type = field.getType();
                        if (type.isAssignableFrom(String.class)) {
                            field.set(object, config.getPropertyString(fieldName));
                        } else if (type.isAssignableFrom(Integer.TYPE)) {
                            field.setInt(object, config.getPropertyInt(fieldName));
                        } else if (type.isAssignableFrom(Boolean.TYPE)) {
                            field.setBoolean(object, config.getPropertyBoolean(fieldName));
                        } else if (type.isAssignableFrom(Sound.class)) {
                            field.set(object, new Sound(Paths.SOUND_DIR + config.getSound(fieldName)));
                        } else if (type.isAssignableFrom(Image.class)) {
                            field.set(object, new Image(Paths.RES_DIR + config.getImage(fieldName)));
                        } else {
                            throw new IllegalArgumentException("Could't map field '" + fieldName + "' of " + type + ":" + object + "("
                                    + object.getClass().getName() + ")");
                        }
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Object not mapable");
        }

    }

    private static List<Field> getDeclaredFields(Object object) {
        Class<?> objectClass = object.getClass();
        Class<?> superclass = objectClass.getSuperclass();

        if (SObject.class.isAssignableFrom(superclass) && superclass != SObject.class) {
            List<Field> fields = Arrays.asList(superclass.getDeclaredFields());
            List<Field> superClassFields = Arrays.asList(objectClass.getDeclaredFields());
            return Stream.concat(fields.stream(), superClassFields.stream()).collect(toList());
        }
        return Arrays.asList(objectClass.getDeclaredFields());

    }

}
