package Model.Boost;

import java.awt.*;

public abstract class Boost {
    private final int x;
    private final int y;
    private int width;
    private int height;
    private final Image icon;
    private boolean toggled;

    public Boost(int x, int y, int width, int height, Image icon) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.icon = icon;
    }

    public Image getIcon() {
        return icon;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    public void toggle() {
        toggled = true;
    }
    public boolean isToggled() {
        return toggled;
    }
}
