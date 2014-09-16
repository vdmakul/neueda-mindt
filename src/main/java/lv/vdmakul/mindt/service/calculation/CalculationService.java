package lv.vdmakul.mindt.service.calculation;


import lv.vdmakul.mindt.domain.EvaluationResult;
import lv.vdmakul.mindt.domain.Request;

import java.math.BigDecimal;

public interface CalculationService {

    EvaluationResult calculate(Request request, BigDecimal value1, BigDecimal value2);
}
