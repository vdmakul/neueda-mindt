package lv.vdmakul.mindt.console;

import lv.vdmakul.mindt.console.options.MindtOption;
import lv.vdmakul.mindt.console.options.OptionsHelper;
import lv.vdmakul.mindt.console.options.OptionsResolver;
import lv.vdmakul.mindt.internal.MindtProperties;
import lv.vdmakul.mindt.service.MindtFacade;
import lv.vdmakul.mindt.service.testing.TestResult;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.io.PrintStream;
import java.util.List;
import java.util.Set;

public class ConsoleApp {

    @Configuration
    @ComponentScan("lv.vdmakul.mindt.service")
    @PropertySource("classpath:/application.properties")
    protected static class ConsoleAppConfig {
        @Bean
        public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
            return new PropertySourcesPlaceholderConfigurer();
        }
    }

    private final PrintStream printStream;
    private final MindtFacade mindtFacade;

    public static void main(String[] args) {
        SpringApplication application = new SpringApplicationBuilder(ConsoleAppConfig.class).application();
        application.setShowBanner(false);
        try (ConfigurableApplicationContext context = application.run(args)) {
            new ConsoleApp(context.getBean(MindtFacade.class), System.out).runWithArgs(args);
        }
    }

    public ConsoleApp(MindtFacade mindtFacade, PrintStream printStream) {
        this.printStream = printStream;
        this.mindtFacade = mindtFacade;
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
            if (MindtOption.FORMAT_GHERKIN.equals(optionsResolver.get(MindtOption.FORMAT))) {
                Set<String> featureFiles = mindtFacade.exportTestPlanAsFeature(
                        optionsResolver.get(MindtOption.MINDMAP),
                        optionsResolver.get(MindtOption.EXPORT));
                print("Test Suite successfully exported in " + featureFiles.size() + " file(s)");
                featureFiles.forEach(this::print);
            } else {
                mindtFacade.exportTestPlan(
                        optionsResolver.get(MindtOption.MINDMAP),
                        optionsResolver.get(MindtOption.EXPORT));
                print("Test Suite successfully exported");
            }

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
            if (MindtOption.FORMAT_GHERKIN.equals(optionsResolver.get(MindtOption.FORMAT))) {
                List<String> output = mindtFacade.testByGherkinScenarios(optionsResolver.get(MindtOption.SUITE));
                output.forEach(this::print);
                return;
            } else {
                results = mindtFacade.testByPlanFromFile(optionsResolver.get(MindtOption.SUITE));
            }
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
