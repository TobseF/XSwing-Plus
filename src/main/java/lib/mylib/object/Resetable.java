/*
 * @version 0.0 30.05.2008
 * @author Tobse F
 */
package lib.mylib.object;

/**
 * To reset objects to their init BallState. Good for game restart.
 *
 * @author Tobse
 */
public interface Resetable {

    /**
     * Resets the Object to the default values
     */
    void reset();
}