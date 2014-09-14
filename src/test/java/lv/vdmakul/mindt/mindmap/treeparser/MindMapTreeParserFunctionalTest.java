package lv.vdmakul.mindt.mindmap.treeparser;

import lv.vdmakul.mindt.domain.TestPlan;
import lv.vdmakul.mindt.domain.test.ProvidedTestPlanBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;

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

        assertEquals(ProvidedTestPlanBuilder.build(), actualTestPlan);
    }
}
