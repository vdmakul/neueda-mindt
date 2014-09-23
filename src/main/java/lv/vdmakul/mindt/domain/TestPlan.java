package lv.vdmakul.mindt.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TestPlan {

    public final List<TestSuite> testSuites;

    public TestPlan(List<TestSuite> testSuites) {
        this.testSuites = Collections.unmodifiableList(testSuites);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestPlan that = (TestPlan) o;
        return new EqualsBuilder().append(this.testSuites.toArray(), that.testSuites.toArray()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(testSuites).toHashCode();
    }

    @Override
    public String toString() {
        return "TestPlan{" +
                "testSuites=" + testSuites.stream().map(TestSuite::toString).collect(Collectors.joining(",")) +
                '}';
    }
}
