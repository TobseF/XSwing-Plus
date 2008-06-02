package xswing;

import java.util.ArrayList;
import java.util.List;
import xswing.Resetable;

public class Reset {
    List<Resetable> list = new ArrayList<Resetable>();

    public void add(Resetable resetableObject) {
        this.list.add(resetableObject);
    }

    public void reset() {
        int i = 0;
        while (i < this.list.size()) {
            this.list.get(i).reset();
            ++i;
        }
    }
}
