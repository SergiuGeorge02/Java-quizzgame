package com.example.thinquizzer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Game {

        public  int generateRandomNumberquestions() {
            Random rand = new Random();
            int randomNumber = rand.nextInt(99) + 1;
            return randomNumber;
        }
    public int generateRandomNumbergametype() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(2) + 1;
        return randomNumber;
    }

    public CompleteQuestion[] getCompleteQuestions() {
        CompleteQuestion[] completeQuestions = new CompleteQuestion[100];
        int k = 0;
        String read;
        try {
            BufferedReader questionReader = new BufferedReader(new FileReader("E:\\Desktop\\Thin Quizzer\\src\\main\\java\\com\\example\\thinquizzer\\CompleteQuestions.txt"));
            BufferedReader answerReader = new BufferedReader(new FileReader("E:\\Desktop\\Thin Quizzer\\src\\main\\java\\com\\example\\thinquizzer\\CompleteQuestionsAnswers.txt"));
            BufferedReader hintReader = new BufferedReader(new FileReader("E:\\Desktop\\Thin Quizzer\\src\\main\\java\\com\\example\\thinquizzer\\hints.txt"));

            while ((read = questionReader.readLine()) != null) {
                completeQuestions[k] = new CompleteQuestion();
                completeQuestions[k].setQuestion(read);
                completeQuestions[k].setCorrect_answer(answerReader.readLine());
                completeQuestions[k].setHint(hintReader.readLine());
                k++;
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return completeQuestions;
    }

    public MultipleChoiceQuestion[] getMultipleChoiceQuestions() {
        List<MultipleChoiceQuestion> questionList = new ArrayList<>();
        String questionFilePath = "E:\\Desktop\\Thin Quizzer\\src\\main\\java\\com\\example\\thinquizzer\\Multipechoicequestions.txt";
        String correctAnswerFilePath = "E:\\Desktop\\Thin Quizzer\\src\\main\\java\\com\\example\\thinquizzer\\Multiplechoicequestionscorrectanswer.txt";
        String answersFilePath = "E:\\Desktop\\Thin Quizzer\\src\\main\\java\\com\\example\\thinquizzer\\Multiplechoicequestionanswers.txt";

        try (BufferedReader questionReader = new BufferedReader(new FileReader(questionFilePath));
             BufferedReader correctAnswerReader = new BufferedReader(new FileReader(correctAnswerFilePath));
             BufferedReader answersReader = new BufferedReader(new FileReader(answersFilePath))) {

            String questionLine;
            String correctAnswerLine;
            String answersLine;

            while ((questionLine = questionReader.readLine()) != null
                    && (correctAnswerLine = correctAnswerReader.readLine()) != null
                    && (answersLine = answersReader.readLine()) != null) {

                MultipleChoiceQuestion question = new MultipleChoiceQuestion();
                question.setQuestion(questionLine);
                question.setCorrect_answer(correctAnswerLine);
                question.setAnswers(answersLine.split(", "));

                questionList.add(question);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return questionList.toArray(new MultipleChoiceQuestion[0]);
    }
    public  int generateRandomNumberquestionsmultiple() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(29) + 1;
        return randomNumber;
    }
}
