package lv.vdmakul.mindt.service.cucumber;

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

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SuppressWarnings("unused")
public class BasicStepdefs {

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

    @Given("^An external calculator by url (\\w*) $")
    public void externalCalculator(String url) throws Throwable {
        this.calculationService = new NeuedaCalculationService(url);
    }

    @Given("^request path is \"(.*?)\"$")
    public void requestPath(String path) {
        this.path = path;
    }

    @Given("^request method is \"(.*?)\"$")
    public void requestMethod(String method) {
        this.method = method;
    }

    @When("^I (\\w*) ([\\-\\d\\.]*) and ([\\-\\d\\.]*)$")
    public void calculate(String operation, String var1, String var2) throws Throwable {
        assertNotNull("calculator must be specified", calculationService);
        assertNotNull("request method must be specified", method);
        assertNotNull("request path must be specified", path);

        Request request = new Request(method, path);
        this.actual = calculationService.calculate(request, new BigDecimal(var1), new BigDecimal(var2));
    }

    @Then("^result is (.*)$")
    public void assertResult(String result) {
        assertNotNull("operation is not specified", actual);

        EvaluationResult expected = EvaluationResult.valueOf(result);
        assertEquals(scenario.getName(), expected, actual);
    }

}
