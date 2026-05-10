package ui.panels;

import ui.frames.HomePage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Child of NormalQuizPanel, adds a timer to the quiz

public class TimedQuizPanel extends NormalQuizPanel {
    private int timeLimit;
    private Timer timer;
    private JLabel timerLabel;

    private HomePage homePage;

    public TimedQuizPanel(HomePage homePage, String difficulty, int amount, String category, int timeLimit) {
        this.homePage = homePage;

        super(homePage, difficulty, amount, category);
        this.timeLimit = timeLimit;

        timerLabel = new JLabel("Time Remaining: " + timeLimit + "s");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        timerLabel.setForeground(TEXT_COLOR);
        timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(timerLabel, 0);
        add(Box.createVerticalStrut(20), 1);

        startTimer();
    }

    private void startTimer() {
        timer = new Timer(1000, new ActionListener() {
            int remainingTime = timeLimit;

            @Override
            public void actionPerformed(ActionEvent e) {
                remainingTime--;
                timerLabel.setText("Time Remaining: " + remainingTime + "s");

                if (remainingTime <= 0) {
                    timer.stop();
                    JOptionPane.showMessageDialog(TimedQuizPanel.this, "Time's Up! Quiz ended.");
                    doneButton.doClick();
                }
            }
        });
        timer.start();
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
    }
}