package ui.panels;

import ui.frames.HomePage;

import javax.swing.*;
import java.awt.*;

// Child of MenuPanel, contains the input fields for the quiz parameters

public class ParameterInputPanel extends JPanel {
    private static final Color MAIN_BACKGROUND_COLOR = new Color(44, 44, 44);
    private static final Color TEXT_COLOR = new Color(196, 196, 196);
    private static final Color BORDER_COLOR = new Color(70, 70, 70);
    private static final Color BACK_BUTTON_COLOR = new Color(91, 131, 131);
    private static final Color MENU_BUTTON_COLOR = new Color(122, 168, 168);

    private JTextField amountField;
    private JTextField difficultyField;
    private JTextField categoryField;
    private JTextField timeField;

    private HomePage homePage;
    private boolean isTimed;

    public ParameterInputPanel(HomePage homePage, boolean isTimed) {
        this.homePage = homePage;
        this.isTimed = isTimed;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(MAIN_BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        amountField = createStyledTextField("10");
        add(createInputGroup("Number of Questions (1-100):", amountField));
        add(Box.createVerticalStrut(5));

        difficultyField = createStyledTextField("easy");
        add(createInputGroup("Difficulty (easy, medium, hard):", difficultyField));
        add(Box.createVerticalStrut(5));

        categoryField = createStyledTextField("9");
        add(createInputGroup("Category Code:", categoryField));
        add(Box.createVerticalStrut(15));

        if (isTimed) {
            timeField = createStyledTextField("10");
            add(createInputGroup("Time Limit (in seconds):", timeField));
            add(Box.createVerticalStrut(15));
        }

        JButton startButton = createStyledButton("START", MENU_BUTTON_COLOR);
        JButton backButton = createStyledButton("BACK", BACK_BUTTON_COLOR);
        add(Box.createVerticalStrut(15));
        add(startButton);
        add(Box.createVerticalStrut(15));
        add(backButton);
        add(Box.createVerticalStrut(15));

        startButton.addActionListener(e -> {
            if( isTimed) {
                TimedQuizPanel timedQuiz = new TimedQuizPanel(homePage, getDifficulty(), getAmount(), getCategory(), getTimeLimit());
                homePage.setContentPane(timedQuiz);
                homePage.revalidate();
                homePage.repaint();
            } else {
                NormalQuizPanel quiz = new NormalQuizPanel(homePage, getDifficulty(), getAmount(), getCategory());
                homePage.setContentPane(quiz);
                homePage.revalidate();
                homePage.repaint();
            }
        });

        backButton.addActionListener(e -> {
            MenuPanel menuPanel = new MenuPanel(homePage);
            homePage.setContentPane(menuPanel);
            homePage.revalidate();
            homePage.repaint();
        });

        JTextArea categoryListArea = new JTextArea(15, 30);
        categoryListArea.setText(getCategoryListText());
        categoryListArea.setEditable(false);
        categoryListArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        categoryListArea.setForeground(TEXT_COLOR.darker());
        categoryListArea.setBackground(MAIN_BACKGROUND_COLOR.darker());

        JScrollPane categoryScrollPane = new JScrollPane(categoryListArea);
        categoryScrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));

        JLabel categoryListHeader = new JLabel("Category Codes (9-32):");
        categoryListHeader.setForeground(TEXT_COLOR);
        categoryListHeader.setFont(new Font("Arial", Font.BOLD, 14));
        categoryListHeader.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel categoryRefPanel = new JPanel();
        categoryRefPanel.setLayout(new BoxLayout(categoryRefPanel, BoxLayout.Y_AXIS));
        categoryRefPanel.setBackground(MAIN_BACKGROUND_COLOR);
        categoryRefPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        categoryRefPanel.add(categoryListHeader);
        categoryRefPanel.add(Box.createVerticalStrut(5));
        categoryRefPanel.add(categoryScrollPane);

        add(categoryRefPanel);
    }

    public int getAmount() {
        return Integer.parseInt(amountField.getText());
    }

    public String getDifficulty() {
        return difficultyField.getText().toLowerCase().trim();
    }

    public String getCategory() {
        return categoryField.getText();
    }

    public int getTimeLimit() {
        return Integer.parseInt(timeField.getText());
    }

    public static JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 40));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(color);
        button.setForeground(TEXT_COLOR);
        button.setOpaque(true);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        return button;
    }

    public static JPanel createInputGroup(String labelText, JComponent inputComponent) {
        JPanel group = new JPanel(new FlowLayout(FlowLayout.CENTER));
        group.setBackground(MAIN_BACKGROUND_COLOR);

        JLabel label = new JLabel(labelText);
        label.setForeground(TEXT_COLOR);

        group.add(label);
        group.add(inputComponent);
        group.setAlignmentX(Component.CENTER_ALIGNMENT);
        return group;
    }

    public static JTextField createStyledTextField(String defaultText) {
        JTextField field = new JTextField(defaultText, 8);
        field.setPreferredSize(new Dimension(150, 25));
        field.setBackground(MAIN_BACKGROUND_COLOR.brighter());
        field.setForeground(TEXT_COLOR);
        field.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        return field;
    }

    private String getCategoryListText() {
        return "9: General Knowledge\n" +
                "10: Entertainment: Books\n" +
                "11: Entertainment: Film\n" +
                "12: Entertainment: Music\n" +
                "13: Entertainment: Musicals & Theatres\n" +
                "14: Entertainment: Television\n" +
                "15: Entertainment: Video Games\n" +
                "16: Entertainment: Board Games\n" +
                "17: Science & Nature\n" +
                "18: Science: Computers\n" +
                "19: Science: Mathematics\n" +
                "20: Mythology\n" +
                "21: Sports\n" +
                "22: Geography\n" +
                "23: History\n" +
                "24: Politics\n" +
                "25: Art\n" +
                "26: Celebrities\n" +
                "27: Animals\n" +
                "28: Vehicles\n" +
                "29: Entertainment: Comics\n" +
                "30: Science: Gadgets\n" +
                "31: Entertainment: Japanese Anime & Manga\n" +
                "32: Entertainment: Cartoon & Animations";
    }
}