package lv.vdmakul.mindt.service;

import lv.vdmakul.mindt.calculation.CalculationService;
import lv.vdmakul.mindt.domain.TestPlan;
import lv.vdmakul.mindt.infrastructure.FileUtils;
import lv.vdmakul.mindt.mindmap.treeparser.MindMapTreeParser;
import lv.vdmakul.mindt.testing.TestExecutor;
import lv.vdmakul.mindt.testing.TestResult;

import java.io.InputStream;
import java.util.List;

public class MindtFacade {

    public final MindMapTreeParser mindMapTreeParser;
    public final TestExecutor testExecutor;

    public MindtFacade(MindMapTreeParser mindMapTreeParser, CalculationService calculationService) {
        this.mindMapTreeParser = mindMapTreeParser;
        this.testExecutor = new TestExecutor(calculationService);
    }

    public void exportTestPlan(String mindMapFileName, String exportFileName) {
        InputStream inputStream = FileUtils.asInputStream(mindMapFileName);
        TestPlan testPlan = mindMapTreeParser.parseMindMap(inputStream);
        FileUtils.saverToFile(testPlan.asJson(), exportFileName);
    }

    public List<TestResult> testByPlanFromFile(String suiteFileName) {
        return testBy(TestPlan.fromJson(FileUtils.loadFile(suiteFileName)));
    }

    public List<TestResult> testByPlanFromMindMap(String mindMapFileName) {
        InputStream inputStream = FileUtils.asInputStream(mindMapFileName);
        return testBy(mindMapTreeParser.parseMindMap(inputStream));
    }

    private List<TestResult> testBy(TestPlan testPlan) {
        return testExecutor.execute(testPlan.testSuites);
    }
}
