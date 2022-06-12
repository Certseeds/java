import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        final double cal = 0.000000000000000000000000000000000000001;
        // System.out.println(cal);
        Scanner input = new Scanner(System.in);
        int times = input.nextInt();
        for (int i = 0; i < times; i++) {
            final double inputNumber = input.nextDouble();
            double begin = 0;
            double middleValue = 0;
            double endValue = 100;
            while (endValue - begin > cal) {
                middleValue = (begin + endValue) / 2;
                if (calculate(middleValue, inputNumber) > 0) {
                    endValue = middleValue - cal;
                } else if (calculate(middleValue, inputNumber) < 0) {
                    begin = middleValue + cal;
                }
                if (Math.abs(calculate(middleValue, inputNumber)) < Math.pow(10, -5)) {
                    if (i + 1 == times) {
                        System.out.printf("%s%d%s%.4f", "Case ", i + 1, ": ", calculate2(middleValue, inputNumber));
                    } else {
                        System.out.printf("%s%d%s%.4f\n", "Case ", i + 1, ": ", calculate2(middleValue, inputNumber));
                    }
                    break;
                }
            }
        }
    }

    public static double calculate(double mid, double y) {
        return 35 * Math.pow(mid, 6) + 36 * Math.pow(mid, 5) + 9 * Math.pow(mid, 2) + 8 * Math.pow(mid, 1) - 2 * y;
    }

    public static double calculate2(double mid, double y) {
        return 5 * Math.pow(mid, 7) + 6 * Math.pow(mid, 6) + 3 * Math.pow(mid, 3) + 4 * Math.pow(mid, 2) - 2 * y * mid;
    }
}