package xswing.properties;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import lib.mylib.properties.GameConfig;
import lib.mylib.properties.GameConfigs;

public class XSGameConfigs {

	private static GameConfigs configs = new DefaultGameConfigs();

	public static GameConfig getConfig()  {
		Gson gson = new Gson();
		GameConfigs config;
		try {
			config = gson.fromJson(new FileReader("config.json"), GameConfigs.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return configs.getSelectedConfig();
		} catch (JsonIOException e) {
			e.printStackTrace();
			return configs.getSelectedConfig();
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
			return configs.getSelectedConfig();
		}
		return config.getSelectedConfig();
	}

}