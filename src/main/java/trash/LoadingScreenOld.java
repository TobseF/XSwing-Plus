/*
 * @version 0.0 21.12.2008
 * @author Tobse F
 */
package trash;

import lib.mylib.object.BasicGameState;
import lib.mylib.util.LanguageSelector;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;

import java.io.IOException;

public class LoadingScreenOld extends BasicGameState {

    private DeferredResource nextResource;

    public LoadingScreenOld(int id) {
        super(id);
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
            throws SlickException {
        g.setColor(Color.black);
        g.fillRect(0, 0, 500, 500); // clear screen, beause
        // "game.setClearEachFrame(false);"

        g.setColor(Color.white);
        if (nextResource != null) {
            g.drawString(LanguageSelector.getString("loading") + ": "
                    + nextResource.getDescription(), 100, 100);
        }

        int total = LoadingList.get().getTotalResources();
        int loaded = LoadingList.get().getTotalResources()
                - LoadingList.get().getRemainingResources();

        g.fillRect(100, 150, loaded * 40, 20);
        g.drawRect(100, 150, total * 40, 20);
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
            enterMenue(game);
        }
    }

    private void enterMenue(StateBasedGame game) {
        LoadingList.setDeferredLoading(false); // All resources should be loaded
        game.enterState(getID() + 1, new EmptyTransition(), new FadeInTransition());
    }

}