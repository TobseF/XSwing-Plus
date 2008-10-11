package xswing.tools;

import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.transition.CrossStateTransition;

final class EmptyState$1
extends CrossStateTransition {
    private final long val$start;

    EmptyState$1(GameState $anonymous0, long l) {
        this.val$start = l;
        super($anonymous0);
    }

    public boolean isComplete() {
        return System.currentTimeMillis() - this.val$start > 2000L;
    }

    public void init(GameState firstState, GameState secondState) {
    }
}
