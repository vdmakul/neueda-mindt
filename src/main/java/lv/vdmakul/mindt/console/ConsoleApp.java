package lv.vdmakul.mindt.console;

import lv.vdmakul.mindt.console.options.MindtOption;
import lv.vdmakul.mindt.console.options.OptionsHelper;
import lv.vdmakul.mindt.console.options.OptionsResolver;
import lv.vdmakul.mindt.internal.MindtProperties;
import lv.vdmakul.mindt.service.MindtFacade;
import lv.vdmakul.mindt.service.calculation.CalculationService;
import lv.vdmakul.mindt.service.calculation.NeuedaCalculationService;
import lv.vdmakul.mindt.service.mindmap.treeparser.MindMapTreeParser;
import lv.vdmakul.mindt.service.testing.TestResult;

import java.io.PrintStream;
import java.util.List;

public class ConsoleApp {

    private final PrintStream printStream;
    private final MindtFacade mindtFacade;

    public static void main(String[] args) {
        new ConsoleApp(new NeuedaCalculationService(), System.out).runWithArgs(args);
    }

    public ConsoleApp(CalculationService calculationService, PrintStream printStream) {
        this.printStream = printStream;
        this.mindtFacade = new MindtFacade(new MindMapTreeParser(), calculationService);
    }

    protected void runWithArgs(String[] args) {
        try {
            OptionsResolver optionsResolver = new OptionsResolver(args);
            performActions(optionsResolver);
        } catch (Exception e) {
            printStream.println("Aborted: " + e.getMessage());
        }
    }

    private void performActions(OptionsResolver optionsResolver) {
        if (optionsResolver.isSet(MindtOption.HELP)) {
            OptionsHelper.printHelpMessage(optionsResolver.getOptions(), printStream);
            return;
        }
        if (optionsResolver.isSet(MindtOption.EXPORT)) {
            mindtFacade.exportTestPlan(
                    optionsResolver.get(MindtOption.MINDMAP),
                    optionsResolver.get(MindtOption.EXPORT));
            print("Test Suite successfully exported");
        }
        if (optionsResolver.isSet(MindtOption.SKIPTEST)) {
            print("Tests were skipped");
            return;
        }
        if (optionsResolver.isSet(MindtOption.URL)) {
            MindtProperties.setProperty(MindtProperties.URL_PROPERTY, optionsResolver.get(MindtOption.URL));
        }

        List<TestResult> results;
        if (optionsResolver.isSet(MindtOption.MINDMAP)) {
            results = mindtFacade.testByPlanFromMindMap(optionsResolver.get(MindtOption.MINDMAP));
        } else {
            results = mindtFacade.testByPlanFromFile(optionsResolver.get(MindtOption.SUITE));
        }
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
