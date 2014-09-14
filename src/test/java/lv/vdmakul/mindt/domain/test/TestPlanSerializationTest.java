package lv.vdmakul.mindt.domain.test;

import org.junit.Assert;
import org.junit.Test;

public class TestPlanSerializationTest {

    @Test
    public void serialize() throws Exception {
        TestPlan plan = ProvidedTestPlanBuilder.build();

        String json = plan.asJson();
        TestPlan another = TestPlan.fromJson(json);

        Assert.assertEquals(plan, another);

    }

}