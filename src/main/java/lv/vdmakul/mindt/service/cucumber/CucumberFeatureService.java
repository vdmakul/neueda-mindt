package lv.vdmakul.mindt.service.cucumber;

import lv.vdmakul.mindt.domain.Test;
import lv.vdmakul.mindt.domain.TestPlan;
import lv.vdmakul.mindt.domain.TestSuite;
import lv.vdmakul.mindt.internal.infrastructure.FileUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CucumberFeatureService {

    public static final String LINE = "\n";

    public Set<String> saveAsFeatures(TestPlan testPlan, String exportFolder) {
        Map<String, String> features = transformToFeatures(testPlan);
        for (Map.Entry<String, String> entry : features.entrySet()) {
            FileUtils.saverToFile(entry.getValue(), exportFolder + "//" + entry.getKey() + ".feature");
        }
        return features.entrySet().stream()
                .map(entry -> entry.getKey() + ".feature")
                .collect(Collectors.toSet());
    }

    private Map<String, String> transformToFeatures(TestPlan testPlan) {
        HashMap<String, String> features = new HashMap<>();
        for (TestSuite suite : testPlan.testSuites) {
            StringBuilder featureBuilder = new StringBuilder();
            appendFeature(suite, featureBuilder);
            appendBackground(suite, featureBuilder);
            for (Test test : suite.tests) {
                appendScenario(featureBuilder, test);
            }
            features.put(suite.name, featureBuilder.toString());
        }
        return features;
    }

    private void appendFeature(TestSuite suite, StringBuilder feature) {
        feature.append("Feature: ").append(suite.name).append(LINE);
        feature.append(LINE);
    }

    private void appendBackground(TestSuite suite, StringBuilder feature) {
        feature.append("Background: ").append(LINE);
        feature.append("\tGiven A local calculator").append(LINE);
        feature.append("\tAnd request path is \"").append(suite.request.path).append("\"").append(LINE);
        feature.append("\tAnd request method is \"").append(suite.request.method).append("\"").append(LINE);
        feature.append(LINE);
    }

    private void appendScenario(StringBuilder feature, Test test) {
        feature.append("Scenario: ").append(test.name).append(LINE);
        feature.append("\tWhen I enter ").append(test.variableOne).append(" and ").append(test.variableTwo)
                .append(LINE);
        feature.append("\tThen result is ").append(test.expectedResult.getResultAsString()).append(LINE);
        feature.append(LINE);
    }
}
