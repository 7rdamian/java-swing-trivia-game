package ui.panels;

import ui.frames.HomePage;

import javax.swing.*;
import java.awt.*;

// Parent of all panels, contains the main menu

public class MenuPanel extends JPanel {
    private static final Color MAIN_BACKGROUND_COLOR = new Color(44, 44, 44);
    private static final Color TEXT_COLOR = new Color(196, 196, 196);

    private static final Color MENU_BUTTON_COLOR = new Color(122, 168, 168);
    private static final Color EXIT_BUTTON_COLOR = new Color(91, 131, 131);

    private static final Dimension BUTTON_SIZE = new Dimension(300, 40);
    private static final Dimension GRID_CONTAINER_SIZE = new Dimension(300, 260);
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 48);

    private HomePage homePage;

    public MenuPanel(HomePage homePage) {
        this.homePage = homePage;
        setLayout(new GridBagLayout());
        setBackground(MAIN_BACKGROUND_COLOR);

        JPanel generalContainer = new JPanel();
        generalContainer.setBackground(MAIN_BACKGROUND_COLOR);
        generalContainer.setLayout(new BoxLayout(generalContainer, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("KNOWLEDGE QUIZ");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_COLOR);

        JPanel titleWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titleWrapper.setBackground(MAIN_BACKGROUND_COLOR);
        titleWrapper.add(titleLabel);

        generalContainer.add(Box.createVerticalStrut(50)); // Top padding
        generalContainer.add(titleWrapper);
        generalContainer.add(Box.createVerticalStrut(50)); // Spacing between title and buttons

        JPanel buttonContainer = new JPanel(new GridLayout(5, 1, 10, 10));
        buttonContainer.setPreferredSize(GRID_CONTAINER_SIZE);
        buttonContainer.setBackground(MAIN_BACKGROUND_COLOR);

        // Buttons
        JButton startQuizButton = createStyledButton("Start New Quiz", MENU_BUTTON_COLOR, TEXT_COLOR);
        JButton timedQuizButton = createStyledButton("Start Timed Quiz", MENU_BUTTON_COLOR, TEXT_COLOR);
        JButton leaderboardButton = createStyledButton("View Leaderboard", MENU_BUTTON_COLOR, TEXT_COLOR);
        JButton exitButton = createStyledButton("Exit", EXIT_BUTTON_COLOR, TEXT_COLOR);

        buttonContainer.add(startQuizButton);
        buttonContainer.add(timedQuizButton);
        buttonContainer.add(leaderboardButton);
        buttonContainer.add(exitButton);

        generalContainer.add(buttonContainer);

        add(generalContainer);

        startQuizButton.addActionListener(e -> {
            ParameterInputPanel inputPanel = new ParameterInputPanel(homePage, false);
            homePage.setContentPane(inputPanel);
            homePage.revalidate();
            homePage.repaint();
        });

        timedQuizButton.addActionListener(e -> {
            ParameterInputPanel inputPanel = new ParameterInputPanel(homePage, true);
            homePage.setContentPane(inputPanel);
            homePage.revalidate();
            homePage.repaint();
        });

        leaderboardButton.addActionListener(e -> {
            LeaderboardPanel leaderboardPanel = new LeaderboardPanel(homePage);
            homePage.setContentPane(leaderboardPanel);
            homePage.revalidate();
            homePage.repaint();
        });

        exitButton.addActionListener(e -> System.exit(0));
    }

    private JButton createStyledButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(BUTTON_SIZE);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setOpaque(true);
        return button;
    }
}