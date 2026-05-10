package service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import model.Question;
import org.json.JSONArray;
import org.json.JSONObject;


public class QuestionLoaderApi {
    private static final String BASE_URL = "https://opentdb.com/api.php?";
    private List<Question> questions;

    public void fetchQuestions(String difficulty, int numberOfQuestions, String category) {
        try {
            String getUrl = BASE_URL + "amount=" + numberOfQuestions + "&difficulty=" + difficulty + "&category=" + category;
            URL obj = new URL(getUrl);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject jsonObject = new JSONObject(response.toString());
            JSONArray results = jsonObject.getJSONArray("results");

            questions = new ArrayList<>();
            for (int i = 0; i < results.length(); i++) {
                JSONObject resultsJson = results.getJSONObject(i);

                String question = decodeHTML(resultsJson.getString("question"));

                JSONArray incorrectAnswers = resultsJson.getJSONArray("incorrect_answers");
                String[] options = new String[incorrectAnswers.length() + 1];
                for (int j = 0; j < incorrectAnswers.length(); j++) {
                    options[j] = decodeHTML(incorrectAnswers.getString(j));
                }
                options[incorrectAnswers.length()] = decodeHTML(resultsJson.getString("correct_answer"));

                List<String> optionList = Arrays.asList(options);
                Collections.shuffle(optionList);
                options = optionList.toArray(new String[optionList.size()]);

                String correctAnswer = "";
                String[] letters = {"A", "B", "C", "D"};
                for (int j = 0; j < options.length; j++) {
                    if (options[j].equals(decodeHTML(resultsJson.getString("correct_answer")))) {
                        correctAnswer = letters[j];
                    }
                    options[j] = letters[j] + ") " + options[j];
                }

                Question q = new Question(question, options, correctAnswer);
                questions.add(q);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public static String decodeHTML(String text) {
        return text
                .replace("&quot;", "\"")
                .replace("&#039;", "'")
                .replace("&amp;", "&")
                .replace("&lt;", "<")
                .replace("&gt;", ">")
                .replace("&ldquo;", "\"")
                .replace("&rdquo;", "\"")
                .replace("&rsquo;", "'")
                .replace("&lsquo;", "'")
                .replace("&hellip;", "…");
    }
}
