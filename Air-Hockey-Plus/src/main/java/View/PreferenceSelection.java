package View;

import Control.GameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PreferenceSelection extends JFrame{
    private JCheckBox timeLimitedCheckBox;
    private JTextField limitTextField;
    private JCheckBox goalLimitedCheckBox;
    private JTextField limitTextField1;
    private JPanel panel1;
    private JTextField margin;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JButton finishedButton;
    private JTextArea Player1Name;
    private JTextArea player2Name;

    public PreferenceSelection(GameManager manager) {
        setSize(new Dimension(500, 500));
        setContentPane(panel1);
        limitTextField.setEnabled(false);
        margin.setEnabled(false);
        timeLimitedCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == 1) {
                    limitTextField.setEnabled(true);
                }
                else {
                    limitTextField.setEnabled(false);
                }
            }
        });
        limitTextField1.setEnabled(false);
        goalLimitedCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == 1) {
                    limitTextField1.setEnabled(true);
                    margin.setEnabled(true);
                }
                else {
                    limitTextField1.setEnabled(false);
                    margin.setEnabled(false);
                }
            }
        });
        limitTextField.setPreferredSize(new Dimension(40, 20));
        limitTextField1.setPreferredSize(new Dimension(40, 20));
        margin.setPreferredSize(new Dimension(40, 20));
        setVisible(true);
        finishedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeLimitedCheckBox.isSelected() || goalLimitedCheckBox.isSelected()) {
                    setVisible(false);
                    manager.newGame(PreferenceSelection.this);
                }
            }
        });
    }

    public JCheckBox getTimeLimitedCheckBox() {
        return timeLimitedCheckBox;
    }

    public JTextField getLimitTextField() {
        return limitTextField;
    }

    public JCheckBox getGoalLimitedCheckBox() {
        return goalLimitedCheckBox;
    }

    public JTextField getLimitTextField1() {
        return limitTextField1;
    }

    public JTextField getMargin() {
        return margin;
    }

    public JComboBox getComboBox1() {
        return comboBox1;
    }

    public JComboBox getComboBox2() {
        return comboBox2;
    }

    public String getPlayer1Name() {
        return Player1Name.getText();
    }

    public String getPlayer2Name() {
        return player2Name.getText();
    }
}
