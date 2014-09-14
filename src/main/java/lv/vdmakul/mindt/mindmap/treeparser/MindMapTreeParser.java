package lv.vdmakul.mindt.mindmap.treeparser;

import lv.vdmakul.mindt.domain.*;
import lv.vdmakul.mindt.domain.builder.RequestBuilder;
import lv.vdmakul.mindt.domain.builder.TestBuilder;
import lv.vdmakul.mindt.domain.builder.TestPlanBuilder;
import lv.vdmakul.mindt.domain.builder.TestSuiteBuilder;
import lv.vdmakul.mindt.mindmap.MindMapParser;
import lv.vdmakul.mindt.mindmap.MindMapParsingException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.math.BigDecimal;

import static lv.vdmakul.mindt.domain.builder.RequestBuilder.aRequest;
import static lv.vdmakul.mindt.domain.builder.TestBuilder.aTest;
import static lv.vdmakul.mindt.domain.builder.TestPlanBuilder.aTestPlan;
import static lv.vdmakul.mindt.domain.builder.TestSuiteBuilder.aTestSuite;

public class MindMapTreeParser implements MindMapParser {

    @Override
    public TestPlan parseMindMap(InputStream mindMapInputStream) {
        SAXParserFactory parserFactor = SAXParserFactory.newInstance();
        NodeTreeSaxBuilder treeSaxBuilder;
        try {
            SAXParser parser = parserFactor.newSAXParser();
            treeSaxBuilder = new NodeTreeSaxBuilder();
            parser.parse(mindMapInputStream, treeSaxBuilder);
        } catch (Exception ex) {
            throw new MindMapParsingException("MindMap parsing failed: " + ex.getMessage(), ex);
        }

        return createTestPlan(treeSaxBuilder.getRoot());
    }

    protected TestPlan createTestPlan(Node root) {
        TestPlanBuilder planBuilder = aTestPlan();
        root.children.stream()
                .map(this::asTestSuite)
                .forEach(planBuilder::withTestSuite);
        return planBuilder.build();
    }

    private TestSuite asTestSuite(Node suiteNode) {
        TestSuiteBuilder testBuilder = aTestSuite().withName(suiteNode.value);
        suiteNode.children.stream()
            .forEach(n -> {
                switch (n.attribute()) {
                    case "Request": testBuilder.withRequest(asRequest(n)); break;
                    case "Test":    testBuilder.withTest(asTest(n)); break;
                    default: //todo warning on unknown attribute

                }
            });
        return testBuilder.build();
    }

    private Request asRequest(Node requestNode) {
        RequestBuilder requestBuilder = aRequest();
        requestNode.children.stream()
            .forEach(n -> {
                       switch (n.attribute()) {
                           case "Method": requestBuilder.withMethod(n.get("Method").toUpperCase()); break;
                           case "Path":   requestBuilder.withPath(n.get("Path")); break;
                           default: //todo warning on unknown attribute
                       }
                    });
        return requestBuilder.build();
    }

    private Test asTest(Node testNode) {
        TestBuilder testBuilder = aTest().withName(testNode.get("Test"));
        testNode.children.stream()
                .forEach(n -> {
                    switch (n.attribute()) {
                        case "variableOne": testBuilder.withVariableOne(parseDecimal(n.get("variableOne"))); break;
                        case "variableTwo": testBuilder.withVariableTwo(parseDecimal(n.get("variableTwo"))); break;
                        case "result":      testBuilder.withExpectedResult(EvaluationResult.valueOf(n.get("result"))); break;
                        default: //todo warning on unknown attribute
                    }
                });
        return testBuilder.build();
    }

    private BigDecimal parseDecimal(String value) {
        return new BigDecimal(value);
    }

}
