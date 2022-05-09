import quick_read.input_reader;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
//import java.util.function.DoubleToLongFunction;


public class Main {
    public static void main(String[] args) {
        final InputStream inputStream = System.in;//new FileInputStream("C:\\Users\\wavator\\Downloads\\test.in");
        final OutputStream outputStream = System.out;
        final input_reader in = new input_reader(inputStream);
        final PrintWriter out = new PrintWriter(outputStream);
        final Task solver = new Task();
        solver.solve(in, out);
        out.close();
    }

    public static boolean judgement(double[] cost, double[] payment, double middle, long giveup) {
        final int lengthOfArray = cost.length;
        double sum = 0;
        final double[] tempArray = new double[lengthOfArray];
        for (int i = 0; i < lengthOfArray; i++) {
            tempArray[i] = cost[i] - payment[i] * middle;
        }
        Arrays.sort(tempArray);

        for (int i = 0; i < giveup; i++) {
            sum += tempArray[lengthOfArray - 1 - i];
        }
        return sum >= 0;
    }

    private static final class Task {
        public void solve(input_reader in, PrintWriter out) {
            final double EPS = Math.pow(10, -12);

            while (in.hasNext()) {
                long classNumber = in.nextLong();
                long classBeDelete = in.nextLong();
                double maxValue = 0;
                double minValue = 0;
                double[] classCredit = new double[(int) classNumber];
                double[] classGrade = new double[(int) classNumber];
                double beginCreditNet = 0;

                for (int i = 0; i < classNumber; i++) {
                    classCredit[i] = in.nextDouble();
                    beginCreditNet += classCredit[i];
                    maxValue = Math.max(maxValue, classCredit[i]);
                }
                for (int i = 0; i < classNumber; i++) {
                    classGrade[i] = in.nextDouble();
                    maxValue = Math.max(maxValue, classGrade[i]);
                }
                double beginGradeNet = 0;
                for (int i = 0; i < classNumber; i++) {
                    classGrade[i] *= classCredit[i];
                    beginGradeNet += classGrade[i];
                }
                minValue = beginGradeNet / beginCreditNet;
                double middle = 0;
                while (Math.abs(maxValue - minValue) > EPS) {
                    //System.out.println(middle);
                    middle = minValue + (maxValue - minValue) / 2;
                    //System.out.println(middle);
                    if (judgement(classGrade, classCredit, middle, classNumber - classBeDelete)) {
                        minValue = middle;
                    } else {
                        maxValue = middle;
                    }

                }
                System.out.printf("%.3f\n", minValue);
            }
        }
    }
}