package lib;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Renderable;

public interface Drawable
extends Renderable {
    public static final int x = 0;
    public static final int y = 0;

    public int getX();

    public int getY();

    public void update();

    public void draw(Graphics var1);

    public void draw(float var1, float var2);
}
