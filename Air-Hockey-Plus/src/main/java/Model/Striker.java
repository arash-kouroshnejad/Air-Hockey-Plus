package Model;

import Model.MovingCircle;

import java.awt.*;

public class Striker extends MovingCircle {
    private int[] bounds = new int[4];
    public Striker(int x, int y, double speedX, double speedY, int radius, Color color, int[] bounds) {
        super(x, y, speedX, speedY, radius, color);
        for (int i =0;i<4;i++) {
            this.bounds[i] = bounds[i];
        }
    }

    public void setBounds(int[] bounds) {
        for(int i =0;i<4;i++) {
            this.bounds[i] = bounds[i];
        }
    }

    public void move() {
        if (bounds[0] <= x + speedX && x + speedX <= bounds[1]) {
            x += speedX;
        }
        if (bounds[2] <= y + speedY && y + speedY <= bounds[3]) {
            y += speedY;
        }
    }
}
