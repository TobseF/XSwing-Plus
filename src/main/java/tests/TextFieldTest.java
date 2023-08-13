/*
 * @version 0.0 04.08.2008
 * @author Tobse F
 */
package tests;

import org.newdawn.slick.*;
import org.newdawn.slick.gui.TextField;

public class TextFieldTest extends BasicGame {

    private TextField textField;

    public TextFieldTest() {
        super("TextFieldTest");
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        textField = new TextField(container, container.getDefaultFont(), 10, 30, 200, 30);
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        // g.fillOval(20, 70, 40, 40); //Textfield won't be rendered when enabled
        Color.white.bind();
        textField.render(container, g);

    }

    public static void main(String[] args) throws Exception {
        AppGameContainer container = new AppGameContainer(new TextFieldTest());
        container.setDisplayMode(640, 480, false);
        container.start();
    }
}