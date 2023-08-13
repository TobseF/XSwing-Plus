/*
 * @version 0.0 24.07.2008
 * @author Tobse F
 */
package trash;

import lib.mylib.object.BasicGameState;
import lib.mylib.object.Resetable;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class GameOverState extends BasicGameState {

    private int score;
    private TextField textField = null;
    private Font font;

    public GameOverState(int id, int score) {
        super(id);
        this.score = score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        System.out.println("init() GemOver");
        font = new AngelCodeFont("res/font2.fnt", "res/font2.png");
        textField = new TextField(container, font, 300, 520, 100, 30);
        textField.setTextColor(Color.yellow);
        textField.setBorderColor(Color.red);
        textField.setText("Test String");
        textField.setFocus(true);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
            throws SlickException {
        g.fillOval(1, 1, 2, 2);
        g.setColor(Color.black);
        g.fillRoundRect(280, 280, 200, 200, 20);
        g.setColor(Color.white);
        g.drawString("GameOverState", 300, 300);
        g.drawString("Press Enter for a new Game", 300, 340);
        g.drawString("Score: " + score, 300, 390);
        textField.render(container, g);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta)
            throws SlickException {
        if (container.getInput().isKeyPressed(Input.KEY_ENTER)) {
            ((Resetable) game.getState(1)).reset();
            game.enterState(1, new FadeOutTransition(Color.black), new FadeInTransition(
                    Color.black));
        }
        if (container.getInput().isKeyPressed(Input.KEY_2)) {
            // ((Resetable)game.getState(1)).reset();
            game.enterState(1, new FadeOutTransition(Color.black), new FadeInTransition(
                    Color.black));
        }
    }
}