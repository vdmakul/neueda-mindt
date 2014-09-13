package lv.vdmakul.mindt.domain.test.builder;


import lv.vdmakul.mindt.domain.test.Request;
import lv.vdmakul.mindt.domain.test.Test;
import lv.vdmakul.mindt.domain.test.TestSuite;

import java.util.ArrayList;
import java.util.List;

public class TestSuiteBuilder {
    public String name;
    public Request request;
    public List<Test> suites = new ArrayList<>();

    private TestSuiteBuilder() {
    }

    public static TestSuiteBuilder aTestSuite() {
        return new TestSuiteBuilder();
    }

    public TestSuiteBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public TestSuiteBuilder withRequest(RequestBuilder requestBuilder) {
        return withRequest(requestBuilder.build());
    }

    public TestSuiteBuilder withRequest(Request request) {
        this.request = request;
        return this;
    }

    public TestSuiteBuilder withTest(TestBuilder suiteBuilder) {
        return withTest(suiteBuilder.build());
    }

    public TestSuiteBuilder withTest(Test suite) {
        this.suites.add(suite);
        return this;
    }

    public TestSuite build() {
        TestSuite testSuite = new TestSuite(name, request, suites);
        return testSuite;
    }
}
