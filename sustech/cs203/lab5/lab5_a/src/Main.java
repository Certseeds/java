import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static PrintWriter out;
    static InputReader in;

    public static void main(String[] args) {
        out = new PrintWriter(System.out);
        in = new InputReader(System.in);
        for (int t = in.nextInt(); t > 0; t--) { //As same as scanner.nextInt()
            int Numbers = in.nextInt();
            String[] temp = new String[Numbers];
            String[] temp2 = new String[Numbers];
            for (int i = 0; i < Numbers; i++) {
                temp[i] = in.next();
                temp2[i] = temp[i].charAt(temp[i].length() - 1) + "";
            }
            int[] count = new int[Numbers];
            count[0] = 1;
            int max = 1;
            for (int i = 1; i < Numbers; i++) {
                if (temp2[i].equals(temp2[i - 1])) {
                    count[i] = count[i - 1] + 1;
                } else {
                    count[i] = 1;
                }
                max = Math.max(max, count[i]);
            }
            out.print(max);
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

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
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
