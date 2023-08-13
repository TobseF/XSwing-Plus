/*
 * @version 0.0 22.07.2008
 * @author Tobse F
 */
package xswing.start;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.FileSystemLocation;
import org.newdawn.slick.util.ResourceLoader;
import xswing.MainGameAndroidX;

import java.io.File;

/**
 * Starts XSwing Plus immediately in <b>single player mode</b></br>
 * <ul>
 * <li>No Options on start</li>
 * <li>No Loading Screen</li>
 * <li>No start menu</li>
 * </ul>
 *
 * @author Tobse
 */
public class XSwingAndroidDesktop extends StateBasedGame {

    public static final String ANDROID_RES_DIR = "../XSwingAndroid/assets/";

    public XSwingAndroidDesktop() {
        super("XSwing");

    }

    /**
     * @param args [0] debu gmode (true= window mode, no mouse grabbing, debuginfos, show fps)
     */
    public static void main(String[] args) {
        try {
            addAndroidResDir();
            AppGameContainer game = new AppGameContainer(new MainGameAndroidX());
            game.setShowFPS(true);
            game.setDisplayMode(800, 480, false);
            game.setClearEachFrame(false);
            //game.setForceExit(false);

            game.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public static void addAndroidResDir() {
        ResourceLoader.removeAllResourceLocations();
        ResourceLoader.addResourceLocation(new FileSystemLocation(new File(ANDROID_RES_DIR)));
    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        //addState(new LoadingScreen(0));
        //addState(new MainGameAndroidX());
    }

}