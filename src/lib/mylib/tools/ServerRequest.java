/*
 * @version 0.0 04.05.2009
 * @author 	Tobse F
 */
package lib.mylib.tools;

import lib.mylib.http.EasyPostString;


/** Sends a Message to a PHP script via a HTTP POST.</br>
 * Example PHP script which secireves the report and sends an email.
 * <pre>
 * 	$receiver = "name@domain.com";
 * 	$germaneness = "Bug Report";
 * 	$text = $_POST["bugReport"];
 * 	mail($receiver, $germaneness, $text,
 * 	"From: $sender bugs@domain.com");
 * </pre>
 * @author Tobse
 */
public class ServerRequest implements SubmitRequest{
	private final String serverURL;
	private static final String REPORT_KEY = "bugReport";
	
	/**
	 * @param server which should recieve the request. Mostly a php script e.g.
	 * "http://server.com/report.php"
	 */
	public ServerRequest(String server) {
		this.serverURL = server;
	}
	
	@Override
	public boolean request(String request) {
		String response = EasyPostString.send(serverURL,
				REPORT_KEY, request);
		return response != null;
	}
	
}
