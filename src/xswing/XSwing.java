package xswing;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;
import xswing.LoadingScreen;
import xswing.MainGame;

public class XSwing
extends StateBasedGame {
    public XSwing() {
        super("Xswing");
    }

    public static void main(String[] args) {
        boolean debug = false;
        if (args.length > 0) {
            debug = Boolean.valueOf(args[0]);
        }
        boolean fullsceen = !debug;
        Log.info("Debugmode: " + debug);
        try {
            AppGameContainer game = new AppGameContainer(new XSwing());
            game.setShowFPS(debug);
            game.setMinimumLogicUpdateInterval(20);
            game.setMaximumLogicUpdateInterval(20);
            game.setDisplayMode(1024, 768, fullsceen);
            game.setClearEachFrame(false);
            game.setIcons(new String[]{"res/16.png", "res/32.png"});
            game.setMouseGrabbed(!debug);
            game.start();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void initStatesList(GameContainer container) throws SlickException {
        this.addState(new LoadingScreen(0));
        this.addState(new MainGame(1));
    }
}
