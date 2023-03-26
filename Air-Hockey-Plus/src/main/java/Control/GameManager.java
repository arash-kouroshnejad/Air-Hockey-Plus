package Control;

import Model.Boost.Boost;
import Model.Boost.FireBall;
import Model.Boost.GlassWall;
import Model.Boost.GoalNet;
import Model.Match;
import Model.MovingCircle;
import Model.Striker;
import View.Animation.Agent;
import View.Animation.Animatable;
import View.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class GameManager implements Animatable {
    private MainMenu mainMenu;
    private GameFrame gameFrame;
    private Match match;
    private Agent mainThread;
    private boolean goalLimited;
    private boolean timeLimited;
    private int timeLimit;
    private Thread timeThread;
    private Thread boostThread;
    private int goalLimit;
    private int margin;
    private Boost boost;
    private final int strikerSpeed = 6;
    private final int ballSpeed = 4;
    private MovingCircle ball;
    private MovingCircle player1;
    private Model.GoalNet player1Goal;
    private long startTime;
    private MovingCircle player2;
    private Model.GoalNet player2Goal;
    private int lastStriker;
    private boolean FireBall;
    private boolean GlassWall;
    private boolean GoalNet;
    private int lastGoalFor;
    private int fireBallOwner;
    private int panelWidth;
    private int panelHeight;
    private boolean ignorePlayer1;
    private boolean ignorePlayer2;

    public void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    public void getPreferences() {
        PreferenceSelection ps = new PreferenceSelection(this);
    }

    public void newGame(PreferenceSelection selection) {
        startTime = System.nanoTime();
        goalLimited = selection.getGoalLimitedCheckBox().isSelected();
        timeLimited = selection.getTimeLimitedCheckBox().isSelected();
        String input = selection.getLimitTextField().getText();
        if (timeLimited) {
            timeLimit = Integer.parseInt(input);
            killGameAt(timeLimit);
        }
        input = selection.getLimitTextField1().getText();
        if (goalLimited) {
            goalLimit = Integer.parseInt(input);
        }
        input = selection.getMargin().getText();
        if (!input.equals("")) {
            margin = Integer.parseInt(input);
        }
        Color a = IO.parseColor((String) selection.getComboBox1().getSelectedItem());
        Color b = IO.parseColor((String) selection.getComboBox2().getSelectedItem());
        gameFrame = new GameFrame(this);
        panelWidth = gameFrame.getWidth();
        panelHeight = gameFrame.getHeight();
        init();
        ball.setSpeedX(Math.random() * 4 - 2);
        ball.setSpeedY(Math.random() * 4 - 2);
        player1.setColor(a);
        player2.setColor(b);
        ball.setColor(Color.GREEN);
        player1Goal = new Model.GoalNet(panelWidth / 2, panelHeight - panelWidth / 5, panelWidth / 4);
        player2Goal = new Model.GoalNet(panelWidth / 2, -panelWidth / 8, panelWidth / 4);
        match = new Match(selection.getPlayer1Name(), selection.getPlayer2Name(), goalLimited, goalLimit, margin, timeLimited, timeLimit, a, b);
        mainThread = new Agent(60, this);
        mainThread.start();
        insertBoost();
    }

    public void player1Scored() {
        reset();
        match.setScore1(match.getScore1() + 1);
        checkState();
    }

    public String getPlayer1Name() {
        return match.getPlayer1Name();
    }

    public String getPlayer2Name() {
        return match.getPlayer2Name();
    }

    public void player2Scored() {
        reset();
        match.setScore2(match.getScore2() + 1);
        checkState();
    }

    public void saveGame() {
        mainThread.kill();
        gameFrame.setEnabled(false);
        gameFrame.setVisible(false);
        match.setTimeElapsed(System.nanoTime() - getStartTime());
        IO.saveGame(match);
        showMenu();
        if (timeThread != null && timeThread.isAlive()) {
            try {
                timeThread.stop();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int getPlayer1Score() {
        return match.getScore1();
    }

    public int getPlayer2Score() {
        return match.getScore2();
    }

    public void killGameAt(int mins) {
        timeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep((long) mins * 60 * 1000);
                    match.finished();
                    saveGame();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        timeThread.start();
    }

    private void checkState() {
        if (goalLimited) {
            if (Math.abs(match.getScore1() - match.getScore2()) >= margin) {
                if (match.getScore1() >= goalLimit) {
                    match.finished();
                    saveGame();
                }
                if (match.getScore2() >= goalLimit) {
                    match.finished();
                    saveGame();
                }
            }
        }
    }

    private void insertBoost() {
        boostThread = new boostThread();
        boostThread.start();
    }

    public void removeBoost() {
        try {
            boost = null;
            boostThread.stop();
            boostThread.join();
            boostThread = new boostThread();
            boostThread.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void toggleBoost() {
        boost.toggle();
    }

    private Boost getRandomBoost() {
        int rand = (int) (Math.random() * 3);
        String name;
        try {
            return switch (rand) {
                case 0 ->
                        new FireBall((int) (Math.random() * 520), (int) (Math.random() * 720), 40, 40, ImageIO.read(new File("./src/main/resources/Fireball.png")));
                case 1 ->
                        new GlassWall((int) (Math.random() * 520), (int) (Math.random() * 720), 40, 40, ImageIO.read(new File("./src/main/resources/GlassWall.png")));
                case 2 ->
                        new GoalNet((int) (Math.random() * 520), (int) (Math.random() * 720), 40, 40, ImageIO.read(new File("./src/main/resources/Goal.png")));
                default -> null;
            };
        } catch (Exception e) {
            System.out.println("Error Reading Images!");
        }
        return null;
    }

    public Boost getBoost() {
        return boost;
    }

    public void showMenu() {
        mainMenu.setVisible(true);
    }

    public void showSaves() {
        ArrayList<MatchSave> output = new ArrayList<>();
        ArrayList<Match> allMatches = IO.getGames();
        for (Match match : allMatches) {
            output.add(new MatchSave(match));
        }
        PreviousGames prevGameSaves = new PreviousGames(output, this);
    }

    public void init() {
        ball = new MovingCircle(panelWidth / 2 - (panelWidth / 40), panelHeight / 2 - panelWidth / 10 + (lastGoalFor == 2 ? 150 : (lastGoalFor == 0 ? 0 : -150)), 0, 0, panelWidth / 40, null);
        int[] bounds = new int[]{0, panelWidth - panelWidth / 20, panelHeight / 2 - panelWidth / 10, panelHeight - panelWidth / 10};
        player1 = new Striker(panelWidth / 2 - panelWidth / 40, panelHeight - panelWidth / 10, 0, 0, panelWidth / 30, null, bounds);
        bounds = new int[]{0, panelWidth - panelWidth / 20, 0, panelHeight / 2 - panelWidth / 10};
        player2 = new Striker(panelWidth / 2 - panelWidth / 40, 0, 0, 0, panelWidth / 30, null, bounds);
    }

    public void reset() {
        ignorePlayer1 = ignorePlayer2 = false;
        removeBoost();
        FireBall = false;
        GoalNet = false;
        GlassWall = false;
        if (player1Goal.isEnlarged()) {
            player1Goal.setEnlarged(false);
        }
        if (player2Goal.isEnlarged()) {
            player2Goal.setEnlarged(false);
        }
        lastStriker = 0;
        Color first = player1.getColor();
        Color second = player2.getColor();
        Color b = ball.getColor();
        init();
        ball.setColor(b);
        player1.setColor(first);
        player2.setColor(second);
    }

    public void update() {
        gameFrame.repaint();
        gameFrame.revalidate();
        checkCollisions();
        ball.move();
        player1.move();
        player2.move();
    }

    private void checkCollisions() {
        if (boost != null && !boost.isToggled()) {
            if (2 * Math.sqrt(Math.pow(ball.getX() - boost.getX(), 2) + Math.pow(ball.getY() - boost.getY(), 2)) <= ball.getRadius() + boost.getWidth()) {
                if (boost instanceof Model.Boost.FireBall) {
                    FireBall = true;
                }
                if (boost instanceof Model.Boost.GlassWall) {
                    GlassWall = true;
                }
                if (boost instanceof Model.Boost.GoalNet) {
                    if (lastStriker == 1) {
                        player2Goal.setEnlarged(true);
                    } else if (lastStriker == 2) {
                        player1Goal.setEnlarged(true);
                    }
                    GoalNet = true;
                }
                toggleBoost();
                fireBallOwner = lastStriker;
            }
        }
        if (ball.getX() >= gameFrame.getWidth() - 2 * ball.getRadius()) {
            if (!GlassWall) {
                ball.setSpeedX(-(FireBall ? 2 : 1) * ballSpeed);
            } else {
                ball.setX(0);
            }
        }
        if (ball.getX() <= 0) {
            if (!GlassWall) {
                ball.setSpeedX((FireBall ? 2 : 1) * ballSpeed);
            } else {
                ball.setX(gameFrame.getWidth() - 2 * ball.getRadius());
            }
        }
        if (ball.getY() <= 0) {
            if (player2Goal.inGoal(ball)) {
                lastGoalFor = 1;
                player1Scored();
            }
            ball.setSpeedY((FireBall ? 2 : 1) * ballSpeed);
        }
        if (ball.getY() >= gameFrame.getHeight() - 3 * ball.getRadius()) {
            if (player1Goal.inGoal(ball)) {
                lastGoalFor = 2;
                player2Scored();
            }
            ball.setSpeedY(-(FireBall ? 2 : 1) * ballSpeed);
        }
        if (2 * Math.sqrt(Math.pow(ball.getX() - player1.getX(), 2) + Math.pow(ball.getY() - player1.getY(), 2)) < ball.getRadius() + player1.getRadius()) {
            if ((!FireBall || fireBallOwner == 1) && !ignorePlayer1) {
                lastStriker = 1;
                ignorePlayer1 = true;
                enable(1);
                ball.setSpeedX((FireBall ? 2 : 1) * handleCollision(ball, player1)[0]);
                ball.setSpeedY((FireBall ? 2 : 1) * handleCollision(ball, player1)[1]);
            }
        }
        if (2 * Math.sqrt(Math.pow(ball.getX() - player2.getX(), 2) + Math.pow(ball.getY() - player2.getY(), 2)) < ball.getRadius() + player2.getRadius()) {
            if ((!FireBall || fireBallOwner == 2) && !ignorePlayer2) {
                lastStriker = 2;
                ignorePlayer2 = true;
                enable(2);
                ball.setSpeedX((FireBall ? 2 : 1) * handleCollision(ball, player2)[0]);
                ball.setSpeedY((FireBall ? 2 : 1) * handleCollision(ball, player2)[1]);
            }
        }
    }

    private double[] handleCollision(MovingCircle ball, MovingCircle player) {
        double[] speed;
        double[] ballCoords = new double[]{ball.getX(), ball.getY()};
        double[] ballSpeed = new double[]{ball.getSpeedX(), ball.getSpeedY()};
        if (VectorUtils.calcNorm(ballSpeed) <= this.ballSpeed) {
            ballSpeed = VectorUtils.scalarMult(VectorUtils.getNormalizedVector(ballSpeed), this.ballSpeed);
        }
        double[] playerCoords = new double[]{player.getX(), player.getY()};
        double[] dist = VectorUtils.sub(ballCoords, playerCoords);
        double[] perpLine = VectorUtils.getPerpVector(dist);
        double[] perpImage = VectorUtils.getPerpImage(ballSpeed, perpLine);
        speed = VectorUtils.scalarMult(VectorUtils.add(ballSpeed, VectorUtils.scalarMult(perpImage, 2)), 0.3);
        if (player.getX() == ball.getX()){
            speed[0] = 0;
            speed[1] = -ball.getSpeedY();
        }
        if (ball.getY() == player.getY()) {
            speed[0] = -ball.getSpeedX();
            speed[1] = 0;
        }
        return speed;
/*        if (player.getSpeedX() == 0) {
            if (player.getX() == ball.getX()) {
                speed[0] = 0;
            } else {
                speed[0] = 2 * (ball.getX() - player.getX()) / Math.abs(ball.getX() - player.getX());
            }
        } else {
            speed[0] = player.getSpeedX();
        }
        if (player.getSpeedY() == 0) {
            if (player.getY() == ball.getY()) {
                speed[1] = 0;
            } else {
                speed[1] = 2 * (ball.getY() - player.getY()) / Math.abs(ball.getY() - player.getY());
            }
        } else {
            speed[1] = player.getSpeedY();
        }*/
    }

    private void enable(int id) {
        Thread control = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                    if (id == 1) {
                        ignorePlayer1 = false;
                    }
                    else {
                        ignorePlayer2 = false;
                    }
                }
                catch (Exception e) {
                    throw new RuntimeException();
                }
            }
        });
        control.start();
    }

    public long getStartTime() {
        return startTime;
    }

    public MovingCircle getBall() {
        return ball;
    }

    public MovingCircle getPlayer1() {
        return player1;
    }

    public Model.GoalNet getPlayer1Goal() {
        return player1Goal;
    }

    public MovingCircle getPlayer2() {
        return player2;
    }

    public Model.GoalNet getPlayer2Goal() {
        return player2Goal;
    }

    public int getStrikerSpeed() {
        return strikerSpeed;
    }

    public int getBallSpeed() {
        return ballSpeed;
    }

    private class boostThread extends Thread {
        public void run() {
            try {
                while (true) {
                    boost = null;
                    Thread.sleep(10 * 1000);
                    boost = getRandomBoost();
                    Thread.sleep(5 * 1000);
                    if (boost != null) {
                        boost.setWidth(boost.getWidth() * 2);
                        boost.setHeight(boost.getHeight() * 2);
                    }
                    Thread.sleep(5 * 1000);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
