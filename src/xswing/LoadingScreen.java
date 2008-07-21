package xswing;

import java.io.IOException;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;

public class LoadingScreen {
    private DeferredResource nextResource;
    Image loading = null;
    private boolean finish = false;

    public boolean isFinish() {
        return this.finish;
    }

    public LoadingScreen() {
        try {
            this.loading = new Image("res/32.png");
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void update() throws SlickException {
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
        } else if (!this.finish) {
            this.finish = true;
        }
    }

    public void draw(Graphics g) {
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
}
