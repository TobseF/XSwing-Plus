/*
 * @version 0.0 03.05.2009
 * @author Tobse F
 */
package lib.mylib.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Utility Class for connecting to a Server by an <code>URL</code> and recieving its response.
 *
 * @author Tobse
 */
public final class EasyServerRequest {

    private EasyServerRequest() {
    }

    /**
     * Connects to the given <code>URL</code> and returns the request. Request is
     * <code>null</code> if the <code>URL</code> is not reachable.
     *
     * @param url to connect to
     * @return Server response. It is null</code> if the <code>URL</code> is not reachable.
     */
    public static String request(URL url) {
        InputStream is = null;
        String request = null;
        try {
            is = url.openStream();
            request = "";
            request = new Scanner(is).useDelimiter("\\Z").next();
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
        return request;
    }

    /**
     * Connects to the given url <code>String</code> and returns the request. Request is
     * <code>null</code> if the url is not reachable. </br> url example:
     * "http://www.google.com/".
     *
     * @param url to connect to
     * @return Server response. It is null</code> if the <code>URL</code> is not reachable.
     */
    public static String request(String urlPath) {
        URL url = null;
        try {
            url = new URL(urlPath);
        } catch (MalformedURLException e) {
            return null;
        }
        return request(url);
    }

}
