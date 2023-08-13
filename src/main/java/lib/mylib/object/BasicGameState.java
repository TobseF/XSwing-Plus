/*
 * @version 0.0 24.07.2008
 * @author Tobse F
 */
package lib.mylib.object;

public abstract class BasicGameState extends org.newdawn.slick.state.BasicGameState implements NumberedWithID {


    public BasicGameState(int id) {
        this.id = id;
    }

    public BasicGameState() {

    }

    private int id = 0;

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }


}
