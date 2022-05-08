import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static PrintWriter out;
    static InputReader in;

    public static void main(String[] args) {
        out = new PrintWriter(System.out);
        in = new InputReader(System.in);
        for (int t = in.nextInt(); t > 0; t--) { //As same as scanner.nextInt()
            int n = in.nextInt();
            int[][] points = new int[n + 1][2];
            for (int i = 1; i < n + 1; i++) {
                points[i][0] = in.nextInt();
                points[i][1] = in.nextInt();
            }
            int q = in.nextInt();
            for (int i = 0; i < q; i++) {
                int begin = in.nextInt();
                int finalNumber = in.nextInt();
                out.print(Math.abs(points[begin][0] - points[finalNumber][0]) + Math.abs(points[begin][1] - points[finalNumber][1]));
                if (i != q - 1) {out.println();}
            }
            if (t != 1) {out.println();}
        }
        out.close(); //Don't forget this line, otherwise you will output nothing. This sentence flush the buffer.
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
