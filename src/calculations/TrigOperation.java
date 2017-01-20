package calculations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

@SuppressWarnings("unused")
public class TrigOperation {
    private DecimalFormat intermittentRounding;
    private DecimalFormat finalRounding;
    
    public TrigOperation() {
        this.intermittentRounding = decimalFormatGenerator(3, 3, false);
        this.finalRounding = decimalFormatGenerator(1, 1, false);
    }
    
    /**
     * @param scientificNotation
     */
    public TrigOperation(boolean scientificNotation) {
        this.intermittentRounding = decimalFormatGenerator(3, 3, scientificNotation);
        this.finalRounding = decimalFormatGenerator(1, 1, scientificNotation);
    }
    
    /**
     * @param decimalPlaces
     * @param minDisplayedDecimalPlaces
     * @param scientificNotation
     */
    public TrigOperation(int decimalPlaces, int minDisplayedDecimalPlaces, boolean scientificNotation) {
        this.intermittentRounding = decimalFormatGenerator(decimalPlaces, minDisplayedDecimalPlaces, scientificNotation);
        this.finalRounding = decimalFormatGenerator(decimalPlaces, minDisplayedDecimalPlaces, scientificNotation);
    }
    
    /**
     * @param intermittentDecimalPlaces
     * @param intermittentMinDisplayedDecimalPlaces
     * @param finalDecimalPlaces
     * @param finalMinDisplayedDecimalPlaces
     */
    public TrigOperation(int intermittentDecimalPlaces, int intermittentMinDisplayedDecimalPlaces,
                               int finalDecimalPlaces, int finalMinDisplayedDecimalPlaces, boolean scientificNotation) {
        this.intermittentRounding = decimalFormatGenerator(intermittentDecimalPlaces, intermittentMinDisplayedDecimalPlaces, scientificNotation);
        this.finalRounding = decimalFormatGenerator(finalDecimalPlaces, finalMinDisplayedDecimalPlaces, scientificNotation);
    }
    
    /**
     * @param rounding
     */
    public TrigOperation(DecimalFormat rounding) {
        this.intermittentRounding = rounding;
        this.finalRounding = rounding;
    }
    
    /**
     * @param intermittentRounding
     * @param finalRounding
     */
    public TrigOperation(DecimalFormat intermittentRounding, DecimalFormat finalRounding) {
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
    
    public static double toDeg(double rad) {
        return rad / Math.PI * 180.0;
    }
    
    public static double toRad(double deg) {
        return deg / 180.0 * Math.PI;
    }
    
    public static Result sin(double rad, double uncertainty) {
        DecimalFormat rounded = new DecimalFormat("#.#####");
        String steps = "";
        String equation = "sin(" + rounded.format(rad) + " ± " + rounded.format(uncertainty) + ")";
        double min = Math.min(Math.sin(rad - uncertainty), Math.sin(rad + uncertainty));
        double max = Math.max(Math.sin(rad - uncertainty), Math.sin(rad + uncertainty));
        double result = Math.sin(rad);
        if (Math.sin(rad - uncertainty) < Math.sin(rad + uncertainty)) {
            steps += "sin(" + rad + " ± " + uncertainty + ") = " + "sin(" + rad + ")" +
                    " ± (sin(" + rounded.format(rad + uncertainty) + ")" +
                    " - sin(" + rounded.format(rad - uncertainty) + ")) / 2\n";
        } else {
            steps += "sin(" + rad + " ± " + uncertainty + ") = " + "sin(" + rad + ")" +
                    " ± (sin(" + rounded.format(rad - uncertainty) + ")" +
                    " - sin(" + rounded.format(rad + uncertainty) + ")) / 2\n";
        }
        steps += "sin(" + rad + " ± " + uncertainty + ") = " + rounded.format(result) + " ± (" + rounded.format(max) + " - " + rounded.format(min) + ") / 2\n";
        double resultUncertainty = BigDecimal.valueOf(max).subtract(BigDecimal.valueOf(min)).divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP).abs().doubleValue();
        steps += "sin(" + rad + " ± " + uncertainty + ") = " + rounded.format(result) + " ± " + rounded.format(resultUncertainty);
        
        double roundedResult = Double.parseDouble(rounded.format(result));
        double roundedUncertainty = Double.parseDouble(rounded.format(resultUncertainty));
        return new Result(equation, roundedResult, roundedUncertainty, steps);
    }
    
    public static Result cos(double rad, double uncertainty) {
        return null;
    }
    
    public static Result tan(double rad, double uncertainty) {
        return null;
    }
}

