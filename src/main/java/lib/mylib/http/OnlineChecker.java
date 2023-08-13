/*
 * @version 0.0 03.05.2009
 * @author Tobse F
 */
package lib.mylib.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Utility Class for pings and checking if a server is reachable.
 *
 * @author Tobse
 */
public final class OnlineChecker {

    /**
     * Server which is used for online and ping check, default is "http://www.google.com/"
     */
    private static final String DEFAULT_SERVER_TO_CHECK = "http://www.google.com/";

    private OnlineChecker() {
    }

    /**
     * Checks if the {@link #DEFAULT_SERVER_TO_CHECK} is reachable
     *
     * @return true if the {@link #DEFAULT_SERVER_TO_CHECK} is reachable
     */
    public static boolean isOnline() {
        URL url = null;
        try {
            url = new URL(DEFAULT_SERVER_TO_CHECK);
        } catch (MalformedURLException e) {
            return false;
        }
        return isOnline(url);
    }

    /**
     * Checks if the given <code>URL</code> from the given <code>String</code> is reachable
     *
     * @param url url <code>String</code> to check eg. "http://www.google.com/"
     * @return true if the url is reachable
     */
    public static boolean isOnline(String urlPath) {
        URL url = null;
        try {
            url = new URL(urlPath);
        } catch (MalformedURLException e) {
            return false;
        }
        return isOnline(url);
    }

    /**
     * Checks if the given URL is reachable
     *
     * @param url URL toch check eg. "http://www.google.com/"
     * @return true if the URL is reachable
     */
    public static boolean isOnline(URL url) {
        InputStream is = null;
        boolean isOnline = false;
        try {
            is = url.openStream();
            isOnline = is != null;
            // System.out.println(new Scanner(is).useDelimiter("\\Z").next());
        } catch (Exception e) {
            // e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
        return isOnline;
    }

    /**
     * Measures the time for <code>pings</code> x online checks. If <code>pings</code> > 1 the
     * average time (in ms) will be returned.
     *
     * @param url   to check
     * @param pings number of ping measurements
     * @return time for all (<code>pings</code>) measurements in ms. 9999 if not reachable
     * @throws IllegalArgumentException if <code>pings</code> <= 0
     */
    public static int ping(URL url, int pings) {
        if (pings <= 0) {
            throw new IllegalArgumentException("ping count can't be <= 0");
        }
        long timeStamp;
        int timeTotal = 0;
        int[] pingsTimeSpamps = new int[pings];
        for (int i = 0; i < pings; i++) {
            timeStamp = System.currentTimeMillis();
            if ((url == null && !isOnline()) || isOnline(url)) {
                pingsTimeSpamps[i] = 9999;
            } else {
                pingsTimeSpamps[i] = (int) (System.currentTimeMillis() - timeStamp);
            }
            timeTotal += pingsTimeSpamps[i];
        }
        return timeTotal / pings;
    }

    /**
     * Measures the time for one online checks in ms.
     *
     * @param url <code>String</code> to check
     * @return time for one measurement in ms. 9999 if not reachable
     */
    public static int ping(String urlPath) {
        URL url = null;
        try {
            url = new URL(urlPath);
        } catch (MalformedURLException e) {
            return 9999;
        }
        return ping(url, 1);
    }

    /**
     * Measures the time for one online checks in ms.
     *
     * @param url to check
     * @return time for one measurements in ms. 9999 if not reachable
     */
    public static int ping(URL url) {
        return ping(url, 1);
    }

    /**
     * Measures the time for one online check of the {@link #DEFAULT_SERVER_TO_CHECK} in ms.
     *
     * @return time for one measurement of the {@link #DEFAULT_SERVER_TO_CHECK} in ms. 9999 if
     * not reachable
     */
    public static int ping() {
        return ping(null, 1);
    }

    /**
     * Measures the time for one online check of the {@link #DEFAULT_SERVER_TO_CHECK} in ms. If
     * pings > 1 the average time (in ms) will be returned.
     *
     * @param pings number of ping measurements
     * @return time for one measurement of the {@link #DEFAULT_SERVER_TO_CHECK} in ms. 9999 if
     * not reachable
     * @throws IllegalArgumentException if <code>pings</code> <= 0
     */
    public static int ping(int pings) {
        return ping(null, pings);
    }

}
