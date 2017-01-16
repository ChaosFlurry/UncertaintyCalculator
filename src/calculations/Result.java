package calculations;

import java.math.BigDecimal;

@SuppressWarnings("unused")
public class Result {
    private String equation;
    private double value;
    private double uncertainty;
    private String steps;
    
    public Result(String equation, double value, double uncertainty, String steps) {
        this.equation = equation;
        this.value = value;
        this.uncertainty = uncertainty;
        this.steps = steps;
    }
    
    public String getEquation() {
        return equation;
    }
    public double getValue() {
        return value;
    }
    public double getAbsoluteUncertainty() {
        return uncertainty;
    }
    public double getRelativeUncertainty() {
        return uncertainty / value * 100;
    }
    public String getSteps() {
        return steps;
    }
    
    public void setEquation(String equation) {
        this.equation = equation;
    }
    public void setValue(double result) {
        this.value = result;
    }
    public void setUncertainty(double uncertainty) {
        this.uncertainty = uncertainty;
    }
    public void setSteps(String steps) {
        this.steps = steps;
    }
    
    @Override
    public String toString() {
        return equation + "\n" +
                value + " ± " + uncertainty + "\n" +
                steps;
    }
}
