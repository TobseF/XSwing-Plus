/*
 * @author Tobse F
 */
package tests;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.slick.NiftyGameState;
import lib.mylib.object.BasicGameState;
import org.newdawn.slick.*;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;

/**
 * Swicht DEFERREDLOADING to <code>true</code> to see the wollwing error: <br>
 * Press space to switch to the Nifty Gamte State<br>
 *
 * <pre>
 * Exception in thread &quot;main&quot; java.lang.IllegalArgumentException: Number of remaining buffer elements is 0, must be at least 3
 * 	at org.lwjgl.BufferChecks.throwBufferSizeException(BufferChecks.java:130)
 * 	at org.lwjgl.BufferChecks.checkBufferSize(BufferChecks.java:145)
 * 	at org.lwjgl.NondirectBufferWrapper.wrapNoCopyBuffer(NondirectBufferWrapper.java:71)
 * 	at org.lwjgl.opengl.GL11.glGetTexImage(GL11.java:1566)
 * 	at org.newdawn.slick.opengl.renderer.ImmediateModeOGLRenderer.glGetTexImage(ImmediateModeOGLRenderer.java:192)
 * 	at org.newdawn.slick.opengl.TextureImpl.getTextureData(TextureImpl.java:285)
 * 	at de.lessvoid.font.TexData.&lt;init&gt;(TexData.java:48)
 * 	at de.lessvoid.font.Font.init(Font.java:101)
 * 	at de.lessvoid.console.Console.&lt;init&gt;(Console.java:30)
 * 	at de.lessvoid.nifty.NiftyDebugConsole.&lt;init&gt;(NiftyDebugConsole.java:37)
 * 	at de.lessvoid.nifty.Nifty.&lt;init&gt;(Nifty.java:171)
 * 	at de.lessvoid.nifty.slick.NiftyGameState.&lt;init&gt;(NiftyGameState.java:65)
 * 	at tests.NiftyLoadingProblem.initStatesList(NiftyLoadingProblem.java:38)
 * 	at org.newdawn.slick.state.StateBasedGame.init(StateBasedGame.java:148)
 * 	at org.newdawn.slick.AppGameContainer.start(AppGameContainer.java:357)
 * 	at tests.NiftyLoadingProblem.main(NiftyLoadingProblem.java:54)
 * </pre>
 */
public class NiftyLoadingProblem extends StateBasedGame {

    private static final boolean DEFERREDLOADING = false;

    public NiftyLoadingProblem() {
        super("Game");
    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        addState(new LoadingScreen());
        addState(new SlickTestState());
        NiftyGameState state = new NiftyGameState(2);
        state.fromXml("niftytest/xmlTest.xml", new ScreenController() {

            @Override
            public void bind(Nifty nifty, Screen screen) {
            }

            @Override
            public void onEndScreen() {
            }

            @Override
            public void onStartScreen() {
            }
        });
        addState(state);
    }

    public static void main(String[] args) {
        try {
            LoadingList.setDeferredLoading(DEFERREDLOADING);
            AppGameContainer game = new AppGameContainer(new NiftyLoadingProblem());
            game.setShowFPS(true);
            game.setMinimumLogicUpdateInterval(25);
            game.setDisplayMode(800, 600, false);
            game.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public class SlickTestState extends BasicGameState {

        private Image image1, image2;
        private float angle = 0.0f;

        @Override
        public void init(GameContainer container, StateBasedGame game) throws SlickException {
            image1 = new Image("niftytest/peng.png");
            image2 = new Image("niftytest/xswing.png");
        }

        @Override
        public void render(GameContainer container, StateBasedGame game, Graphics g)
                throws SlickException {
            g.drawString("Press space for Nifty State", 100, 250);
            image2.draw();
            g.rotate(150, 150, angle);
            g.setColor(Color.gray);
            g.fillRect(100, 100, 100, 100);
            g.setColor(Color.red);
            g.setLineWidth(2f);
            g.drawRect(100, 100, 100, 100);
            g.drawImage(image1, 110, 110);
            g.drawImage(image1, 140, 140);
            angle += 0.01f;
        }

        @Override
        public void update(GameContainer container, StateBasedGame game, int delta)
                throws SlickException {
            if (container.getInput().isKeyPressed(Input.KEY_SPACE)) {
                System.out.println("asd");
                enterState(2);
            }
        }
    }

    public class LoadingScreen extends BasicGameState {

        public LoadingScreen() {
        }

        private DeferredResource nextResource;

        @Override
        public void update(GameContainer container, StateBasedGame game, int delta)
                throws SlickException {
            if (nextResource != null) {
                try {
                    nextResource.load();
                } catch (IOException e) {
                    throw new SlickException("Failed to load: "
                            + nextResource.getDescription(), e);
                }
                nextResource = null;
            }

            if (LoadingList.get().getRemainingResources() > 0) {
                nextResource = LoadingList.get().getNext();
            } else {
                enterState(0);
            }
        }

        @Override
        public void init(GameContainer container, StateBasedGame game) throws SlickException {
            // TODO Auto-generated method stub

        }

        @Override
        public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
            // TODO Auto-generated method stub

        }
    }

}