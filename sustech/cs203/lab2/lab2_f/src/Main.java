import java.io.*;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) {
        final InputStream inputStream = System.in;
        final OutputStream outputStream = System.out;
        final InputReader in = new InputReader(inputStream);
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

        public void solve(InputReader in, PrintWriter out) {
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

    private static final class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }


        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public boolean hasNext() {
            try {
                String string = reader.readLine();
                if (string == null) {
                    return false;
                }
                tokenizer = new StringTokenizer(string);
                return tokenizer.hasMoreTokens();
            } catch (IOException e) {
                return false;
            }
        }
    }
}