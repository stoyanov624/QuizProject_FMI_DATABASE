module TriviaGui {
    requires javafx.fxml;
    requires javafx.controls;

    requires DatabaseManagement;

    opens TriviaGui to javafx.fxml;
    exports TriviaGui to javafx.graphics;
}