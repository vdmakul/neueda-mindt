package lv.vdmakul.mindt.domain.test;

import lv.vdmakul.mindt.domain.EvaluationResult;
import lv.vdmakul.mindt.domain.TestPlan;
import lv.vdmakul.mindt.domain.builder.RequestBuilder;
import lv.vdmakul.mindt.domain.builder.TestBuilder;

import java.math.BigDecimal;

import static lv.vdmakul.mindt.domain.builder.TestPlanBuilder.aTestPlan;
import static lv.vdmakul.mindt.domain.builder.TestSuiteBuilder.aTestSuite;

public class ProvidedTestPlanBuilder {

    public static TestPlan build() {
        return aTestPlan()
                .withTestSuite(aTestSuite()
                        .withName("Multiply")
                        .withRequest(aRequest("POST", "/rest/multiply"))
                        .withTest(aTest("simple multiplication", "5", "9", "45"))
                        .withTest(aTest("multiplying negatives", "-2.3", "-6.76", "15.548")))
                .withTestSuite(aTestSuite()
                        .withName("Divide")
                        .withRequest(aRequest("POST", "/rest/divide"))
                        .withTest(aTest("simple division", "5", "2", "2.5"))
                        .withTest(aTest("division by negative number", "22.36", "-5", "-4.47"))
                        .withTest(aTest("division by zero", "10", "0", "DIV/0")))
                .withTestSuite(aTestSuite()
                        .withName("Add")
                        .withRequest(aRequest("POST", "/rest/add"))
                        .withTest(aTest("simple addition", "6", "8", "14"))
                        .withTest(aTest("adding a negative number", "-5.34", "3.95", "-1.39")))
                .withTestSuite(aTestSuite()
                        .withName("Subtract")
                        .withRequest(aRequest("POST", "/rest/subtract"))
                        .withTest(aTest("simple subtraction", "97", "58", "39"))
                        .withTest(aTest("subtracting a negative number", "-34.12", "-55.7", "21.58")))
                .build();
    }


    public static RequestBuilder aRequest(String method, String path) {
        return RequestBuilder.aRequest()
                .withMethod(method)
                .withPath(path);
    }

    public static TestBuilder aTest(String name, String var1, String var2, String res) {
        return TestBuilder.aTest()
                .withName(name)
                .withVariableOne(new BigDecimal(var1))
                .withVariableTwo(new BigDecimal(var2))
                .withExpectedResult(EvaluationResult.valueOf(res));
    }
}
