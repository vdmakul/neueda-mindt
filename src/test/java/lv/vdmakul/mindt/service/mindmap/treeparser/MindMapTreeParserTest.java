package lv.vdmakul.mindt.service.mindmap.treeparser;

import lv.vdmakul.mindt.domain.TestPlan;
import lv.vdmakul.mindt.domain.TestSuite;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static lv.vdmakul.mindt.service.mindmap.treeparser.NodeBuilder.aNode;

public class MindMapTreeParserTest {


    private MindMapTreeParser parser;

    @Before
    public void setUp() {
        parser = new MindMapTreeParser();
    }

    @Test
    public void parseEmpty() {
        TestPlan plan = parser.createTestPlan(aNode()
                .withValue("empty test")
                .build());

        Assert.assertTrue(plan.testSuites.isEmpty());
    }

    @Test
    public void parseTwoTestInSingleSuite() {
        TestPlan plan = parser.createTestPlan(
                aNode("Calculator tests")
                        .withChild(aNode("Multiply")
                                .withChild(aNode("Request")
                                        .withChild(aNode("Method: POST"))
                                        .withChild(aNode("Path: /rest/multiply")))
                                .withChild(aNode("Test: simple multiplication")
                                        .withChild(aNode("variableOne: 5"))
                                        .withChild(aNode("variableTwo: 9"))
                                        .withChild(aNode("result: 45")))
                                .withChild(aNode("Test: multiplying negatives")
                                        .withChild(aNode("variableOne: -2.3"))
                                        .withChild(aNode("variableTwo: -6.76"))
                                        .withChild(aNode("result: 15.548"))))
                        .build());

        Assert.assertEquals(1, plan.testSuites.size());

        TestSuite suite = plan.testSuites.get(0);
        Assert.assertEquals("Multiply", suite.name);

        Assert.assertNotNull(suite.request);
        Assert.assertEquals("POST", suite.request.method);
        Assert.assertEquals("/rest/multiply", suite.request.path);

        Assert.assertEquals(2, suite.tests.size());

        lv.vdmakul.mindt.domain.Test test1 = suite.tests.get(0);
        Assert.assertEquals("simple multiplication", test1.name);
        Assert.assertEquals(new BigDecimal("5"), test1.variableOne);
        Assert.assertEquals(new BigDecimal("9"), test1.variableTwo);
        Assert.assertEquals(new BigDecimal("45"), test1.expectedResult.getValue());

        lv.vdmakul.mindt.domain.Test test2 = suite.tests.get(1);
        Assert.assertEquals("multiplying negatives", test2.name);
        Assert.assertEquals(new BigDecimal("-2.3"), test2.variableOne);
        Assert.assertEquals(new BigDecimal("-6.76"), test2.variableTwo);
        Assert.assertEquals(new BigDecimal("15.548"), test2.expectedResult.getValue());
    }

    @Test
    public void parseTwoSuites() {
        TestPlan plan = parser.createTestPlan(
                aNode("Calculator tests")
                        .withChild(aNode("Multiply")
                                .withChild(aNode("Request")
                                        .withChild(aNode("Method: POST"))
                                        .withChild(aNode("Path: /rest/multiply")))
                                .withChild(aNode("Test: simple multiplication")
                                        .withChild(aNode("variableOne: 5"))
                                        .withChild(aNode("variableTwo: 9"))
                                        .withChild(aNode("result: 45"))))
                        .withChild(aNode("Divide")
                                .withChild(aNode("Request")
                                        .withChild(aNode("Method: POST"))
                                        .withChild(aNode("Path: /rest/divide")))
                                .withChild(aNode("Test: simple division")
                                        .withChild(aNode("variableOne: 5"))
                                        .withChild(aNode("variableTwo: 2"))
                                        .withChild(aNode("result: 2.5"))))
                        .build());

        Assert.assertEquals(2, plan.testSuites.size());

        TestSuite suite1 = plan.testSuites.get(0);
        Assert.assertEquals("Multiply", suite1.name);
        Assert.assertNotNull(suite1.request);
        Assert.assertEquals("POST", suite1.request.method);
        Assert.assertEquals("/rest/multiply", suite1.request.path);

        Assert.assertEquals(1, suite1.tests.size());

        lv.vdmakul.mindt.domain.Test test1 = suite1.tests.get(0);
        Assert.assertEquals("simple multiplication", test1.name);
        Assert.assertEquals(new BigDecimal("5"), test1.variableOne);
        Assert.assertEquals(new BigDecimal("9"), test1.variableTwo);
        Assert.assertEquals(new BigDecimal("45"), test1.expectedResult.getValue());


        TestSuite suite2 = plan.testSuites.get(1);
        Assert.assertEquals("Divide", suite2.name);
        Assert.assertNotNull(suite2.request);
        Assert.assertEquals("POST", suite2.request.method);
        Assert.assertEquals("/rest/divide", suite2.request.path);

        Assert.assertEquals(1, suite2.tests.size());

        lv.vdmakul.mindt.domain.Test test2 = suite2.tests.get(0);
        Assert.assertEquals("simple division", test2.name);
        Assert.assertEquals(new BigDecimal("5"), test2.variableOne);
        Assert.assertEquals(new BigDecimal("2"), test2.variableTwo);
        Assert.assertEquals(new BigDecimal("2.5"), test2.expectedResult.getValue());
    }
}