package Main.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private JFXButton dictionaryicon;

    @FXML
    private JFXButton dictionarytxt;

    @FXML
    private JFXButton favoriteicon;

    @FXML
    private JFXButton favoritetxt;

    @FXML
    private JFXButton loginicon;

    @FXML
    private JFXButton logintxt;

    @FXML
    private Label menu;

    @FXML
    private Label menuBack;

    @FXML
    private JFXButton quizgameicon;

    @FXML
    private JFXButton quizgametxt;

    @FXML
    private JFXButton settingicon;

    @FXML
    private AnchorPane sidebar;

    @FXML
    private AnchorPane slider;

    @FXML
    private JFXButton translatoricon;

    @FXML
    private JFXButton translatortxt;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        slider.setTranslateX(-500);

        menu.setOnMouseEntered(event -> slideIn());
        dictionaryicon.setOnMouseEntered(event -> slideIn());
        translatoricon.setOnMouseEntered(event -> slideIn());
        favoriteicon.setOnMouseEntered(event -> slideIn());
        quizgameicon.setOnMouseEntered(event -> slideIn());
        loginicon.setOnMouseEntered(event -> slideIn());
        settingicon.setOnMouseEntered(event -> slideIn());

        menu.setOnMouseExited(event -> slideOut());
        sidebar.setOnMouseExited(event -> slideOut());
        slider.setOnMouseExited(event -> slideOut());

        dictionarytxt.setOnMouseClicked(event -> loadPage("/Main/Interface/main.fxml"));
        translatortxt.setOnMouseClicked(event -> loadPage("/Main/Interface/translator.fxml"));
        favoritetxt.setOnMouseClicked(event -> loadPage("/Main/Interface/favorite.fxml"));
        quizgametxt.setOnMouseClicked(event -> loadPage("/Main/Interface/quizgame.fxml"));
        logintxt.setOnMouseClicked(event -> loadPage("/Main/Interface/login.fxml"));
    }

    private void slideIn() {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.5));
        slide.setNode(slider);

        slide.setToX(0);
        slide.play();

        slider.setTranslateX(-500);

        slide.setOnFinished((ActionEvent e) -> {
            menu.setVisible(false);
            menuBack.setVisible(true);
        });
    }

    private void slideOut() {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.5));
        slide.setNode(slider);

        slide.setToX(-500);
        slide.play();

        slider.setTranslateX(0);

        slide.setOnFinished((ActionEvent e) -> {
            menu.setVisible(true);
            menuBack.setVisible(false);
        });
    }

    private void loadPage(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Scene scene = new Scene(root);
            Stage stage = (Stage) slider.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
