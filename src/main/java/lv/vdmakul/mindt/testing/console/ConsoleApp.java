package lv.vdmakul.mindt.testing.console;

import lv.vdmakul.mindt.calculation.NeuedaCalculationService;
import lv.vdmakul.mindt.domain.test.TestPlan;
import lv.vdmakul.mindt.mindmap.treeparser.MindMapTreeParser;
import lv.vdmakul.mindt.testing.TestExecutor;
import lv.vdmakul.mindt.testing.TestResult;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class ConsoleApp {

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        if (args.length == 0) {
            System.out.println("MindMap file path argument missing");
            return;
        }

        TestExecutor testExecutor = new TestExecutor(new NeuedaCalculationService());
//        TestExecutor testExecutor = new TestExecutor(new LocalCalculationService());

        FileInputStream inputStream = new FileInputStream(new File(args[0]));
        TestPlan testPlan = new MindMapTreeParser().parseMindMap(inputStream);
        List<TestResult> results = testExecutor.execute(testPlan.testSuites);

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

    private static void print(String message) {
        System.out.println(message);
    }

}
