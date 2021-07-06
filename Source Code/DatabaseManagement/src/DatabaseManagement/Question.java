package DatabaseManagement;

import java.util.Collections;
import java.util.List;

public class Question {
    private String questionID;
    private String questionText;
    private String difficulty;
    private List<Answer> answers;

    public Question(String questionID) {
        this.questionID = questionID;
        questionText = TriviaDataManager.getInstance().getQuestionText(questionID);
        difficulty = TriviaDataManager.getInstance().getQuestionDifficulty(questionID);
        answers = TriviaDataManager.getInstance().getAnswers(questionID);
        Collections.shuffle(answers);
    }

    @Override
    public String toString() {
        return String.format("Question:\n questionID: %s \n Question text: %s \n Answers:\n %s", questionID, questionText ,answers.toString());
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getAnswerTextAtIndex(int index) {
        return answers.get(index).getAnswerText();
    }

    public char getQuestionType() {
        return answers.get(0).getAnswerType();
    }

    public boolean isCorrectAnswerGiven(byte givenAnswerIndex) {
        return answers.get(givenAnswerIndex).isCorrect();
    }

    public int getQuestionPoints() {
        System.out.println('[' + difficulty + ']');
        if(difficulty.equals("easy")) {
            return 10;
        }

        if(difficulty.equals("medium")) {
            return 30;
        }

        if(difficulty.equals("hard")) {
            return 45;
        }

        return 0;
    }

    public String getQuestionID() {
        return questionID;
    }

    public Answer getAnswerAtIndex(byte givenAnswerIndex) {
        return answers.get(givenAnswerIndex);
    }
}
