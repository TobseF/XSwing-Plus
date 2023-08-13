/*
 * @version 0.0 06.03.2010
 * @author 	Tobse F
 */
package trash;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class ResizeScreen {

    private GameContainer container;
    private int widht, height;

    public float getGraphicsScale() {
        return graphicsScale;
    }

    private int scale = 1;
    private final int[] scales = new int[]{1024, 800, 640};

    /**
     * Current scale of the graphics
     */
    private float graphicsScale = 1f;

    /**
     * Changes screensize beteen available screen sizes
     *
     * @param game game with <code>DisplayMode</code>
     */
    @SuppressWarnings("unused")
    private void shrinkGame(StateBasedGame game) {
        scale = scale == scales.length - 1 ? 0 : scale + 1;
        graphicsScale = scales[scale] / 1024f;
        try {
            ((AppGameContainer) container).setDisplayMode((int) (widht * graphicsScale),
                    (int) (height * graphicsScale), container.isFullscreen());
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
