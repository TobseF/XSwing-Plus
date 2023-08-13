/*
 * @version 0.0 03.05.2009
 * @author Tobse F
 */
package xswing;

import lib.mylib.ident.Identable;
import lib.mylib.ident.TimeIdent;
import lib.mylib.object.Resetable;
import lib.mylib.options.DefaultArgs.Args;
import lib.mylib.util.MyOptions;
import org.newdawn.slick.util.Log;
import xswing.events.XSwingEvent;
import xswing.events.XSwingEvent.GameEventType;
import xswing.events.XSwingListener;

/**
 * Counts the number of all played and canceled games and sums the time in ms for these games.
 *
 * @author Tobse
 */
public class LocalXSwingStatistics implements XSwingListener, Resetable {

    private long timeStampGameStarted;
    private long timePlayingCurrentGame;

    /**
     * Number of played games
     */
    private int playedGames;
    /**
     * Number canceled/ aborted games
     */
    private int canceledGames;
    /**
     * Time of all played games together in ms
     */
    private double timePlayedAllGames;

    /**
     * Separates values in the {@link #getScoreInOneHTTPLine()} Server Request
     */
    private static final String SEPERATOR = ";";

    private final Identable identable = new TimeIdent();

    public LocalXSwingStatistics() {
        load();
    }

    @Override
    public void gameEvent(XSwingEvent e) {
        if (e.getGameEventType() == GameEventType.GAME_STARTED) {
            timeStampGameStarted = System.currentTimeMillis();

        } else if (e.getGameEventType() == GameEventType.GAME_PAUSED) {

            timePlayingCurrentGame += (System.currentTimeMillis() - timeStampGameStarted);

        } else if (e.getGameEventType() == GameEventType.GAME_RESUMED) {

            timeStampGameStarted = System.currentTimeMillis();

        } else if (e.getGameEventType() == GameEventType.GAME_OVER
                || e.getGameEventType() == GameEventType.GAME_STOPPED) {

            timePlayingCurrentGame += (System.currentTimeMillis() - timeStampGameStarted);
            timePlayedAllGames += (timePlayingCurrentGame);

            if (e.getGameEventType() == GameEventType.GAME_OVER) {
                playedGames++;
                Log.debug("Played Games: " + playedGames);
            } else if (e.getGameEventType() == GameEventType.GAME_STOPPED) {
                canceledGames++;
                Log.debug("Clanceled Games: " + canceledGames);
            }
            timeStampGameStarted = 0;
            timePlayingCurrentGame = 0;
            save();
        }
    }

    @Override
    public String toString() {
        double min = (timePlayedAllGames / 60 / 1000);
        int sec = (int) ((min % 1) * 60);
        return "playedGames: " + playedGames + " TimeOfAllGames: " + min + ":"
                + sec;
    }

    public String getScoreInOneHTTPLine() {
        return playedGames + SEPERATOR + canceledGames + SEPERATOR
                + (int) timePlayedAllGames
                + SEPERATOR + identable.getIdentity();
    }

    /**
     * @return {@link #timePlayedAllGames}
     */
    public double getTimePlayedAllGames() {
        return timePlayedAllGames;
    }

    /**
     * @return {@link #playedGames}
     */
    public double getPlayedGames() {
        return playedGames;
    }

    /**
     * @return {@link #canceledGames}
     */
    public double getCanceledGames() {
        return canceledGames;
    }

    @Override
    public void reset() {
        timePlayedAllGames = 0;
        playedGames = 0;
        canceledGames = 0;
        save();
    }

    private void load() {
        Log.debug("Loaded Statistic");
        playedGames = (int) MyOptions.getNumber(Args.playedGames);
        canceledGames = (int) MyOptions.getNumber(Args.canceledGames);
        timePlayedAllGames = MyOptions.getNumber(Args.totalTime);
    }

    private void save() {
        Log.debug("Saved Statistic");
        MyOptions.setNumber(Args.playedGames, playedGames);
        MyOptions.setNumber(Args.canceledGames, canceledGames);
        MyOptions.setNumber(Args.totalTime, timePlayedAllGames);
    }

}