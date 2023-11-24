package Main.Controller.Quiz;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;

public class QuizController implements Initializable  {
    @FXML
    private JFXButton answerButtonA;

    @FXML
    private JFXButton answerButtonB;

    @FXML
    private JFXButton answerButtonC;

    @FXML
    private JFXButton answerButtonD;

    @FXML
    private Label questionLabel;

    // Declare a list of questions and a list of answers
    private ArrayList<String> questions;
    private ArrayList<String> answers;
    private ArrayList<String> userAnswers;
    private ArrayList<Boolean> answerCorrectness;
    private int currentQuestion;
    private int correctAnswers;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Initialize the questions and answers lists by reading from the questions.txt file
        questions = new ArrayList<>();
        answers = new ArrayList<>();
        userAnswers = new ArrayList<>();
        answerCorrectness = new ArrayList<>();
        readQuestionsFromFile();
        shuffleQuestions();
        currentQuestion = 0;
        correctAnswers = 0;
        displayQuestion();

        // Set action handlers for answer buttons using lambda expressions
        answerButtonA.setOnAction(event -> checkAnswer(event));
        answerButtonB.setOnAction(event -> checkAnswer(event));
        answerButtonC.setOnAction(event -> checkAnswer(event));
        answerButtonD.setOnAction(event -> checkAnswer(event));
    }

    // This method reads the questions and answers from the questions.txt file
    private void readQuestionsFromFile() {
        try {
            Scanner scanner = new Scanner(new File("src/main/resources/Data/Questions.txt"));
            while (scanner.hasNextLine()) {
                String question = scanner.nextLine();
                String answer = scanner.nextLine();
                questions.add(question);
                answers.add(answer);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // This method shuffles the questions and answers lists using a random seed
    private void shuffleQuestions() {
        // Create a random object with a fixed seed
        Random random = new Random();
        // Loop through the lists from the last index to the first
        for (int i = questions.size() - 1; i > 0; i--) {
            // Generate a random index between 0 and i
            int j = random.nextInt(i + 1);
            // Swap the elements at i and j in both lists
            String tempQuestion = questions.get(i);
            String tempAnswer = answers.get(i);
            questions.set(i, questions.get(j));
            answers.set(i, answers.get(j));
            questions.set(j, tempQuestion);
            answers.set(j, tempAnswer);
        }
    }

    // This method displays the current question and the answer choices
    private void displayQuestion() {
        // Check if there are more questions to display
        if (currentQuestion < questions.size()) {
            // Get the current question and the answer from the lists
            String question = questions.get(currentQuestion);
            String answer = answers.get(currentQuestion);
            // Split the question by the comma to get the answer choices
            String[] choices = question.split(",");
            questionLabel.setText(choices[0]);
            answerButtonA.setText(choices[1]);
            answerButtonB.setText(choices[2]);
            answerButtonC.setText(choices[3]);
            answerButtonD.setText(choices[4]);
        } else {
            System.out.println("No more questions to display.");
        }
    }

    // This method checks the user's answer and moves to the next question
    private void checkAnswer(ActionEvent event) {
        JFXButton selectedAnswerButton = (JFXButton) event.getSource();
        String selectedAnswer = selectedAnswerButton.getText().substring(0, 1); // Extract the first character (letter)

        // Get the correct answer from the list
        String correctAnswer = answers.get(currentQuestion);
        String correctAnswerLetter = correctAnswer.substring(0, 1); // Extract the first character (letter)

        userAnswers.add(selectedAnswer);

        boolean isCorrect = selectedAnswer.equals(correctAnswerLetter);
        answerCorrectness.add(isCorrect);

        if(isCorrect) {
            System.out.println("Correct!");
            correctAnswers++;
        } else {
            System.out.println("Wrong!");
        }

        currentQuestion++;

        if (currentQuestion < 10) {
            displayQuestion();
        } else {
            // If there are no more questions, show the result of the quiz and load the endquiz.fxml file
            System.out.println("You have completed the quiz!");
            System.out.println("You got " + correctAnswers + " out of 10 questions correct!");
            loadPage("/Main/Interface/Quiz/endquiz.fxml", event);
        }
    }

    // This method loads a new page from the given fxml file
    private void loadPage(String fxml, javafx.event.ActionEvent choice) {
        try {
            // Get the current stage
            Stage stage = (Stage) ((javafx.scene.Node) choice.getSource()).getScene().getWindow();
            // Load the new page as a parent node
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            // Set the scene with the new page
            stage.setScene(new Scene(root));
            // Show the stage
            stage.show();
        } catch (IOException e) {
            // Handle the exception
            e.printStackTrace();
        }
    }
}