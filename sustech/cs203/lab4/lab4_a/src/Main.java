import quick_read.input_reader;

import java.io.PrintWriter;
import java.util.Stack;

public class Main {
    static PrintWriter out;
    static input_reader in;

    public static void main(String[] args) {
        out = new PrintWriter(System.out);
        in = new input_reader(System.in);
        for (int t = in.nextInt(); t > 0; t--) { //As same as scanner.nextInt()
            Stack<String> lanran = new Stack<>();
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
