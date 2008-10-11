package xswing;

import java.io.IOException;
import lib.mylib.BasicGameState;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class LoadingScreen
extends BasicGameState {
    public static final String RES = "res/";
    private DeferredResource nextResource;
    private Image loading = null;
    private boolean finish = false;

    public LoadingScreen(int id) {
        super(id);
    }

    public boolean isFinish() {
        return this.finish;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        this.loading = new Image("res/32.png");
        LoadingList.setDeferredLoading(true);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.setColor(Color.black);
        g.fillRect(0.0f, 0.0f, 500.0f, 500.0f);
        g.setColor(Color.white);
        if (this.nextResource != null) {
            g.drawString("Loading: " + this.nextResource.getDescription(), 100.0f, 100.0f);
        }
        int total = LoadingList.get().getTotalResources();
        int loaded = LoadingList.get().getTotalResources() - LoadingList.get().getRemainingResources();
        g.fillRect(100.0f, 150.0f, loaded * 40, 20.0f);
        g.drawRect(100.0f, 150.0f, total * 40, 20.0f);
        g.drawImage(this.loading, 90 + loaded * 40, 140.0f);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        if (this.nextResource != null) {
            try {
                this.nextResource.load();
            }
            catch (IOException e) {
                throw new SlickException("Failed to load: " + this.nextResource.getDescription(), e);
            }
            this.nextResource = null;
        }
        if (LoadingList.get().getRemainingResources() > 0) {
            this.nextResource = LoadingList.get().getNext();
        } else {
            this.finish = true;
            LoadingList.setDeferredLoading(false);
            this.goToGame(game);
        }
    }

    private void goToGame(StateBasedGame game) {
        game.enterState(1, new FadeOutTransition(Color.black), null);
    }
}
