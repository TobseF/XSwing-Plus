package tests;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * This is the original TestState1 from slick tests extended to a nifty gui overlay. This only
 * implements from ScreenController because we have a quit() onClick action definied in the
 * nifty xml file that is handled in here to quit the demo.
 *
 * @author void
 */
public class TestState2 extends BasicGameState implements ScreenController {

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        // TODO Auto-generated method stub

    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        // TODO Auto-generated method stub

    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        // TODO Auto-generated method stub

    }

    @Override
    public void bind(Nifty arg0, Screen arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onEndScreen() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStartScreen() {
        // TODO Auto-generated method stub

    }

    @Override
    public int getID() {
        // TODO Auto-generated method stub
        return 0;
    }

	
/*
	public static final int ID = 1;
	private GameContainer container;
	private Font font;
	private Color currentColor;
	private Nifty nifty;
	private NiftyGameState niftyGameState;

	@Override
	public int getID() {
		return ID;
	}

	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.container = container;
		font = new AngelCodeFont("restest/menu.fnt", "restest/menu.png");
		currentColor = Color.white;
		niftyGameState = new NiftyGameState(2);
		niftyGameState.fromXml("tests/overlay.xml", this);
		container.getInput().addListener(niftyGameState);
		niftyGameState.init(container, game);
		// nifty = niftyGameState.getNifty();
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		g.setFont(font);
		g.setColor(currentColor);
		g.drawString("State Based Game Test", 100, 100);
		g.drawString("1-3 will switch between colors", 100, 300);
		g.drawString("(this is all slick rendering!)", 100, 400);
		g.drawString("and this is more slick text", 360, 650);
		g.drawString("below (!) a nifty-gui overlay", 360, 700);

		niftyGameState.render(container, game, g);
	}

	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		niftyGameState.enter(container, game);
	}

	@Override
	public void keyReleased(int key, char c) {
		if (key == Input.KEY_1) {
			currentColor = Color.red;
			getElement("red").startEffect(EffectEventId.onCustom);
		}
		if (key == Input.KEY_2) {
			currentColor = Color.green;
			getElement("green").startEffect(EffectEventId.onCustom);
		}
		if (key == Input.KEY_3) {
			currentColor = Color.blue;
			getElement("blue").startEffect(EffectEventId.onCustom);
		}
	}

	private Element getElement(final String id) {
		return nifty.getCurrentScreen().findElementByName(id);
	}

	public void bind(Nifty nifty, Screen screen) {}

	public void onEndScreen() {}

	public void onStartScreen() {}

	public void quit() {
		nifty.getCurrentScreen().endScreen(new EndNotify() {

			public void perform() {
				container.exit();
			}
		});
	}
	*/
}