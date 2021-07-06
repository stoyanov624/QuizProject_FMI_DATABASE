package DatabaseManagement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DB2Manager {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;


    public void openConnection(){

// Step 1: Load IBM DB2 JDBC driver	

        try {
            DriverManager.registerDriver(new com.ibm.db2.jcc.DB2Driver());
        }

        catch(Exception cnfex) {

            System.out.println("Problem in loading or registering IBM DB2 JDBC driver");

            cnfex.printStackTrace();
        }

// Step 2: Opening database connection


        try {
            connection = DriverManager.getConnection("jdbc:db2://62.44.108.24:50000/SAMPLE", "db2admin", "db2admin");
            statement = connection.createStatement();
        }

        catch(SQLException s) {
            s.printStackTrace();
        }
    }

    public void closeConnection(){

        try {
            if(null != connection) {

                // cleanup resources, once after processing
                if(resultSet != null) {
                    resultSet.close();
                }

                statement.close();

                // and then finally close connection

                connection.close();
            }
        }

        catch (SQLException s) {

            s.printStackTrace();

        }
    }

    public List<String> getListOfResults(String stmnt, int row, int column) {
        ArrayList<String> result = new ArrayList<>();
        try {
            resultSet = statement.executeQuery(stmnt);
            for (int i = 0; i < row; i++) {
                resultSet.next();
            }

            for (int i = 1; i <= column; i++) {
                result.add(resultSet.getString(i));
            }
        }
        catch (SQLException s) {
            s.printStackTrace();
        }

        return result;
    }

    public String getSelectedColumnValue(String stmnt) {
        String resultColumnValue = new String();
        try {
            resultSet = statement.executeQuery(stmnt);
            resultSet.next();
            resultColumnValue = (resultSet.getString(1));
        }
        catch (SQLException s) {
            s.printStackTrace();
        }
        return resultColumnValue;
    }

    public boolean isExistingValueInTheDatabase(String selectStatement) {
        try {
            resultSet = statement.executeQuery(selectStatement);
            return resultSet.next();
        }
        catch (SQLException s) {
            s.printStackTrace();
        }
        return false;
    }

    public void executeUpdate(String stmnt) { // С това можем да insert-ваме, update-ваме и delete-ваме.
        try {
            statement.executeUpdate(stmnt);
        }

        catch (SQLException s){
            s.printStackTrace();
        }
        System.out.println("Successfully updated table!");
    }
}