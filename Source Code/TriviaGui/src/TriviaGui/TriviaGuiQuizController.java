package TriviaGui;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import DatabaseManagement.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class TriviaGuiQuizController {

    private final int NUMBER_OF_QUESTIONS = 10;
    private Game game = new Game(NUMBER_OF_QUESTIONS, UserHolder.getInstance().getUser().getId());
    private List<Question> questionsInGame;
    private byte pickedAnswerIndex;
    private int answeredQuestions = 0;
    private int pointsFromQuestions = 0;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane quizPane;

    @FXML
    private Text txtQuestion;

    @FXML
    private RadioButton radioAnswerOne;

    @FXML
    private ToggleGroup answers;

    @FXML
    private RadioButton radioAnswerTwo;

    @FXML
    private RadioButton radioAnswerThree;

    @FXML
    private RadioButton radioAnswerFour;

    @FXML
    private Button btnNext;

    @FXML
    private Button btnAudience;

    @FXML
    private Button btnFriend;

    @FXML
    private Button btnFifty;

    @FXML
    void btnAudienceOnAction(ActionEvent event) {
        game.usingAudience();
        btnAudience.setDisable(true);
    }

    @FXML
    void btnFiftyOnAction(ActionEvent event) {
        game.usingFifty();
        btnFifty.setDisable(true);
    }

    @FXML
    void btnFriendOnAction(ActionEvent event) {
        game.usingFriend();
        btnFriend.setDisable(true);
    }

    @FXML
    void btnNextOnAction(ActionEvent event) throws IOException {
        evaluateGivenAnswer();
        TriviaDataManager.getInstance().insertAnsweredQuestion(game,
                                        UserHolder.getInstance().getUser(),
                                        questionsInGame.get(answeredQuestions),
                                        questionsInGame.get(answeredQuestions).getAnswerAtIndex(pickedAnswerIndex));

        if(answeredQuestions == NUMBER_OF_QUESTIONS - 1) {
            UserHolder.getInstance().getUser().updatePoints(pointsFromQuestions);
            TriviaDataManager.getInstance().updateHelpers(game);
            AnchorPane pane = FXMLLoader.load(getClass().getResource("TriviaGuiGameFXML.fxml"));
            quizPane.getChildren().setAll(pane);
            return;
        }

        answeredQuestions++;
        generateQuestion();
        btnNext.setDisable(true);
        uncheckRadioButtons();

        if(answeredQuestions == NUMBER_OF_QUESTIONS - 1) {
            btnNext.setText("End");
        }
    }

    @FXML
    void radioAnswerOneOnAction(ActionEvent event) {
        btnNext.setDisable(false);
        pickedAnswerIndex = 0;
    }

    @FXML
    void radioAnswerTwoOnAction(ActionEvent event) {
        btnNext.setDisable(false);
        pickedAnswerIndex = 1;
    }

    @FXML
    void radioAnswerThreeOnAction(ActionEvent event) {
        btnNext.setDisable(false);
        pickedAnswerIndex = 2;
    }

    @FXML
    void radioAnswerFourOnAction(ActionEvent event) {
        btnNext.setDisable(false);
        pickedAnswerIndex = 3;
    }

    @FXML
    void initialize() {
        assert quizPane != null : "fx:id=\"quizPane\" was not injected: check your FXML file 'TriviaGuiQuizFXML.fxml'.";
        assert txtQuestion != null : "fx:id=\"txtQuestion\" was not injected: check your FXML file 'TriviaGuiQuizFXML.fxml'.";
        assert radioAnswerOne != null : "fx:id=\"radioAnswerOne\" was not injected: check your FXML file 'TriviaGuiQuizFXML.fxml'.";
        assert answers != null : "fx:id=\"answers\" was not injected: check your FXML file 'TriviaGuiQuizFXML.fxml'.";
        assert radioAnswerTwo != null : "fx:id=\"radioAnswerTwo\" was not injected: check your FXML file 'TriviaGuiQuizFXML.fxml'.";
        assert radioAnswerThree != null : "fx:id=\"radioAnswerThree\" was not injected: check your FXML file 'TriviaGuiQuizFXML.fxml'.";
        assert radioAnswerFour != null : "fx:id=\"radioAnswerFour\" was not injected: check your FXML file 'TriviaGuiQuizFXML.fxml'.";
        assert btnNext != null : "fx:id=\"btnNext\" was not injected: check your FXML file 'TriviaGuiQuizFXML.fxml'.";
        assert btnAudience != null : "fx:id=\"btnAudience\" was not injected: check your FXML file 'TriviaGuiQuizFXML.fxml'.";
        assert btnFriend != null : "fx:id=\"btnFriend\" was not injected: check your FXML file 'TriviaGuiQuizFXML.fxml'.";
        assert btnFifty != null : "fx:id=\"btnFifty\" was not injected: check your FXML file 'TriviaGuiQuizFXML.fxml'.";

        questionsInGame = game.getListOfQuestions();
        Collections.shuffle(questionsInGame);
        generateQuestion();
    }

    private void generateQuestion() {
        txtQuestion.setText(questionsInGame.get(answeredQuestions).getQuestionText());
        radioAnswerOne.setText(questionsInGame.get(answeredQuestions).getAnswerTextAtIndex(0));
        radioAnswerTwo.setText(questionsInGame.get(answeredQuestions).getAnswerTextAtIndex(1));

        if(questionsInGame.get(answeredQuestions).getQuestionType() == '1') {
            radioAnswerThree.setText(questionsInGame.get(answeredQuestions).getAnswerTextAtIndex(2));
            radioAnswerThree.setVisible(true);
            radioAnswerFour.setText(questionsInGame.get(answeredQuestions).getAnswerTextAtIndex(3));
            radioAnswerFour.setVisible(true);
        } else {
            radioAnswerThree.setVisible(false);
            radioAnswerFour.setVisible(false);
        }
    }

    private void uncheckRadioButtons() {
        radioAnswerOne.setSelected(false);
        radioAnswerTwo.setSelected(false);
        radioAnswerThree.setSelected(false);
        radioAnswerFour.setSelected(false);
    }

    private void evaluateGivenAnswer() {
        if(questionsInGame.get(answeredQuestions).isCorrectAnswerGiven(pickedAnswerIndex)) {
            System.out.println("Correct!");
            pointsFromQuestions += questionsInGame.get(answeredQuestions).getQuestionPoints();
        } else {
            System.out.println("Wrong!");
        }
    }
}