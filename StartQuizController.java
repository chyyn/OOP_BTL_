package Main.Controller.Quiz;

import Main.Controller.RootLayoutController;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;

public class StartQuizController {
    @FXML
    private JFXButton startquizbtn;


    @FXML
    public void initialize() {
        startquizbtn.setOnMouseClicked(event -> loadPage("/Main/Interface/Quiz/quiz.fxml"));
        RootLayoutController.getInstance().loadPage("/Main/Interface/Quiz/quiz.fxml");
    }

    private void loadPage(String fxmlFile) {
        try {
            // Get the current stage
            Stage stage = (Stage) startquizbtn.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}