package lv.vdmakul.mindt.domain.test;

import java.math.BigDecimal;

public class EvaluationResult {

    public static final String NAN = "DIV/0";
    public static final String ERROR = "ERR";

    private BigDecimal value;
    private String nonValue;
    private String errorMessage;

    private EvaluationResult(BigDecimal value, String nonValue, String errorMessage) {
        this.value = value;
        this.nonValue = nonValue;
        this.errorMessage = errorMessage;
    }

    public static EvaluationResult value(BigDecimal value) {
        return new EvaluationResult(value, null, null);
    }

    public static EvaluationResult notANumber() {
        return new EvaluationResult(null, NAN, null);
    }

    public static EvaluationResult error() {
        return error(null);
    }

    public static EvaluationResult error(String errorMessage) {
        return new EvaluationResult(null, ERROR, errorMessage);
    }

    public static EvaluationResult valueOf(String result) {
        switch (result) {
            case NAN:
                return notANumber();
            case ERROR:
                return error();
            default:
                return parse(result);
        }
    }

    private static EvaluationResult parse(String result) {
        try {
            return value(new BigDecimal(result));
        } catch (NumberFormatException ex) {
            return error(ex.getMessage());
        }
    }

    public BigDecimal getValue() {
        return value;
    }

    public boolean isNan() {
        return NAN.equals(nonValue);
    }

    public boolean isError() {
        return ERROR.equals(nonValue);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
