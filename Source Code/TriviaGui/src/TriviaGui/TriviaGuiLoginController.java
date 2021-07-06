package TriviaGui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import DatabaseManagement.TriviaDataManager;
import DatabaseManagement.User;
import DatabaseManagement.UserHolder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class TriviaGuiLoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane loginPane;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnBack;

    @FXML
    private Label lblUsername;

    @FXML
    private Label lblPassword;

    @FXML
    private Button btnLogin;

    @FXML
    void btnBackAction(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("TriviaGuiMainFXML.fxml"));
        loginPane.getChildren().setAll(pane);
    }

    @FXML
    void btnLoginAction(ActionEvent event) throws IOException {
        setLabelsToInvisible();
        if(isLoginSuccessful()) {
            String username = txtUsername.getText();
            User user = new User(TriviaDataManager.getInstance().getUserId(username),
                                TriviaDataManager.getInstance().getUserPoints(username),
                                username);

            UserHolder userHolder = UserHolder.getInstance();
            userHolder.setUser(user);

            AnchorPane pane = FXMLLoader.load(getClass().getResource("TriviaGuiGameFXML.fxml"));
            loginPane.getChildren().setAll(pane);
        }
    }

    private boolean isLoginSuccessful() {
        return isUsernameValid() && isPasswordValid();
    }

    private boolean isUsernameValid() {
        String username = txtUsername.getText();
        if(username.equals("")) {
            lblUsername.setText("Field can't be empty");
            lblUsername.setVisible(true);
            return false;
        }

        if(!TriviaDataManager.getInstance().isExistingUsername(username)) {
            lblUsername.setText("Username doesn't exist");
            lblUsername.setVisible(true);
            return false;
        }

        return true;
    }

    private boolean isPasswordValid() {
        String password = txtPassword.getText();
        String username = txtUsername.getText();
        if(password.equals("")) {
            lblPassword.setText("Field can't be empty");
            lblPassword.setVisible(true);
            return false;
        }

        if(!TriviaDataManager.getInstance().isCorrectPassword(username, password)) {
            lblPassword.setText("Wrong password");
            lblPassword.setVisible(true);
            return false;
        }
        return true;
    }

    private void setLabelsToInvisible() {
        lblUsername.setVisible(false);
        lblPassword.setVisible(false);
    }

    @FXML
    void initialize() {
        assert loginPane != null : "fx:id=\"loginPane\" was not injected: check your FXML file 'TriviaGuiLoginFXML.fxml'.";
        assert txtUsername != null : "fx:id=\"txtUsername\" was not injected: check your FXML file 'TriviaGuiLoginFXML.fxml'.";
        assert txtPassword != null : "fx:id=\"txtPassword\" was not injected: check your FXML file 'TriviaGuiLoginFXML.fxml'.";
        assert btnBack != null : "fx:id=\"btnBack\" was not injected: check your FXML file 'TriviaGuiLoginFXML.fxml'.";
        assert lblUsername != null : "fx:id=\"lblUsername\" was not injected: check your FXML file 'TriviaGuiLoginFXML.fxml'.";
        assert lblPassword != null : "fx:id=\"lblPassword\" was not injected: check your FXML file 'TriviaGuiLoginFXML.fxml'.";
        assert btnLogin != null : "fx:id=\"btnRegister\" was not injected: check your FXML file 'TriviaGuiLoginFXML.fxml'.";
    }
}
