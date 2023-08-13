package xswing.properties;

import lib.mylib.Sound;
import lib.mylib.properties.GameConfig;
import lib.mylib.properties.ObjectConfig;
import lib.mylib.properties.ObjectConfigSet;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

import java.util.HashMap;
import java.util.Map;

import static lib.mylib.options.Paths.*;

public class ResourcesLoader {

    public static Map<String, ObjectConfig> getObjectStore(ObjectConfigSet objectConfigSet) {
        Map<String, ObjectConfig> objectsStore = new HashMap<String, ObjectConfig>(objectConfigSet.getConfigs().size());
        for (ObjectConfig objectConfig : objectConfigSet) {
            objectsStore.put(objectConfig.getObjectName(), objectConfig);
        }
        return objectsStore;
    }

    public static void accesAllResources(GameConfig config) throws SlickException {
        for (ObjectConfigSet objectConfigSet : config) {

            if (config.isSetMusicPlayList()) {
                for (String music : config.getMusicPlayList()) {
                    new Music(MUSIC_DIR + music, true);
                }
            }

            for (ObjectConfig objectConfig : objectConfigSet) {
                if (objectConfig.isSetImage()) {
                    new Image(RES_DIR + objectConfig.getImage());
                }
                if (objectConfig.isSetSounds()) {
                    for (String sound : objectConfig.getSounds().values()) {
                        new Sound(SOUND_DIR + sound);
                    }
                }
                if (objectConfig.isSetImages()) {
                    for (String image : objectConfig.getImages().values()) {
                        new Image(RES_DIR + image);
                    }
                }
                if (objectConfig.isSetFonts()) {
                    for (String font : objectConfig.getFonts()) {
                        new AngelCodeFont(FONT_DIR + font + ".fnt", FONT_DIR + font + ".png");
                    }
                }
            }
        }

    }

}
