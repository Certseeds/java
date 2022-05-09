import quick_read.input_reader;

import java.io.PrintWriter;

public class Main {
    static PrintWriter out;
    static input_reader in;

    public static void main(String[] args) {
        out = new PrintWriter(System.out);
        in = new input_reader(System.in);
        for (int t = in.nextInt(); t > 0; t--) { //As same as scanner.nextInt()
            int columns = in.nextInt();
            int edges = in.nextInt();
            boolean[][] judgement = new boolean[columns][columns];
            for (int i = 0; i < edges; i++) {
                judgement[in.nextInt() - 1][in.nextInt() - 1] = true;
            }
            for (int i = 0; i < columns; i++) {
                for (int j = 0; j < judgement.length; j++) {
                    out.print(judgement[i][j] ? 1 : 0);
                    out.print(" ");
                }
                if (t != 1 || i != columns - 1) {out.println();}
            }
            // if (t != 1) {out.println();}
        }
        out.close(); //Don't forget this line, otherwise you will output nothing. This sentence flush the buffer.
    }


}
