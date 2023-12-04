package Main.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;


public class LoginController {
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField textField;
    @FXML
    private JFXButton toggleButton;
    @FXML
    private ImageView toggleImage;

    public void initialize() {
        // Load the images
        Image viewImage = new Image("/Main/PNG/view.png");
        Image hideImage = new Image("/MAIN/PNG/hide.png");

        // Initially, set the image to 'view'
        toggleImage.setImage(viewImage);

        // When the toggle button is clicked, switch the visibility of the text and password fields and change the button image
        toggleButton.setOnAction(e -> {
            if (passwordField.isVisible()) {
                // If the password field is visible, hide it and show the text field, and change the button image to 'hide'
                textField.setText(passwordField.getText());
                passwordField.getStyleClass().add("hidden");
                textField.getStyleClass().remove("hidden");
                toggleImage.setImage(hideImage);
            } else {
                // If the text field is visible, hide it and show the password field, and change the button image to 'view'
                passwordField.setText(textField.getText());
                passwordField.getStyleClass().remove("hidden");
                textField.getStyleClass().add("hidden");
                toggleImage.setImage(viewImage);
            }
        });
    }

    @FXML
    private void handleMouseEnter() {
        // When the mouse enters the button, change the image to 'hide'
        toggleImage.setImage(new Image("/Main/PNG/hide.png"));
    }

    @FXML
    private void handleMouseExit() {
        // When the mouse exits the button, change the image back to 'view'
        toggleImage.setImage(new Image("/Main/PNG/view.png"));
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