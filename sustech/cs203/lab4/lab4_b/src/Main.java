import java.io.*;
import java.util.Stack;
import java.util.StringTokenizer;


public class Main {
    static PrintWriter out;
    static InputReader in;

    public static void main(String[] args) {
        out = new PrintWriter(System.out);
        in = new InputReader(System.in);
        for (int t = in.nextInt(); t > 0; t--) { // As same as scanner.nextInt()
            int length = in.nextInt();
            String temp = in.next();
            if (length % 2 == 1) {
                out.print("NO");
            } else {
                int[] temp2 = new int[length];
                for (int i = 0; i < length; i++) {
                    temp2[i] = temp.charAt(i);
                }
                Stack<Integer> brackets = new Stack<>();
                brackets.push(temp2[0]);
                for (int i = 1; i < length; i++) {
                    if (temp2[i] == 40 || temp2[i] == 123 || temp2[i] == 91) {
                        brackets.push(temp2[i]);
                    } else {
                        int temp3 = Math.abs(brackets.peek() - temp2[i]);
                        if (temp3 >= 1 && temp3 <= 2) {
                            brackets.pop();
                        } else {//out.print(i);
                            out.print("NO");
                            break;
                        }
                    }
                }
                if (brackets.empty()) {out.print("YES");}

            }
            if (t != 1) {out.println();}
        }
        out.close(); // Don't forget this line, otherwise you will output nothing. This sentence
        // flush the buffer.
    }

}

final class InputReader {
    public BufferedReader br;
    public StringTokenizer tokenizer;

    public InputReader(InputStream stream) {
        br = new BufferedReader(new InputStreamReader(stream), 327680);
        tokenizer = null;
    }

    public boolean hasNext() {
        while (tokenizer == null || !tokenizer.hasMoreElements()) {
            try {
                tokenizer = new StringTokenizer(br.readLine());
            } catch (Exception e) {
                return false;
            }
        }
        return true;
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

    public long nextLong() {
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
            long x = 0;
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