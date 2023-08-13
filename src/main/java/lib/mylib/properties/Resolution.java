package lib.mylib.properties;

public class Resolution {
    /**
     * Display width in px
     */
    private int width;
    /**
     * Display height in px
     */
    private int height;

    /**
     * Description of this {@link Resolution} (e.g. FullHD)
     */
    private String name;

    public Resolution() {
    }

    public Resolution(int height, int width, String name) {
        this(height, width);
        this.name = name;
    }

    public Resolution(int width, int height) {
        this.height = height;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Resolution [height=" + height + ", width=" + width + ", name=" + name + "]";
    }

}
