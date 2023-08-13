/*
 * @version 0.0 21.12.2008
 * @author Tobse F
 */
package lib.mylib.gamestates;

import lib.mylib.object.BasicGameState;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class Spashscreen extends BasicGameState {

    private final Image image;
    private boolean goToNextState = false;
    private StateBasedGame game;
    private boolean swichtOnMousePressed = false;

    public Spashscreen(int id, Image image) {
        super(id);
        this.image = image;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        this.game = game;
        clearInputs(container);
        container.getInput().addListener(this);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
            throws SlickException {
        image.draw();
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta)
            throws SlickException {
        if (goToNextState) {
            clearInputs(container);
            switchToNextScreen();
        }
    }

    /**
     * Swichtes to the next screen
     */
    protected void switchToNextScreen() {
        game.enterState(getID() + 1, new FadeOutTransition(), new FadeInTransition());
    }

    /**
     * Clears imput to make sure no iputs are send to the next screen
     *
     * @param container
     */
    private void clearInputs(GameContainer container) {
        container.getInput().clearControlPressedRecord();
        container.getInput().clearKeyPressedRecord();
        container.getInput().clearMousePressedRecord();
    }

    @Override
    public void keyPressed(int key, char c) {
        super.keyPressed(key, c);
        goToNextScreen();
    }

    /**
     * Swichtes to the next screen
     */
    private void goToNextScreen() {
        if (getID() == game.getCurrentStateID()) {
            goToNextState = true;
        }
    }

    @Override
    public void controllerButtonPressed(int controller, int button) {
        super.controllerButtonPressed(controller, button);
        goToNextScreen();
    }

    public void setSwichtOnMousePressed(boolean swichtOnMousePressed) {
        this.swichtOnMousePressed = swichtOnMousePressed;
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        super.mouseClicked(button, x, y, clickCount);
        goToNextState = swichtOnMousePressed;
    }

}