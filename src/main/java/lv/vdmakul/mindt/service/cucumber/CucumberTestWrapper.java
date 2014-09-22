package lv.vdmakul.mindt.service.cucumber;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CucumberTestWrapper {

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

}
