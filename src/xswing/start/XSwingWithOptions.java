/*
 * @version 0.0 21.02.2009
 * @author 	Tobse F
 */
package xswing.start;

import lib.mylib.tools.OptionStarter;

public class XSwingWithOptions {
	public XSwingWithOptions(String[] args) {
		new OptionStarter(XSwing.class, args);
	}

	public static void main(String[] args) {
		new XSwingWithOptions(args);
	}
}