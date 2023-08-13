/*
 * @version 0.0 02.05.2009
 * @author Tobse F
 */
package lib.mylib.http;

import lib.http.MultiPartFormOutputStream;
import org.newdawn.slick.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Utility Class for connecting to a Server and send a <code>String</code> via HTTP POP
 * Protocol and recieving its response.
 *
 * @author Tobse
 */
public final class EasyPostString {

    private EasyPostString() {
    }

    /**
     * Sends a message via HTTP POP Protocol to a server (usually PHP).
     *
     * @param urlPath eg. "http://www.yourdomain.com/upload.php"
     * @param key     eg. "fullname"
     * @param message eg. "Mr Bloomberg"
     * @return Server request
     */
    public static String send(String urlPath, String key, String message) {
        URL url = null;
        try {
            url = new URL(urlPath);
        } catch (MalformedURLException e) {
            Log.warn("Not a valid URL: " + urlPath);
            // e.printStackTrace();
        }
        // create a boundary string
        String boundary = MultiPartFormOutputStream.createBoundary();
        URLConnection urlConn = null;
        try {
            urlConn = MultiPartFormOutputStream.createConnection(url);
        } catch (IOException e) {
            Log.warn("Could not create connection with: " + urlPath);
            // e.printStackTrace();
        }
        urlConn.setRequestProperty("Accept", "*/*");
        urlConn.setRequestProperty("Content-Type", MultiPartFormOutputStream.getContentType(boundary));
        // set some other request headers...
        urlConn.setRequestProperty("Connection", "Keep-Alive");
        urlConn.setRequestProperty("Cache-Control", "no-cache");
        MultiPartFormOutputStream out;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            out = new MultiPartFormOutputStream(urlConn.getOutputStream(), boundary);
            // write a text field element
            out.writeField(key, message);
            out.close();

            // read response from server
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line = "";
            while ((line = in.readLine()) != null) {
                stringBuilder.append(line);
            }
            in.close();
        } catch (IOException e) {
            // e.printStackTrace();
            Log.warn("Could not send to: " + urlPath);
        }
        return stringBuilder.toString();
    }
}
