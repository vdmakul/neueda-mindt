package lv.vdmakul.mindt.calculation;


import lv.vdmakul.mindt.domain.test.EvaluationResult;
import lv.vdmakul.mindt.domain.test.Request;

import java.math.BigDecimal;

public interface CalculationService {

    EvaluationResult calculate(Request request, BigDecimal value1, BigDecimal value2);
}
