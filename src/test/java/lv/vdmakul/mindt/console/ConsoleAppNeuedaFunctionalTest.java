package lv.vdmakul.mindt.console;

import lv.vdmakul.mindt.service.MindtFacade;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.PrintStream;

import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ConsoleApp.ConsoleAppConfig.class})
public class ConsoleAppNeuedaFunctionalTest {

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
    @Ignore
    public void callRealNeuedaService() {
        consoleApp = new ConsoleApp(mindtFacade, printStream);
        callWithArgs("-mindmap", "src/test/resources/calc_tests.mm");
        verify(printStream).println("9 tests have been executed");
        verify(printStream).println("All tests passed");
    }
}