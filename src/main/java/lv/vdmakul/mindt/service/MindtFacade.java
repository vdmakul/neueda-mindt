package lv.vdmakul.mindt.service;

import lv.vdmakul.mindt.domain.TestPlan;
import lv.vdmakul.mindt.internal.infrastructure.FileUtils;
import lv.vdmakul.mindt.service.cucumber.CucumberFeatureService;
import lv.vdmakul.mindt.service.cucumber.CucumberTestWrapper;
import lv.vdmakul.mindt.service.mindmap.MindMapParser;
import lv.vdmakul.mindt.service.testing.TestExecutor;
import lv.vdmakul.mindt.service.testing.TestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Set;

@Service
public class MindtFacade {

    @Autowired private MindMapParser mindMapParser;
    @Autowired private TestExecutor testExecutor;
    @Autowired private CucumberFeatureService cucumberFeatureService;
    @Autowired private CucumberTestWrapper cucumberTestWrapper;

    private TestPlan transformFromMindMap(String mindMapFileName) {
        InputStream inputStream = FileUtils.asInputStream(mindMapFileName);
        return mindMapParser.parseMindMap(inputStream);
    }

    public void exportTestPlan(String mindMapFileName, String exportFileName) {
        TestPlan testPlan = transformFromMindMap(mindMapFileName);
        FileUtils.saverToFile(testPlan.asJson(), exportFileName);
    }

    public Set<String> exportTestPlanAsFeature(String mindMapFileName, String exportFolder) {
        TestPlan testPlan = transformFromMindMap(mindMapFileName);
        return cucumberFeatureService.saveAsFeatures(testPlan, exportFolder);
    }

    public List<TestResult> testByPlanFromFile(String suiteFileName) {
        return testBy(TestPlan.fromJson(FileUtils.loadFile(suiteFileName)));
    }

    public List<String> testByGherkinScenarios(String featureFolder) {
        return cucumberTestWrapper.externallyTestByCucumber(featureFolder);
    }

    public List<TestResult> testByPlanFromMindMap(String mindMapFileName) {
        InputStream inputStream = FileUtils.asInputStream(mindMapFileName);
        return testByPlanFromMindMap(inputStream);
    }

    public List<TestResult> testByPlanFromMindMap(InputStream mindMapInputStream) {
        return testBy(mindMapParser.parseMindMap(mindMapInputStream));
    }

    private List<TestResult> testBy(TestPlan testPlan) {
        return testExecutor.execute(testPlan.testSuites);
    }
}
