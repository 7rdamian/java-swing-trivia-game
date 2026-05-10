package ui.panels;

import model.Player;
import model.Question;
import service.Leaderboard;
import service.QuestionLoaderApi;
import ui.frames.HomePage;

import java.util.List;
import javax.swing.*;
import java.awt.*;

// Parent of all quiz panels, contains the question list and the score

public class NormalQuizPanel extends JPanel{
    protected static final Color MAIN_BACKGROUND_COLOR = new Color(44, 44, 44);
    protected static final Color TEXT_COLOR = new Color(228, 228, 228);
    protected static final Color BACK_BUTTON_COLOR = new Color(91, 131, 131);
    protected static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 48);
    protected static final Color MENU_BUTTON_COLOR = new Color(122, 168, 168);

    protected List<Question> questionList;
    protected int score;
    protected JButton doneButton;

    private HomePage homePage;

    public NormalQuizPanel(HomePage homePage, String difficulty, int amount, String category) {
        this.homePage = homePage;

        QuestionLoaderApi questionLoader = new QuestionLoaderApi();
        questionLoader.fetchQuestions(difficulty, amount, category);
        questionList = questionLoader.getQuestions();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(MAIN_BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel titleLabel = new JLabel("QUIZ");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_COLOR);

        JPanel titleWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titleWrapper.setBackground(MAIN_BACKGROUND_COLOR);
        titleWrapper.add(titleLabel);

        add(Box.createVerticalStrut(20));
        add(titleWrapper);
        add(Box.createVerticalStrut(50));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(MAIN_BACKGROUND_COLOR);

        for (Question question : questionList) {
            contentPanel.add(createQuestionPanel(question));
        }
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(25);

        add(scrollPane);

        doneButton = ParameterInputPanel.createStyledButton("DONE", MENU_BUTTON_COLOR);
        Leaderboard leaderboard = new Leaderboard();
        doneButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "Your score is: " + score + "/10" + "\n\n" + "Enter your name: ");
            if (name != null && !name.isEmpty()) {
                Player player = new Player();
                player.setName(name);
                player.setScore(Integer.toString(score) + "/" + Integer.toString(questionList.size()));
                leaderboard.savePlayer(player);
            }
        });
        add(Box.createVerticalStrut(50));
        add(doneButton);

        JButton backButton = ParameterInputPanel.createStyledButton("BACK", BACK_BUTTON_COLOR);
        backButton.addActionListener(e -> {
            MenuPanel menuPanel = new MenuPanel(homePage);
            homePage.setContentPane(menuPanel);
            homePage.revalidate();
            homePage.repaint();
        });
        add(Box.createVerticalStrut(10));
        add(backButton);
    }

    private JPanel createQuestionPanel(Question question) {
        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
        questionPanel.setBackground(MAIN_BACKGROUND_COLOR);
        questionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel questionLabel = new JLabel(question.getText());
        questionLabel.setForeground(TEXT_COLOR);
        questionPanel.add(questionLabel);
        questionPanel.add(Box.createVerticalStrut(5));

        for (String answer : question.getOptions()) {
            JLabel answerLabel = new JLabel(answer);
            answerLabel.setForeground(TEXT_COLOR);
            questionPanel.add(answerLabel);
            questionPanel.add(Box.createVerticalStrut(5));
        }

        JTextField answerField = ParameterInputPanel.createStyledTextField("");
        questionPanel.add(answerField);
        questionPanel.add(Box.createVerticalStrut(5));

        questionPanel.add(Box.createVerticalStrut(5));

        final JLabel resultLabel = new JLabel("Answer: "); // Initial state
        resultLabel.setForeground(TEXT_COLOR);
        questionPanel.add(resultLabel);
        questionPanel.add(Box.createVerticalStrut(10));

        JButton submitButton = new JButton("Submit");
        submitButton.setPreferredSize(new Dimension(100, 30));
        submitButton.setBackground(MENU_BUTTON_COLOR);
        submitButton.setForeground(TEXT_COLOR);
        submitButton.setOpaque(true);

        submitButton.addActionListener(e -> {
            String answer = answerField.getText().trim();
            String resultText;

            if (question.isCorrect(answer)) {
                score++;
                resultText = "Correct!";
            } else {
                resultText = "Wrong! Correct answer: " + question.getCorrectAnswer();
            }

            resultLabel.setText(resultText);

            answerField.setEditable(false);
            submitButton.setEnabled(false);
        });

        questionPanel.add(submitButton);
        questionPanel.add(Box.createVerticalStrut(50));
        return questionPanel;
    }
}
