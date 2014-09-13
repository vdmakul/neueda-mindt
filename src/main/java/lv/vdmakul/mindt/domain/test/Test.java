package lv.vdmakul.mindt.domain.test;

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
}
