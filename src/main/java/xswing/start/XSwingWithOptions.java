/*
 * @version 0.0 21.02.2009
 * @author Tobse F
 */
package xswing.start;

import lib.mylib.http.OnlineChecker;
import lib.mylib.options.DefaultArgs.Args;
import lib.mylib.tools.*;
import lib.mylib.util.*;
import lib.mylib.version.UpdateMessage;
import org.newdawn.slick.util.Log;
import static xswing.properties.XSGameConfigs.getConfig;

/**
 * Starts XSwing Plus with an updatecheck and an option dialog on start
 * 
 * @author Tobse
 */
public class XSwingWithOptions {

	private OptionStarter starter;

	public XSwingWithOptions(String[] args) {
		MyPropertys.setCheckForDefaults(true);

		starter = new OptionStarter(XSwing.class, args);

		if (MyOptions.getBoolean(Args.firstStart, true)) {
			Log.info("Game was started the first time");
			openOnlineChecker();
		}
		MyOptions.setBoolean(Args.firstStart, false);

		
		new UpdateMessage(starter, "http://xswing.net/version.php", "http://xswing.net");
		
	}

	/**
	 * Opens a Window which helps to connect to the internet (Useful to config a firewall)
	 */
	private void openOnlineChecker() {
		if (!OnlineChecker.isOnline()) {
			Log.info("No online connection available!");
			new OnlineCheckWindow();
		}
		
	}

	public static void main(String[] args) {
		try {
			new XSwingWithOptions(args);
		} catch (Exception e) {
			e.printStackTrace();
			new ErrorReporter(e, new ServerRequest(XSwing.POST_BUG_URL));
		}
	}
}