package calculations;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;

@SuppressWarnings("unused")
public class Result {
    private Uncertainty answer;
    private Map<String, ArrayList<BigDecimal>> equation;
    private Map<String, ArrayList<BigDecimal>> steps;
    private DecimalFormat equationValueRounding;
    private DecimalFormat equationUncertaintyRounding;
    private DecimalFormat intermediateValueRounding;
    private DecimalFormat intermediateUncertaintyRounding;
    private DecimalFormat resultValueRounding;
    private DecimalFormat resultUncertaintyRounding;
 
    public Result(Uncertainty answer, Map<String, ArrayList<BigDecimal>> equation, Map<String, ArrayList<BigDecimal>> steps) {
        
    }
}