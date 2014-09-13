package lv.vdmakul.mindt.mindmap.treeparser;

import lv.vdmakul.mindt.domain.test.EvaluationResult;
import lv.vdmakul.mindt.domain.test.TestPlan;
import lv.vdmakul.mindt.domain.test.builder.RequestBuilder;
import lv.vdmakul.mindt.domain.test.builder.TestBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.math.BigDecimal;

import static lv.vdmakul.mindt.domain.test.builder.TestPlanBuilder.aTestPlan;
import static lv.vdmakul.mindt.domain.test.builder.TestSuiteBuilder.aTestSuite;
import static org.junit.Assert.assertEquals;

public class MindMapTreeParserFunctionalTest {

    private MindMapTreeParser parser;

    @Before
    public void setUp() throws Exception {
        parser = new MindMapTreeParser();
    }

    @Test
    public void testName() throws Exception {
        FileInputStream inputStream = new FileInputStream(getClass().getResource("/calc_tests.mm").getFile());
        TestPlan actualTestPlan = parser.parseMindMap(inputStream);

        assertEquals(aTestPlan()
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
                .build()
                , actualTestPlan);
    }

    private RequestBuilder aRequest(String method, String path) {
        return RequestBuilder.aRequest()
                .withMethod(method)
                .withPath(path);
    }

    private TestBuilder aTest(String name, String var1, String var2, String res) {
        return TestBuilder.aTest()
                .withName(name)
                .withVariableOne(new BigDecimal(var1))
                .withVariableTwo(new BigDecimal(var2))
                .withExpectedResult(EvaluationResult.valueOf(res));
    }
}
