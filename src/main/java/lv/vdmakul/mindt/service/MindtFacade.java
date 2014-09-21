package lv.vdmakul.mindt.service;

import lv.vdmakul.mindt.domain.TestPlan;
import lv.vdmakul.mindt.internal.infrastructure.FileUtils;
import lv.vdmakul.mindt.service.mindmap.MindMapParser;
import lv.vdmakul.mindt.service.testing.TestExecutor;
import lv.vdmakul.mindt.service.testing.TestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class MindtFacade {

    @Autowired private MindMapParser mindMapParser;
    @Autowired private TestExecutor testExecutor;

    public void exportTestPlan(String mindMapFileName, String exportFileName) {
        InputStream inputStream = FileUtils.asInputStream(mindMapFileName);
        TestPlan testPlan = mindMapParser.parseMindMap(inputStream);
        FileUtils.saverToFile(testPlan.asJson(), exportFileName);
    }

    public List<TestResult> testByPlanFromFile(String suiteFileName) {
        return testBy(TestPlan.fromJson(FileUtils.loadFile(suiteFileName)));
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
