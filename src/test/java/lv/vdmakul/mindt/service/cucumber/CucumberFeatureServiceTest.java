package lv.vdmakul.mindt.service.cucumber;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static lv.vdmakul.mindt.domain.ReferenceTestPlanHelper.*;
import static lv.vdmakul.mindt.domain.builder.TestPlanBuilder.aTestPlan;
import static lv.vdmakul.mindt.domain.builder.TestSuiteBuilder.aTestSuite;
import static org.junit.Assert.assertEquals;

public class CucumberFeatureServiceTest {

    private CucumberFeatureService service;

    @Before
    public void setUp() throws Exception {
        service = new CucumberFeatureService();
    }

    @Test
    public void shouldCreateFeature() throws Exception {

        List<String> features = service.transformToFeatures(aTestPlan()
                .withTestSuite(aTestSuite()
                        .withName("Multiply")
                        .withRequest(aRequest("POST", "/rest/multiply"))
                        .withTest(aTest("simple multiplication", "5", "9", "45"))
                        .withTest(aTest("multiplying negatives", "-2.3", "-6.76", "15.548")))
                .withTestSuite(aTestSuite()
                        .withName("Add")
                        .withRequest(aRequest("POST", "/rest/add"))
                        .withTest(aTest("simple addition", "6", "8", "14"))
                        .withTest(aTest("adding a negative number", "-5.34", "3.95", "-1.39")))
                .build());

        assertEquals(features.get(0), (MULTIPLY_FEATURE));
        assertEquals(features.get(1), (ADD_FEATURE));
    }
}