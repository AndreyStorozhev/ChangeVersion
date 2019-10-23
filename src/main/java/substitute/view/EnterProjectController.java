package substitute.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import substitute.service.version.FinderFiles;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class EnterProjectController {
    @FXML
    private TextField filePath;

    @FXML
    private Button OkButton;

    @FXML
    void initialize() {
        OkButton.setOnAction(event -> goToDependencyChanges(FinderFiles.findAllPom(filePath.getText())));
    }

    /**
     * Метод осуществляет переход к новому контроллеру и передачи в него paths
     * @param paths пути ко всем pom в проекте
     */
    private void goToDependencyChanges(List<Path> paths) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getClassLoader().getResource("main-view.fxml"));
        load(fxmlLoader);
        ViewController controller = fxmlLoader.getController();
        controller.setPathsList(paths);
        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    private void load(FXMLLoader fxmlLoader) {
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
