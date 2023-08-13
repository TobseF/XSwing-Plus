/*
 * @version 0.0 29.04.2008
 * @author Tobse F
 */
package lib.mylib.gamestates;

import lib.mylib.object.BasicGameState;
import lib.mylib.util.LanguageSelector;
import org.newdawn.slick.*;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.Transition;

import java.io.IOException;

/**
 * The loading screen, which is shwon until all resources have been loaded. To use it, you have
 * to set <code>LoadingList.setDeferredLoading(true);</code> before the init methods are
 * executed.
 */
public class LoadingScreen extends BasicGameState {

    public static final String RES = "res/";

    private Image loading = null;
    private final Transition transitionLeave;
    private final Transition transitionEnter;

    private int loadedResources;

    private int totalResources;

    private DeferredResource nextResource;

    public LoadingScreen(int id, Transition transitionLeave, Transition transitionEnter) {
        super(id);
        this.transitionLeave = transitionLeave;
        this.transitionEnter = transitionEnter;
    }

    public LoadingScreen(int id) {
        super(id);
        transitionLeave = new EmptyTransition();
        transitionEnter = new FadeInTransition();
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        try {
            loading = new Image(RES + "32.png");
        } catch (RuntimeException e) {
        }
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
            throws SlickException {
        // TODO: call render more than "loaded" times?
        g.clear();
        g.setColor(Color.white);
        if (nextResource != null) {
            g.drawString(LanguageSelector.getString("loading") + ": "
                    + nextResource.getDescription(), 100, 100);
        }

        renderLoadingBar(g, 100, 180, 500);
        // System.out.println("Render: " + " L: "+renderI + "  R: "+updateI);
    }

    private void renderLoadingBar(Graphics g, int x, int y, int widht) {
        int resourcesInList = LoadingList.get().getTotalResources();
        if (resourcesInList != 0) {
            totalResources = resourcesInList;
            loadedResources = resourcesInList - LoadingList.get().getRemainingResources();
        } else if (totalResources == 0) {
            totalResources = 1;
        }
        g.drawString("(" + loadedResources + "/" + totalResources + ")", 100, 130);
        int height = 35, step = widht / totalResources;
        g.setAntiAlias(true);
        GradientFill gradient = new GradientFill(0, 0, Color.lightGray, 0, height, Color.white);
        RoundedRectangle bar = new RoundedRectangle(x, y, loadedResources * step, height, 12);
        g.fill(bar, gradient);
        g.setLineWidth(2f);
        g.draw(new RoundedRectangle(x, y, totalResources * step, height, 12));
        if (loading != null) {
            loading.drawCentered(x + loadedResources * step - 10, y + height / 2);
        }
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta)
            throws SlickException {
        if (nextResource != null) {
            try {
                nextResource.load();
            } catch (IOException e) {
                throw new SlickException("Failed to load: " + nextResource.getDescription(), e);
            }
            nextResource = null;
        }

        if (LoadingList.get().getRemainingResources() > 0) {
            nextResource = LoadingList.get().getNext();
        } else {
            enterNextScreen(game);
            LoadingList.setDeferredLoading(false); // All resources should be loaded
        }
    }

    protected void enterNextScreen(StateBasedGame game) {
        game.enterState(getID() + 1, transitionLeave, transitionEnter);
    }
}