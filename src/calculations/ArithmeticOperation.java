package calculations;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@SuppressWarnings("unused")
public class ArithmeticOperation {
    private DecimalFormat intermittentRounding;
    private DecimalFormat finalRounding;
    
    public ArithmeticOperation() {
        this.intermittentRounding = decimalFormatGenerator(3, 3, false);
        this.finalRounding = decimalFormatGenerator(1, 1, false);
    }
    
    /**
     * @param scientificNotation
     */
    public ArithmeticOperation(boolean scientificNotation) {
        this.intermittentRounding = decimalFormatGenerator(3, 3, scientificNotation);
        this.finalRounding = decimalFormatGenerator(1, 1, scientificNotation);
    }
    
    /**
     * @param decimalPlaces
     * @param minDisplayedDecimalPlaces
     * @param scientificNotation
     */
    public ArithmeticOperation(int decimalPlaces, int minDisplayedDecimalPlaces, boolean scientificNotation) {
        this.intermittentRounding = decimalFormatGenerator(decimalPlaces, minDisplayedDecimalPlaces, scientificNotation);
        this.finalRounding = decimalFormatGenerator(decimalPlaces, minDisplayedDecimalPlaces, scientificNotation);
    }
    
    /**
     * @param intermittentDecimalPlaces
     * @param intermittentMinDisplayedDecimalPlaces
     * @param finalDecimalPlaces
     * @param finalMinDisplayedDecimalPlaces
     */
    public ArithmeticOperation(int intermittentDecimalPlaces, int intermittentMinDisplayedDecimalPlaces,
                               int finalDecimalPlaces, int finalMinDisplayedDecimalPlaces, boolean scientificNotation) {
        this.intermittentRounding = decimalFormatGenerator(intermittentDecimalPlaces, intermittentMinDisplayedDecimalPlaces, scientificNotation);
        this.finalRounding = decimalFormatGenerator(finalDecimalPlaces, finalMinDisplayedDecimalPlaces, scientificNotation);
    }
    
    /**
     * @param rounding
     */
    public ArithmeticOperation(DecimalFormat rounding) {
        this.intermittentRounding = rounding;
        this.finalRounding = rounding;
    }
    
    /**
     * @param intermittentRounding
     * @param finalRounding
     */
    public ArithmeticOperation(DecimalFormat intermittentRounding, DecimalFormat finalRounding) {
        this.intermittentRounding = intermittentRounding;
        this.finalRounding = finalRounding;
    }
    
    public DecimalFormat getIntermittentRounding() {
        return intermittentRounding;
    }
    
    public DecimalFormat getFinalRounding() {
        return finalRounding;
    }
    
    public void setIntermittentRounding(DecimalFormat intermittentRounding) {
        this.intermittentRounding = intermittentRounding;
    }
    
    public void setIntermittentRounding(int decimalPlaces, int minDisplayedDecimalPlaces, boolean scientificNotation) {
        this.intermittentRounding = decimalFormatGenerator(decimalPlaces, minDisplayedDecimalPlaces, scientificNotation);
    }
    
    public void setFinalRounding(DecimalFormat finalRounding) {
        this.finalRounding = finalRounding;
    }
    
    public void setFinalRounding(int decimalPlaces, int minDisplayedDecimalPlaces, boolean scientificNotation) {
        this.finalRounding = decimalFormatGenerator(decimalPlaces, minDisplayedDecimalPlaces, scientificNotation);
    }
    
