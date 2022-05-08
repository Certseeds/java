import java.io.*;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    static PrintWriter out;
    static InputReader in;

    public static void main(String[] args) {
        out = new PrintWriter(System.out);
        in = new InputReader(System.in);
        for (int t = in.nextInt(); t > 0; t--) {
            final Stack<String> item = new Stack<>();
            final Stack<Integer> item2 = new Stack<>();
            final Stack<String> item3 = new Stack<>();
            final Stack<String> item4 = new Stack<>();
            final Stack<int[][]> item5 = new Stack<>();
            int n = in.nextInt();
            int m = in.nextInt();
            int[][][] matrix = new int[n][m][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    for (int k = 0; k < m; k++) {
                        matrix[i][j][k] = in.nextInt();
                    }
                }
            }
            String temp = in.next();
            String[] temp2 = temp.split("");
            for (String s : temp2) {
                if (judgeNumber(s)) {
                    item3.push(s);
                } else {
                    switch (s) {
                        case "(":
                            item.push(s);
                            item2.push(0);
                            break;
                        case ")":
                            while (!item2.isEmpty() && item2.peek() != 0) {
                                item3.push(item.pop());
                                item2.pop();
                            }
                            item.pop();
                            item2.pop();
                            break;
                        case "*":
                            while (!item2.isEmpty() && item2.peek() == 2) {
                                item3.push(item.pop());
                                item2.pop();
                            }
                            item.push(s);
                            item2.push(2);
                            break;
                        case "+":
                        case "-":
                            while (!item2.isEmpty() && item2.peek() >= 1) {
                                item3.push(item.pop());
                                item2.pop();
                            }
                            item.push(s);
                            item2.push(1);
                            break;
                    }
                }

            }
            while (!item.isEmpty()) {
                item3.push(item.pop());
            }
            while (!item3.isEmpty()) {
                item4.push(item3.pop());
            }
            while (!item4.isEmpty()) {
                if (judgeNumber(item4.peek())) {
                    int[][] matrix2 = new int[m][m];
                    for (int j = 0; j < m; j++) {
                        for (int k = 0; k < m; k++) {
                            matrix2[j][k] = matrix[Integer.parseInt(item4.peek()) - 1][j][k];
                        }
                    }
                    item5.push(matrix2);
                } else if (item4.peek().equals("+")) {
                    int[][] number1 = item5.pop();
                    int[][] number2 = item5.pop();

                    item5.push(plusMatrix(number2, number1));
                } else if (item4.peek().equals("-")) {
                    int[][] number1 = item5.pop();
                    int[][] number2 = item5.pop();
                    item5.push(minusMatrix(number2, number1));
                } else if (item4.peek().equals("*")) {
                    int[][] number1 = item5.pop();
                    int[][] number2 = item5.pop();
                    item5.push(multipMatrix(number2, number1));
                }
                item4.pop();
            }
            int[][] finalResult = item5.pop();
            for (int j = 0; j < m; j++) {
                for (int k = 0; k < m; k++) {
                    out.print(finalResult[j][k]);
                    if (k != m - 1) {out.print(" ");}
                }
                if (j != m - 1) {out.println();}
            }
            if (t != 1) {out.println();}
        }
        out.close(); //Don't forget this line, otherwise you will output nothing. This sentence flush the buffer.
    }

    public static boolean judgeNumber(String temp) {
        boolean temp2 = false;
        try {
            final int temp3 = Integer.parseInt(temp);
            temp2 = true;
        } catch (NumberFormatException ignored) {}
        return temp2;
    }

    public static int[][] plusMatrix(int[][] a, int[][] b) {
        final int modNumber = 1000000007;
        int lengthM = a[0].length;
        int[][] c = new int[lengthM][lengthM];
        for (int i = 0; i < lengthM; i++) {
            for (int j = 0; j < lengthM; j++) {
                c[i][j] = ((a[i][j] % modNumber + b[i][j] % modNumber) % modNumber + modNumber) % modNumber;
            }
        }
        return c;
    }

    public static int[][] minusMatrix(int[][] a, int[][] b) {
        final int modNumber = 1000000007;
        int lengthM = a[0].length;
        int[][] c = new int[lengthM][lengthM];
        for (int i = 0; i < lengthM; i++) {
            for (int j = 0; j < lengthM; j++) {
                c[i][j] = ((a[i][j] % modNumber - b[i][j] % modNumber) % modNumber + modNumber) % modNumber;
            }
        }
        return c;
    }

    public static int[][] multipMatrix(int[][] a, int[][] b) {
        final int modNumber = 1000000007;
        int lengthM = a[0].length;
        int[][] c = new int[lengthM][lengthM];
        for (int i = 0; i < lengthM; i++) {
            for (int j = 0; j < lengthM; j++) {
                long tempValue = 0;
                for (int k = 0; k < lengthM; k++) {
                    long temp1 = a[i][k] % modNumber;
                    long temp2 = b[k][j] % modNumber;
                    long temp3 = (temp1 * temp2) % modNumber;
                    tempValue += (temp3 + modNumber) % modNumber;
                }
                c[i][j] = (int) ((tempValue % modNumber + modNumber) % modNumber);
            }
        }
        return c;
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
