/*
 * @version 0.0 03.05.2009
 * @author 	Tobse F
 */
package lib.mylib.version;

import java.net.*;
import lib.mylib.http.EasyServerRequest;

public class UpdateChecker {
	private URL updateSite;
	private String onlineVersion = null;
	
	public UpdateChecker(URL updateSite) {
		this.updateSite = updateSite;
	}
	
	public UpdateChecker(String updateSite) {
		try {
			this.updateSite = new URL(updateSite);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("updateSite can't be parsed");
		}
	}
	
	public boolean isUpdateAvailable(){
		String version = Version.getVersion();
		if(version != null){
			onlineVersion = getOnlineVersion();
			if(onlineVersion != null){
				return !version.equals(onlineVersion);
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	public String getOnlineVersion(){
		if(onlineVersion == null){
			onlineVersion = EasyServerRequest.request(updateSite);
		}
		return  onlineVersion;
	}
	
	public String gertLocalVersion(){
		return Version.getVersion();
	}

}