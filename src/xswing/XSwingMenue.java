package xswing;

import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.slick.NiftyGameState;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;
import xswing.LoadingScreen;
import xswing.MainGame;
import xswing.gui.XSwingScreenController;

public class XSwingMenue
extends StateBasedGame {
    public XSwingMenue() {
        super("Xswing");
    }

    public void initStatesList(GameContainer container) throws SlickException {
        XSwingScreenController controller = new XSwingScreenController(this);
        this.addState(new LoadingScreen(0));
        NiftyGameState state = new NiftyGameState(1);
        state.fromXml("res/gui/StartScreen.xml", new ScreenController[]{controller});
        this.addState(state);
        this.addState(new MainGame(2));
    }

    public static void main(String[] args) {
        boolean debug = false;
        if (args.length > 0) {
            debug = Boolean.valueOf(args[0]);
        }
        boolean fullsceen = !debug;
        Log.info("Debugmode: " + debug);
        try {
            AppGameContainer game = new AppGameContainer(new XSwingMenue());
            game.setShowFPS(debug);
            game.setMinimumLogicUpdateInterval(20);
            game.setMaximumLogicUpdateInterval(20);
            game.setDisplayMode(1024, 768, fullsceen);
            game.setClearEachFrame(false);
            game.setIcons(new String[]{"res/16.png", "res/32.png"});
            game.start();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
