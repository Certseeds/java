import java.io.*;
import java.util.StringTokenizer;


public class Main {
    static PrintWriter out;
    static InputReader in;

    public static void main(String[] args) throws IOException {
        out = new PrintWriter(System.out);
        in = new InputReader(System.in);
        for (int t = in.nextInt(); t > 0; t--) { //As same as scanner.nextInt()
            final int numberFirst = in.nextInt();
            final int numberSecond = in.nextInt();
            final int[] arrayFirst = new int[numberFirst + 1];
            final int[] arraySecond = new int[numberSecond + 1];
            for (int i = 0; i < numberFirst; i++) {
                arrayFirst[i] = in.nextInt();

            }
            arrayFirst[numberFirst] = 124638;

            for (int i = 0; i < numberSecond; i++) {
                arraySecond[i] = in.nextInt();
            }
            arraySecond[numberSecond] = -54521;
            int[] arrayFInal = new int[numberFirst + numberSecond];
            int first = 0;
            int second = 0;
            for (int i = 0; i < numberFirst + numberSecond; i++) {
                if (arrayFirst[first] >= arraySecond[second]) {
                    arrayFInal[i] = arraySecond[second];
                    second++;
                } else {
                    arrayFInal[i] = arrayFirst[first];
                    first++;
                }
                if (first == numberFirst) {
                    int temp = numberSecond - second;
                    for (int j = 0; j < temp; j++, second++) {
                        arrayFInal[i + j + 1] = arraySecond[second];
                    }
                    break;
                } else if (second == numberSecond) {
                    int temp = numberFirst - first;
                    for (int j = 0; j < temp; j++, first++) {
                        arrayFInal[i + j + 1] = arrayFirst[first];
                    }
                    break;
                }
            }
            for (int i = 0; i < numberFirst + numberSecond; i++) {
                out.print(arrayFInal[i] + " ");
            }
            if (t != 1) {out.println();}
        }
        out.close();
    }

    private static final class InputReader {
        public BufferedReader br;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            br = new BufferedReader(new InputStreamReader(stream), 327680);
            tokenizer = null;
        }

        public int nextInt() {
            try {
                int c = br.read();
                while (c <= 32) {
                    c = br.read();
                }
                boolean negative = false;
                if (c == '-') {
                    negative = true;
                    c = br.read();
                }
                int x = 0;
                while (c > 32) {
                    x = x * 10 + c - '0';
                    c = br.read();
                }
                return negative ? -x : x;
            } catch (IOException e) {
                return -1;
            }
        }

    }
}
