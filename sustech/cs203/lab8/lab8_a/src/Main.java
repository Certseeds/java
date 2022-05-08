import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static PrintWriter out;
    static InputReader in;

    public static void main(String[] args) {
        out = new PrintWriter(System.out);
        in = new InputReader(System.in);
        for (int t = in.nextInt(); t > 0; t--) { //As same as scanner.nextInt()
            int columns = in.nextInt();
            int edges = in.nextInt();
            boolean[][] judgement = new boolean[columns][columns];
            for (int i = 0; i < edges; i++) {
                judgement[in.nextInt() - 1][in.nextInt() - 1] = true;
            }
            for (int i = 0; i < columns; i++) {
                for (int j = 0; j < judgement.length; j++) {
                    out.print(judgement[i][j] ? 1 : 0);
                    out.print(" ");
                }
                if (t != 1 || i != columns - 1) {out.println();}
            }
            // if (t != 1) {out.println();}
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
