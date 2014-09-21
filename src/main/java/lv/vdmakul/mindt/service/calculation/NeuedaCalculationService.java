package lv.vdmakul.mindt.service.calculation;


import com.google.gson.Gson;
import lv.vdmakul.mindt.domain.EvaluationResult;
import lv.vdmakul.mindt.domain.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class NeuedaCalculationService implements CalculationService {

    @Value("${service.calculator.url}")
    private String serviceUrl;

    private static final Logger logger = LoggerFactory.getLogger(NeuedaCalculationService.class);

    @Override
    public EvaluationResult calculate(Request request, BigDecimal value1, BigDecimal value2) {
        try {
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
            logger.error(ex.getMessage(), ex);
            return EvaluationResult.error(ex.getMessage());
        }
    }

    private static class Response {
        public String result;
    }
}
