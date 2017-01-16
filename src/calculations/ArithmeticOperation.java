package calculations;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class ArithmeticOperation {
    public static Result add(double value1, double uncertainty1, double value2, double uncertainty2) {
        DecimalFormat rounded = new DecimalFormat("#.000");
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
        DecimalFormat rounded = new DecimalFormat("#.000");
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
        DecimalFormat rounded = new DecimalFormat("#.000");
        String steps = "";
        String equation = value1 + " ± " + uncertainty1 + " * " + value2 + " ± " + uncertainty2;
        double result = BigDecimal.valueOf(value1).multiply(BigDecimal.valueOf(value2)).doubleValue();
        double relUncertainty1 = BigDecimal.valueOf(uncertainty1).setScale(128, BigDecimal.ROUND_HALF_UP).divide(BigDecimal.valueOf(value1), BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)).doubleValue();
        double relUncertainty2 = BigDecimal.valueOf(uncertainty2).setScale(128, BigDecimal.ROUND_HALF_UP).divide(BigDecimal.valueOf(value2), BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)).doubleValue();
        double resultUncertainty = BigDecimal.valueOf(relUncertainty1).setScale(128, BigDecimal.ROUND_HALF_UP).add(BigDecimal.valueOf(relUncertainty2)).divide(BigDecimal.valueOf(100), BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(result)).doubleValue();
        
        steps += value1 + " ± " + uncertainty1 + " * " + value2 + " ± " + uncertainty2 + " = " +
                value1 + " ± " + rounded.format(relUncertainty1) + "% * " + value2 + " ± " + rounded.format(relUncertainty2) + "%\n";
        steps += value1 + " ± " + uncertainty1 + " * " + value2 + " ± " + uncertainty2 + " = " +
                "(" + value1 + " * " + value2 + ") ± (" + rounded.format(relUncertainty1) + "% + " + rounded.format(relUncertainty2) + "%)\n";
        steps += value1 + " ± " + uncertainty1 + " * " + value2 + " ± " + uncertainty2 + " = " +
                result + " ± " + rounded.format(relUncertainty1 + relUncertainty2) + "%\n";
        steps += value1 + " ± " + uncertainty1 + " * " + value2 + " ± " + uncertainty2 + " = " +
                result + " ± " + rounded.format(resultUncertainty);
        return new Result(equation, result, resultUncertainty, steps);
    }
    
    public static Result divide(double value1, double uncertainty1, double value2, double uncertainty2) {
        DecimalFormat rounded = new DecimalFormat("#.000");
        String steps = "";
        String equation = value1 + " ± " + uncertainty1 + " * " + value2 + " ± " + uncertainty2;
        double result = BigDecimal.valueOf(value1).divide(BigDecimal.valueOf(value2), BigDecimal.ROUND_HALF_UP).doubleValue();
        double relUncertainty1 = BigDecimal.valueOf(uncertainty1).setScale(128, BigDecimal.ROUND_HALF_UP).divide(BigDecimal.valueOf(value1), BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)).doubleValue();
        double relUncertainty2 = BigDecimal.valueOf(uncertainty2).setScale(128, BigDecimal.ROUND_HALF_UP).divide(BigDecimal.valueOf(value2), BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)).doubleValue();
        double resultUncertainty = BigDecimal.valueOf(relUncertainty1).setScale(128, BigDecimal.ROUND_HALF_UP).add(BigDecimal.valueOf(relUncertainty2)).divide(BigDecimal.valueOf(100), BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(result)).doubleValue();
        
        steps += value1 + " ± " + uncertainty1 + " / " + value2 + " ± " + uncertainty2 + " = " +
                value1 + " ± " + rounded.format(relUncertainty1) + "% / " + value2 + " ± " + rounded.format(relUncertainty2) + "%\n";
        steps += value1 + " ± " + uncertainty1 + " / " + value2 + " ± " + uncertainty2 + " = " +
                "(" + value1 + " / " + value2 + ") ± (" + rounded.format(relUncertainty1) + "% + " + rounded.format(relUncertainty2) + "%)";
        steps += value1 + " ± " + uncertainty1 + " / " + value2 + " ± " + uncertainty2 + " = " +
                result + " ± " + rounded.format(relUncertainty1 + relUncertainty2) + "%";
        steps += value1 + " ± " + uncertainty1 + " / " + value2 + " ± " + uncertainty2 + " = " +
                result + " ± " + rounded.format(resultUncertainty);
        return new Result(equation, result, resultUncertainty, steps);
    }
}
