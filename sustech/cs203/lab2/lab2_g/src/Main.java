import java.io.*;
//import java.lang.reflect.Array;
import java.math.*;
//import java.time.chrono.MinguoChronology;
import java.util.Arrays;
import java.util.StringTokenizer;
//import java.util.function.DoubleToLongFunction;


public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;//new FileInputStream("C:\\Users\\wavator\\Downloads\\test.in");
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        Task solver = new Task();
        solver.solve(in, out);
        out.close();
    }

    static class Task {

        public void solve(InputReader in, PrintWriter out) {
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
                    maxValue = maxValue > classCredit[i] ? maxValue : classCredit[i];
                }
                for (int i = 0; i < classNumber; i++) {
                    classGrade[i] = in.nextDouble();
                    maxValue = maxValue > classGrade[i] ? maxValue : classGrade[i];
                }
                double beginGradeNet = 0;
                for (int i = 0; i < classNumber; i++) {
                    classGrade[i] *= classCredit[i];
                    beginGradeNet += classGrade[i];
                }
                double averagegGrade = beginGradeNet / beginCreditNet;
                // System.out.println(averagegGrade);
                minValue = averagegGrade;
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

    public static boolean judgement(double[] cost, double[] payment, double middle, long giveup) {
        int lengthOfArray = cost.length;
        double sum = 0;
        boolean judge = false;
        double[] tempArray = new double[lengthOfArray];
        for (int i = 0; i < lengthOfArray; i++) {
            tempArray[i] = cost[i] - payment[i] * middle;
        }
        Arrays.sort(tempArray);

        for (int i = 0; i < giveup; i++) {
            sum += tempArray[lengthOfArray - 1 - i];
        }
        if (sum >= 0) {
            return !judge;
        }
        return judge;


    }

    static class InputReader {
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

        public long nextLong() {
            return Long.parseLong(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public char[] nextCharArray() {
            return next().toCharArray();
        }

        //         public boolean hasNext() {
//             try {
//                 return reader.ready();
//             } catch(IOException e) {
//                 throw new RuntimeException(e);
//             }
//         }
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

        public BigInteger nextBigInteger() {
            return new BigInteger(next());
        }

        public BigDecimal nextBigDecinal() {
            return new BigDecimal(next());
        }
    }
}