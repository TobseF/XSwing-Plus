package lib.mylib.properties;

public class SoundConfig {
    /**
     * Music volume (0-100).
     */
    private int musicVolume;
    /**
     * Sound Effects Volume (0-100).
     */
    private int fxVoulme;

    public SoundConfig() {
    }

    public SoundConfig(int musicVolume, int fxVoulme) {
        this.musicVolume = musicVolume;
        this.fxVoulme = fxVoulme;
    }

    public int getMusicVolume() {
        return musicVolume;
    }

    public void setMusicVolume(int musicVolume) {
        this.musicVolume = musicVolume;
    }

    public int getFxVoulme() {
        return fxVoulme;
    }

    public void setFxVoulme(int fxVoulme) {
        this.fxVoulme = fxVoulme;
    }

    @Override
    public String toString() {
        return "SoundConfig [musicVolume=" + musicVolume + ", fxVoulme=" + fxVoulme + "]";
    }


}
