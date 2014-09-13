package lv.vdmakul.mindt.domain.test.builder;

import lv.vdmakul.mindt.domain.test.EvaluationResult;
import lv.vdmakul.mindt.domain.test.Test;

import java.math.BigDecimal;

public class TestBuilder {
    public String name;
    public BigDecimal variableOne;
    public BigDecimal variableTwo;
    public EvaluationResult expectedResult;

    private TestBuilder() {
    }

    public static TestBuilder aTest() {
        return new TestBuilder();
    }

    public TestBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public TestBuilder withVariableOne(BigDecimal variableOne) {
        this.variableOne = variableOne;
        return this;
    }

    public TestBuilder withVariableTwo(BigDecimal variableTwo) {
        this.variableTwo = variableTwo;
        return this;
    }

    public TestBuilder withExpectedResult(EvaluationResult result) {
        this.expectedResult = result;
        return this;
    }

    public Test build() {
        Test test = new Test(name, variableOne, variableTwo, expectedResult);
        return test;
    }
}
