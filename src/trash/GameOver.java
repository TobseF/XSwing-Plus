package trash;

import lib.mylib.BasicGameState;
import lib.mylib.Resetable;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class GameOver
extends BasicGameState {
    private int score;
    private TextField textField = null;
    Font font;

    public GameOver(int id, int score) {
        super(id);
        this.score = score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        System.out.println("init() GemOver");
        this.font = new AngelCodeFont("res/font2.fnt", "res/font2.png");
        this.textField = new TextField(container, this.font, 300, 520, 100, 30);
        this.textField.setTextColor(Color.yellow);
        this.textField.setBorderColor(Color.red);
        this.textField.setText("Test String");
        this.textField.setFocus(true);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.fillOval(1.0f, 1.0f, 2.0f, 2.0f);
        g.setColor(Color.black);
        g.fillRoundRect(280.0f, 280.0f, 200.0f, 200.0f, 20);
        g.setColor(Color.white);
        g.drawString("GameOver", 300.0f, 300.0f);
        g.drawString("Press Enter for a new Game", 300.0f, 340.0f);
        g.drawString("Score: " + this.score, 300.0f, 390.0f);
        this.textField.render(container, g);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        if (container.getInput().isKeyPressed(28)) {
            ((Resetable)((Object)game.getState(1))).reset();
            game.enterState(1, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
        }
        if (container.getInput().isKeyPressed(3)) {
            game.enterState(1, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
        }
    }
}
