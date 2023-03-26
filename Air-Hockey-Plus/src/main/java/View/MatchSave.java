package View;

import Model.Match;

import javax.swing.*;
import java.awt.*;

public class MatchSave extends JPanel{
    private JPanel panel1;
    private JLabel player1Score;
    private JLabel player1Name;
    private JLabel player2Name;
    private JLabel player2Score;
    private JLabel timeElapsed;
    private JLabel colon;
    private JLabel finished;
    private JLabel goalLimited;
    private JLabel timeLimited;

    public MatchSave(Match match) {
        setSize(new Dimension(500, 100));
        add(panel1);
        panel1.setSize(new Dimension(500, 100));
        int mins = (int)((match.getTimeElapsed() / Math.pow(10, 9)) / 60);
        timeElapsed.setText(mins + " : " + (int)(match.getTimeElapsed() / Math.pow(10, 9) - 60 * mins));
        player1Score.setForeground(match.getPlayer1());
        player1Name.setForeground(match.getPlayer1());
        player1Name.setFont(new Font("TimesRoman", Font.BOLD, 16));
        player1Name.setText(match.getPlayer1Name());
        player1Score.setFont(new Font("TimesRoman", Font.BOLD, 20));
        player1Score.setText(String.valueOf(match.getScore1()));
        player2Score.setForeground(match.getPlayer2());
        player2Name.setForeground(match.getPlayer2());
        player2Name.setFont(new Font("TimesRoman", Font.BOLD, 16));
        player2Name.setText(match.getPlayer2Name());
        player2Score.setFont(new Font("TimesRoman", Font.BOLD, 20));
        player2Score.setText(String.valueOf(match.getScore2()));
        colon.setFont(new Font("TimesRoman", Font.BOLD, 20));
        colon.setText(":");
        if (!match.isFinished()) {
            finished.setForeground(Color.RED);
        }
        finished.setText((match.isFinished() ? "Finished" : "Not Finished"));
        if (!match.isGoalLimited()) {
            goalLimited.setForeground(Color.RED);
        }
        goalLimited.setText(match.isGoalLimited() ? "Goal Limit : " + match.getGoalLimit() + " Margin : " + match.getMargin() : "Not Goal Limited");
        if (!match.isTimeLimited()) {
            timeLimited.setForeground(Color.RED);
        }
        timeLimited.setText(match.isTimeLimited() ? "Time Limit : " + match.getTimeLimit() : "Not Time Limited");
        setVisible(true);
    }
}
