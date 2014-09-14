package lv.vdmakul.mindt.testing;

import lv.vdmakul.mindt.calculation.CalculationService;
import lv.vdmakul.mindt.domain.test.EvaluationResult;
import lv.vdmakul.mindt.domain.test.Request;
import lv.vdmakul.mindt.domain.test.Test;
import lv.vdmakul.mindt.domain.test.TestSuite;

import java.util.List;
import java.util.stream.Collectors;

public class TestExecutor {

    private final CalculationService calculationService; //todo use different injection?

    public TestExecutor(CalculationService calculationService) {
        this.calculationService = calculationService;
    }

    public List<TestResult> execute(List<TestSuite> testSuites) {
        return testSuites.stream()
                .map(this::executeSuite)
                .flatMap(testResults -> testResults.stream())
                .collect(Collectors.toList());
    }

    private List<TestResult> executeSuite(TestSuite testSuite) {
        return testSuite.tests.stream()
                .map(test -> executeTest(testSuite.request, test))
                .collect(Collectors.toList());
    }

    public TestResult executeTest(Request request, Test test) {
        EvaluationResult actualResult = calculationService.calculate(
                request,
                test.variableOne,
                test.variableTwo);
        return new TestResult(test.name, test.expectedResult, actualResult);
    }
}
