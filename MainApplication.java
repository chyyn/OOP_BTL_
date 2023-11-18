package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainApplication extends Application {
    double x, y = 0;

    @Override
    public void start(Stage stage) throws IOException {
        /*Parent root = FXMLLoader.load(getClass().getResource("/Main/Interface/main.fxml"));
        stage.initStyle(StageStyle.UNDECORATED);

        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            stage.setX(event.getSceneX() - x);
            stage.setY(event.getSceneY() - y);
        });

        stage.setScene(new Scene(root, 720, 480));
        stage.show();*/


        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/Main/Interface/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 720, 480);
        stage.setTitle("Eng-Vi Dictionary");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
