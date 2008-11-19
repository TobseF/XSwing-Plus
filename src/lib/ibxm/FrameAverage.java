/*
 * @version 0.0 21.11.2008
 * @author Tobse F
 */
package lib.ibxm;

import java.util.ArrayList;
import java.util.List;

import lib.mylib.Resetable;
import lib.mylib.Updateable;

import org.newdawn.slick.GameContainer;

public class FrameAverage implements Resetable, Updateable {
	private GameContainer container;
	private List<Integer> revordedFrameRates = new ArrayList<Integer>();

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
		if (revordedFrameRates.size() > 100) {
			revordedFrameRates.remove(0);
		}
		// }
	}

	public int getAverageFrameRate() {
		int averageFrameRate = 0;
		if (revordedFrameRates.size() > 0) {
			for (int i = 0; i < revordedFrameRates.size(); i++) {
				averageFrameRate += revordedFrameRates.get(i);
			}
			averageFrameRate = averageFrameRate / revordedFrameRates.size();
		}
		return averageFrameRate;
	}
}
