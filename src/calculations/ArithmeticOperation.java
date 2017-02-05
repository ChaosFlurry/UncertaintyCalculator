package calculations;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

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
    
    public Result add(Uncertainty x, Uncertainty y) {
        String equationLayout = "";
        ArrayList<BigDecimal> equationValues = new ArrayList<>();
        String stepsLayout = "";
        ArrayList<BigDecimal> stepValues = new ArrayList<>();
        
        BigDecimal answerValue = x.getValue().add(y.getValue());
        BigDecimal answerUncertainty = x.getAbsUncertainty().add(y.getAbsUncertainty());
        Uncertainty answer = new Uncertainty(answerValue, answerUncertainty, Uncertainty.ABSOLUTE);
    
        equationLayout += "V0 PM U0 + V1 PM U1";
        equationValues.add(x.getValue());
        equationValues.add(x.getAbsUncertainty());
        equationValues.add(y.getValue());
        equationValues.add(y.getAbsUncertainty());
        
        stepsLayout += "V0 PM U0 + V1 PM U1 = V0 + V1 PM U0 + U1";
        stepsLayout += "V0 PM U0 + V1 PM U1 = V2 PM U2";
        stepValues.add(x.getValue());
        stepValues.add(x.getAbsUncertainty());
        stepValues.add(y.getValue());
        stepValues.add(y.getAbsUncertainty());
        stepValues.add(x.getValue().add(y.getValue()));
        stepValues.add(x.getAbsUncertainty().add(y.getAbsUncertainty()));
        
        Map<String, ArrayList<BigDecimal>> equation = new LinkedHashMap<>();
        equation.put(equationLayout, equationValues);
        Map<String, ArrayList<BigDecimal>> steps = new LinkedHashMap<>();
        steps.put(stepsLayout, stepValues);
        
        return new Result(answer, equation, steps);
    }
    
    public Result add(ArrayList<Uncertainty> values) {
        String equationLayout = "";
        ArrayList<BigDecimal> equationValues = new ArrayList<>();
        String stepsLayout = "";
        ArrayList<BigDecimal> stepValues = new ArrayList<>();
        
        BigDecimal answerValue = BigDecimal.ZERO;
        BigDecimal answerUncertainty = BigDecimal.ZERO;
        for (int i = 0; i < values.size(); i++) {
            answerValue = answerValue.add(values.get(i).getValue());
            answerUncertainty = answerUncertainty.add(values.get(i).getAbsUncertainty());
    
            equationLayout += "V" + i + " PM " + "U" + i;
            equationValues.add(values.get(i).getValue());
            equationValues.add(values.get(i).getAbsUncertainty());
            
            /*
            v1 u1 v2 u2 v3 u3 = v1 v2 v3 u1 u2 u3
            v1 u1 v2 u2 v3 u3 = va ua
             */
        }
        
        for (int i = 0; i < values.size(); i++) {
            if (i == 0) {
                stepsLayout += " PM ";
                stepsLayout = "V" + (values.size() - 1) + stepsLayout;
                stepsLayout += "U" + i;
            } else {
                stepsLayout += " + " + "V" + i + " PM " + "U" + i;
                stepsLayout = "V" + (values.size() - 1 - i) + "+" + stepsLayout;
                stepsLayout += " + " + "U" + i;
            }
            // At the front of the list, add values back to front (Vn, Vn-1, ... V1, V0)
            stepValues.add(0, values.get(values.size() - 1 - i).getValue());
            // At the end of the list, add uncertainties in normal order (U0, U1, ... Un-1, Un)
            stepValues.add(stepValues.size() - 1, values.get(i).getAbsUncertainty());
            // End result will be a list with elements V0, V1 ... Vn-1, Vn, U0, U1, ... Un-1, Un)
        }
        stepsLayout += ""
        
        for (int i = 0; i < values.size(); i++) {
            if (i == 0) {
                stepsLayout += "V" + i;
            } else {
                stepsLayout += " + " + "V" + i;
            }
            stepsLayout +=
            
            if (i == 0) {
                stepsLayout += "V" + i + " PM " + "U" + i;
            } else {
                stepsLayout += " + " + "V" + i + " PM " + "U" + i;
            }
            stepValues.add(values.get(i).getValue());
            stepValues.add(values.get(i).getAbsUncertainty());
        }
        stepsLayout += ""
        
        stepsLayout += "V" + (values.size() + 1) + " PM " + "U" + (values.size() + 1);
        stepValues.add(answerValue);
        stepValues.add(answerUncertainty);
        
        Uncertainty answer = new Uncertainty(answerValue, answerUncertainty, Uncertainty.ABSOLUTE);
        Map<String, ArrayList<BigDecimal>> equation = new LinkedHashMap<>();
        equation.put(equationLayout, equationValues);
        Map<String, ArrayList<BigDecimal>> steps = new LinkedHashMap<>();
        steps.put(stepsLayout, stepValues);
    
        return new Result(answer, equation, steps);
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
                finalRounding.format(result) + " ± " + finalRounding.format(uncertainty) + "\n";
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
                finalRounding.format(result) + " ± " + finalRounding.format(uncertainty) + "\n";
        return new Result(equation, result, uncertainty, steps);
    }
    
    public Result multiply(double value1, double uncertainty1, double value2, double uncertainty2) {
        DecimalFormat inputRounding1 = new DecimalFormat();
        DecimalFormat inputRounding2 = new DecimalFormat();
        int decimals1 = BigDecimal.valueOf(uncertainty1).toPlainString().split("\\.")[1].length();
        int decimals2 = BigDecimal.valueOf(uncertainty2).toPlainString().split("\\.")[1].length();
        inputRounding1.setMinimumIntegerDigits(1);
        inputRounding2.setMinimumIntegerDigits(1);
        inputRounding1.setMinimumFractionDigits(decimals1);
        inputRounding1.setMaximumFractionDigits(decimals1);
        inputRounding2.setMinimumFractionDigits(decimals2);
        inputRounding2.setMaximumFractionDigits(decimals2);
        
        String steps = "";
        String equation = inputRounding1.format(value1) + " ± " + inputRounding1.format(uncertainty1) + " * " +
                inputRounding2.format(value2) + " ± " + inputRounding2.format(uncertainty2);
        double result = BigDecimal.valueOf(value1).multiply(BigDecimal.valueOf(value2)).doubleValue();
        double relUncertainty1 = BigDecimal.valueOf(uncertainty1).setScale(128, BigDecimal.ROUND_HALF_UP).divide(BigDecimal.valueOf(value1), BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)).doubleValue();
        double relUncertainty2 = BigDecimal.valueOf(uncertainty2).setScale(128, BigDecimal.ROUND_HALF_UP).divide(BigDecimal.valueOf(value2), BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)).doubleValue();
        double resultUncertainty = BigDecimal.valueOf(relUncertainty1).setScale(128, BigDecimal.ROUND_HALF_UP).add(BigDecimal.valueOf(relUncertainty2)).divide(BigDecimal.valueOf(100), BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(result)).doubleValue();
        
        steps += inputRounding1.format(value1) + " ± " + inputRounding1.format(uncertainty1) + " * " +
                inputRounding2.format(value2) + " ± " + inputRounding2.format(uncertainty2) + " = " +
                intermittentRounding.format(value1) + " ± " + intermittentRounding.format(relUncertainty1) + "% * " + intermittentRounding.format(value2) + " ± " + intermittentRounding.format(relUncertainty2) + "%" + "\n";
        steps += inputRounding1.format(value1) + " ± " + inputRounding1.format(uncertainty1) + " * " +
                inputRounding2.format(value2) + " ± " + inputRounding2.format(uncertainty2) + " = " +
                "(" + intermittentRounding.format(value1) + " * " + intermittentRounding.format(value2) + ") ± (" + intermittentRounding.format(relUncertainty1) + "% + " + intermittentRounding.format(relUncertainty2) + "%)" + "\n";
        steps += inputRounding1.format(value1) + " ± " + inputRounding1.format(uncertainty1) + " * " +
                inputRounding2.format(value2) + " ± " + inputRounding2.format(uncertainty2) + " = " +
                intermittentRounding.format(result) + " ± " + intermittentRounding.format(BigDecimal.valueOf(relUncertainty1).add(BigDecimal.valueOf(relUncertainty2))) + "%" + "\n";
        steps += inputRounding1.format(value1) + " ± " + inputRounding1.format(uncertainty1) + " * " +
                inputRounding2.format(value2) + " ± " + inputRounding2.format(uncertainty2) + " = " +
                finalRounding.format(result) + " ± " + finalRounding.format(resultUncertainty) + "\n";
        return new Result(equation, result, resultUncertainty, steps);
    }
    
    public Result multiply(double value, double uncertainty, double constant) {
        DecimalFormat inputRounding = new DecimalFormat();
        int decimals = BigDecimal.valueOf(uncertainty).toPlainString().split("\\.")[1].length();
        inputRounding.setMinimumIntegerDigits(1);
        inputRounding.setMinimumFractionDigits(decimals);
        inputRounding.setMaximumFractionDigits(decimals);
        
        String steps = "";
        String equation = inputRounding.format(value) + " ± " + inputRounding.format(uncertainty) + " * " + constant;
        double result = BigDecimal.valueOf(value).setScale(128, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(constant)).doubleValue();
        double relUncertainty = BigDecimal.valueOf(uncertainty).setScale(128, BigDecimal.ROUND_HALF_UP).divide(BigDecimal.valueOf(value), BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)).doubleValue();
        double resultUncertainty = BigDecimal.valueOf(relUncertainty).setScale(128, BigDecimal.ROUND_HALF_UP).divide(BigDecimal.valueOf(100), BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(result)).doubleValue();
    
        steps += inputRounding.format(value) + " ± " + inputRounding.format(uncertainty) + " * " + constant + " = " +
                "(" + intermittentRounding.format(value) + " * " + intermittentRounding.format(constant) + ")" +
                " ± " + intermittentRounding.format(relUncertainty) + "%" + "\n";
        steps += inputRounding.format(value) + " ± " + inputRounding.format(uncertainty) + " * " + constant + " = " +
                intermittentRounding.format(result) + " ± " + intermittentRounding.format(relUncertainty) + "%" + "\n";
        steps += inputRounding.format(value) + " ± " + inputRounding.format(uncertainty) + " * " + constant + " = " +
                finalRounding.format(result) + " ± " + finalRounding.format(resultUncertainty) + "\n";
        return new Result(equation, result, resultUncertainty, steps);
    }
    
    public Result divide(double value1, double uncertainty1, double value2, double uncertainty2) {
        DecimalFormat inputRounding1 = new DecimalFormat();
        DecimalFormat inputRounding2 = new DecimalFormat();
        int decimals1 = BigDecimal.valueOf(uncertainty1).toPlainString().split("\\.")[1].length();
        int decimals2 = BigDecimal.valueOf(uncertainty2).toPlainString().split("\\.")[1].length();
        inputRounding1.setMinimumIntegerDigits(1);
        inputRounding2.setMinimumIntegerDigits(1);
        inputRounding1.setMinimumFractionDigits(decimals1);
        inputRounding1.setMaximumFractionDigits(decimals1);
        inputRounding2.setMinimumFractionDigits(decimals2);
        inputRounding2.setMaximumFractionDigits(decimals2);
        
        String steps = "";
        String equation = inputRounding1.format(value1) + " ± " + inputRounding1.format(uncertainty1) + " / " +
                inputRounding2.format(value2) + " ± " + inputRounding2.format(uncertainty2);
        double result = BigDecimal.valueOf(value1).divide(BigDecimal.valueOf(value2), BigDecimal.ROUND_HALF_UP).doubleValue();
        double relUncertainty1 = BigDecimal.valueOf(uncertainty1).setScale(128, BigDecimal.ROUND_HALF_UP).divide(BigDecimal.valueOf(value1), BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)).doubleValue();
        double relUncertainty2 = BigDecimal.valueOf(uncertainty2).setScale(128, BigDecimal.ROUND_HALF_UP).divide(BigDecimal.valueOf(value2), BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)).doubleValue();
        double resultUncertainty = BigDecimal.valueOf(relUncertainty1).setScale(128, BigDecimal.ROUND_HALF_UP).add(BigDecimal.valueOf(relUncertainty2)).divide(BigDecimal.valueOf(100), BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(result)).doubleValue();
        
        steps += inputRounding1.format(value1) + " ± " + inputRounding1.format(uncertainty1) + " / " +
                inputRounding2.format(value2) + " ± " + inputRounding2.format(uncertainty2) + " = " +
                intermittentRounding.format(value1) + " ± " + intermittentRounding.format(relUncertainty1) + "% / " + intermittentRounding.format(value2) + " ± " + intermittentRounding.format(relUncertainty2) + "%" + "\n";
        steps += inputRounding1.format(value1) + " ± " + inputRounding1.format(uncertainty1) + " / " +
                inputRounding2.format(value2) + " ± " + inputRounding2.format(uncertainty2) + " = " +
                "(" + intermittentRounding.format(value1) + " / " + intermittentRounding.format(value2) + ") ± (" + intermittentRounding.format(relUncertainty1) + "% + " + intermittentRounding.format(relUncertainty2) + "%)" + "\n";
        steps += inputRounding1.format(value1) + " ± " + inputRounding1.format(uncertainty1) + " / " +
                inputRounding2.format(value2) + " ± " + inputRounding2.format(uncertainty2) + " = " +
                intermittentRounding.format(result) + " ± " + intermittentRounding.format(BigDecimal.valueOf(relUncertainty1).add(BigDecimal.valueOf(relUncertainty2))) + "%" + "\n";
        steps += inputRounding1.format(value1) + " ± " + inputRounding1.format(uncertainty1) + " / " +
                inputRounding2.format(value2) + " ± " + inputRounding2.format(uncertainty2) + " = " +
                finalRounding.format(result) + " ± " + finalRounding.format(resultUncertainty) + "\n";
        return new Result(equation, result, resultUncertainty, steps);
    }
    
    public Result divide(double value, double uncertainty, double constant) {
        DecimalFormat inputRounding = new DecimalFormat();
        int decimals = BigDecimal.valueOf(uncertainty).toPlainString().split("\\.")[1].length();
        inputRounding.setMinimumIntegerDigits(1);
        inputRounding.setMinimumFractionDigits(decimals);
        inputRounding.setMaximumFractionDigits(decimals);
        
        String steps = "";
        String equation = inputRounding.format(value) + " ± " + inputRounding.format(uncertainty) + " / " + constant;
        double result = BigDecimal.valueOf(value).setScale(128, BigDecimal.ROUND_HALF_UP).divide(BigDecimal.valueOf(constant), BigDecimal.ROUND_HALF_UP).doubleValue();
        double relUncertainty = BigDecimal.valueOf(uncertainty).setScale(128, BigDecimal.ROUND_HALF_UP).divide(BigDecimal.valueOf(value), BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)).doubleValue();
        double resultUncertainty = BigDecimal.valueOf(relUncertainty).setScale(128, BigDecimal.ROUND_HALF_UP).divide(BigDecimal.valueOf(100), BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(result)).doubleValue();
        
        steps += inputRounding.format(value) + " ± " + inputRounding.format(uncertainty) + " / " + constant + " = " +
                "(" + intermittentRounding.format(value) + " / " + intermittentRounding.format(constant) + ")" +
                " ± " + intermittentRounding.format(relUncertainty) + "%" + "\n";
        steps += inputRounding.format(value) + " ± " + inputRounding.format(uncertainty) + " / " + constant + " = " +
                intermittentRounding.format(result) + " ± " + intermittentRounding.format(relUncertainty) + "%" + "\n";
        steps += inputRounding.format(value) + " ± " + inputRounding.format(uncertainty) + " / " + constant + " = " +
                finalRounding.format(result) + " ± " + finalRounding.format(resultUncertainty) + "\n";
        return new Result(equation, result, resultUncertainty, steps);
    }
    
    public Result power(double value, double uncertainty, double power) {
        String steps = "";
        String equation = "(" + value + " ± " + uncertainty + ")" + "^" + power;
        double result = Math.pow(value, power);
        double relUncertainty = BigDecimal.valueOf(uncertainty).setScale(128, BigDecimal.ROUND_HALF_UP).divide(BigDecimal.valueOf(value), BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)).doubleValue();
        double resultUncertainty = BigDecimal.valueOf(relUncertainty).setScale(128, BigDecimal.ROUND_HALF_UP).divide(BigDecimal.valueOf(100), BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(power)).doubleValue();
        
        steps += "(" + value + " ± " + uncertainty + ")" + "^" + power + " = " +
                intermittentRounding.format(value) + "^" + intermittentRounding.format(power) + " ± " + intermittentRounding.format(power) + " * " + intermittentRounding.format(relUncertainty) + "%" + "\n";
        steps += "(" + value + " ± " + uncertainty + ")" + "^" + power + " = " +
                intermittentRounding.format(result) + " ± " + intermittentRounding.format(BigDecimal.valueOf(relUncertainty).multiply(BigDecimal.valueOf(power))) + "%" + "\n";
        steps += "(" + value + " ± " + uncertainty + ")" + "^" + power + " = " +
                finalRounding.format(result) + " ± " + finalRounding.format(resultUncertainty) + "\n";
        return new Result(equation, result, resultUncertainty, steps);
    }
    
    public Result root(double value, double uncertainty, double root) {
        String steps = "";
        String equation = "(" + value + " ± " + uncertainty + ")" + "^" + "(1/" + root + ")";
        double result = Math.pow(value, BigDecimal.ONE.setScale(128, BigDecimal.ROUND_HALF_UP).divide(BigDecimal.valueOf(root), BigDecimal.ROUND_HALF_UP).doubleValue());
        double relUncertainty = BigDecimal.valueOf(uncertainty).setScale(128, BigDecimal.ROUND_HALF_UP).divide(BigDecimal.valueOf(value), BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)).doubleValue();
        double resultUncertainty = BigDecimal.valueOf(relUncertainty).setScale(128, BigDecimal.ROUND_HALF_UP).divide(BigDecimal.valueOf(100), BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.ONE.setScale(128, BigDecimal.ROUND_HALF_UP).divide(BigDecimal.valueOf(root), BigDecimal.ROUND_HALF_UP)).doubleValue();
        
        steps += "(" + value + " ± " + uncertainty + ")" + "^" + "(1/" + root + ")" + " = " +
                intermittentRounding.format(value) + "^" + "(" + intermittentRounding.format(1) + "/" + intermittentRounding.format(root) + ")" +
                " ± " + "(" + intermittentRounding.format(1) + "/" + intermittentRounding.format(root) + ")" + " * " + intermittentRounding.format(relUncertainty) + "%" + "\n";
        steps += "(" + value + " ± " + uncertainty + ")" + "^" + "(1/" + root + ")" + " = " +
                intermittentRounding.format(result) + " ± " + intermittentRounding.format(BigDecimal.valueOf(relUncertainty).setScale(128, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.ONE.setScale(128, BigDecimal.ROUND_HALF_UP).divide(BigDecimal.valueOf(root), BigDecimal.ROUND_HALF_UP))) + "%" + "\n";
        steps += "(" + value + " ± " + uncertainty + ")" + "^" + "(1/" + root + ")" + " = " +
                finalRounding.format(result) + " ± " + finalRounding.format(resultUncertainty) + "\n";
        return new Result(equation, result, resultUncertainty, steps);
    }
}
