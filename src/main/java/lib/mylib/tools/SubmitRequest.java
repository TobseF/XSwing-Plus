/*
 * @version 0.0 04.05.2009
 * @author Tobse F
 */
package lib.mylib.tools;

/**
 * Generic request for telling something to a server
 *
 * @author Tobse
 */
public interface SubmitRequest {

    /**
     * Sends a message to a server
     *
     * @param message to send
     * @return <code>true</code> if the request was sucsessful
     */
    boolean request(String message);

}
