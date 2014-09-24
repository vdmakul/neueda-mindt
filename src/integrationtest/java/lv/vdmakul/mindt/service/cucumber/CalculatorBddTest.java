package lv.vdmakul.mindt.service.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        glue = "lv.vdmakul.mindt.service.cucumber",
        features = "src/integrationtest/resources/cucumber/suite"
)
public class CalculatorBddTest {
}
