package com.example.thinquizzer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class multipleQController {
    private MultipleChoiceQuestion[] questions = new MultipleChoiceQuestion[5];
    private Game gameobj = new Game();
    private int score ;
    private int lives ;
    private int questionNumber=10;
    private int[] questionarray = new int[15];

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
    private ChoiceBox<String> chooseAnswer;

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

        if(this.lives==1)
            hearth1.setImage(null);
        scorelabel.setText("Score: " + score);
        int random;
        do {
            random = gameobj.generateRandomNumberquestionsmultiple();
        } while (checkQuestionArray(random) == 0);
        questionarray[questionNumber] = random;
        questionNumber++;
        question.setText(questionNumber + "/15 " + questions[random].getQuestion());
        String a=questions[random].getAnswers()[0];
        String b=questions[random].getAnswers()[1];
        String c=questions[random].getAnswers()[2];
        System.out.println(questionNumber);
        chooseAnswer.getItems().addAll(a, b, c);

    }


    @FXML
    private void handleSubmit(ActionEvent event) {

        String userAnswer = String.valueOf(chooseAnswer.getValue());
        int currentQuestionNumber = questionNumber - 1;
        MultipleChoiceQuestion currentQuestion = questions[questionarray[currentQuestionNumber]];

        if (userAnswer.equalsIgnoreCase(currentQuestion.getCorrect_answer())) {
            score++;
        } else {
            lives--;
            if (lives == 1) {
                hearth1.setImage(null);
            }
        }

        scorelabel.setText("Score: " + score);

        if (questionNumber < 15 && lives > 0) {
            int random;
            do {
                random = gameobj.generateRandomNumberquestionsmultiple();
            } while (checkQuestionArray(random) == 0);
            questionarray[questionNumber] = random;
            questionNumber++;

            chooseAnswer.getItems().clear();

            question.setText(questionNumber + "/15 " + questions[random].getQuestion());
            String a=questions[random].getAnswers()[0];
            String b=questions[random].getAnswers()[1];
            String c=questions[random].getAnswers()[2];
            System.out.println(questionNumber);
            chooseAnswer.getItems().addAll(a, b, c);




        } else {
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

    public void setScore(int score, int lives) {
        this.score=score;

        this.lives=lives;
        if(this.lives==1)
            hearth1.setImage(null);

        scorelabel.setText("Score: " + score);

    }

    private void initializeQuestions() {
        questions = gameobj.getMultipleChoiceQuestions();
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
