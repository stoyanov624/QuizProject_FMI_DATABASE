package DatabaseManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class TriviaDataManager {
    private final DB2Manager db2M;

    private final static TriviaDataManager INSTANCE = new TriviaDataManager();

    public static TriviaDataManager getInstance() {
        return INSTANCE;
    }

    public TriviaDataManager() {
        db2M = new DB2Manager();
    }

    private String getRandomFiveDigitID() {
        StringBuilder id = new StringBuilder();
        Random random = new Random();
        id.append(random.nextInt(9) + 1);
        for (int i = 1; i < 5; i++) {
            id.append(random.nextInt(9));
        }
        return id.toString();
    }

    private boolean isExistingGame(String gameID) {
        db2M.openConnection();
        String selectStatement = String.format("SELECT 1 FROM FN71975.USERS WHERE ID = '%s'", gameID);
        boolean check = db2M.isExistingValueInTheDatabase(selectStatement);
        db2M.closeConnection();
        return check;
    }

    public String generateUniqueGameID() {
        String gameID;

        do {
            gameID = getRandomFiveDigitID();
        } while(isExistingGame(gameID));

        return gameID;
    }

    public void insertPlayedGame(String statement) {
        db2M.openConnection();
        db2M.executeUpdate(statement);
        db2M.closeConnection();
    }

    public String getUserId(String username) {
        db2M.openConnection();
        String selectStatement = String.format("SELECT ID FROM FN71975.USERS WHERE USERNAME = '%s'", username);
        String ID = db2M.getSelectedColumnValue(selectStatement);
        db2M.closeConnection();

        return ID;
    }


    public String getQuestionText(String questionID) {
        db2M.openConnection();
        String selectStatement = String.format("SELECT TEXT FROM FN71975.QUESTIONS WHERE ID = '%s'", questionID);
        String text = db2M.getSelectedColumnValue(selectStatement);
        db2M.closeConnection();

        return text;
    }

    public List<Answer> getAnswers(String questionID) {
        db2M.openConnection();

        final int INFO_COLUMNS = 4;
        byte numberOfQuestions = (getNumberOfQuestionsNeeded(questionID));
        byte row_info = 1;

        List<Answer> result = new ArrayList<>();
        List<String> answerInfo;
        Answer answer;

        while(numberOfQuestions > 0) {
            answerInfo = db2M.getListOfResults(String.format("SELECT * FROM FN71975.ANSWERS WHERE QUESTION_ID = %s", questionID), row_info, INFO_COLUMNS);
            answer = new Answer(answerInfo.get(0), answerInfo.get(1).charAt(0), answerInfo.get(3).charAt(0), answerInfo.get(2));
            result.add(answer);
            row_info++;
            numberOfQuestions--;
        }

        db2M.closeConnection();
        return result;
    }


    public void updateHelpers(Game game) {
        db2M.openConnection();
        String updateStatement = String.format("UPDATE FN71975.GAMES SET AUDIENCE_HELP = '%c', FRIEND_HELP = '%c', FIFTY_FIFTY = '%c' WHERE ID = '%s'",
                game.getUsedAudience(), game.getUsedFriend(), game.getUsedFifty(), game.getGameID());
        db2M.executeUpdate(updateStatement);
        db2M.closeConnection();
    }

    public void updateUserPoints(String userID, int points) {
        db2M.openConnection();
        String updateStatement = String.format("UPDATE FN71975.USERS SET POINTS = %d WHERE ID = %s", points, userID);
        db2M.executeUpdate(updateStatement);
        db2M.closeConnection();
    }

    public byte getNumberOfQuestionsNeeded(String questionID) {
        db2M.openConnection();
        String selectStatement = String.format("SELECT TYPE FROM FN71975.ANSWERS WHERE QUESTION_ID = %s", questionID);
        char type = db2M.getSelectedColumnValue(selectStatement).charAt(0);

        switch (type) {
            case '1' : return 4;
            case '2' : return 2;
        }
        return 0;
    }

    public int getUserPoints(String username) {
        db2M.openConnection();
        String selectStatement = String.format("SELECT POINTS FROM FN71975.USERS WHERE USERNAME = '%s'", username);
        String ID = db2M.getSelectedColumnValue(selectStatement);
        db2M.closeConnection();

        return Integer.parseInt(ID);
    }


    public boolean isCorrectPassword(String username, String password) {
        db2M.openConnection();
        String selectStatement = String.format("SELECT 1 FROM FN71975.USERS WHERE USERNAME = '%s' AND PASSWORD = '%s'", username, password);
        boolean check = db2M.isExistingValueInTheDatabase(selectStatement);
        db2M.closeConnection();
        return check;
    }

    public boolean isExistingEmail(String email) {
        db2M.openConnection();
        String selectStatement = String.format("SELECT 1 FROM FN71975.USERS WHERE EMAIL = '%s'", email);
        boolean check = db2M.isExistingValueInTheDatabase(selectStatement);
        db2M.closeConnection();
        return check;
    }

    public boolean isExistingUsername(String username) {
        db2M.openConnection();
        String selectStatement = String.format("SELECT 1 FROM FN71975.USERS WHERE USERNAME = '%s'", username);
        boolean check = db2M.isExistingValueInTheDatabase(selectStatement);
        db2M.closeConnection();
        return check;
    }

    public void registerUser(String email, String userName, String password) {
        db2M.openConnection();
        String userID;
        String selectStatement;
        do {
            selectStatement = "SELECT 1 FROM FN71975.USERS WHERE ID = ";
            userID = getRandomFiveDigitID();
            selectStatement += userID;
        } while(db2M.isExistingValueInTheDatabase(selectStatement));

        String insertStatement = " INSERT INTO FN71975.USERS VALUES ('" + userID + "'," + "DEFAULT,'";
        insertStatement += email + "','";
        insertStatement += userName + "','";
        insertStatement += password + "')";

        db2M.executeUpdate(insertStatement);
        db2M.closeConnection();
    }

    public List<String> getUniqueRandomQuestionIDs(int numberOfQuestionWanted) {
        db2M.openConnection();

        StringBuilder stringBuilder = new StringBuilder("''");
        List<String> result = new ArrayList<>();
        do {
            String selectStatement = String.format("SELECT ID FROM FN71975.QUESTIONS WHERE ID NOT IN(%s) ORDER BY RAND() FETCH FIRST 1 ROWS ONLY", stringBuilder.toString());
            String ID = db2M.getSelectedColumnValue(selectStatement);
            result.add(ID);
            stringBuilder.append(",'").append(ID).append("'");
            numberOfQuestionWanted--;
        } while(numberOfQuestionWanted > 0);
        db2M.closeConnection();

        return result;
    }

    public String getQuestionDifficulty(String questionID) {
        db2M.openConnection();
        String selectStatement = String.format("SELECT DIFFICULTY FROM FN71975.QUESTIONS WHERE ID = '%s'", questionID);
        String text = db2M.getSelectedColumnValue(selectStatement);
        db2M.closeConnection();

        return text;
    }

    public void insertAnsweredQuestion(Game game, User user, Question question, Answer answer) {
        db2M.openConnection();
        String insertStatement = " INSERT INTO FN71975.ANSWERED_QUESTIONS VALUES ('";
        insertStatement += game.getGameID() + "','";
        insertStatement += UserHolder.getInstance().getUser().getId() + "','";
        insertStatement += question.getQuestionID() + "','";
        insertStatement += answer.getAnswerID() + "')";
        db2M.executeUpdate(insertStatement);
        db2M.closeConnection();
    }
}
