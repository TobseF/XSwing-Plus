/*
 * @version 0.0 21.02.2009
 * @author Tobse F
 */
package xswing.start;

import lib.mylib.tools.OptionStarter;
import lib.mylib.version.*;

/** Starts XSwing Plus with an updatecheck and an option dialog on start
 * @author Tobse
 */
public class XSwingWithOptions {

	public XSwingWithOptions(String[] args) {
		new UpdateMessage(
				new OptionStarter(XSwing.class, args),
				"http://xswing.net/version.php", "http://xswing.net");
	}

	public static void main(String[] args) {
		new XSwingWithOptions(args);
	}
}