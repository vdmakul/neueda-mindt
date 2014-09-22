package lv.vdmakul.mindt.service.cucumber;

import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@Cucumber.Options(
        glue = "lv.vdmakul.mindt.service.cucumber",
        features = "src/test/resources/cucumber/suite"
)
public class BddCalculatorTest {
}
