package DZ_3.Task_1;

public class Calculator<T extends Number> {

    public static <T extends Number> T sum(T num1, T num2) {
        return (T)Double.valueOf(num1.doubleValue() + num2.doubleValue());
    }

    public static <T extends Number> T mult(T num1, T num2) {
        return (T)Double.valueOf(num1.doubleValue() * num2.doubleValue());
    }

    public static <T extends Number> T div(T num1, T num2) {
        if(num2.intValue()==0) throw new ArithmeticException("Divided by zero");
        return (T)Double.valueOf(num1.doubleValue() / num2.doubleValue());
    }

    public static <T extends Number> T sub(T num1, T num2) {
        return (T)Double.valueOf(num1.doubleValue() - num2.doubleValue());
    }
}