package Model;

import java.awt.*;

public class Match {
    private long timeElapsed;
    private String player1Name;
    private String player2Name;
    private int score1;
    private int score2;
    private final boolean goalLimited;
    private final int goalLimit;
    private final int margin;
    private final boolean timeLimited;
    private final int timeLimit;
    private boolean finished;

    public Match(String player1Name, String player2Name, boolean goalLimited, int goalLimit, int margin, boolean timeLimited, int timeLimit, Color player1, Color player2) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.goalLimited = goalLimited;
        this.goalLimit = goalLimit;
        this.margin = margin;
        this.timeLimited = timeLimited;
        this.timeLimit = timeLimit;
        this.player1 = player1;
        this.player2 = player2;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public int getScore1() {
        return score1;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }

    public int getScore2() {
        return score2;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
    }

    private final Color player1;
    private final Color player2;


    public long getTimeElapsed() {
        return timeElapsed;
    }

    public void setTimeElapsed(long timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    public Color getPlayer1() {
        return player1;
    }

    public Color getPlayer2() {
        return player2;
    }

    public boolean isGoalLimited() {
        return goalLimited;
    }

    public int getGoalLimit() {
        return goalLimit;
    }

    public int getMargin() {
        return margin;
    }

    public boolean isTimeLimited() {
        return timeLimited;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public boolean isFinished() {
        return finished;
    }

    public void finished() {
        finished = true;
    }
}
