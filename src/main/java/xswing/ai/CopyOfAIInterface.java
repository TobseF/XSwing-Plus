/*
 * @version 0.0 08.01.2009
 * @author Tobse F
 */
package xswing.ai;

import lib.mylib.object.Updateable;
import xswing.Cannon;
import xswing.ball.BallTable;

public class CopyOfAIInterface implements Updateable {

    private final BallTable ballTable;
    private final Cannon cannon;
    private boolean isLeftPressed, isRightPressed, isDownPressed;
    private final Simulator simulator;

    public CopyOfAIInterface(BallTable ballTable, Cannon cannon) {
        this.ballTable = ballTable;
        this.cannon = cannon;
        simulator = new Simulator();
        /*
         * new Thread(){@Override //besser: benutze update! public void run() { runAI();
         * }}.start();
         */
    }

    @SuppressWarnings("unused")
    private void simulateABallDrop() {
        System.out.println(ballTable);
        BallTable newBallTable;
        newBallTable = simulator.simulate(ballTable, cannon.getBall(), cannon.getPos());
        int newSocre = simulator.getScore(); // getScore resets the score in Simulator
        System.out.println(newBallTable);
        System.out.println(newSocre);
        // du kannst auch ohne einen Ball ein zu schie�en simulieren: newBallTable =
        // simulator.simulate(ballTable, null, 0);
    }

    int timeAfterLastUpdate = 0;

    public void runAI(int delta) {
        timeAfterLastUpdate += delta;
        if (timeAfterLastUpdate >= 800) { // alle 800ms
            timeAfterLastUpdate = 0;
            // isDownPressed = true; isRightPressed = true; //test
        }
    }

    public boolean isLeftPressed() {
        if (isLeftPressed) {
            isLeftPressed = false; // reset nach abfragen
            return true;
        } else {
            return false;
        }
    }

    public boolean isRightPressed() {
        if (isRightPressed) {
            isRightPressed = false; // reset nach abfragen
            return true;
        } else {
            return false;
        }
    }

    public boolean isDownPressed() {
        if (isDownPressed) {
            isDownPressed = false; // reset nach abfragen
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void update(int delta) {
        runAI(delta);
    }

    @SuppressWarnings("unused")
    private void printInfos() {
        System.out.println(cannon.getPos()); // pos der Kanone 0-8
        if (cannon.getBall() != null) { // Kanone k�nnte leer sein
            System.out.println(cannon.getBall().getNr()); // BallFarbe in der Kanone
        }

        System.out.println(ballTable); // Spielfeld ausgeben
        ballTable.getColumnHeight(0); // Stabelh�he in Spalte 0

        if (!ballTable.isEmpty(0, 0)) { // Feld k�nnte leer sein
            System.out.println(ballTable.getBall(0, 0).getNr()); // Farbe (0-45)
        }
    }

}