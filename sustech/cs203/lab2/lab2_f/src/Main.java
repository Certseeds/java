import quick_read.input_reader;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) {
        final InputStream inputStream = System.in;
        final OutputStream outputStream = System.out;
        final input_reader in = new input_reader(inputStream);
        final PrintWriter out = new PrintWriter(outputStream);
        final Task solver = new Task();
        solver.solve(in, out);
        out.close();
    }

    public static double calculus(double P, double[] kids, double[] kidsWeight) {
        long lengthOfKids = kids.length;
        double minimum = 0;
        for (int i = 0; i < lengthOfKids; i++) {
            minimum += Math.pow(Math.abs(kids[i] - P), 3) * kidsWeight[i];
        }
        return minimum;
    }

    private static final class Task {

        public void solve(input_reader in, PrintWriter out) {
            final double EPS = Math.pow(10, -12);
            while (in.hasNext()) {
                int times = in.nextInt();
                for (int i = 0; i < times; i++) {
                    int cal = i + 1;
                    int itemTimes = in.nextInt();
                    double[] kids = new double[itemTimes];
                    double[] kidsWeight = new double[itemTimes];
                    double max = 0;
                    for (int j = 0; j < itemTimes; j++) {
                        kids[j] = in.nextDouble();
                        max = Math.max(max, kids[j]);
                        kidsWeight[j] = in.nextDouble();
                    }
                    double min = kids[0];
                    for (int j = 0; j < itemTimes; j++) {
                        min = Math.min(min, kids[j]);
                    }
                    while (Math.abs(max - min) >= EPS) {
                        double middleLeft = min + (max - min) / 3;
                        double middleRight = min + (max - min) * 2 / 3;
                        if (calculus(middleLeft, kids, kidsWeight) - calculus(middleRight, kids, kidsWeight) > EPS) {
                            min = middleLeft;
                        } else {
                            max = middleRight;
                        }
                    }
                    long finalNumber = (long) (calculus(min, kids, kidsWeight) + 0.5);
                    if (times == cal) {
                        System.out.printf("%s%d%s%d", "Case #", cal, ": ", finalNumber);
                    } else {
                        System.out.printf("%s%d%s%d\n", "Case #", cal, ": ", finalNumber);
                    }
                }

            }
        }

    }
}