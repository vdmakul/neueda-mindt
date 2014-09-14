package lv.vdmakul.mindt.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;

public class Test {

    public final String name;
    public final BigDecimal variableOne;
    public final BigDecimal variableTwo;
    public final EvaluationResult expectedResult;

    public Test(String name, BigDecimal variableOne, BigDecimal variableTwo, EvaluationResult expectedResult) {
        this.name = name;
        this.variableOne = variableOne;
        this.variableTwo = variableTwo;
        this.expectedResult = expectedResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Test that = (Test) o;
        return new EqualsBuilder()
                .append(this.name, that.name)
                .append(this.variableOne, that.variableOne)
                .append(this.variableTwo, that.variableTwo)
                .append(this.expectedResult, that.expectedResult)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(variableOne).append(variableTwo).append(expectedResult).toHashCode();
    }

    @Override
    public String toString() {
        return "Test{" +
                "name='" + name + '\'' +
                ", variableOne=" + variableOne +
                ", variableTwo=" + variableTwo +
                ", expectedResult=" + expectedResult +
                '}';
    }
}
