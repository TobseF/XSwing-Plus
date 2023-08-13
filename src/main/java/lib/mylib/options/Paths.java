/*
 * @version 0.0 18.09.2009
 * @author Tobse F
 */
package lib.mylib.options;

public interface Paths {

    /**
     * Relative location of the resource dir
     */
    String RES_DIR = "res/";
    /**
     * Relative location of the font dir
     */
    String FONT_DIR = RES_DIR + "fonts/";
    /**
     * Relative location of the sound dir
     */
    String SOUND_DIR = RES_DIR + "sounds/";
    /**
     * Relative location of the music dir
     */
    String MUSIC_DIR = RES_DIR + "music/";
    /**
     * Relative location of the resource in the src lib dir (mylib)
     */
    String RES_DIR_LIB = "lib/mylib/res/";
    /**
     * Relative location of the resource dir for the tests
     */
    String RES_TEST_DIR = "restest/";

}
