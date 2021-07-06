package DatabaseManagement;

public class Answer {
    private String ID;
    private char answerType;
    private char correctness;
    private String answerText;

    public Answer(String ID, char answerType, char correctness, String answerText) {
        this.ID = ID;
        this.answerType = answerType;
        this.correctness = correctness;
        this.answerText = answerText;
    }

    @Override
    public String toString() {
        return String.format("Answer:\n ID: %s \n Type: %c \n Correctness: %c \n Text: %s \n\n", ID, answerType, correctness , answerText);
    }

    public String getAnswerText() {
        return answerText;
    }

    public char getAnswerType() {
        return answerType;
    }

    public boolean isCorrect() {
        return correctness == 'y';
    }

    public String getAnswerID() {
        return ID;
    }
}
