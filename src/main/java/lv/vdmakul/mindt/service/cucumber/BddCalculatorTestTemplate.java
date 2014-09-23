package lv.vdmakul.mindt.service.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        glue = "lv.vdmakul.mindt.service.cucumber"
)
public class BddCalculatorTestTemplate {
}
