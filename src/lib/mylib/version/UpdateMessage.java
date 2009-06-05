/*
 * @version 0.0 04.05.2009
 * @author 	Tobse F
 */
package lib.mylib.version;

import java.awt.*;
import java.net.URI;
import javax.swing.JOptionPane;
import lib.mylib.util.LanguageSelector;


public class UpdateMessage {

	public UpdateMessage(Component component, String updateSite, String websiteToOpen) {
		UpdateChecker updateChecker = new UpdateChecker(updateSite);
		if(updateChecker.isUpdateAvailable()){
			int reply = JOptionPane.showConfirmDialog(component, 
					LanguageSelector.getString("update_available_long"), 
					LanguageSelector.getString("update_available_short"), 
					JOptionPane.INFORMATION_MESSAGE);
			if ( reply == JOptionPane.YES_OPTION ){
				try {
					Desktop.getDesktop().browse(new URI(websiteToOpen));
				} catch (Exception ex) {
					//ex.printStackTrace();
				}
			}
		}
	}

}
