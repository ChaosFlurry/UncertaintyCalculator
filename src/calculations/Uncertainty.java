package calculations;

import java.math.BigDecimal;

public class Uncertainty {
    private BigDecimal value;
    private BigDecimal absUncertainty;
    private BigDecimal relUncertainty;
    
    private int valueDecimalPrecision;
    private int uncertaintyDecimalPrecision;
    
    public static String ABSOLUTE = "absolute";
    public static String RELATIVE = "relative";
    
    public Uncertainty(String value, String uncertainty, String uncertaintyType) {
        if (uncertaintyType.toLowerCase().equals("absolute") || uncertaintyType.toLowerCase().equals("abs")) {
            this.value = new BigDecimal(value);
            this.absUncertainty = new BigDecimal(uncertainty);
            this.relUncertainty = this.absUncertainty.divide(this.value, BigDecimal.ROUND_HALF_UP);
            
            // Set the precision of the value
            // Precision is > 0 if the value has decimals, else precision is 0
            if (value.contains(".")) {
                // Remove scientific notation after the decimal places (if formatted in scientific notation)
                if (value.contains("e") || value.contains("E")) {
                    this.valueDecimalPrecision = value.split("[e|E]")[0].split("\\.")[1].length();
                } else {
                    valueDecimalPrecision = value.split("\\.")[1].length();
                }
            } else {
                this.valueDecimalPrecision = 0;
            }
            
            // Set the precision of the uncertainty
            // Precision is > 0 if the uncertainty has decimals, else precision is 0
            if (uncertainty.contains(".")) {
                // Remove scientific notation after the decimal places (if formatted in scientific notation)
                if (uncertainty.contains("e") || uncertainty.contains("E")) {
                    this.valueDecimalPrecision = uncertainty.split("[e|E]")[0].split("\\.")[1].length();
                } else {
                    valueDecimalPrecision = uncertainty.split("\\.")[1].length();
                }
            } else {
                this.uncertaintyDecimalPrecision = 0;
            }
        } else if (uncertaintyType.toLowerCase().equals("relative") || uncertaintyType.toLowerCase().equals("rel") ) {
            this.value = new BigDecimal(value);
            this.relUncertainty = new BigDecimal(uncertainty).divide(BigDecimal.valueOf(100), BigDecimal.ROUND_HALF_UP);
            this.absUncertainty = this.relUncertainty.multiply(this.value);
            
            // Set the precision of the value
            // Precision is > 0 if the value has decimals, else precision is 0
            if (value.contains(".")) {
                // Remove scientific notation after the decimal places (if formatted in scientific notation)
                if (value.contains("e") || value.contains("E")) {
                    this.valueDecimalPrecision = value.split("[e|E]")[0].split("\\.")[1].length();
                } else {
                    valueDecimalPrecision = value.split("\\.")[1].length();
                }
            } else {
                this.valueDecimalPrecision = 0;
            }
    
            // Set the precision of the uncertainty
            // Precision is > 0 if the uncertainty has decimals, else precision is 0
            if (uncertainty.contains(".")) {
                // Remove scientific notation after the decimal places (if formatted in scientific notation)
                if (uncertainty.contains("e") || uncertainty.contains("E")) {
                    this.valueDecimalPrecision = uncertainty.split("[e|E]")[0].split("\\.")[1].length();
                } else {
                    valueDecimalPrecision = uncertainty.split("\\.")[1].length();
                }
            } else {
                this.uncertaintyDecimalPrecision = 0;
            }
        } else {
            throw new IllegalArgumentException("Unknown uncertainty type - " + uncertaintyType);
        }
    }
    
    public Uncertainty(BigDecimal value, BigDecimal uncertainty, String uncertaintyType) {
        this(value.toString(), uncertainty.toString(), uncertaintyType);
    }
    
    public Uncertainty(double value, double uncertainty, String uncertaintyType) {
        this(Double.toString(value), Double.toString(uncertainty), uncertaintyType);
    }
    
    public Uncertainty(int value, int uncertainty, String uncertaintyType) {
        this(Integer.toString(value), Integer.toString(uncertainty), uncertaintyType);
    }
    
    public BigDecimal getValue() {
        return value;
    }
    public BigDecimal getAbsUncertainty() {
        return absUncertainty;
    }
    public BigDecimal getRelUncertaintyAsDecimal() {
        return relUncertainty;
    }
    public BigDecimal getRelUncertaintyAsPercentage() { return relUncertainty.multiply(BigDecimal.valueOf(100)); }
    public int getValueDecimalPrecision() {
        return valueDecimalPrecision;
    }
    public int getUncertaintyDecimalPrecision() {
        return uncertaintyDecimalPrecision;
    }
    
    public void setValue(BigDecimal value) {
        this.value = value;
    }
    public void setAbsUncertainty(BigDecimal absUncertainty) {
        this.absUncertainty = absUncertainty;
    }
    public void setRelUncertaintyFromDecimal(BigDecimal relUncertainty) {
        this.relUncertainty = relUncertainty;
    }
    public void setRelUncertaintyFromPercentage(BigDecimal relUncertainty) {
        this.relUncertainty = relUncertainty.divide(BigDecimal.valueOf(100), BigDecimal.ROUND_HALF_UP);
    }
    public void setValueDecimalPrecision(int valueDecimalPrecision) {
        this.valueDecimalPrecision = valueDecimalPrecision;
    }
    public void setUncertaintyDecimalPrecision(int uncertaintyDecimalPrecision) {
        this.uncertaintyDecimalPrecision = uncertaintyDecimalPrecision;
    }
}
