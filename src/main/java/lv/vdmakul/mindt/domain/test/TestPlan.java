package lv.vdmakul.mindt.domain.test;

import java.util.Collections;
import java.util.List;

public class TestPlan {

    public final List<TestSuite> testSuites;

    public TestPlan(List<TestSuite> testSuites) {
        this.testSuites = Collections.unmodifiableList(testSuites);
    }
}
