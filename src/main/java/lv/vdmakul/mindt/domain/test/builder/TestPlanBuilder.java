package lv.vdmakul.mindt.domain.test.builder;

import lv.vdmakul.mindt.domain.test.TestPlan;
import lv.vdmakul.mindt.domain.test.TestSuite;

import java.util.ArrayList;
import java.util.List;

public class TestPlanBuilder {
    public List<TestSuite> testSuites = new ArrayList<>();

    private TestPlanBuilder() {
    }

    public static TestPlanBuilder aTestPlan() {
        return new TestPlanBuilder();
    }

    public TestPlanBuilder withTestSuites(List<TestSuite> testSuites) {
        this.testSuites.addAll(testSuites);
        return this;
    }

    public TestPlanBuilder withTestSuite(TestSuite testSuite) {
        this.testSuites.add(testSuite);
        return this;
    }

    public TestPlanBuilder withTestSuite(TestSuiteBuilder testBuilder) {
        return withTestSuite(testBuilder.build());
    }

    public TestPlan build() {
        TestPlan testPlan = new TestPlan(testSuites);
        return testPlan;
    }
}