    /**
     * @param decimalPlaces
     * @param minDisplayedDecimalPlaces
     * @param scientificNotation
     * @return
     */
    public static DecimalFormat decimalFormatGenerator(int decimalPlaces, int minDisplayedDecimalPlaces, boolean scientificNotation) {
        if (decimalPlaces < 0) {
            throw new IllegalArgumentException("Number of decimal places cannot be less than 0");
        }
        if (minDisplayedDecimalPlaces < 0) {
            throw new IllegalArgumentException("Minimum number of decimal places cannot be less than 0");
        }
        if (minDisplayedDecimalPlaces > decimalPlaces) {
            minDisplayedDecimalPlaces = decimalPlaces;
        }
        
        String format;
        if (scientificNotation) {
            format = "0";
            if (decimalPlaces > 0) {
                format += ".";
                for (int i = 0; i < minDisplayedDecimalPlaces; i++) {
                    format += "0";
                }
                for (int i = 0; i < (decimalPlaces - minDisplayedDecimalPlaces); i++) {
                    format += "#";
                }
            }
            format += "E0";
        } else {
            format = "0";
            if (decimalPlaces > 0) {
                format += ".";
                for (int i = 0; i < minDisplayedDecimalPlaces; i++) {
                    format += "0";
                }
                for (int i = 0; i < (decimalPlaces - minDisplayedDecimalPlaces); i++) {
                    format += "#";
                }
            }
        }
        return new DecimalFormat(format);
    }
    
    public Result add(double value1, double uncertainty1, double value2, double uncertainty2) {
        String steps = "";
        String equation = value1 + " ± " + uncertainty1 + " + " + value2 + " ± " + uncertainty2;
        double result = BigDecimal.valueOf(value1).setScale(128, BigDecimal.ROUND_HALF_UP).add(BigDecimal.valueOf(value2)).doubleValue();
        double resultUncertainty = BigDecimal.valueOf(uncertainty1).setScale(128, BigDecimal.ROUND_HALF_UP).add(BigDecimal.valueOf(uncertainty2)).doubleValue();
        
        steps += value1 + " ± " + uncertainty1 + " + " + value2 + " ± " + uncertainty2 + " = " +
                "(" + intermittentRounding.format(value1) + " + " + intermittentRounding.format(value2) + ") ± (" + intermittentRounding.format(uncertainty1) + " + " + intermittentRounding.format(uncertainty2) + ")" + "\n";
        steps += value1 + " ± " + uncertainty1 + " + " + value2 + " ± " + uncertainty2 + " = " +
                finalRounding.format(result) + " ± " + finalRounding.format(resultUncertainty) + "\n";
        return new Result(equation, result, resultUncertainty, steps);
    }
    
    public Result add(double value, double uncertainty, double constant) {
        String steps = "";
        String equation = value + " ± " + uncertainty + " + " + constant;
        double result = BigDecimal.valueOf(value).setScale(128, BigDecimal.ROUND_HALF_UP).add(BigDecimal.valueOf(constant)).doubleValue();
        
        steps += value + " ± " + uncertainty + " + " + constant + " = " +
                "(" + intermittentRounding.format(value) + " + " + intermittentRounding.format(constant) + ") ± " + intermittentRounding.format(uncertainty) + "\n";
        steps += value + " ± " + uncertainty + " + " + constant + " = " +
                finalRounding.format(result) + " ±" + finalRounding.format(uncertainty) + "\n";
        return new Result(equation, result, uncertainty, steps);
    }
    
    public Result subtract(double value1, double uncertainty1, double value2, double uncertainty2) {
        String steps = "";
        String equation = value1 + " ± " + uncertainty1 + " - " + value2 + " ± " + uncertainty2;
        double result = BigDecimal.valueOf(value1).setScale(128, BigDecimal.ROUND_HALF_UP).add(BigDecimal.valueOf(value2)).doubleValue();
        double resultUncertainty = BigDecimal.valueOf(uncertainty1).setScale(128, BigDecimal.ROUND_HALF_UP).add(BigDecimal.valueOf(uncertainty2)).doubleValue();
        
        steps += value1 + " ± " + uncertainty1 + " - " + value2 + " ± " + uncertainty2 + " = " +
                "(" + intermittentRounding.format(value1) + " - " + intermittentRounding.format(value2) + ") ± (" + intermittentRounding.format(uncertainty1) + " + " + intermittentRounding.format(uncertainty2) + ")\n";
        steps += value1 + " ± " + uncertainty1 + " - " + value2 + " ± " + uncertainty2 + " = " +
                finalRounding.format(result) + " ± " + finalRounding.format(resultUncertainty) + "\n";
        return new Result(equation, result, resultUncertainty, steps);
    }
    
