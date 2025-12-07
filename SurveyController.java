import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class SurveyController {

    public void displaySurveys(int userId) {

        Map<Integer, String> surveys = loadSurveys();
        if (surveys.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No surveys found in the database.");
            return;
        }

        String[] options = surveys.entrySet()
                .stream()
                .map(e -> e.getKey() + ". " + e.getValue())
                .toArray(String[]::new);

        String selected = (String) JOptionPane.showInputDialog(
                null,
                "Select a Survey:",
                "Survey Selection",
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]);

        if (selected == null)
            return;

        int surveyId = Integer.parseInt(selected.substring(0, selected.indexOf(".")));
        takeSurvey(userId, surveyId, surveys.get(surveyId));
    }

    // Load surveys from DB
    public Map<Integer, String> loadSurveys() {
        Map<Integer, String> surveys = new HashMap<>();

        try (var conn = Database.connect();
                var stmt = conn.prepareStatement("SELECT survey_id, title FROM surveys");
                var rs = stmt.executeQuery()) {

            while (rs.next()) {
                surveys.put(rs.getInt("survey_id"), rs.getString("title"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return surveys;
    }

    // Ask the question
    public void takeSurvey(int userId, int surveyId, String surveyName) {

        String[] options = {
                "Very Satisfied",
                "Satisfied",
                "Neutral",
                "Dissatisfied",
                "Very Dissatisfied"
        };

        String response = (String) JOptionPane.showInputDialog(
                null,
                "How satisfied are you with " + surveyName + "?",
                "Survey Question",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        if (response != null) {
            saveResponse(userId, surveyId, response);
            JOptionPane.showMessageDialog(null, "Response saved successfully!");
        }
    }

    // Save survey response to DB
    public void saveResponse(int userId, int surveyId, String response) {
        String sql = "INSERT INTO survey_responses (user_id, survey_id, response) VALUES (?, ?, ?)";

        try (var conn = Database.connect();
                var stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, surveyId);
            stmt.setString(3, response);

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
