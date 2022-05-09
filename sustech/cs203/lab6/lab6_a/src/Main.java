import quick_read.input_reader;

import java.io.PrintWriter;

public class Main {
    static PrintWriter out;
    static input_reader in;

    public static void main(String[] args) {
        out = new PrintWriter(System.out);
        in = new input_reader(System.in);
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
}
