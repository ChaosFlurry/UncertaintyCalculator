package calculations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class TrigOperation {
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

