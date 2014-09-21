package lv.vdmakul.mindt.rest.controller.response;

import lv.vdmakul.mindt.service.testing.TestResult;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TestResultResponse {

    public final int totalTestCont;
    public final String message;
    public final boolean allOk;

    private TestResultResponse(int totalTestCont, String message, boolean allOk) {
        this.totalTestCont = totalTestCont;
        this.message = message;
        this.allOk = allOk;
    }

    public static TestResultResponse createFromResults(List<TestResult> results) {
        List<TestResult> failed = results.stream().filter(TestResult::failed).collect(Collectors.toList());
        if (failed.isEmpty()) {
            return new TestResultResponse(results.size(), "All tests passed", true);
        } else {
            return new FailedTestResultResponse(results.size(), failed);
        }
    }

    public static class FailedTestResultResponse extends TestResultResponse {
        public final int failedCount;
        public final List<FailedTest> failedTests;

        public FailedTestResultResponse(int totalTestCont, List<TestResult> failedResults) {
            super(totalTestCont, failedResults.size() + " test(s) failed", false);
            failedCount = failedResults.size();
            failedTests = Collections.unmodifiableList(failedResults.stream()
                    .map(FailedTest::new)
                    .collect(Collectors.toList()));
        }
    }
}
