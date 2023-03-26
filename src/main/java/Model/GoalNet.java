package Model;

public class GoalNet {
    private int x;
    private int y;
    private double width;
    private boolean enlarged;

    public GoalNet(int x, int y, int width) {
        this.x = x;
        this.y = y;
        this.width = width;
    }
    public boolean isEnlarged() {
        return enlarged;
    }
    public void setEnlarged(boolean enlarged) {
        if (!this.enlarged && enlarged) {
            width *= 2;
        }
        if (this.enlarged && !enlarged) {
            width /= 2;
        }
        this.enlarged = enlarged;
    }
    public int getStartX() {
        return x - (int)(width / 2);
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getWidth() {
        return (int)width;
    }
    public boolean inGoal (MovingCircle ball) {
        if (x - width / 2 <= ball.getX() && ball.getX() <= x + width / 2) {
            return true;
        }
        return false;
    }
}
