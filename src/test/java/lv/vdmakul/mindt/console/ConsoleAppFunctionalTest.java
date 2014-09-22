package lv.vdmakul.mindt.console;

import lv.vdmakul.mindt.service.calculation.LocalCalculationService;
import lv.vdmakul.mindt.service.calculation.NeuedaCalculationService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.mockito.Mockito.verify;

public class ConsoleAppFunctionalTest {

    private ConsoleApp consoleApp;
    private PrintStream printStream;

    @Before
    public void setUp() {
        printStream = Mockito.spy(System.out);
        consoleApp = new ConsoleApp(new LocalCalculationService(), printStream);
    }

    private void callWithArgs(String... args) {
        consoleApp.runWithArgs(args);
    }

    @Test
    public void noArguments() {
        callWithArgs();
        verify(printStream).println("Aborted: either -mindmap or -suite option must be specified");
    }

    @Test
    public void exportWithoutMindMap() {
        callWithArgs("-export", "export.file");
        verify(printStream).println("Aborted: -mindmap option must be specified");
    }

    @Test
    public void exportMindMapWithSuite() {
        callWithArgs("-mindmap", "mindmap.file",
                "-export", "export.file",
                "-format", "json",
                "-suite", "suite.file");
        verify(printStream).println("Aborted: -export option is not compatible with -suite option");
    }

    @Test
    public void exportMindMapWithoutFormat() {
        callWithArgs("-mindmap", "mindmap.file",
                "-export", "export.file",
                "-skiptest");
        verify(printStream).println("Aborted: -format must be specified");
    }

    @Test
    public void exportMindMapWithUnknownFormat() {
        callWithArgs("-mindmap", "mindmap.file",
                "-export", "export.file",
                "-format", "myFormat",
                "-skiptest");
        verify(printStream).println("Aborted: format myFormat is not supported");
    }

    @Test
    public void fielNotFound() {
        callWithArgs("-suite", "suite.file", "-format", "json", "-skiptest");
        verify(printStream).println("Aborted: suite.file does not exist");
    }

    @Test
    public void skipTest() {
        callWithArgs("-suite", "README.md", "-format", "json", "-skiptest");
        verify(printStream).println("Tests were skipped");
    }

    @Test
    public void test() {
        callWithArgs("-mindmap", "src/test/resources/calc_tests.mm");
        verify(printStream).println("9 tests have been executed");
        verify(printStream).println("1 test(s) failed");
        verify(printStream).println("\nTest failed: division by negative number");
        verify(printStream).println("Expected: \t-4.47");
        verify(printStream).println("Actual: \t-4.472");
    }

    @Test
    public void exportJsonAndThenTest() throws IOException {
        File file = File.createTempFile("test", "json");
        file.deleteOnExit();

        callWithArgs("-mindmap", "src/test/resources/calc_tests.mm", "-export", file.getAbsolutePath(), "-format", "json", "-skiptest");
        verify(printStream).println("Test Suite successfully exported");
        verify(printStream).println("Tests were skipped");

        callWithArgs("-suite", file.getAbsolutePath(), "-format", "json");
        verify(printStream).println("9 tests have been executed");
        verify(printStream).println("1 test(s) failed");
        verify(printStream).println("\nTest failed: division by negative number");
        verify(printStream).println("Expected: \t-4.47");
        verify(printStream).println("Actual: \t-4.472");
    }

    @Test
    @Ignore //too long
    public void exportGherkinAndThenTest() throws IOException {
        Path path = Files.createTempDirectory("test");
        callWithArgs("-mindmap", "src/test/resources/calc_tests.mm",
                "-export", path.toAbsolutePath().toString(),
                "-format", "gherkin",
                "-skiptest");
        verify(printStream).println("Test Suite successfully exported in 4 file(s)");
        verify(printStream).println("Subtract.feature");
        verify(printStream).println("Multiply.feature");
        verify(printStream).println("Add.feature");
        verify(printStream).println("Divide.feature");
        verify(printStream).println("Tests were skipped");

        callWithArgs("-suite", path.toAbsolutePath().toString(), "-format", "gherkin");
        verify(printStream).println(":compileJava UP-TO-DATE");
    }

    @Test
    public void help() {
        callWithArgs("-help");
        //cannot test the output, commons-cli lib very sophisticated
//        verify(printStream).println("usage: neueda-mindt");
    }

    @Test
    @Ignore
    public void callRealNeuedaService() {
        consoleApp = new ConsoleApp(new NeuedaCalculationService(), printStream);
        callWithArgs("-mindmap", "src/test/resources/calc_tests.mm");
        verify(printStream).println("9 tests have been executed");
        verify(printStream).println("All tests passed");
    }
}