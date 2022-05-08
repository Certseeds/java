import java.io.*;
import java.util.Arrays;
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

    public static int judgement(long[] diffArray, int runersNumber, long middle) {
        long tempLength = 0;
        int calcales = 0;
        for (long l : diffArray) {
            if (tempLength + l > middle) {
                tempLength = l;
                calcales++;
            } else {
                tempLength += l;
            }
        }
        return calcales <= runersNumber - 2 ? -1 : 1;
    }

    private static final class Task {
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
                }
                stopArray[stopNumber] = lengthOfRun;
                Arrays.sort(stopArray);
                long[] diffArray = new long[stopNumber + 2];
                diffArray[0] = stopArray[0];
                diffArray[stopNumber + 1] = 0;
                long max = 0;
                for (int i = 1; i < stopNumber + 1; i++) {
                    diffArray[i] = stopArray[i] - stopArray[i - 1];
                    max = Math.max(max, diffArray[i]);
                }
                long begin = max;
                long finalNumber = lengthOfRun;
                long middle = 0;
                while (begin <= finalNumber) {
                    middle = begin + (finalNumber - begin) / 2;
                    if (judgement(diffArray, runerNumber, middle) == -1) {
                        finalNumber = middle - 1;
                    } else {
                        begin = middle + 1;
                    }
                }
                judge = in.hasNext();
                if (judge) {
                    System.out.println(begin);
                } else {
                    System.out.print(begin);
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