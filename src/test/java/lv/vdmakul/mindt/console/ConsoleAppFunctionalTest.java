package lv.vdmakul.mindt.console;

import lv.vdmakul.mindt.calculation.LocalCalculationService;
import lv.vdmakul.mindt.calculation.NeuedaCalculationService;
import lv.vdmakul.mindt.console.options.OptionsParsingException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import static org.mockito.Mockito.verify;

public class ConsoleAppFunctionalTest {

    private ConsoleApp consoleApp;
    private PrintStream printStream;

    @Before
    public void setUp() throws Exception {
        printStream = Mockito.spy(System.out);
        consoleApp = new ConsoleApp(new LocalCalculationService(), printStream);
    }

    private void callWithArgs(String... args) throws OptionsParsingException, ParserConfigurationException, SAXException, IOException {
        consoleApp.runWithArgs(args);
    }

    @Test
    public void noArguments() throws Exception {
        callWithArgs();
        verify(printStream).println("either -mindmap or -suite option must be specified");
    }

    @Test
    public void exportWithoutMindMap() throws Exception {
        callWithArgs("-export", "export.file");
        verify(printStream).println("-mindmap option must be specified");
    }

    @Test
    public void exportMindMapWithSuite() throws Exception {
        callWithArgs("-mindmap", "mindmap.file", "-export", "export.file", "-suite", "suite.file");
        verify(printStream).println("-export option is not compatible with -suite option");
    }

    @Test
    public void skipTest() throws Exception {
        callWithArgs("-suite", "suite.file", "-skiptest");
        verify(printStream).println("Tests were skipped");
    }

    @Test
    public void test() throws Exception {
        callWithArgs("-mindmap", "src/test/resources/calc_tests.mm");
        verify(printStream).println("9 tests have been executed");
        verify(printStream).println("1 test(s) failed");
        verify(printStream).println("\nTest failed: division by negative number");
        verify(printStream).println("Expected: \t-4.47");
        verify(printStream).println("Actual: \t-4.472");
    }

    @Test
    public void exportAndThenTest() throws Exception {
        File file = File.createTempFile("test", "json");
        file.deleteOnExit();

        callWithArgs("-mindmap", "src/test/resources/calc_tests.mm", "-export", file.getAbsolutePath(), "-skiptest");
        verify(printStream).println("Test Suite successfully exported");

        callWithArgs("-suite", file.getAbsolutePath());
        verify(printStream).println("9 tests have been executed");
        verify(printStream).println("1 test(s) failed");
        verify(printStream).println("\nTest failed: division by negative number");
        verify(printStream).println("Expected: \t-4.47");
        verify(printStream).println("Actual: \t-4.472");
    }

    @Test
    public void help() throws Exception {
        callWithArgs("-help");
        //cannot test the output, commons-cli lib very sophisticated
//        verify(printStream).println("usage: neueda-mindt");
    }

    @Test
    @Ignore
    public void callRealNeuedaService() throws Exception {
        consoleApp = new ConsoleApp(new NeuedaCalculationService(), printStream);
        callWithArgs("-mindmap", "src/test/resources/calc_tests.mm");
        verify(printStream).println("9 tests have been executed");
        verify(printStream).println("All tests passed");
    }
}