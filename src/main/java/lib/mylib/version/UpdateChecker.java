/*
 * @version 0.0 03.05.2009
 * @author Tobse F
 */
package lib.mylib.version;

import lib.mylib.http.EasyServerRequest;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Tobse Checks for Online updates: compares the version in the local version file with
 * the version line which of a website
 * @see Version#getVersion()
 */
public class UpdateChecker {

    private URL updateSite = null;
    private String onlineVersion = null;

    /**
     * Url which points to one line with the game version </br> eg.:</br>
     * http://myWebsite.com/myGame/getVersion.php</br> v0.123
     *
     * @param updateSite Url which points to one line with the game version
     */
    public UpdateChecker(URL updateSite) {
        this.updateSite = updateSite;
    }

    /**
     * Url which points to one line with the game version </br> eg.:</br>
     * http://myWebsite.com/myGame/getVersion.php</br> v0.123
     *
     * @param updateSite Url which points to one line with the game version
     */
    public UpdateChecker(String updateSite) {
        try {
            this.updateSite = new URL(updateSite);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("updateSite can't be parsed");
        }
    }

    /**
     * compares the version in the local version file with the version line which of a website
     *
     * @return if the local version is not eaual to the online version
     * @see #getOnlineVersion()
     * @see Version#getVersion()
     */
    public boolean isUpdateAvailable() {
        String version = Version.getVersion();
        if (version != null) {
            onlineVersion = getOnlineVersion();
            if (onlineVersion == null) {
                // Do not bother user for internet problems
                return false;
            } else {
                return !version.equals(onlineVersion);
            }
        } else {
            return false;
        }
    }

    /**
     * Reads the version for one time from the {@link #updateSite} online
     *
     * @return the version (complete site) of the {@link #updateSite}
     */
    private String getOnlineVersion() {
        if (onlineVersion == null) {
            onlineVersion = EasyServerRequest.request(updateSite);
            if (onlineVersion != null) {
                onlineVersion = onlineVersion.trim();
            }
        }
        return onlineVersion;
    }

}