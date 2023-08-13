package tools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import xswing.properties.DefaultGameConfigs;
import xswing.properties.XSGameConfigs;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class RestoreDefaultGameConfig {
    public RestoreDefaultGameConfig() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            Writer writer = new FileWriter(XSGameConfigs.OPTION_FILE_NAME);
            gson.toJson(new DefaultGameConfigs(), writer);
            writer.close();
            System.out.println("Successfully written new config file: " + XSGameConfigs.OPTION_FILE_NAME);
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
        new RestoreDefaultGameConfig();
    }

}
