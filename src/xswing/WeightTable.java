package xswing;

import lib.SObject;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import xswing.BallTable;

public class WeightTable
extends SObject {
    private final Font font;
    BallTable ballTable;
    int[] weights = new int[8];
    private int letterLenght;

    public WeightTable(Font font, BallTable ballTable) {
        this.font = font;
        this.ballTable = ballTable;
        this.letterLenght = font.getWidth("0");
    }

    @Override
    public void draw(Graphics g) {
        int gap = 16;
        int ballA = 48;
        int i = 0;
        while (i < 8) {
            this.font.drawString(this.x + (gap + ballA) * i - (String.valueOf(this.weights[i]).length() - 1) * this.letterLenght / 2, this.y, "" + this.weights[i]);
            ++i;
        }
    }

    @Override
    public void update() {
        int i = 0;
        while (i < 8) {
            this.weights[i] = this.ballTable.getColumnWeight(i);
            ++i;
        }
    }
}
