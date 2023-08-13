package lib.mylib.properties;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class GameConfig implements Iterable<ObjectConfigSet> {
    private String name;
    private String description;
    private boolean debugMode;
    private int configSetIndex;
    private DisplayConfig displayConfig;
    private SoundConfig soundConfig;
    private Integer minimumLogicUpdateInterval;
    private Integer maximalLogicUpdateInterval;
    private List<String> musicPlayList;
    private List<ObjectConfigSet> configSets = new LinkedList<ObjectConfigSet>();

    public GameConfig() {
    }

    public GameConfig(String name, String description) {
        this(description);
        this.description = description;
    }

    public GameConfig(String name) {
        this.name = name;
    }

    @Override
    public Iterator<ObjectConfigSet> iterator() {
        return configSets.iterator();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ObjectConfigSet> getConfigSets() {
        return configSets;
    }

    public void setConfigSets(List<ObjectConfigSet> configSets) {
        this.configSets = configSets;
    }

    public void addObjectConfigSet(ObjectConfigSet configSet) {
        configSets.add(configSet);
    }

    public DisplayConfig getDisplayConfig() {
        return displayConfig;
    }

    public void setDisplayConfig(DisplayConfig displayConfig) {
        this.displayConfig = displayConfig;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public void setMinimumLogicUpdateInterval(int minimumLogicUpdateInterval) {
        this.minimumLogicUpdateInterval = minimumLogicUpdateInterval;
    }

    public int getMinimumLogicUpdateInterval() {
        return minimumLogicUpdateInterval;
    }

    public boolean isSetMaximalLogicUpdateInterval() {
        return maximalLogicUpdateInterval == null;
    }

    public boolean isSetMinimumLogicUpdateInterval() {
        return minimumLogicUpdateInterval == null;
    }

    public List<String> getMusicPlayList() {
        return musicPlayList;
    }

    public void setMusicPlayList(List<String> musicPlayList) {
        this.musicPlayList = musicPlayList;
    }

    public SoundConfig getSoundConfig() {
        return soundConfig;
    }

    public void setSoundConfig(SoundConfig soundConfig) {
        this.soundConfig = soundConfig;
    }

    public void setMinimumLogicUpdateInterval(Integer minimumLogicUpdateInterval) {
        this.minimumLogicUpdateInterval = minimumLogicUpdateInterval;
    }

    public void setMaximalLogicUpdateInterval(Integer maximalLogicUpdateInterval) {
        this.maximalLogicUpdateInterval = maximalLogicUpdateInterval;
    }

    public void setConfigSetIndex(int configSetIndex) {
        this.configSetIndex = configSetIndex;
    }

    public int getConfigSetIndex() {
        return configSetIndex;
    }

    public ObjectConfigSet getSelctedObjectConfigSet() {
        return configSets == null || configSets.isEmpty() ? null : configSets.get(configSetIndex);
    }

    public boolean isSetMusicPlayList() {
        return musicPlayList != null;
    }

    @Override
    public String toString() {
        return "GameConfig [name=" + name + ", description=" + description + ", debugMode=" + debugMode + ", displayConfig="
                + displayConfig + ", soundConfig=" + soundConfig + ", minimumLogicUpdateInterval=" + minimumLogicUpdateInterval
                + ", maximalLogicUpdateInterval=" + maximalLogicUpdateInterval + ", configSetIndex=" + configSetIndex + ", configSets="
                + configSets + ", musicPlayList=" + musicPlayList + "]";
    }

}
