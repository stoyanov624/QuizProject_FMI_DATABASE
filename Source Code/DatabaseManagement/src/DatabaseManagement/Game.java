package DatabaseManagement;

import java.util.*;

public class Game {
    private String gameID;
    private String userID;
    private Map<String, Question> questions;
    private char usedAudience = 'n';
    private char usedFifty = 'n';
    private char usedFriend = 'n';


    public Game(int numberOfQuestions, String userID) {
        gameID = TriviaDataManager.getInstance().generateUniqueGameID();
        this.userID = userID;
        questions = new HashMap<String, Question>();
        List<String> questionIDs = TriviaDataManager.getInstance().getUniqueRandomQuestionIDs(numberOfQuestions);
        for (String ID: questionIDs) {
            questions.put(ID, new Question(ID));
        }
        registerGameInDatabase();
    }

    public List<Question> getListOfQuestions() {
        List<Question> questionsInGame = new ArrayList<Question>();
        for (String ID: questions.keySet()) {
            questionsInGame.add(questions.get(ID));
        }

        return questionsInGame;
    }

    public void print() {
        System.out.println("Game ID: " + gameID + "\n");
        for (String ID: questions.keySet()) {
            System.out.println(questions.get(ID).toString());
        }
    }

    public String getGameID() {
        return gameID;
    }

    public void usingAudience() {
        usedAudience = 'y';
    }

    public void usingFriend() {
        usedFriend = 'y';
    }

    public void usingFifty() {
        usedFifty = 'y';
    }

    private void registerGameInDatabase() {
        String insertStatement = " INSERT INTO FN71975.GAMES VALUES ('";
        insertStatement += gameID + "','";
        insertStatement += userID + "','";
        insertStatement += usedAudience + "','";
        insertStatement += usedFriend + "','";
        insertStatement += usedFifty + "')";
        TriviaDataManager.getInstance().insertPlayedGame(insertStatement);
    }


    public char getUsedAudience() {
        return usedAudience;
    }

    public char getUsedFifty() {
        return usedFifty;
    }

    public char getUsedFriend() {
        return usedFriend;
    }
}
