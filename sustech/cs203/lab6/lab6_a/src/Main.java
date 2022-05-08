import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static PrintWriter out;
    static InputReader in;

    public static void main(String[] args) {
        out = new PrintWriter(System.out);
        in = new InputReader(System.in);
        for (int t = in.nextInt(); t > 0; t--) { // As same as scanner.nextInt()
            int[] first = new int[10001];
            int edges = in.nextInt();
            int[] firstNum = new int[edges - 1];
            int[] secondNum = new int[edges - 1];
            for (int i = 0; i < edges - 1; i++) {
                firstNum[i] = in.nextInt();
                secondNum[i] = in.nextInt();
                first[firstNum[i]] += 1;
                first[secondNum[i]] += 1;
            }
            for (int i = 2; i < 10001; i++) {
                if (first[i] == 1) {
                    out.print(i + " ");
                }
            }
            if (t != 1) {
                out.println();
            }
        }
        out.close(); // Don't forget this line, otherwise you will output nothing. This sentence
        // flush the buffer.
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
