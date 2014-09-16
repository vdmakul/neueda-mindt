package lv.vdmakul.mindt.service;

import lv.vdmakul.mindt.domain.TestPlan;
import lv.vdmakul.mindt.internal.infrastructure.FileUtils;
import lv.vdmakul.mindt.service.calculation.CalculationService;
import lv.vdmakul.mindt.service.mindmap.MindMapParser;
import lv.vdmakul.mindt.service.testing.TestExecutor;
import lv.vdmakul.mindt.service.testing.TestResult;

import java.io.InputStream;
import java.util.List;

public class MindtFacade {

    public final MindMapParser mindMapParser;
    public final TestExecutor testExecutor;

    public MindtFacade(MindMapParser mindMapParser, CalculationService calculationService) {
        this.mindMapParser = mindMapParser;
        this.testExecutor = new TestExecutor(calculationService);
    }

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
        return testBy(mindMapParser.parseMindMap(inputStream));
    }

    private List<TestResult> testBy(TestPlan testPlan) {
        return testExecutor.execute(testPlan.testSuites);
    }
}
