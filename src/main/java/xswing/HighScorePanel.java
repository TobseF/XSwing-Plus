/*
 * @version 0.0 01.09.2008
 * @author Tobse F
 */
package xswing;

import lib.mylib.MyTimer;
import lib.mylib.color.TransparancySlider;
import lib.mylib.highscore.HighScoreLine;
import lib.mylib.highscore.HighScoreTable;
import lib.mylib.object.Resetable;
import lib.mylib.object.SObject;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.text.NumberFormat;

/**
 * Loads, saves and draws the HighScore table
 *
 * @author Tobse
 */
public class HighScorePanel extends SObject implements Resetable {

    private final MyTimer timer;
    /**
     * Index of the current displyed score value
     */
    private int index = 0;
    private final AngelCodeFont font;
    private final TransparancySlider fadeIn;
    private final TransparancySlider fadeOut;

    private final HighScoreTable scoreTable;

    public HighScorePanel(AngelCodeFont font, HighScoreTable scoreTable) {
        this.font = font;
        this.scoreTable = scoreTable;
        fadeIn = new TransparancySlider(20, 1000, TransparancySlider.LINEAR_FADE_IN);
        fadeOut = new TransparancySlider(20, 1000, TransparancySlider.LINEAR_FADE_OUT);
        timer = new MyTimer(22000, true) {
            @Override
            protected void timerAction() {
                switchIndex();
            }
        };
    }

    @Override
    public void render(Graphics g) {
        if (isVisible) {
            drawScoreTableFading();
        }
    }

    private void drawScoreTableFading() {
        Color fadeInColor = fadeIn.getTranparency();
        // Color fadeOutColor = fadeOut.getTranparency();

        int gap = font.getLineHeight() - 2;
        if (scoreTable.size() >= 1) {
            drawScoreTable(index, 0, fadeInColor);
            if (scoreTable.size() >= 2) {
                drawScoreTable(index + 1, gap, fadeInColor);
                /*
                 * if (highScoreTable.length >= 4) { drawScoreTable( getNextIndex() + 1, 0,
                 * fadeOutColor); drawScoreTable( getNextIndex() + 2, gab_between_balls,
                 * fadeOutColor); }
                 */
            }
        }
    }

    private void drawScoreTable(int index, int gapBetweenLines, Color transparency) {
        int maxScoreLength = font.getWidth("000000");
        HighScoreLine scoreLine = scoreTable.getScore(index);
        // Position
        font.drawString(x - 150, y + gapBetweenLines, (index + 1) + "", transparency);
        // Name
        font.drawString(x - 5 - font.getWidth(scoreLine.getName()) - maxScoreLength, y
                + gapBetweenLines, scoreLine.getName(), transparency);
        // Score
        int score = scoreLine.getScore();
        String formattesScore = NumberFormat.getInstance().format(score);

        font.drawString(x - font.getWidth(formattesScore), y + gapBetweenLines,
                formattesScore, transparency);
    }

    /**
     * Switch to the next element in the highScoreList
     */
    private void switchIndex() {
        index = getNextIndex();
        fadeIn.reset();
        fadeOut.reset();
        // fadeOut.invert();
        // fadeIn.invert();
    }

    /**
     * @return nextIndexInHighScoreList
     */
    private int getNextIndex() {
        int indexNew = (index + 1) < (scoreTable.size() - 1) ? index + 1 : 0;
        return indexNew;
    }

    @Override
    public void update(int delta) {
        fadeIn.update(delta);
        fadeOut.update(delta);
        timer.update(delta);
    }

    @Override
    public void reset() {
        index = 0;
        timer.reset();
        fadeIn.reset();
        fadeOut.reset();
    }
}