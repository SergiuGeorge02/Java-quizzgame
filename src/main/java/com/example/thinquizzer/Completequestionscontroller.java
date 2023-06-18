package com.example.thinquizzer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Completequestionscontroller {
    private CompleteQuestion[] questions = new CompleteQuestion[10];
    private Game gameobj = new Game();
    private int score = 0;
    private int lives = 2;
    private int questionNumber = 0;
    private int[] questionarray = new int[10];

    @FXML
    private Label question;
    @FXML
    private TextField answer;
    @FXML
    private Button submit;
    @FXML
    private Button exitButton;
    @FXML
    private Label scorelabel;
    @FXML
    private ImageView hearth1;
    @FXML
    private ImageView hearth2;
    @FXML
    private Button hint;
    @FXML
    private Label hintlabel;
    private boolean hintUsed = false;
    @FXML
    public void exitButtonOnAction(ActionEvent e) {
        Stage s = (Stage) exitButton.getScene().getWindow();
        s.close();

        try {
            File file = new File("usernames.txt");
            FileWriter writer = new FileWriter(file, false);

            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        initializeQuestions();
        scorelabel.setText("Score: " + score);
        int random;
        do {
            random = gameobj.generateRandomNumberquestions();
        } while (checkQuestionArray(random) == 0);
        questionarray[questionNumber] = random;
        questionNumber++;
        question.setText(questionNumber + "/15 " + questions[random].getQuestion());

        if (hintUsed) {
            hint.setDisable(true);
        } else {
            hint.setDisable(false);
        }
    }


    @FXML
    private void handleSubmit(ActionEvent event) {
        hintlabel.setText("");
        String userAnswer = answer.getText().trim();
        int currentQuestionNumber = questionNumber - 1;
        CompleteQuestion currentQuestion = questions[questionarray[currentQuestionNumber]];

        if (userAnswer.equalsIgnoreCase(currentQuestion.getCorrect_answer())) {
            score++;
        } else {
            lives--;
            if (lives == 1) {
                hearth1.setImage(null);
            }
        }

        scorelabel.setText("Score: " + score);

        if (questionNumber < 10 && lives > 0) {
            int random;
            do {
                random = gameobj.generateRandomNumberquestions();
            } while (checkQuestionArray(random) == 0);
            questionarray[questionNumber] = random;
            questionNumber++;
            question.setText(questionNumber + "/15 " + questions[random].getQuestion());
            answer.clear();

            if (currentQuestion.isHintUsed()) {
                hint.setDisable(true);
            } else {
                hint.setDisable(false);
            }
        }
        else if (questionNumber == 10 & lives > 0)
        {
            submit.setDisable(true);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("multipleQ.fxml"));
            BorderPane root;
            try {
                root = loader.load();
                multipleQController controller = loader.getController();
                controller.setScore(score, lives);
                Scene scene = new Scene(root);
                Stage stage = (Stage) submit.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            submit.setDisable(true);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("end_game.fxml"));
            BorderPane root;
            try {
                root = loader.load();
                EndGameController controller = loader.getController();
                controller.setScore(score, lives);
                Scene scene = new Scene(root);
                Stage stage = (Stage) submit.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    private void handleHint(ActionEvent event) {
        int currentQuestionNumber = questionNumber - 1;
        CompleteQuestion currentQuestion = questions[questionarray[currentQuestionNumber]];

        if (!currentQuestion.isHintUsed()) {
            // Display the hint in the hint label
            hintlabel.setText(currentQuestion.getHint());

            // Mark the hint as used
            currentQuestion.setHintUsed(true);

            // Disable the Hint button after it's used
            hint.setDisable(true);
        }
    }



    private void initializeQuestions() {
        questions = gameobj.getCompleteQuestions();
    }

    private int checkQuestionArray(int x) {
        for (int i : questionarray) {
            if (i == x) {
                return 0;
            }
        }
        return 1;
    }
}
