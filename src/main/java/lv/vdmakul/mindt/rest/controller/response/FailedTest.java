package lv.vdmakul.mindt.rest.controller.response;

import lv.vdmakul.mindt.service.testing.TestResult;

public class FailedTest {
    public final String expectedResult;
    public final String actualResult;
    public final String testName;

    public FailedTest(TestResult result) {
        expectedResult = result.expectedResult.getResultAsString();
        actualResult = result.actualResult.getResultAsString();
        testName = result.testName;
    }
}
