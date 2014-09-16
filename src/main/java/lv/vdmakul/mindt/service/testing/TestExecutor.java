package lv.vdmakul.mindt.service.testing;

import lv.vdmakul.mindt.domain.EvaluationResult;
import lv.vdmakul.mindt.domain.Request;
import lv.vdmakul.mindt.domain.Test;
import lv.vdmakul.mindt.domain.TestSuite;
import lv.vdmakul.mindt.service.calculation.CalculationService;

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
