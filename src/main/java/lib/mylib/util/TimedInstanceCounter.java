/*
 * @version 0.0 19.06.2011
 * @author 	Tobse F
 */
package lib.mylib.util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class TimedInstanceCounter {

    private final List<TimeStamp> stamps = new LinkedList<TimeStamp>();

    public TimedInstanceCounter() {

    }

    public int count() {
        for (Iterator<TimeStamp> i = stamps.iterator(); i.hasNext(); ) {
            TimeStamp element = i.next();
            final long current = System.currentTimeMillis();
            if (element.isOver(current)) {
                i.remove();
            }
        }
        return stamps.size();
    }

    public void add(long time) {
        stamps.add(new TimeStamp(System.currentTimeMillis(), time));
    }

    public static class TimeStamp {
        private final long end;

        public TimeStamp(long start, long time) {
            this.end = start + time;
        }

        public boolean isOver(long timeStamp) {
            return timeStamp > end;
        }

    }

    public void clear() {
        stamps.clear();
    }

}
