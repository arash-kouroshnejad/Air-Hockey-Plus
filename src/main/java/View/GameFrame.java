package View;

import Control.GameManager;
import Model.Boost.Boost;
import Model.GoalNet;
import Model.MovingCircle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameFrame extends JFrame implements KeyListener {
    private final GameManager gm;
    public GameFrame(GameManager manager) {
        gm = manager;
        gm.init();
        setSize(new Dimension(600, 800));
        setResizable(false);
        gm.getBall().setSpeedX((int)(Math.random() * 2));
        gm.getBall().setSpeedY((int)(Math.random() * 2));
        setContentPane(new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                MovingCircle player1 = gm.getPlayer1();
                MovingCircle player2 = gm.getPlayer2();
                MovingCircle ball = gm.getBall();
                Boost boost = gm.getBoost();
                GoalNet player1Goal = gm.getPlayer1Goal();
                GoalNet player2Goal = gm.getPlayer2Goal();
                if (gm.getBoost() != null && !gm.getBoost().isToggled()) {
                    g.drawImage(boost.getIcon(), boost.getX(), gm.getBoost().getY(), boost.getWidth(), boost.getHeight(), this);
                }
                g.setFont(new Font("TimesRoman", Font.BOLD, 20));
                g.setColor(ball.getColor());
                g.fillOval(ball.getX(), ball.getY(), ball.getRadius(), ball.getRadius());
                g.setColor(player1.getColor());
                g.fillOval(player1.getX(), player1.getY(), player1.getRadius(), player1.getRadius());
                g.drawString(String.valueOf(gm.getPlayer1Score()), getWidth() / 4, getHeight() / 2);
                g.drawString(gm.getPlayer1Name(), getWidth() / 4, getHeight() / 2 + 20);
                g.drawArc(player1Goal.getStartX(),player1Goal.getY() - (player1Goal.isEnlarged() ? player1Goal.getWidth() / 4 : 0), player1Goal.getWidth(), player1Goal.getWidth(), 0, 180 );
                g.setColor(player2.getColor());
                g.fillOval(player2.getX(), player2.getY(), player2.getRadius(), player2.getRadius());
                g.drawString(String.valueOf(gm.getPlayer2Score()), 3 * getWidth() / 4, getHeight() / 2);
                g.drawString(gm.getPlayer2Name(), 3 * getWidth() / 4, getHeight() / 2 + 20);
                g.drawArc(player2Goal.getStartX(),player2Goal.getY() - (player2Goal.isEnlarged() ? player2Goal.getWidth() / 4 : 0), player2Goal.getWidth(), player2Goal.getWidth(), 180, 180 );
            }
        });
        addKeyListener(this);
        setVisible(true);
    }
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {
        MovingCircle player1 = gm.getPlayer1();
        MovingCircle player2 = gm.getPlayer2();
        int strikerSpeed = gm.getStrikerSpeed();
        MovingCircle ball = gm.getBall();
        int keyCode = e.getKeyCode();
        if(keyCode == 87) { // W
            player1.setSpeedY(-strikerSpeed);
        }
        else if (keyCode == 65) { // A
            player1.setSpeedX(-strikerSpeed);
        }
        else if(keyCode == 83) { // S
            player1.setSpeedY(strikerSpeed);
        }
        else if (keyCode == 68) { // D
            player1.setSpeedX(strikerSpeed);
        }
        else if (keyCode == 85) { // U
            player2.setSpeedY(-strikerSpeed);
        }
        else if (keyCode == 72) { // H
            player2.setSpeedX(-strikerSpeed);
        }
        else if (keyCode == 74) { // J
            player2.setSpeedY(strikerSpeed);
        }
        else if (keyCode == 75) { // K
            player2.setSpeedX(strikerSpeed);
        }
        else if (keyCode == 38) {
            ball.setSpeedY(-strikerSpeed);
        }
        else if (keyCode == 39) {
            ball.setSpeedX(strikerSpeed);
        }
        else if (keyCode == 37) {
            ball.setSpeedX(-strikerSpeed);
        }
        else if (keyCode == 40) {
            ball.setSpeedY(strikerSpeed);
        }
        else if (keyCode == 27) {
            gm.saveGame();
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        MovingCircle ball = gm.getBall();
        MovingCircle player1 = gm.getPlayer1();
        MovingCircle player2 = gm.getPlayer2();
        int keyCode = e.getKeyCode();
        if(keyCode == 87) { // W
            player1.setSpeedY(0);
        }
        else if (keyCode == 65) { // A
            player1.setSpeedX(0);
        }
        else if(keyCode == 83) { // S
            player1.setSpeedY(0);
        }
        else if (keyCode == 68) { // D
            player1.setSpeedX(0);
        }
        else if (keyCode == 85) { // U
            player2.setSpeedY(0);
        }
        else if (keyCode == 72) { // H
            player2.setSpeedX(0);
        }
        else if (keyCode == 74) { // J
            player2.setSpeedY(0);
        }
        else if (keyCode == 75) { // K
            player2.setSpeedX(0);
        }
        else if (keyCode == 38) {
            ball.setSpeedY(0);
        }
        else if (keyCode == 39) {
            ball.setSpeedX(0);
        }
        else if (keyCode == 37) {
            ball.setSpeedX(0);
        }
        else if (keyCode == 40) {
            ball.setSpeedY(0);
        }
    }
}