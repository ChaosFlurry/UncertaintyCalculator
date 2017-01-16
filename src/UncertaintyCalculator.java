import calculations.ArithmeticOperation;
import calculations.Result;
import calculations.TrigOperation;

public class UncertaintyCalculator {
    public static void main(String[] args) {
        Result test = TrigOperation.sin(0.5, 0.1);
        Result a = ArithmeticOperation.multiply(3, 1, 10, 3);
        System.out.println(test);
    }

}
