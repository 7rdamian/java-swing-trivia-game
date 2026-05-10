package model;

// Handles the structure of a question, with different fields for text, options and correct answer

public class Question {
    private String text;
    private String[] options;
    private String correctAnswer;

    public Question(String text, String[] options, String correctAnswer) {
        this.text = text;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        if (options != null)
            this.options = options;
        else
            System.out.println("Options cannot be null!");
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public boolean isCorrect(String answer) {
        return answer.toUpperCase().equals(correctAnswer);
    }

    // Prints a question as it is found in the file but without the correct answer
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(text).append("\n");
        for (int i = 0; i < options.length; i++) {
            sb.append(options[i]).append("\n");
        }
        return sb.toString();
    }
}
