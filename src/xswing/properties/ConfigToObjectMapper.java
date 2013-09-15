package xswing.properties;

import java.lang.reflect.Field;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import lib.mylib.object.SObject;
import lib.mylib.options.Paths;
import lib.mylib.properties.ObjectConfig;

public class ConfigToObjectMapper {
	
	
	public static void map(Iterable<Object> objects, ObjectConfig config) throws SlickException {
		for (Object object : objects) {
			map(object, config);
		}
	}

	public static void map(Object object, ObjectConfig config) throws SlickException {

		if (object instanceof SObject) {
			SObject sobject = (SObject) object;
			if(config.isSetX()){
				sobject.setX(config.getX());				
			}
			if(config.isSetY()){
				sobject.setY(config.getY());				
			}
			if(config.isSetWidth()){
				sobject.setWidth(config.getWidth());				
			}

			if(config.isSetHeight()){
				sobject.setHeight(config.getHeight());				
			}

			if(config.isSetVisible()){
				sobject.setVisible(config.isVisible());				
			}

			for (Field field : object.getClass().getDeclaredFields()) {
				String fieldName = field.getName();
				if ((config.isSetProperties() && config.getProperties().containsKey(fieldName) )||(config.isSetSounds()&& config.getSounds().containsKey(fieldName))) {
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
							field.set(object,new Sound(Paths.SOUND_DIR+config.getSound(fieldName)));
						} else {
							throw new IllegalArgumentException("Could't map field" + fieldName + " of " + object);
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

}
