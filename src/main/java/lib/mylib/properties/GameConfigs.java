package lib.mylib.properties;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class GameConfigs implements Iterable<GameConfig> {
    private Integer configIndex = 0;
    private List<GameConfig> configs = new LinkedList<GameConfig>();

    public void addGameConfig(GameConfig config) {
        configs.add(config);
    }

    @Override
    public String toString() {
        return "GameConfigs [configs=" + configs + "]";
    }

    @Override
    public Iterator<GameConfig> iterator() {
        return configs.iterator();
    }

    public String toJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        // Gson gson = new Gson();
        return gson.toJson(this);
    }

    public List<GameConfig> getConfigs() {
        return configs;
    }

    public void setConfigs(List<GameConfig> configs) {
        this.configs = configs;
    }

    public void setConfigIndex(Integer configIndex) {
        this.configIndex = configIndex;
    }

    public Integer getConfigIndex() {
        return configIndex;
    }

    public GameConfig getSelectedConfig() {
        return configs == null || configs.isEmpty() ? null : configs.get(configIndex);
    }

}
