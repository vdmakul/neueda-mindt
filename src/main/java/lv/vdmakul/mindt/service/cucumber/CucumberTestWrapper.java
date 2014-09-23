package lv.vdmakul.mindt.service.cucumber;

import lv.vdmakul.mindt.internal.FileUtils;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

@Service
public class CucumberTestWrapper {

    private static final Object lock = new Object();

    public String internalTestByCucumber(String featureContent) {
        File tempFile = null;
        try {
            tempFile = FileUtils.createTempFile("test", ".feature");
            FileUtils.saverToFile(featureContent, tempFile.getAbsolutePath());

            Result result = runCucumber(tempFile.getAbsolutePath());

            return createTestResultMessage(result);

        } finally {
            if (tempFile != null) {
                tempFile.delete();
            }
        }
    }

    private Result runCucumber(String featurePath) {
        Result result;
        synchronized (lock) {
            System.setProperty("cucumber.options", featurePath);
            JUnitCore jUnitCore = new JUnitCore();
            result = jUnitCore.run(BddCalculatorTestTemplate.class);
        }
        return result;
    }

    private String createTestResultMessage(Result result) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);
            TextListener textListener = new TextListener(ps);
            textListener.testRunFinished(result);

            return baos.toString("UTF-8");
        } catch (IOException e) {
            throw new CucumberTestingException(e);
        }
    }
}
