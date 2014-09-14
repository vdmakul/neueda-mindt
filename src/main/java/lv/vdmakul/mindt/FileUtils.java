package lv.vdmakul.mindt;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils { //todo repackage

    public static String loadFile(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
    }

    public static void saverToFile(String content, String fileName) throws IOException {
        Files.write(Paths.get(fileName), content.getBytes(StandardCharsets.UTF_8));
    }

    public static InputStream asInputStream(String fileName) throws FileNotFoundException {
        return new FileInputStream(fileName);
    }
}
