package lv.vdmakul.mindt.domain;

import lv.vdmakul.mindt.domain.builder.RequestBuilder;
import lv.vdmakul.mindt.domain.builder.TestBuilder;

import java.math.BigDecimal;

import static lv.vdmakul.mindt.domain.builder.TestPlanBuilder.aTestPlan;
import static lv.vdmakul.mindt.domain.builder.TestSuiteBuilder.aTestSuite;

public class ReferenceTestPlanHelper {

    public final static String ADD_FEATURE = "Feature: Add\n" +
            "\n" +
            "Background: \n" +
            "\tGiven A Neueda calculator\n" +
            "\tAnd request path is \"/rest/add\"\n" +
            "\tAnd request method is \"POST\"\n" +
            "\n" +
            "Scenario: simple addition\n" +
            "\tWhen I enter 6 and 8\n" +
            "\tThen result is 14\n" +
            "\n" +
            "Scenario: adding a negative number\n" +
            "\tWhen I enter -5.34 and 3.95\n" +
            "\tThen result is -1.39\n" +
            "\n";

    public final static String MULTIPLY_FEATURE = "Feature: Multiply\n" +
            "\n" +
            "Background: \n" +
            "\tGiven A Neueda calculator\n" +
            "\tAnd request path is \"/rest/multiply\"\n" +
            "\tAnd request method is \"POST\"\n" +
            "\n" +
            "Scenario: simple multiplication\n" +
            "\tWhen I enter 5 and 9\n" +
            "\tThen result is 45\n" +
            "\n" +
            "Scenario: multiplying negatives\n" +
            "\tWhen I enter -2.3 and -6.76\n" +
            "\tThen result is 15.548\n" +
            "\n";


    public static TestPlan referenceTestPlan() {
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
