package View;

import Control.GameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class PreviousGames extends JFrame{
    private JPanel panel1;
    public PreviousGames(ArrayList<MatchSave> allMatches, GameManager manager) {
        JPanel mainPanel = new JPanel();
        JScrollPane scrollable = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        for (MatchSave save : allMatches) {
            mainPanel.add(save);
            save.setVisible(true);
            save.setPreferredSize(new Dimension(400, 100));
            save.revalidate();
        }
        setContentPane(scrollable);
        setSize(new Dimension(600, 800));
        setVisible(true);
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 27) {
                    setVisible(false);
                    manager.showMenu();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }
}
