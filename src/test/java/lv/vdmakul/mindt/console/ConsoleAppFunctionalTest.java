package lv.vdmakul.mindt.console;

import lv.vdmakul.mindt.service.MindtFacade;
import lv.vdmakul.mindt.service.calculation.CalculationService;
import lv.vdmakul.mindt.service.calculation.LocalCalculationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ConsoleApp.ConsoleAppConfig.class, ConsoleAppFunctionalTest.TestConfiguration.class})
public class ConsoleAppFunctionalTest {

    @Configuration
    protected static class TestConfiguration {
        @Bean
        public CalculationService calculationService() {
            return new LocalCalculationService();
        }
    }

    @Autowired private MindtFacade mindtFacade;

    private ConsoleApp consoleApp;
    private PrintStream printStream;

    @Before
    public void setUp() {
        printStream = Mockito.spy(System.out);
        consoleApp = new ConsoleApp(mindtFacade, printStream);
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
        callWithArgs("-mindmap", "mindmap.file", "-export", "export.file", "-suite", "suite.file");
        verify(printStream).println("Aborted: -export option is not compatible with -suite option");
    }

    @Test
    public void skipTest() {
        callWithArgs("-suite", "suite.file", "-skiptest");
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
    public void exportAndThenTest() throws IOException {
        File file = File.createTempFile("test", "json");
        file.deleteOnExit();

        callWithArgs("-mindmap", "src/test/resources/calc_tests.mm", "-export", file.getAbsolutePath(), "-skiptest");
        verify(printStream).println("Test Suite successfully exported");
        verify(printStream).println("Tests were skipped");

        callWithArgs("-suite", file.getAbsolutePath());
        verify(printStream).println("9 tests have been executed");
        verify(printStream).println("1 test(s) failed");
        verify(printStream).println("\nTest failed: division by negative number");
        verify(printStream).println("Expected: \t-4.47");
        verify(printStream).println("Actual: \t-4.472");
    }

    @Test
    public void help() {
        callWithArgs("-help");
        //cannot test the output, commons-cli lib very sophisticated
//        verify(printStream).println("usage: neueda-mindt");
    }
}