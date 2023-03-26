package View.Animation;

public class Agent extends Thread{
    private final int FPS;
    private final Animatable FRAME;
    private boolean killed;
    public Agent(int FPS, Animatable frame) {
        this.FPS = FPS;
        FRAME = frame;
    }
    @Override
    public void run() {
        long currentTime = System.nanoTime();
        long sleepTime;
        while(!killed) {
            if (System.nanoTime() - currentTime >= (double) 1000000 / FPS ) {
                currentTime = System.nanoTime();
                FRAME.update();
                sleepTime =  (long) ((1000 / ((double) FPS)) -  (System.nanoTime() - currentTime) / 1000000);
                try {
                    sleep(Math.max(sleepTime, 0));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public void kill() {
        killed = true;
    }
    public void start() {
        killed = false;
        super.start();
    }
}
