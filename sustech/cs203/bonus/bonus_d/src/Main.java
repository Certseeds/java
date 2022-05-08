import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static PrintWriter out;
    static InputReader in;

    public static void main(String[] args) {
        out = new PrintWriter(System.out);
        in = new InputReader(System.in);
        int t = in.nextInt();
        String[] result = new String[t];
        for (int useless = t; useless > 0; useless--) { // As same as scanner.nextInt()
            int number = in.nextInt();
            if (number <= 2) {
                for (int i = 0; i < number; i++) {
                    int temp = in.nextInt();
                }
                result[useless - 1] = "Y";
                //out.print("Y");
            } else {
                int[] number1 = new int[3];
                number1[0] = in.nextInt();
                number1[1] = in.nextInt();
                number1[2] = in.nextInt();
                if (number1[0] > number1[1] && number1[1] > number1[2]) {
                    result[useless - 1] = "N";
                    //out.print("N");
                } else {
                    boolean temp = false;
                    for (int i = 0; i < number - 3; i++) {
                        number1[0] = number1[1];
                        number1[1] = number1[2];
                        number1[2] = in.nextInt();
                        if (number1[0] > number1[1] && number1[1] > number1[2]) {
                            result[useless - 1] = "N";
                            //out.print("N");
                            temp = true;
                            break;
                        }
                    }
                    if (!temp) {
                        result[useless - 1] = "Y";
                        //out.print("Y");
                        temp = false;
                    }

                }
            }

        }
        for (int i = t; i > 0; i--) {
            out.print(result[i - 1]);
            if (i != 1) {
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
