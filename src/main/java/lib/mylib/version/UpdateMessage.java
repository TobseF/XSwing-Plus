/*
 * @version 0.0 04.05.2009
 * @author Tobse F
 */
package lib.mylib.version;

import lib.mylib.options.DefaultArgs.Args;
import lib.mylib.util.LanguageSelector;
import lib.mylib.util.MyOptions;
import org.newdawn.slick.util.Log;

import javax.swing.*;
import java.awt.*;
import java.net.URI;

/**
 * @author Tobse If the {@link Args#checkForUpdates} in {@link MyOptions} is <code>true</code>,
 * the {@link UpdateChecker} will shearch for a new version online an open a Dialog to
 * open a website if ther's an update available.
 * @see @see Version#getVersion()
 */
public class UpdateMessage {

    /**
     * If the {@link Args#checkForUpdates} in {@link MyOptions} is <code>true</code>, the
     * {@link UpdateChecker} will shearch for a new version online an open a Dialog to open a
     * website if ther's an update available (it compares the 'version' file in the main src)
     *
     * @param component     which will be locked while the update dialog is visible, can be
     *                      <code>null</code>
     * @param updateSite    website where the online version string stands in one line
     * @param websiteToOpen website where the user can find an new version
     * @see Version#getVersion()
     */
    public UpdateMessage(Component component, String updateSite, String websiteToOpen) {
        boolean checkForUpdates = MyOptions.getBoolean(Args.checkForUpdates, true);
        if (checkForUpdates) {
            UpdateChecker updateChecker = new UpdateChecker(updateSite);
            if (updateChecker.isUpdateAvailable()) {
                Log.info("New update available!");
                int reply = JOptionPane.showConfirmDialog(component, LanguageSelector
                        .getString("update_available_long"), LanguageSelector
                        .getString("update_available_short"), JOptionPane.INFORMATION_MESSAGE);
                if (reply == JOptionPane.YES_OPTION) {
                    try {
                        Desktop.getDesktop().browse(new URI(websiteToOpen));
                    } catch (Exception ex) {
                        Log.warn("Update webSite could not be opened");
                    }
                }
            }
        }
    }

}
