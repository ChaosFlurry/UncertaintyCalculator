package calculations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class ArithmeticOperation {
    public static Result add(double value1, double uncertainty1, double value2, double uncertainty2) {
        DecimalFormat rounding = new DecimalFormat("#.###");
        String steps = "";
        String equation = value1 + " ± " + uncertainty1 + " + " + value2 + " ± " + uncertainty2;
        double result = BigDecimal.valueOf(value1).add(BigDecimal.valueOf(value2)).doubleValue();
        double resultUncertainty = BigDecimal.valueOf(uncertainty1).add(BigDecimal.valueOf(uncertainty2)).doubleValue();
        steps += value1 + " ± " + uncertainty1 + " + " + value2 + " ± " + uncertainty2 + " = " +
                "(" + value1 + " + " + value2 + ") ± (" + uncertainty1 + " + " + uncertainty2 + ")\n";
        steps += value1 + " ± " + uncertainty1 + " + " + value2 + " ± " + uncertainty2 + " = " +
                result + " ± " + resultUncertainty;
        return new Result(equation, result, resultUncertainty, steps);
    }
    
    public static Result subtract(double value1, double uncertainty1, double value2, double uncertainty2) {
        DecimalFormat rounding = new DecimalFormat("#.###");
        String steps = "";
        String equation = value1 + " ± " + uncertainty1 + " - " + value2 + " ± " + uncertainty2;
        double result = BigDecimal.valueOf(value1).add(BigDecimal.valueOf(value2)).doubleValue();
        double resultUncertainty = BigDecimal.valueOf(uncertainty1).add(BigDecimal.valueOf(uncertainty2)).doubleValue();
        steps += value1 + " ± " + uncertainty1 + " - " + value2 + " ± " + uncertainty2 + " = " +
                "(" + value1 + " - " + value2 + ") ± (" + uncertainty1 + " + " + uncertainty2 + ")\n";
        steps += value1 + " ± " + uncertainty1 + " - " + value2 + " ± " + uncertainty2 + " = " +
                result + " ± " + resultUncertainty;
        return new Result(equation, result, resultUncertainty, steps);
    }
    
    public static Result multiply(double value1, double uncertainty1, double value2, double uncertainty2) {
        DecimalFormat rounding = new DecimalFormat("#.###");
        String steps = "";
        String equation = value1 + " ± " + uncertainty1 + " * " + value2 + " ± " + uncertainty2;
        double result = BigDecimal.valueOf(value1).multiply(BigDecimal.valueOf(value2)).doubleValue();
        double relUncertainty1 = BigDecimal.valueOf(uncertainty1).divide(BigDecimal.valueOf(value1), RoundingMode.HALF_UP).doubleValue();
        double relUncertainty2 = BigDecimal.valueOf(uncertainty2).divide(BigDecimal.valueOf(value2), RoundingMode.HALF_UP).doubleValue();
        double resultUncertainty = BigDecimal.valueOf(relUncertainty1).add(BigDecimal.valueOf(relUncertainty2)).multiply(BigDecimal.valueOf(result)).doubleValue();
        steps += value1 + " ± " + uncertainty1 + " * " + value2 + " ± " + uncertainty2 + " = " +
                value1 + " ± " + (relUncertainty1 * 100) + "% * " + value2 + " ± " + (relUncertainty2 * 100) + "%\n";
        steps += value1 + " ± " + uncertainty1 + " * " + value2 + " ± " + uncertainty2 + " = " +
                "(" + value1 + " * " + value2 + ") ± (" + (relUncertainty1 * 100) + "% + " + (relUncertainty2 * 100) + "%)";
        steps += value1 + " ± " + uncertainty1 + " * " + value2 + " ± " + uncertainty2 + " = " +
                result + " ± " + (relUncertainty1 * 100 + relUncertainty2 * 100) + "%";
        steps += value1 + " ± " + uncertainty1 + " * " + value2 + " ± " + uncertainty2 + " = " +
                result + " ± " + resultUncertainty;
        return new Result(equation, result, resultUncertainty, steps);
    }
    
    public static Result divide(double value1, double uncertainty1, double value2, double uncertainty2) {
        DecimalFormat rounding = new DecimalFormat("#.###");
        String steps = "";
        String equation = value1 + " ± " + uncertainty1 + " * " + value2 + " ± " + uncertainty2;
        double result = BigDecimal.valueOf(value1).divide(BigDecimal.valueOf(value2), RoundingMode.HALF_UP).doubleValue();
        double relUncertainty1 = BigDecimal.valueOf(uncertainty1).divide(BigDecimal.valueOf(value1), RoundingMode.HALF_UP).doubleValue();
        double relUncertainty2 = BigDecimal.valueOf(uncertainty2).divide(BigDecimal.valueOf(value2), RoundingMode.HALF_UP).doubleValue();
        double resultUncertainty = BigDecimal.valueOf(relUncertainty1).add(BigDecimal.valueOf(relUncertainty2)).multiply(BigDecimal.valueOf(result)).doubleValue();
        steps += value1 + " ± " + uncertainty1 + " / " + value2 + " ± " + uncertainty2 + " = " +
                value1 + " ± " + (relUncertainty1 * 100) + "% / " + value2 + " ± " + (relUncertainty2 * 100) + "%\n";
        steps += value1 + " ± " + uncertainty1 + " / " + value2 + " ± " + uncertainty2 + " = " +
                "(" + value1 + " / " + value2 + ") ± (" + (relUncertainty1 * 100) + "% + " + (relUncertainty2 * 100) + "%)";
        steps += value1 + " ± " + uncertainty1 + " / " + value2 + " ± " + uncertainty2 + " = " +
                result + " ± " + (relUncertainty1 * 100 + relUncertainty2 * 100) + "%";
        steps += value1 + " ± " + uncertainty1 + " / " + value2 + " ± " + uncertainty2 + " = " +
                result + " ± " + resultUncertainty;
        return new Result(equation, result, resultUncertainty, steps);
    }
}
