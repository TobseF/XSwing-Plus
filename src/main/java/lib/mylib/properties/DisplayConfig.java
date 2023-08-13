package lib.mylib.properties;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class DisplayConfig implements Iterable<Resolution> {
    /**
     * Description of this {@link DisplayConfig} (e.g. Desktop Resolutions).
     */
    private String name;
    private boolean fullscreen;
    private Integer resolutionIndex = 0;
    private List<Resolution> resolutions = new LinkedList<Resolution>();

    public DisplayConfig() {
    }

    public List<Resolution> getResolutions() {
        return resolutions;
    }

    public void setResolutions(List<Resolution> resolutions) {
        this.resolutions = resolutions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFullscreen() {
        return fullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }

    public void addResolution(Resolution resolution) {
        resolutions.add(resolution);
    }

    public Integer getResolutionIndex() {
        return resolutionIndex;
    }

    public void setResolutionIndex(Integer resolutionIndex) {
        this.resolutionIndex = resolutionIndex;
    }

    public Resolution getSelectedResolution() {
        return resolutions == null || resolutions.isEmpty() ? null : resolutions.get(resolutionIndex);
    }

    @Override
    public Iterator<Resolution> iterator() {
        return resolutions.iterator();
    }

    @Override
    public String toString() {
        return "PisplayConfig [resolutions=" + resolutions + ", name=" + name + ", fullscreen=" + fullscreen + ", resolutionIndex="
                + resolutionIndex + "]";
    }

}
