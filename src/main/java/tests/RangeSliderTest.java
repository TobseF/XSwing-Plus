/*
 * @version 0.0 23.12.2008
 * @author Tobse F
 */
package tests;

import lib.mylib.math.RangeSlider;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class RangeSliderTest extends JFrame implements KeyListener {

    RangeSlider rangeSlider;

    public RangeSliderTest() {
        rangeSlider = new RangeSlider(0, 50, 2);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(60, 60);
        setAlwaysOnTop(true);
        addKeyListener(this);
        setVisible(true);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        new RangeSliderTest();

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            rangeSlider.stepLeft();
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rangeSlider.stepRight();

        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            rangeSlider.invert();
        }
        if (e.getKeyCode() == KeyEvent.VK_X) {
            rangeSlider.setValue(100);
        }
        if (e.getKeyCode() == KeyEvent.VK_Y) {
            rangeSlider.setValue(-100);
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            rangeSlider.setInfinite(true);
        }
        System.out.println(rangeSlider.getValue() + " inverted: " + rangeSlider.isInverted()
                + " infinite " + rangeSlider.isInfinite());
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

}
