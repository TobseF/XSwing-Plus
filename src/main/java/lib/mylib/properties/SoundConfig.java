package lib.mylib.properties;

public class SoundConfig {
    /**
     * Music volume (0-100).
     */
    private int musicVolume;
    /**
     * Sound Effects Volume (0-100).
     */
    private int fxVolume;

    public SoundConfig() {
    }

    public SoundConfig(int musicVolume, int fxVolume) {
        this.musicVolume = musicVolume;
        this.fxVolume = fxVolume;
    }

    public int getMusicVolume() {
        return musicVolume;
    }

    public void setMusicVolume(int musicVolume) {
        this.musicVolume = musicVolume;
    }

    public int getfxVolume() {
        return fxVolume;
    }

    public void setfxVolume(int fxVolume) {
        this.fxVolume = fxVolume;
    }

    @Override
    public String toString() {
        return "SoundConfig [musicVolume=" + musicVolume + ", fxVolume=" + fxVolume + "]";
    }


}
