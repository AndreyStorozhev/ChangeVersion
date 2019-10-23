package substitute.service.version;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FinderFiles {

    /**
     * Метод ищет все pom файлы в проекте
     * @return лист путей ко всем найденным pom
     * @param pathProject путь к проекту
     */
    public static List<Path> findAllPom(String pathProject) {
        try {
            return Files.find(Paths.get(pathProject), 20, (path, attr) -> path.getFileName().toString().equals("pom.xml")).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
