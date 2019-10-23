package substitute.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import substitute.service.version.VersionConstitute;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewController {
    private Map<String, String> depends = new HashMap<>();

    private VersionConstitute versionConstitute = new VersionConstitute();

    private List<Path> pathsList;

    @FXML
    private AnchorPane scene;

    @FXML
    private Button refreshDependencies;

    @FXML
    private Button appendButton;

    private int numberEnd = 1;


    @FXML
    void initialize() {
        appendButton.setOnAction(event -> addTextFields());

        refreshDependencies.setOnAction(event -> {
            getDependInUi();
            changeDepend();
        });
    }

    /**
     * Метод выполняет замену версий у указанных зависимостей
     */
    private void changeDepend() {
        for (Path path : pathsList) {
            versionConstitute.setFilePath(path);
            for (String key : depends.keySet()) {
                versionConstitute.replaceDependencies(key, depends.get(key));
            }
        }
    }

    /**
     * По нажатию на кнопку добавлем новые поля ввода
     */
    private void addTextFields() {
        ObservableList<Node> children = scene.getChildren();
        int count = 0;
        String suffix = String.valueOf(numberEnd);
        for (Node node : children) {
            if (node.getTypeSelector().equals("TextField") && !node.isVisible() && node.getId().endsWith(suffix)) {
                node.setVisible(true);
                count++;

                if (count % 2 == 0) {
                    numberEnd++;
                    break;
                }
            }
        }
    }

    /**
     * Метод выполняет заполнение мапы (artifactId, version) с UI
     * по нажатию на кнопку
     */
    private void getDependInUi() {
        ObservableList<Node> children = scene.getChildren();
        int count = 0;
        int endsOnGettingValues = 0;
        String nameDepend = "";
        String versionDepend = "";
        for (int i = 0; i < children.size(); i++) {
            Node node = children.get(i);
            if (node.getTypeSelector().equals("TextField") && node.isVisible() && node.getId().endsWith(String.valueOf(endsOnGettingValues))) {
                count++;

                if (node.getId().contains("Name")) {
                    TextField textField = (TextField) node;
                    nameDepend = textField.getText();
                    textField.setText(null);
                } else {
                    TextField textField = (TextField) node;
                    versionDepend = textField.getText();
                    textField.setText(null);
                }

                if (count % 2 == 0) {
                    endsOnGettingValues++;
                    depends.put(nameDepend, versionDepend);
                    i = 0;
                }
            }
        }
    }

    void setPathsList(List<Path> pathsList) {
        this.pathsList = pathsList;
    }
}
