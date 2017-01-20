import calculations.ArithmeticOperation;
import calculations.Result;
import calculations.TrigOperation;

public class UncertaintyCalculator {
    public static void main(String[] args) {
        Result test = TrigOperation.sin(0.5, 0.1);
        ArithmeticOperation calculator = new ArithmeticOperation(false);
        Result a = calculator.multiply(3, 1, 10, 3);
        System.out.println(test);
        System.out.println(a);
        Result root = calculator.root(10, 7, 3);
        System.out.println(root);
        Result power = calculator.power(256, 3.3, 5);
        System.out.println(power);
    }
}
