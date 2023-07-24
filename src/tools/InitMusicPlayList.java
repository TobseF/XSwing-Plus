package tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;

import xswing.properties.XSGameConfigs;
import lib.mylib.options.Paths;
import lib.mylib.properties.GameConfigs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class InitMusicPlayList {
	public InitMusicPlayList() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		GameConfigs config;
		try {
			config = gson.fromJson(new FileReader(XSGameConfigs.OPTION_FILE_NAME), GameConfigs.class);
//			System.out.println(gson.toJson(config));
			File folder = new File(Paths.MUSIC_DIR);
			File[] listOfFiles = folder.listFiles();
			List<String> musicFiles = new LinkedList<String>();
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					String filename = listOfFiles[i].getName();
					if (filename.endsWith(".ogg")) {
						musicFiles.add(filename);
					}
				}
			}
			System.out.println("found music files: "+musicFiles);
		    Writer writer = new FileWriter(XSGameConfigs.OPTION_FILE_NAME);

			config.getSelectedConfig().setMusicPlayList(musicFiles);
			gson.toJson(config,writer);
			writer.close();
			System.out.println("Successfully written new music files!");
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new InitMusicPlayList();
	}

}
