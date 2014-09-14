package lv.vdmakul.mindt.console;

import lv.vdmakul.mindt.calculation.CalculationService;
import lv.vdmakul.mindt.calculation.NeuedaCalculationService;
import lv.vdmakul.mindt.console.options.MindtOptionUtils;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import java.io.PrintStream;

import static lv.vdmakul.mindt.console.options.MindtOptionUtils.*;

public class ConsoleApp {

    private final PrintStream printStream;
    private final CalculationService calculationService;

    public static void main(String[] args) {
        new ConsoleApp(new NeuedaCalculationService(), System.out).runWithArgs(args);
    }

    public ConsoleApp(CalculationService calculationService, PrintStream printStream) {
        this.printStream = printStream;
        this.calculationService = calculationService;
    }

    protected void runWithArgs(String[] args) {
        try {
            Options options = MindtOptionUtils.createDefaultOptions();
            CommandLine line = MindtOptionUtils.parseOptions(options, args);
            MindtActions actions = new MindtActions(
                    calculationService,
                    MindtOptionUtils.createFromLine(line),
                    printStream,
                    () -> MindtOptionUtils.printHelpMessage(options, printStream));
            perform(actions, line);
        } catch (Exception e) {
            printStream.println("Aborted: " + e.getMessage());
        }
    }

    private void perform(MindtActions actions, CommandLine line) {
        if (printHelpOnly(line)) {
            actions.printHelp();
            return;
        }
        if (needToExport(line)) {
            actions.exportTestPlan();
        }
        if (skipTest(line)) {
            actions.skipTests();
            return;
        }
        if (needUpdateUrl(line)) {
            actions.updateUrl();
        }
        if (calculateFromMindMap(line)) {
            actions.testByPlanFromMindMap();
        } else {
            actions.testByPlanFromFile();
        }
    }
}
