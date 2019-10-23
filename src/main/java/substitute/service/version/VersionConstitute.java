package substitute.service.version;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class VersionConstitute {
    private Path filePath;

    /**
     * Метод заменят все версии указанных зависимостей
     * на пользовательские игнорируя ренж версий и ${}
     * @param dependence имя зависимости
     * @param version версия зависимости
     */
    public void replaceDependencies(String dependence, String version) {
        List<String> allLines = readAllLines(filePath);
        for (int i = 0; i < allLines.size(); i++) {
            String line = allLines.get(i);
            if (line.contains(dependence)) {
                if (line.contains("<version>") && !line.contains("$") && !line.contains("(,")) {
                    allLines.set(i, line.replaceAll(getReplaceVersion(line), version));
                } else if (allLines.get(i + 1).contains("<version>") && !line.contains("$") && !line.contains("(,")) {
                    String nextLine = allLines.get(i + 1);
                    allLines.set(i + 1, nextLine.replaceAll(getReplaceVersion(nextLine), version));
                }
            }
        }
        writeFile(allLines);
    }

    private String getReplaceVersion(String line) {
        String[] strings = line.trim().split(" ");
        String versionString = strings[strings.length - 1];
        return versionString.substring(9, versionString.length() - 10);
    }

    private List<String> readAllLines(Path path) {
        List<String> allLines;
        try {
            allLines = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось прочитать файл " + path);
        }
        return allLines;
    }

    private void writeFile(List<String> allLines) {
        try {
            Files.write(filePath, allLines, StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new RuntimeException("Возникла ошибка при записи файла " + e.getMessage());
        }
    }

    public void setFilePath(Path filePath) {
        this.filePath = filePath;
    }
}
