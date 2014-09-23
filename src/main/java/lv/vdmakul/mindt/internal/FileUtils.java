package lv.vdmakul.mindt.internal;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {

    public static File createTempFile(String prefix, String suffix) {
        try {
            return Files.createTempFile(prefix, suffix).toFile();
        } catch (IOException e) {
            throw new FileException(e.getMessage(), e);
        }
    }

    public static String loadFile(String fileName) {
        checkIfExists(fileName);
        try {
            return new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new FileException(e.getMessage(), e);
        }
    }

    public static void saverToFile(String content, String fileName) {
        saverToFile(content, Paths.get(fileName));
    }

    public static void saverToFile(String content, Path path) {
        try {
            Files.write(path, content.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new FileException(e.getMessage(), e);
        }
    }

    public static InputStream asInputStream(String fileName) {
        checkIfExists(fileName);
        try {
            return new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            throw new FileException(e.getMessage(), e);
        }
    }

    private static void checkIfExists(String fileName) {
        if (!Files.exists(Paths.get(fileName))) {
            throw new FileException("File does not exist: " + fileName);
        }
    }
}
