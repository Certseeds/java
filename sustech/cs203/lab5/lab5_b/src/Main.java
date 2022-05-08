import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static PrintWriter out;
    static InputReader in;

    public static void main(String[] args) throws IOException {
        out = new PrintWriter(System.out);
        in = new InputReader(System.in);
        for (int t = in.nextInt(); t > 0; t--) { //As same as scanner.nextInt()
            int lengthFirst = in.nextInt();
            int lengthSecond = in.nextInt();
            String basic = in.next();
            String Search = in.next();
            if (!basic.contains("*")) {
                if (Search.contains(basic)) {out.print("YES");} else {out.print("NO");}
            } else {
                int index = basic.indexOf("*");
                if (index == 0) {
                    String halfBasic = "";
                    String halfBasicTwo = basic.substring(1);
                    if (Search.contains(halfBasicTwo)) {out.print("YES");} else {out.print("NO");}
                } else if (index == lengthFirst - 1) {
                    String halfBasicTwo = "";
                    String halfBasic = basic.substring(0, lengthFirst - 2);
                    if (Search.contains(halfBasic)) {out.print("YES");} else {out.print("NO");}
                } else {

                    String[] temp = basic.split("\\*");
                    //out.println(temp[0]);
                    //out.println(temp[1]);
                    String halfBasic = temp[0];
                    String halfBasicTwo = temp[1];
                    int firstAppear = Search.indexOf(halfBasic);
                    int secondAppear = Search.indexOf(halfBasicTwo, firstAppear + halfBasic.length());
                    if (firstAppear + halfBasic.length() <= secondAppear) {
                        out.print("YES");
                    } else {
                        //out.print(firstAppear+halfBasic.length()+" "+ secondAppear);
                        out.print("NO");
                    }
                }

            }
            if (t != 1) {out.println();}
        }
        out.close(); //Don't forget this line, otherwise you will output nothing. This sentence flush the buffer.
    }

    private static final class InputReader {
        public BufferedReader br;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream){
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
