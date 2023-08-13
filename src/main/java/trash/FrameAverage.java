/*
 * @version 0.0 21.11.2008
 * @author Tobse F
 */
package trash;

import lib.mylib.object.Resetable;
import lib.mylib.object.Updateable;
import org.newdawn.slick.GameContainer;

import java.util.LinkedList;
import java.util.List;

public class FrameAverage implements Resetable, Updateable {

    private final GameContainer container;
    public static final int FRAMES_TO_COUNT = 100;
    private final List<Integer> revordedFrameRates = new LinkedList<Integer>();

    public FrameAverage(GameContainer container) {
        this.container = container;
    }

    public void startRecording() {

    }

    @Override
    public void reset() {
        revordedFrameRates.clear();
    }

    @Override
    public void update(int delta) {
        int fps = container.getFPS();
        // if(!revordedFrameRates.contains(fps)){
        revordedFrameRates.add(fps);
        if (revordedFrameRates.size() > FRAMES_TO_COUNT) {
            revordedFrameRates.remove(0);
        }
        // }
    }

    public int getAverageFrameRate() {
        int averageFrameRate = 0;
        for (Integer i : revordedFrameRates) {
            averageFrameRate += i;
        }
        return averageFrameRate / revordedFrameRates.size();
    }
}
