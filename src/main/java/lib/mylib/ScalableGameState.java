package lib.mylib;

import org.newdawn.slick.*;
import org.newdawn.slick.opengl.SlickCallable;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * A wrapper to allow any game to be scalable. This relies on knowing the normal width/height of the game - i.e. the
 * dimensions that the game is expecting to be run at. The wrapper then takes the size of the container and scales
 * rendering and input based on the ratio. Note: Using OpenGL directly within a ScalableGame can break it
 *
 * @author kevin
 */
public class ScalableGameState implements GameState {
    /**
     * The renderer to use for all GL operations
     */
    private static final SGL GL = Renderer.get();

    /**
     * The normal or native width of the game
     */
    private final float normalWidth;
    /**
     * The normal or native height of the game
     */
    private final float normalHeight;
    /**
     * The game that is being wrapped
     */
    private final GameState game;
    /**
     * True if we should maintain the aspect ratio
     */
    private final boolean maintainAspect;
    /**
     * The target width
     */
    private int targetWidth;
    /**
     * The target height
     */
    private int targetHeight;
    /**
     * The game container wrapped
     */
    private GameContainer container;

    private int id;

    /**
     * Create a new scalable game wrapper
     *
     * @param held         The game to be wrapper and displayed at a different resolution
     * @param normalWidth  The normal width of the game
     * @param normalHeight The noral height of the game
     */
    public ScalableGameState(GameState held, int normalWidth, int normalHeight) {
        this(held, normalWidth, normalHeight, false);
    }

    /**
     * Create a new scalable game wrapper
     *
     * @param held           The game to be wrapper and displayed at a different resolution
     * @param normalWidth    The normal width of the game
     * @param normalHeight   The noral height of the game
     * @param maintainAspect True if we should maintain the aspect ratio
     */
    public ScalableGameState(GameState held, int normalWidth, int normalHeight, boolean maintainAspect) {
        this.game = held;
        this.normalWidth = normalWidth;
        this.normalHeight = normalHeight;
        this.maintainAspect = maintainAspect;
    }

    /**
     * Recalculate the scale of the game
     *
     * @throws SlickException Indicates a failure to reinit the game
     */
    public void recalculateScale() throws SlickException {
        targetWidth = container.getWidth();
        targetHeight = container.getHeight();

        if (maintainAspect) {
            boolean normalIsWide = (normalWidth / normalHeight > 1.6);
            boolean containerIsWide = ((float) targetWidth / (float) targetHeight > 1.6);
            float wScale = targetWidth / normalWidth;
            float hScale = targetHeight / normalHeight;

            if (normalIsWide & containerIsWide) {
                float scale = (wScale < hScale ? wScale : hScale);
                targetWidth = (int) (normalWidth * scale);
                targetHeight = (int) (normalHeight * scale);
            } else if (normalIsWide & !containerIsWide) {
                targetWidth = (int) (normalWidth * wScale);
                targetHeight = (int) (normalHeight * wScale);
            } else if (!normalIsWide & containerIsWide) {
                targetWidth = (int) (normalWidth * hScale);
                targetHeight = (int) (normalHeight * hScale);
            } else {
                float scale = (wScale < hScale ? wScale : hScale);
                targetWidth = (int) (normalWidth * scale);
                targetHeight = (int) (normalHeight * scale);
            }

        }

        if (this.game instanceof InputListener) {
            container.getInput().addListener(this.game);
        }
        container.getInput().setScale(normalWidth / targetWidth, normalHeight / targetHeight);

        int yoffset = 0;
        int xoffset = 0;

        if (targetHeight < container.getHeight()) {
            yoffset = (container.getHeight() - targetHeight) / 2;
        }
        if (targetWidth < container.getWidth()) {
            xoffset = (container.getWidth() - targetWidth) / 2;
        }
        container.getInput().setOffset(-xoffset / (targetWidth / normalWidth), -yoffset / (targetHeight / normalHeight));

    }

    public void mouseWheelMoved(int change) {
    }

    public void mouseClicked(int button, int x, int y, int clickCount) {
    }

    public void mousePressed(int button, int x, int y) {
    }

    public void mouseReleased(int button, int x, int y) {
    }

    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
    }

    public void mouseDragged(int oldx, int oldy, int newx, int newy) {
    }

    public void setInput(Input input) {
    }

    public boolean isAcceptingInput() {
        return false;
    }

    public void inputEnded() {
    }

    public void inputStarted() {
    }

    public void keyPressed(int key, char c) {
    }

    public void keyReleased(int key, char c) {
    }

    public void controllerLeftPressed(int controller) {
    }

    public void controllerLeftReleased(int controller) {
    }

    public void controllerRightPressed(int controller) {
    }

    public void controllerRightReleased(int controller) {
    }

    public void controllerUpPressed(int controller) {
    }

    public void controllerUpReleased(int controller) {
    }

    public void controllerDownPressed(int controller) {
    }

    public void controllerDownReleased(int controller) {
    }

    public void controllerButtonPressed(int controller, int button) {
    }

    public void controllerButtonReleased(int controller, int button) {
    }

    public int getID() {
        return id;
    }

    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        this.container = container;
        this.game.init(container, game);
    }

    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        int yoffset = 0;
        int xoffset = 0;

        if (targetHeight < container.getHeight()) {
            yoffset = (container.getHeight() - targetHeight) / 2;
        }
        if (targetWidth < container.getWidth()) {
            xoffset = (container.getWidth() - targetWidth) / 2;
        }

        SlickCallable.enterSafeBlock();
        g.setClip(xoffset, yoffset, targetWidth, targetHeight);
        GL.glTranslatef(xoffset, yoffset, 0);
        g.scale(targetWidth / normalWidth, targetHeight / normalHeight);
        GL.glPushMatrix();
        this.game.render(container, game, g);
        GL.glPopMatrix();
        g.clearClip();
        SlickCallable.leaveSafeBlock();

        renderOverlay(container, g);
    }

    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        if ((targetHeight != container.getHeight()) || (targetWidth != container.getWidth())) {
            recalculateScale();
        }

        this.game.update(container, game, delta);
    }

    /**
     * Render the overlay that will sit over the scaled screen
     *
     * @param container The container holding the game being render
     * @param g         Graphics context on which to render
     */
    protected void renderOverlay(GameContainer container, Graphics g) {
    }

    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        this.game.enter(container, game);
        recalculateScale();

    }

    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
        this.game.leave(container, game);
        container.getInput().setScale(1, 1);
        container.getInput().setOffset(0, 0);

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
