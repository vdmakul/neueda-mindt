package lv.vdmakul.mindt.console;

import lv.vdmakul.mindt.calculation.CalculationService;
import lv.vdmakul.mindt.calculation.NeuedaCalculationService;
import lv.vdmakul.mindt.console.options.MindtOptionUtils;
import lv.vdmakul.mindt.console.options.OptionsParsingException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.PrintStream;

import static lv.vdmakul.mindt.console.options.MindtOptionUtils.*;

public class ConsoleApp {

    private final PrintStream printStream;
    private final CalculationService calculationService;

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        new ConsoleApp(new NeuedaCalculationService(), System.out).runWithArgs(args);
    }

    public ConsoleApp(CalculationService calculationService, PrintStream printStream) {
        this.printStream = printStream;
        this.calculationService = calculationService;
    }

    protected void runWithArgs(String[] args) throws ParserConfigurationException, SAXException, IOException {
        try {
            Options options = MindtOptionUtils.createDefaultOptions();
            CommandLine line = MindtOptionUtils.parseOptions(options, args);
            MindtActions actions = new MindtActions(
                    calculationService,
                    MindtOptionUtils.createFromLine(line),
                    printStream,
                    () -> MindtOptionUtils.printHelpMessage(options, printStream));
            perform(actions, line);
        } catch (OptionsParsingException e) {
            printStream.println(e.getMessage());
        }
    }

    private void perform(MindtActions actions, CommandLine line) throws IOException, SAXException, ParserConfigurationException {
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
