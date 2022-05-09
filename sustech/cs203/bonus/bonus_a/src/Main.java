import quick_read.input_reader;

import java.io.PrintWriter;

public class Main {
    static PrintWriter out;
    static input_reader in;

    public static void main(String[] args) {
        out = new PrintWriter(System.out);
        in = new input_reader(System.in);
        for (int t = in.nextInt(); t > 0; t--) { // As same as scanner.nextInt()
            int number = in.nextInt();
            int Max = -10 ^ 9 - 1;
            for (int i = 0; i < number; i++) {
                Max = Math.max(Max, in.nextInt());
            }
            out.print(Max);
            if (t != 1) {
                out.println();
            }
        }
        out.close(); // Don't forget this line, otherwise you will output nothing. This sentence
        // flush the buffer.
    }

}
