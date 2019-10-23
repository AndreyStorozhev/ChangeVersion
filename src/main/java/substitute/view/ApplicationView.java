package substitute.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class ApplicationView extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent load = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("enter-project-window.fxml")));
        primaryStage.setTitle("Обновление зависимостей");
        primaryStage.setScene(new Scene(load));
        primaryStage.show();
    }
}