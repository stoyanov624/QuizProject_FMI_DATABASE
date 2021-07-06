package TriviaGui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import DatabaseManagement.User;
import DatabaseManagement.UserHolder;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class TriviaGuiGameController {
    private User loggedUser;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane gamePane;

    @FXML
    private Button btnStart;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnQuit;

    @FXML
    private Text txtUsername;

    @FXML
    private Text txtPoints;


    @FXML
    void btnLogoutAction(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("TriviaGuiMainFXML.fxml"));
        gamePane.getChildren().setAll(pane);
    }

    @FXML
    void btnQuitAction(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void btnStartAction(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("TriviaGuiQuizFXML.fxml"));
        gamePane.getChildren().setAll(pane);
    }

    @FXML
    void initialize() {
        assert gamePane != null : "fx:id=\"gamePane\" was not injected: check your FXML file 'TriviaGuiGameFXML.fxml'.";
        assert btnStart != null : "fx:id=\"btnStart\" was not injected: check your FXML file 'TriviaGuiGameFXML.fxml'.";
        assert btnLogout != null : "fx:id=\"btnLogout\" was not injected: check your FXML file 'TriviaGuiGameFXML.fxml'.";
        assert btnQuit != null : "fx:id=\"btnQuit\" was not injected: check your FXML file 'TriviaGuiGameFXML.fxml'.";
        assert txtUsername != null : "fx:id=\"txtUsername\" was not injected: check your FXML file 'TriviaGuiGameFXML.fxml'.";
        assert txtPoints != null : "fx:id=\"txtPoints\" was not injected: check your FXML file 'TriviaGuiGameFXML.fxml'.";
        loggedUser = UserHolder.getInstance().getUser();
        txtUsername.setText(loggedUser.getUsername());
        txtPoints.setText("Points: " + loggedUser.getPoints());
    }
}