package TriviaGui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import DatabaseManagement.TriviaDataManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class TriviaGuiRegisterController {

    @FXML
    private Label lblUsername;

    @FXML
    private Label lblPassword;

    @FXML
    private Label lblEmail;


    @FXML
    private AnchorPane registerPane;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtEmail;

    @FXML
    private Button btnRegister;

    @FXML
    private Button btnBack;


    @FXML
    void btnBackAction(ActionEvent event) throws IOException {
        goToMainScene();
    }

    @FXML
    void btnRegisterAction(ActionEvent event) throws IOException {
        setLabelsToInvisible();
        if(isSuccessfulRegistration()) {
            TriviaDataManager.getInstance().registerUser(txtEmail.getText(), txtUsername.getText(), txtPassword.getText());
            alertSuccessfulRegistration();
            goToMainScene();
        }
    }

    private boolean isSuccessfulRegistration() {
        return isValidUsername() && isValidPassword() && isValidEmail();
    }

    private boolean isValidEmail() {
        String email = txtEmail.getText();
        if(email.equals("")) {
            lblEmail.setText("Field can't be empty");
            lblEmail.setVisible(true);
            return false;
        }

        if(!email.matches("^(.+)@(\\S+)$")) {
            lblEmail.setText("Invalid email!");
            lblEmail.setVisible(true);
            return false;
        }

        if(TriviaDataManager.getInstance().isExistingEmail(email)) {
            lblEmail.setText("User with that email exists!");
            lblEmail.setVisible(true);
            return false;
        }

        return true;
    }


    private boolean isValidUsername() {
        String username = txtUsername.getText();
        if(username.equals("")) {
            lblUsername.setText("Field can't be empty");
            lblUsername.setVisible(true);
            return false;
        }

        if(username.length() < 6) {
            lblUsername.setText("Username must be 6 characters long!");
            lblUsername.setVisible(true);
            return false;
        }

        if(TriviaDataManager.getInstance().isExistingUsername(username)) {
            lblUsername.setText("User with that username exists!");
            lblUsername.setVisible(true);
            return false;
        }

        return true;
    }

    private boolean isValidPassword() {
        String password = txtPassword.getText();

        if(password.equals("")) {
            lblPassword.setText("Field can't be empty");
            lblPassword.setVisible(true);
            return false;
        }

        if(password.length() < 6) {
            lblPassword.setText("Password must be 6 characters long!");
            lblPassword.setVisible(true);
            return false;
        }

        if(!password.matches(".*\\d+.*")) {
            lblPassword.setText("Password must contain at least 1 number!");
            lblPassword.setVisible(true);
            return false;
        }

        return true;
    }



    private void setLabelsToInvisible() {
        lblUsername.setVisible(false);
        lblPassword.setVisible(false);
        lblEmail.setVisible(false);
    }

    private void goToMainScene() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("TriviaGuiMainFXML.fxml"));
        registerPane.getChildren().setAll(pane);
    }

    private void alertSuccessfulRegistration() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Successful registration");
        alert.setHeaderText("Registration Successful!");
        alert.setContentText(null);

        alert.showAndWait();
    }

    @FXML
    void initialize() {
        assert registerPane != null : "fx:id=\"registerPane\" was not injected: check your FXML file 'TriviaGuiRegisterFXML.fxml'.";
        assert txtPassword != null : "fx:id=\"txtPassword\" was not injected: check your FXML file 'TriviaGuiRegisterFXML.fxml'.";
        assert txtUsername != null : "fx:id=\"txtUsername\" was not injected: check your FXML file 'TriviaGuiRegisterFXML.fxml'.";
        assert txtEmail != null : "fx:id=\"txtEmail\" was not injected: check your FXML file 'TriviaGuiRegisterFXML.fxml'.";
        assert btnRegister != null : "fx:id=\"btnRegister\" was not injected: check your FXML file 'TriviaGuiRegisterFXML.fxml'.";
        assert btnBack != null : "fx:id=\"btnBack\" was not injected: check your FXML file 'TriviaGuiRegisterFXML.fxml'.";
        assert lblUsername != null : "fx:id=\"lblUsername\" was not injected: check your FXML file 'TriviaGuiRegisterFXML.fxml'.";
        assert lblPassword != null : "fx:id=\"lblPassword\" was not injected: check your FXML file 'TriviaGuiRegisterFXML.fxml'.";
        assert lblEmail != null : "fx:id=\"lblEmail\" was not injected: check your FXML file 'TriviaGuiRegisterFXML.fxml'.";
    }
}