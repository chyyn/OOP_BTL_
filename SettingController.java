package Main.Controller;

import Main.Animation.FadeAnimation;
import Main.Animation.RotateAnimation;
import Main.Animation.SlideAnimation;
import Main.DictionaryCMD.HTMLDictionary;
import Main.DictionaryCMD.textToHTML;
import com.jfoenix.controls.JFXButton;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingController implements Initializable {
    private boolean enable = true;
    private static boolean isDayTheme = true;

    public static boolean isIsDayTheme() {
        return isDayTheme;
    }

    public static void setIsDayTheme(boolean isDayTheme) {
        SettingController.isDayTheme = isDayTheme;
    }

    @FXML
    private AnchorPane dayButton;

    @FXML
    private AnchorPane nightButton;

    @FXML
    private AnchorPane sky;

    @FXML
    private ImageView star;

    @FXML
    private ImageView sun;

    @FXML
    private JFXButton resetSetting;
    @FXML
    private AnchorPane blockPane;
    @FXML
    private AnchorPane notificationPane;
    @FXML
    private JFXButton confirmButton;
    @FXML
    private JFXButton cancelButton;

    @FXML
    void changeTheme(MouseEvent event) {
        if(!enable) return;
        if(isDayTheme) {
            isDayTheme = false;
            changeToNight();
        } else {
            isDayTheme = true;
            changeToDay();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        blockPane.setVisible(false);
        notificationPane.setVisible(true);
        notificationPane.setTranslateY(700);
        dayButton.setTranslateX(159);
        FadeAnimation.fadeAnimation(nightButton, 1, 0, 0.01);
        star.setTranslateX(150);
        star.setTranslateY(-200);

        resetSetting.setOnMouseClicked(event -> {
            blockPane.setVisible(true);
            SlideAnimation.SlideToAnimation(notificationPane, 0, 0, 0.5);
        });
        confirmButton.setOnMouseClicked(event -> {
            try {
                textToHTML.resetHtmlDictionary();
                HTMLDictionary.insertFromHTMLFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            blockPane.setVisible(false);
            SlideAnimation.SlideToAnimation(notificationPane, 0, 700, 0.5);
        });
        cancelButton.setOnMouseClicked(event -> {
            blockPane.setVisible(false);
            SlideAnimation.SlideToAnimation(notificationPane, 0, 700, 0.5);
        });
    }

    public void changeToNight() {
        enable = false;
        SlideAnimation.SlideToAnimation(sky, 0, 330, 1);
        SlideAnimation.SlideToAnimation(sun, -150, 200, 1);
        SlideAnimation.SlideToAnimation(star, 0, 0, 1);
        FadeAnimation.fadeAnimation(nightButton, 0, 1, 1);
        RotateAnimation.rotateAnimation(dayButton, 360, 0, 1);
        SlideAnimation.SlideToAnimation(dayButton, 0, 0, 1);
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.play();
        pause.setOnFinished(actionEvent -> {
            enable = true;
        });
    }

    public void changeToDay() {
        enable = false;
        SlideAnimation.SlideToAnimation(sky, 0, 0, 1);
        SlideAnimation.SlideToAnimation(sun, 0, 0, 1);
        SlideAnimation.SlideToAnimation(star, 150, -200, 1);
        FadeAnimation.fadeAnimation(nightButton, 1, 0, 1);
        RotateAnimation.rotateAnimation(dayButton, 0, 360, 1);
        SlideAnimation.SlideToAnimation(dayButton, 159, 0, 1);
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.play();
        pause.setOnFinished(actionEvent -> {
            enable = true;
        });
    }


    public void saveNotification(ActionEvent actionEvent) {
        Image image = new Image(getClass().getResourceAsStream("/Main/PNG/check90px.png"));
        Notifications notifications = Notifications.create();
        notifications.graphic(new ImageView(image));
        notifications.text("You've log-in successfully");
        notifications.title("Success message");
        notifications.hideAfter(Duration.seconds(2));
        //notifications.position()
        //notifications.darkStyle();
        notifications.show();
    }
}
