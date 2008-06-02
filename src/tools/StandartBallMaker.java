package tools;

import lib.SpriteSheet;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class StandartBallMaker {
    static SpriteSheet sp;
    private final int ballsX = 9;
    private final int ballsY = 5;
    private final int balls = 45;
    private final int ballsA = 48;
    Image image = null;
    Image ballBackgrund = null;
    Font font;
    boolean drawNumbers = true;

    public StandartBallMaker() {
        Graphics g = null;
        try {
            this.image = new Image(432, 240);
            this.font = new AngelCodeFont("res/font2.fnt", "res/font2.png");
            this.ballBackgrund = new Image("res/ball.png");
            g = this.image.getGraphics();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
        int x = 0;
        int y = 0;
        int i = 0;
        while (i < 45) {
            g.setAntiAlias(true);
            if (this.image == null) {
                g.setColor(Color.red);
                g.fillOval(x, y, 48.0f, 48.0f);
            } else {
                g.drawImage(this.ballBackgrund, x, y);
            }
            g.setColor(Color.black);
            g.drawOval(x, y, 48.0f, 48.0f);
            if (this.drawNumbers) {
                this.font.drawString(x + 12, y + 12, String.valueOf(i + 1));
            }
            x += 48;
            if ((i + 1) % 9 == 0 && i > 1) {
                y += 48;
                x = 0;
            }
            ++i;
        }
        sp = new SpriteSheet(this.image, 48, 48);
    }

    public Image getImage() {
        return this.image;
    }

    public SpriteSheet getSprite() {
        return sp;
    }
}
