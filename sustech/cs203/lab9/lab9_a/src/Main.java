import quick_read.input_reader;

import java.io.PrintWriter;

public class Main {
    static PrintWriter out;
    static input_reader in;

    public static void main(String[] args) {
        out = new PrintWriter(System.out);
        in = new input_reader(System.in);
        for (int t = in.nextInt(); t > 0; t--) { //As same as scanner.nextInt()
            int n = in.nextInt();
            int[][] points = new int[n + 1][2];
            for (int i = 1; i < n + 1; i++) {
                points[i][0] = in.nextInt();
                points[i][1] = in.nextInt();
            }
            int q = in.nextInt();
            for (int i = 0; i < q; i++) {
                int begin = in.nextInt();
                int finalNumber = in.nextInt();
                out.print(Math.abs(points[begin][0] - points[finalNumber][0]) + Math.abs(points[begin][1] - points[finalNumber][1]));
                if (i != q - 1) {out.println();}
            }
            if (t != 1) {out.println();}
        }
        out.close(); //Don't forget this line, otherwise you will output nothing. This sentence flush the buffer.
    }
}
