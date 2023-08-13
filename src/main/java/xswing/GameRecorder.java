/*
 * @version 0.0 19.04.2009
 * @author Tobse F
 */
package xswing;

import lib.mylib.object.Resetable;
import lib.mylib.object.Updateable;
import xswing.events.XSwingEvent;
import xswing.events.XSwingListener;

import java.util.LinkedList;

public class GameRecorder implements XSwingListener, Updateable, Resetable {

    private long timeStamp = 0;
    private int pointer = 0;
    // private List<XSwingEvent> gameEvents = new ArrayList<XSwingEvent>();
    // private HashMap<Long, XSwingEvent> timeLine = new HashMap<Long, XSwingEvent>();
    LinkedList<TimedXSwingEvent> timeLine = new LinkedList<TimedXSwingEvent>();
    private XSwingListener makro;
    /**
     * If the game is in recording mode (true) or in playing mode (false)
     */
    private boolean recording = false;
    /**
     * Pause for playing and recording
     */
    private boolean pause = false;

    // LinkedHashMap<Long, XSwingEvent> timeLineTest = new LinkedHashMap<Long, XSwingEvent>();

    @Override
    public void gameEvent(XSwingEvent e) {
        if (recording && !pause) {
            timeLine.add(new TimedXSwingEvent(e, timeStamp));
            // System.out.println(timeLine.size());
        }
    }

    @Override
    public void update(int delta) {
        if (!pause) {
            if (!recording) {
                if (makro != null) {
                    // makro.gameEvent(timeLine.get(timeStamp));
                    TimedXSwingEvent event = timeLine.get(pointer);
                    if (event.getTimeStamp() <= timeStamp) {
                        makro.gameEvent(event.getSwingEvent());
                        pointer++;
                    }
                }
            } else {

            }
        }
    }

    public void play() {
        recording = true;
        timeStamp = System.currentTimeMillis();
        pause = false;
        timeStamp = 0;
    }

    public boolean isPause() {
        return pause;
    }

    public boolean isRecording() {
        return recording;
    }

    public void setRecording(boolean recording) {
        this.recording = recording;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public void setXSwingListener(XSwingListener listener) {
        makro = listener;
    }

    private class TimedXSwingEvent {

        private final XSwingEvent swingEvent;
        private final long timeStamp;

        public TimedXSwingEvent(XSwingEvent swingEvent, long timeStamp) {
            this.swingEvent = swingEvent;
            this.timeStamp = timeStamp;
        }

        public long getTimeStamp() {
            return timeStamp;
        }

        public XSwingEvent getSwingEvent() {
            return swingEvent;
        }
    }

    @Override
    public void reset() {
        pointer = 0;
        timeStamp = 0;
        timeLine.clear();
        pause = false;
        recording = false;
    }

}