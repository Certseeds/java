import java.io.*;
import java.util.Stack;
import java.util.StringTokenizer;


public class Main {
    static PrintWriter out;
    static InputReader in;

    public static void main(String[] args) {
        out = new PrintWriter(System.out);
        in = new InputReader(System.in);
        for (int t = in.nextInt(); t > 0; t--) { //As same as scanner.nextInt()
            Stack<String> lanran = new Stack<String>();
            String temp = in.next();
            String[] temp2 = temp.split("");
            int size = 1;
            for (String s : temp2) {
                if (size == 1 && s.equals("l")) {
                    lanran.push(s);
                    size++;
                } else if (size == 2 && s.equals("a")) {
                    lanran.push(s);
                    size++;
                } else if (size == 3 && s.equals("n")) {
                    lanran.push(s);
                    size++;
                } else if (size == 4 && s.equals("r")) {
                    lanran.push(s);
                    size++;
                } else if (size == 5 && s.equals("a")) {
                    lanran.push(s);
                    size++;
                } else if (size == 6 && s.equals("n")) {
                    lanran.push(s);
                    size++;
                }


            }
            if (lanran.size() < 6) {
                out.print("NO");
            } else {
                out.print("YES");
            }

            if (t != 1) {out.println();}
        }
        out.close(); //Don't forget this line, otherwise you will output nothing. This sentence flush the buffer.
    }

}

final class InputReader {
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