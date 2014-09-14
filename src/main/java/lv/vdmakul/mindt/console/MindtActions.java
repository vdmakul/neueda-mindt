package lv.vdmakul.mindt.console;

import lv.vdmakul.mindt.FileUtils;
import lv.vdmakul.mindt.calculation.CalculationService;
import lv.vdmakul.mindt.config.MindtProperties;
import lv.vdmakul.mindt.domain.test.TestPlan;
import lv.vdmakul.mindt.mindmap.treeparser.MindMapTreeParser;
import lv.vdmakul.mindt.testing.TestExecutor;
import lv.vdmakul.mindt.testing.TestResult;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

public class MindtActions {

    private final PrintStream printStream;
    private final MindtActionParameters parameters;
    private final Runnable helpPrinter;
    private final MindMapTreeParser mindMapTreeParser;
    private final TestExecutor testExecutor;

    public MindtActions(CalculationService calculationService, MindtActionParameters parameters, PrintStream printStream, Runnable helpPrinter) {
        this.parameters = parameters;
        this.printStream = printStream;
        this.helpPrinter = helpPrinter;
        this.testExecutor = new TestExecutor(calculationService);
        this.mindMapTreeParser = new MindMapTreeParser();
    }

    public void printHelp() {
        helpPrinter.run();
    }

    public void exportTestPlan() throws IOException, SAXException, ParserConfigurationException {
        InputStream inputStream = FileUtils.asInputStream(parameters.mindMapFileName);
        TestPlan testPlan = mindMapTreeParser.parseMindMap(inputStream);
        FileUtils.saverToFile(testPlan.asJson(), parameters.exportFileName);
        print("Test Suite successfully exported");
    }

    public void skipTests() {
        print("Tests were skipped");
    }

    public void updateUrl() {
        MindtProperties.setProperty(MindtProperties.URL_PROPERTY, parameters.url);
    }

    public void testByPlanFromFile() throws IOException {
        testBy(TestPlan.fromJson(FileUtils.loadFile(parameters.suiteFileName)));
    }

    public void testByPlanFromMindMap() throws IOException, SAXException, ParserConfigurationException {
        InputStream inputStream = FileUtils.asInputStream(parameters.mindMapFileName);
        testBy(mindMapTreeParser.parseMindMap(inputStream));
    }

    private void testBy(TestPlan testPlan) {
        List<TestResult> results = testExecutor.execute(testPlan.testSuites);
        printResults(results);
    }

    private void printResults(List<TestResult> results) {
        int overall = results.size();
        long failed = results.stream().filter(TestResult::failed).count();

        print(String.format("%s tests have been executed", overall));
        if (failed == 0) {
            print("All tests passed");
        } else {
            print(failed + " test(s) failed");
            results.stream().filter(TestResult::failed).forEach(r -> {
                print("\nTest failed: " + r.testName);
                print("Expected: \t" + r.expectedResult.getResultAsString());
                print("Actual: \t" + r.actualResult.getResultAsString());
            });
        }
    }

    private void print(String message) {
        printStream.println(message);
    }

}
