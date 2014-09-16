package lv.vdmakul.mindt.service.calculation;


import com.google.gson.Gson;
import lv.vdmakul.mindt.domain.EvaluationResult;
import lv.vdmakul.mindt.domain.Request;
import lv.vdmakul.mindt.internal.MindtProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class NeuedaCalculationService implements CalculationService {

    @Override
    public EvaluationResult calculate(Request request, BigDecimal value1, BigDecimal value2) {
        try {
            String serviceUrl = MindtProperties.getProperty(MindtProperties.URL_PROPERTY);

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
        } catch (Exception ex) {
            return EvaluationResult.error(ex.getMessage());
        }
    }

    private static class Response {
        public String result;
    }
}