    public Result subtract(double value, double uncertainty, double constant) {
        String steps = "";
        String equation = value + " ± " + uncertainty + " - " + constant;
        double result = BigDecimal.valueOf(value).setScale(128, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.valueOf(constant)).doubleValue();
        
        steps += value + " ± " + uncertainty + " - " + constant + " = " +
                "(" + intermittentRounding.format(value) + " - " + intermittentRounding.format(constant) + ") ± " + intermittentRounding.format(uncertainty) + "\n";
        steps += value + " ± " + uncertainty + " - " + constant + " = " +
                finalRounding.format(result) + " ±" + finalRounding.format(uncertainty) + "\n";
        return new Result(equation, result, uncertainty, steps);
    }
    
    public Result multiply(double value1, double uncertainty1, double value2, double uncertainty2) {
        String steps = "";
        String equation = value1 + " ± " + uncertainty1 + " * " + value2 + " ± " + uncertainty2;
        double result = BigDecimal.valueOf(value1).multiply(BigDecimal.valueOf(value2)).doubleValue();
        double relUncertainty1 = BigDecimal.valueOf(uncertainty1).setScale(128, BigDecimal.ROUND_HALF_UP).divide(BigDecimal.valueOf(value1), BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)).doubleValue();
        double relUncertainty2 = BigDecimal.valueOf(uncertainty2).setScale(128, BigDecimal.ROUND_HALF_UP).divide(BigDecimal.valueOf(value2), BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)).doubleValue();
        double resultUncertainty = BigDecimal.valueOf(relUncertainty1).setScale(128, BigDecimal.ROUND_HALF_UP).add(BigDecimal.valueOf(relUncertainty2)).divide(BigDecimal.valueOf(100), BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(result)).doubleValue();
        
        steps += value1 + " ± " + uncertainty1 + " * " + value2 + " ± " + uncertainty2 + " = " +
                intermittentRounding.format(value1) + " ± " + intermittentRounding.format(relUncertainty1) + "% * " + intermittentRounding.format(value2) + " ± " + intermittentRounding.format(relUncertainty2) + "%" + "\n";
        steps += value1 + " ± " + uncertainty1 + " * " + value2 + " ± " + uncertainty2 + " = " +
                "(" + intermittentRounding.format(value1) + " * " + intermittentRounding.format(value2) + ") ± (" + intermittentRounding.format(relUncertainty1) + "% + " + intermittentRounding.format(relUncertainty2) + "%)" + "\n";
        steps += value1 + " ± " + uncertainty1 + " * " + value2 + " ± " + uncertainty2 + " = " +
                intermittentRounding.format(result) + " ± " + intermittentRounding.format(relUncertainty1 + relUncertainty2) + "%" + "\n";
        steps += value1 + " ± " + uncertainty1 + " * " + value2 + " ± " + uncertainty2 + " = " +
                finalRounding.format(result) + " ± " + finalRounding.format(resultUncertainty) + "\n";
        return new Result(equation, result, resultUncertainty, steps);
    }
    
    public Result multiply(double value, double uncertainty, double constant) {
        String steps = "";
        String equation = value + " ± " + uncertainty + " * " + constant;
        double result = BigDecimal.valueOf(value).setScale(128, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(constant)).doubleValue();
        double relUncertainty = BigDecimal.valueOf(uncertainty).setScale(128, BigDecimal.ROUND_HALF_UP).divide(BigDecimal.valueOf(value), BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)).doubleValue();
        double resultUncertainty = BigDecimal.valueOf(relUncertainty).setScale(128, BigDecimal.ROUND_HALF_UP).divide(BigDecimal.valueOf(100), BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(result)).doubleValue();
    
        steps += value + " ± " + uncertainty + " * " + constant + " = " +
                "(" + intermittentRounding.format(value) + " * " + intermittentRounding.format(constant) + ")" +
                " ± " + intermittentRounding.format(relUncertainty) + "%" + "\n";
        steps += value + " ± " + uncertainty + " * " + constant + " = " +
                intermittentRounding.format(result) + " ± " + intermittentRounding.format(relUncertainty) + "%" + "\n";
        steps += value + " ± " + uncertainty + " * " + constant + " = " +
                finalRounding.format(result) + " ±" + finalRounding.format(uncertainty) + "\n";
        return new Result(equation, result, resultUncertainty, steps);
    }
    
    public Result divide(double value1, double uncertainty1, double value2, double uncertainty2) {
        String steps = "";
        String equation = value1 + " ± " + uncertainty1 + " * " + value2 + " ± " + uncertainty2;
        double result = BigDecimal.valueOf(value1).divide(BigDecimal.valueOf(value2), BigDecimal.ROUND_HALF_UP).doubleValue();
        double relUncertainty1 = BigDecimal.valueOf(uncertainty1).setScale(128, BigDecimal.ROUND_HALF_UP).divide(BigDecimal.valueOf(value1), BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)).doubleValue();
        double relUncertainty2 = BigDecimal.valueOf(uncertainty2).setScale(128, BigDecimal.ROUND_HALF_UP).divide(BigDecimal.valueOf(value2), BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)).doubleValue();
        double resultUncertainty = BigDecimal.valueOf(relUncertainty1).setScale(128, BigDecimal.ROUND_HALF_UP).add(BigDecimal.valueOf(relUncertainty2)).divide(BigDecimal.valueOf(100), BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(result)).doubleValue();
        
        steps += value1 + " ± " + uncertainty1 + " / " + value2 + " ± " + uncertainty2 + " = " +
                intermittentRounding.format(value1) + " ± " + intermittentRounding.format(relUncertainty1) + "% / " + intermittentRounding.format(value2) + " ± " + intermittentRounding.format(relUncertainty2) + "%" + "\n";
        steps += value1 + " ± " + uncertainty1 + " / " + value2 + " ± " + uncertainty2 + " = " +
                "(" + intermittentRounding.format(value1) + " / " + intermittentRounding.format(value2) + ") ± (" + intermittentRounding.format(relUncertainty1) + "% + " + intermittentRounding.format(relUncertainty2) + "%)" + "\n";
        steps += value1 + " ± " + uncertainty1 + " / " + value2 + " ± " + uncertainty2 + " = " +
                intermittentRounding.format(result) + " ± " + intermittentRounding.format(relUncertainty1 + relUncertainty2) + "%" + "\n";
        steps += value1 + " ± " + uncertainty1 + " / " + value2 + " ± " + uncertainty2 + " = " +
                finalRounding.format(result) + " ± " + finalRounding.format(resultUncertainty) + "\n";
        return new Result(equation, result, resultUncertainty, steps);
    }
    
    public Result divide(double value, double uncertainty, double constant) {
        String steps = "";
        String equation = value + " ± " + uncertainty + " / " + constant;
        double result = BigDecimal.valueOf(value).setScale(128, BigDecimal.ROUND_HALF_UP).divide(BigDecimal.valueOf(constant), BigDecimal.ROUND_HALF_UP).doubleValue();
        double relUncertainty = BigDecimal.valueOf(uncertainty).setScale(128, BigDecimal.ROUND_HALF_UP).divide(BigDecimal.valueOf(value), BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)).doubleValue();
        double resultUncertainty = BigDecimal.valueOf(relUncertainty).setScale(128, BigDecimal.ROUND_HALF_UP).divide(BigDecimal.valueOf(100), BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(result)).doubleValue();
        
        steps += value + " ± " + uncertainty + " / " + constant + " = " +
                "(" + intermittentRounding.format(value) + " / " + intermittentRounding.format(constant) + ")" +
                " ± " + intermittentRounding.format(relUncertainty) + "%" + "\n";
        steps += value + " ± " + uncertainty + " / " + constant + " = " +
                intermittentRounding.format(result) + " ± " + intermittentRounding.format(relUncertainty) + "%" + "\n";
        steps += value + " ± " + uncertainty + " / " + constant + " = " +
                finalRounding.format(result) + " ±" + finalRounding.format(uncertainty) + "\n";
        return new Result(equation, result, resultUncertainty, steps);
    }
}
