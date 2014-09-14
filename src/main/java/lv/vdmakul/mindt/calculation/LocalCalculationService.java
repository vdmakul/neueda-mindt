package lv.vdmakul.mindt.calculation;

import lv.vdmakul.mindt.domain.EvaluationResult;
import lv.vdmakul.mindt.domain.Request;

import java.math.BigDecimal;

public class LocalCalculationService implements CalculationService {

    public static final String ADD = "/rest/add";
    public static final String SUBTRACT = "/rest/subtract";
    public static final String MULTIPLY = "/rest/multiply";
    public static final String DIVIDE = "/rest/divide";

    public EvaluationResult calculate(Request request, BigDecimal value1, BigDecimal value2) {
        try {
            switch (request.path) {
                case MULTIPLY:
                    return EvaluationResult.value(value1.multiply(value2));
                case DIVIDE:
                    return EvaluationResult.value(value1.divide(value2));
                case ADD:
                    return EvaluationResult.value(value1.add(value2));
                case SUBTRACT:
                    return EvaluationResult.value(value1.subtract(value2));
                default:
                    throw new UnsupportedOperationException(request.path + " is not supported");
            }
        } catch (ArithmeticException ex) {
            return EvaluationResult.notANumber();
        } catch (Exception ex) {
            return EvaluationResult.error(ex.getMessage());
        }
    }
}
