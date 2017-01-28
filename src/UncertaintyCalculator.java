import calculations.ArithmeticOperation;
import calculations.Input;
import calculations.Result;
import calculations.TrigOperation;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class UncertaintyCalculator {
    public static void main(String[] args) {
        ArithmeticOperation calculator = new ArithmeticOperation(5, 5, false);
        Input input = new Input();
        while (true) {
            System.out.print(">> ");
            String command = input.getInput().toLowerCase();
            if (command.equals("add")) {
                double v1 = Double.parseDouble(input.getInput("Value 1: "));
                double u1 = Double.parseDouble(input.getInput("Uncertainty 1: "));
                double v2 = Double.parseDouble(input.getInput("Value 2: "));
                double u2 = Double.parseDouble(input.getInput("Uncertainty 2: "));
                Result r = calculator.add(v1, u1, v2, u2);
                System.out.println("\n" + r);
            } else if (command.equals("add c")) {
                double v1 = Double.parseDouble(input.getInput("Value 1: "));
                double u1 = Double.parseDouble(input.getInput("Uncertainty 1: "));
                double c = Double.parseDouble(input.getInput("Constant: "));
                Result r = calculator.add(v1, u1, c);
                System.out.println("\n" + r);
            } else if (command.equals("subtract")) {
                double v1 = Double.parseDouble(input.getInput("Value 1: "));
                double u1 = Double.parseDouble(input.getInput("Uncertainty 1: "));
                double v2 = Double.parseDouble(input.getInput("Value 2: "));
                double u2 = Double.parseDouble(input.getInput("Uncertainty 2: "));
                Result r = calculator.subtract(v1, u1, v2, u2);
                System.out.println(r);
            } else if (command.equals("subtract c")) {
                double v1 = Double.parseDouble(input.getInput("Value 1: "));
                double u1 = Double.parseDouble(input.getInput("Uncertainty 1: "));
                double c = Double.parseDouble(input.getInput("Constant: "));
                Result r = calculator.subtract(v1, u1, c);
                System.out.println("\n" + r);
            } else if (command.equals("multiply")) {
                double v1 = Double.parseDouble(input.getInput("Value 1: "));
                double u1 = Double.parseDouble(input.getInput("Uncertainty 1: "));
                double v2 = Double.parseDouble(input.getInput("Value 2: "));
                double u2 = Double.parseDouble(input.getInput("Uncertainty 2: "));
                Result r = calculator.multiply(v1, u1, v2, u2);
                System.out.println(r);
            } else if (command.equals("multiply c")) {
                double v1 = Double.parseDouble(input.getInput("Value 1: "));
                double u1 = Double.parseDouble(input.getInput("Uncertainty 1: "));
                double c = Double.parseDouble(input.getInput("Constant: "));
                Result r = calculator.multiply(v1, u1, c);
                System.out.println("\n" + r);
            } else if (command.equals("divide")) {
                double v1 = Double.parseDouble(input.getInput("Value 1: "));
                double u1 = Double.parseDouble(input.getInput("Uncertainty 1: "));
                double v2 = Double.parseDouble(input.getInput("Value 2: "));
                double u2 = Double.parseDouble(input.getInput("Uncertainty 2: "));
                Result r = calculator.divide(v1, u1, v2, u2);
                System.out.println(r);
            } else if (command.equals("divide c")) {
                double v1 = Double.parseDouble(input.getInput("Value 1: "));
                double u1 = Double.parseDouble(input.getInput("Uncertainty 1: "));
                double c = Double.parseDouble(input.getInput("Constant: "));
                Result r = calculator.divide(v1, u1, c);
                System.out.println("\n" + r);
            } else if (command.equals("exit")) {
                input.close();
                System.exit(0);
            } else {
                System.out.println("Unknown command");
            }
        }
    }
}