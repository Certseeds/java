import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static PrintWriter out;
    static InputReader in;

    public static void main(String[] args) throws IOException {
        out = new PrintWriter(System.out);
        in = new InputReader(System.in);
        for (int t = in.nextInt(); t > 0; t--) { // As same as scanner.nextInt()
            int lengthFirst = in.nextInt();
            int lengthSecond = in.nextInt();
            String first = in.next();
            String Second = in.next();
            int[] firstInt = stringToInt(first);
            int[] secondInt = stringToInt(Second);
            int[] firstNext = getNext(first);
            int i = 0;
            int j = 0;
            while (i < lengthSecond) {
                if (j == -1 || firstInt[j] == secondInt[i]) {
                    i++;
                    j++;
                    if (j == lengthFirst) {
                        break;
                    }
                } else {
                    j = firstNext[j];
                }
            }
            out.printf("%d%s%s", j, " ", first.substring(0, j));
            if (t != 1) {
                out.println();
            }
        }
        out.close(); // Don't forget this line, otherwise you will output nothing. This sentence
        // flush the buffer.
    }

    public static int[] stringToInt(String tran) {
        int length = tran.length();
        int[] Toint = new int[length];
        for (int i = 0; i < length; i++) {
            Toint[i] = (int) (tran.charAt(i));
        }
        return Toint;
    }

    public static int[] getNext(String temp) {
        int BasicLength = temp.length();
        int MaxCommon = 0;
        int[] BasicArrayInt = stringToInt(temp);
        int[] BasicNext = new int[BasicLength];
        BasicNext[0] = 0;
        for (int i = 1; i < BasicLength; i++) {
            while (MaxCommon > 0 && !(BasicArrayInt[i] == BasicArrayInt[MaxCommon])) {
                MaxCommon = BasicNext[MaxCommon - 1];
            }
            if (BasicArrayInt[i] == BasicArrayInt[MaxCommon]) {
                MaxCommon++;
            }
            BasicNext[i] = MaxCommon;
        }
        System.arraycopy(BasicNext, 0, BasicNext, 1, BasicLength - 1);
        BasicNext[0] = -1;
        return BasicNext;
    }

    public static boolean KMP(String search, String Basic) {
        int BasicLength = Basic.length();
        int searchLength = search.length();
        if (searchLength < BasicLength || BasicLength == 0 || searchLength == 0) {
            return false;
        }
        int[] BasicArrayInt = stringToInt(Basic);
        int[] searchArrayInt = stringToInt(search);
        int[] BasicNext = getNext(Basic);
        int i = 0;
        int j = 0;
        if (searchLength == 1 && BasicLength == 1) {
            if (BasicArrayInt[0] == searchArrayInt[0]) {
                return true;
            } else {
                return false;
            }
        }
        while (i < searchLength) {
            if (j == -1 || searchArrayInt[i] == BasicArrayInt[j]) {
                i++;
                j++;
                if (j == BasicLength) {
                    return true;
                }
            } else {
                j = BasicNext[j];
            }
        }
        return false;
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
