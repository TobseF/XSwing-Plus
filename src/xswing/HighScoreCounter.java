package xswing;

import java.text.NumberFormat;
import lib.SObject;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import xswing.HighScoreMultiplicator;
import xswing.Resetable;

public class HighScoreCounter
extends SObject
implements Resetable {
    private int score = 0;
    private Font font;
    private int bonus = 0;
    private HighScoreMultiplicator multiplicator;
    private int letterLenght;

    public HighScoreCounter(Font font, int x, int y, HighScoreMultiplicator multiplicator) {
        super(x, y);
        this.font = font;
        this.multiplicator = multiplicator;
        this.letterLenght = font.getWidth("0");
    }

    public void score(int score) {
        this.bonus = score * this.multiplicator.getMulti();
        this.score += this.bonus;
        this.multiplicator.score();
    }

    @Override
    public void draw(Graphics g) {
        String scoreS = NumberFormat.getInstance().format(this.score);
        this.font.drawString(this.x - (scoreS.length() - 1) * this.letterLenght, this.y, scoreS);
        String bonusS = NumberFormat.getInstance().format(this.bonus);
        this.font.drawString(this.x - (bonusS.length() - 1) * this.letterLenght, this.y + 55, bonusS);
    }

    @Override
    public void reset() {
        this.score = 0;
        this.bonus = 0;
    }
}
