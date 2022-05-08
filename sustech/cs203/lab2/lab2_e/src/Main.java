import java.io.*;
import java.math.*;
import java.util.Arrays;
import java.util.StringTokenizer;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        Task solver = new Task();
        solver.solve(in, out);
        out.close();
    }

    static class Task {
        public void solve(InputReader in, PrintWriter out) {
            boolean judge = in.hasNext();
            while (judge) {
                long lengthOfRun = in.nextInt();
                int stopNumber = in.nextInt();
                long[] stopArray = new long[stopNumber + 1];
                int runerNumber = in.nextInt();
                runerNumber += 1;
                for (int i = 0; i < stopNumber; i++) {
                    stopArray[i] = in.nextInt();
                    //System.out.println(stopArray[i]);
                }
                stopArray[stopNumber] = lengthOfRun;
                Arrays.sort(stopArray);
                long[] diffArray = new long[stopNumber + 2];
                diffArray[0] = stopArray[0];
                //diffArray[stopNumber] = lengthOfRun;
                diffArray[stopNumber + 1] = 0;
                long max = 0;
                for (int i = 1; i < stopNumber + 1; i++) {
                    diffArray[i] = stopArray[i] - stopArray[i - 1];
                    max = max > diffArray[i] ? max : diffArray[i];
                }
                // get the diff array and the number m
                long begin = max;
                long finalNumber = lengthOfRun;
                long middle = 0;
                while (begin <= finalNumber) {
                    middle = begin + (finalNumber - begin) / 2;
                    if (judgement(diffArray, runerNumber, middle) == -1) {
                        finalNumber = middle - 1;
                        //System.out.println("up");
                    } else {
                        begin = middle + 1;
                        //System.out.println("down");
                    }
                }
                judge = in.hasNext();
                //st = new StringTokenizer(temp);
                if (judge) {
                    System.out.println(begin);
                } else {
                    System.out.print(begin);
                    //break;
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

    public static int judgement(long[] diffArray, int runersNumber, long middle) {
        int length = diffArray.length;
        long tempLength = 0;
        int calcales = 0;
        for (int i = 0; i < length; i++) {
            if (tempLength + diffArray[i] > middle) {
                tempLength = diffArray[i];
                calcales++;
            } else {
                tempLength += diffArray[i];
            }
        }
        return calcales <= runersNumber - 2 ? -1 : 1;
    }

}