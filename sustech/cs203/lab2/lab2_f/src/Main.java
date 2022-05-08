import java.io.*;
import java.math.*;
import java.util.StringTokenizer;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 * Author: Wavator
 * User: Nanoseeds
 */
public class Main {
    public static void main(String[] args) {
        final double EPS = Math.pow(10, -12);
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
                int times = in.nextInt();
                for (int i = 0; i < times; i++) {
                    int cal = i + 1;
                    // String calString = "" + cal;
                    //st = new StringTokenizer(br.readLine());
                    int itemTimes = in.nextInt();
                    //System.out.println(itemTimes);
                    double[] kids = new double[itemTimes];
                    double[] kidsWeight = new double[itemTimes];
                    double max = 0;
                    //double min = 0;
                    for (int j = 0; j < itemTimes; j++) {
                        //System.out.println(j);
                        //st = new StringTokenizer(br.readLine());
                        kids[j] = in.nextDouble();
                        max = max > kids[j] ? max : kids[j];
                        //min = min < kids[j]?min : kids[j];
                        kidsWeight[j] = in.nextDouble();
                        //System.out.println(kids[j]);
                    }
                    double min = kids[0];
                    for (int j = 0; j < itemTimes; j++) {
                        min = min > kids[j] ? kids[j] : min;
                    }
//                  System.out.println(kids[0]);
//                  System.out.println(kids[1]);
//                  System.out.println(kids[2]);
//                  System.out.println(kids[3]);
//                  System.out.println(max);
//                  System.out.println(min);
                    while (Math.abs(max - min) >= EPS) {
                        double middleLeft = min + (max - min) / 3;
                        double middleRight = min + (max - min) * 2 / 3;
                        //System.out.println(min);
                        //System.out.println(middleLeft);
                        //System.out.println(middleRight);
                        if (calculus(middleLeft, kids, kidsWeight) - calculus(middleRight, kids, kidsWeight) > EPS) {
                            min = middleLeft;
                        } else {
                            //min = middleLeft;
                            max = middleRight;
                        }
                    }
                    long finalNumber = (long) (calculus(min, kids, kidsWeight) + 0.5);
                    //System.out.printf("%s%d%s%d\n","Case #",cal,": ",finalNumber);
                    if (times == cal) {
                        System.out.printf("%s%d%s%d", "Case #", cal, ": ", finalNumber);
                        //System.out.print("smjb");


                    } else {
                        System.out.printf("%s%d%s%d\n", "Case #", cal, ": ", finalNumber);
                    }
                }

            }
        }

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

    public static double calculus(double P, double[] kids, double[] kidsWeight) {
        long lengthOfKids = kids.length;
        double minimum = 0;
        for (int i = 0; i < lengthOfKids; i++) {
            minimum += Math.pow(Math.abs(kids[i] - P), 3) * kidsWeight[i];
        }
        return minimum;

    }
}