package lv.vdmakul.mindt.testing;

import lv.vdmakul.mindt.domain.test.EvaluationResult;

public class TestResult {

    public final EvaluationResult actualResult;
    public final EvaluationResult expectedResult;
    public final String testName;

    public TestResult(String testName, EvaluationResult expectedResult, EvaluationResult actualResult) {
        this.actualResult = actualResult;
        this.expectedResult = expectedResult;
        this.testName = testName;
    }
}
