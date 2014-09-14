package lv.vdmakul.mindt.calculation;


import com.google.gson.Gson;
import lv.vdmakul.mindt.config.MindtProperties;
import lv.vdmakul.mindt.domain.test.EvaluationResult;
import lv.vdmakul.mindt.domain.test.Request;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class NeuedaCalculationService implements CalculationService {

    private final String serviceUrl;

    public NeuedaCalculationService() {
        this.serviceUrl = MindtProperties.getProperty(MindtProperties.URL_PROPERTY);
    }

    @Override
    public EvaluationResult calculate(Request request, BigDecimal value1, BigDecimal value2) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");

        String json = String.format("{\"variableOne\":%s, \"variableTwo\":%s}", value1, value2);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                serviceUrl + request.path,
                HttpMethod.valueOf(request.method),
                new HttpEntity<>(json, headers),
                String.class);

        Response response = new Gson().fromJson(responseEntity.getBody(), Response.class);
        return EvaluationResult.valueOf(response.result);
    }

    private static class Response {
        public String result;
    }
}
