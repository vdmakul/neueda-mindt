package lv.vdmakul.mindt.service.testing.bdd;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lv.vdmakul.mindt.domain.EvaluationResult;
import lv.vdmakul.mindt.domain.Request;
import lv.vdmakul.mindt.service.calculation.CalculationService;
import lv.vdmakul.mindt.service.calculation.LocalCalculationService;
import lv.vdmakul.mindt.service.calculation.NeuedaCalculationService;
import org.junit.Assert;

import java.math.BigDecimal;

public class CalculatorSteps {

    private Scenario scenario;

    private CalculationService calculationService;
    private String path;
    private String method;

    private EvaluationResult actual;

    @Before
    public void before(Scenario scenario) {
        this.scenario = scenario;
    }

    @Given("^A local calculator$")
    public void localCalculator() throws Throwable {
        this.calculationService = new LocalCalculationService();
    }

    @Given("^A Neueda calculator$")
    public void neuedaCalculator() throws Throwable {
        this.calculationService = new NeuedaCalculationService();
    }

    @Given("^request path is \"(.*?)\"$")
    public void requestPath(String path) {
        this.path = path;
    }

    @Given("^request method is (\\w+)$")
    public void requestMethod(String method) {
        this.method = method;
    }

    @When("^I multiply (\\d+) and (\\d+)$")
    public void multiply(BigDecimal var1, BigDecimal var2) throws Throwable {
        Request request = new Request(method, path);
        this.actual = calculationService.calculate(request, var1, var2);
    }

    @Then("^result is ([\\w\\.]*)$")
    public void assertResult(String result) {
        EvaluationResult expected = EvaluationResult.valueOf(result);
        Assert.assertEquals(scenario.getName(), expected, actual);
    }


}
