import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SurveyApp {

    private static final SurveyController surveyController = new SurveyController();

    public static void main(String[] args) {

        JFrame frame = new JFrame("Survey System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new FlowLayout());

        JLabel userIdLabel = new JLabel("Enter your user ID:");
        JTextField userIdField = new JTextField(20);
        JButton startSurveyButton = new JButton("Start Survey");

        startSurveyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                SwingWorker<Void, Void> worker = new SwingWorker<>() {
                    @Override
                    protected Void doInBackground() {
                        try {
                            int userId = Integer.parseInt(userIdField.getText());
                            surveyController.displaySurveys(userId);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame,
                                    "Please enter a valid numeric user ID",
                                    "Invalid Input",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                        return null;
                    }
                };

                worker.execute();
            }
        });

        frame.add(userIdLabel);
        frame.add(userIdField);
        frame.add(startSurveyButton);
        frame.setVisible(true);
    }
}
