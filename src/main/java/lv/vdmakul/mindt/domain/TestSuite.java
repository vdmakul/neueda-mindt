package lv.vdmakul.mindt.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TestSuite {

    public final String name;
    public final Request request;
    public final List<Test> tests;

    public TestSuite(String name, Request request, List<Test> tests) {
        this.name = name;
        this.request = request;
        this.tests = Collections.unmodifiableList(tests);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestSuite that = (TestSuite) o;
        return new EqualsBuilder()
                .append(this.name, that.name)
                .append(this.request, that.request)
                .append(this.tests.toArray(), that.tests.toArray())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(request).append(tests).toHashCode();
    }

    @Override
    public String toString() {
        return "TestSuite{" +
                "name='" + name + '\'' +
                ", request=" + request +
                ", tests=" + tests.stream().map(Test::toString).collect(Collectors.joining(",")) +
                '}';
    }
}
