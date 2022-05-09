import quick_read.input_reader;

import java.io.PrintWriter;

public class Main {
    static PrintWriter out;
    static input_reader in;

    public static void main(String[] args) {
        out = new PrintWriter(System.out);
        in = new input_reader(System.in);
        for (int t = in.nextInt(); t > 0; t--) { //As same as scanner.nextInt()
            int Numbers = in.nextInt();
            String[] temp = new String[Numbers];
            String[] temp2 = new String[Numbers];
            for (int i = 0; i < Numbers; i++) {
                temp[i] = in.next();
                temp2[i] = temp[i].charAt(temp[i].length() - 1) + "";
            }
            int[] count = new int[Numbers];
            count[0] = 1;
            int max = 1;
            for (int i = 1; i < Numbers; i++) {
                if (temp2[i].equals(temp2[i - 1])) {
                    count[i] = count[i - 1] + 1;
                } else {
                    count[i] = 1;
                }
                max = Math.max(max, count[i]);
            }
            out.print(max);
            if (t != 1) {out.println();}
        }
        out.close(); //Don't forget this line, otherwise you will output nothing. This sentence flush the buffer.
    }


}
