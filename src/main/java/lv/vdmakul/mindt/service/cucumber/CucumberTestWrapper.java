package lv.vdmakul.mindt.service.cucumber;

import lv.vdmakul.mindt.internal.FileUtils;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class CucumberTestWrapper {

    private static final Object lock = new Object();

    public List<String> externallyTestByCucumber(String featureFolder) {
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("./gradlew cucumber -Dmindt.features=" + featureFolder + "");

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

            ArrayList<String> output = new ArrayList<>();

            String line;
            while ((line = stdInput.readLine()) != null) {
                output.add(line);
            }
            return output;
        } catch (IOException e) {
            throw new RuntimeException(e); //todo specify exception class
        }

    }

    public String internalTestByCucumber(String featureContent) {
        Result result = null;
        Path tempFile = null;
        try {
            tempFile = Files.createTempFile("test", ".feature");

            FileUtils.saverToFile(featureContent, tempFile.toAbsolutePath());

            synchronized (lock) {
                System.setProperty("cucumber.options", tempFile.toAbsolutePath().toString());
                JUnitCore jUnitCore = new JUnitCore();
                result = jUnitCore.run(BddCalculatorTestTemplate.class);
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);
            TextListener textListener = new TextListener(ps);
            textListener.testRunFinished(result);

            return baos.toString("UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e); //todo specify exception class
        } finally {
            if (tempFile != null) {
                tempFile.toFile().delete();
            }
        }
    }

}
