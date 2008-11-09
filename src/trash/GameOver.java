/*
 * @version 0.0 24.07.2008
 * @author 	Tobse F
 */
package trash;

import lib.mylib.BasicGameState;
import lib.mylib.Resetable;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class GameOver extends BasicGameState{
	private int score;
	private TextField textField=null;
	
	public GameOver(int id, int score) {
		super(id);
		this.score=score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	Font font;
	@Override
	public void init(GameContainer container, StateBasedGame game)throws SlickException {
		System.out.println("init() GemOver");
		font=new AngelCodeFont("res/font2.fnt","res/font2.png");
		textField=new TextField(container,font,300, 520,100,30);
		textField.setTextColor(Color.yellow);
		textField.setBorderColor(Color.red);
		textField.setText("Test String");
		//name.setCursorPos(0);
		textField.setFocus(true);
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)throws SlickException {
		//g.destroy();
		
		g.fillOval(1, 1, 2, 2);
		
		g.setColor(Color.black);
		g.fillRoundRect(280, 280, 200, 200, 20);
		g.setColor(Color.white);
		g.drawString("GameOver", 300, 300);
		g.drawString("Press Enter for a new Game", 300, 340);
		g.drawString("Score: "+score, 300, 390);
		textField.render(container, g);
	}


	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)throws SlickException {
		if(container.getInput().isKeyPressed(Input.KEY_ENTER)){
			((Resetable)game.getState(1)).reset();
			game.enterState(1, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
		}
		if(container.getInput().isKeyPressed(Input.KEY_2)){
			//((Resetable)game.getState(1)).reset();
			game.enterState(1, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
		}
	}

}
