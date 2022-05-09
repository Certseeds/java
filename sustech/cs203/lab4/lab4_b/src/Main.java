import quick_read.input_reader;

import java.io.PrintWriter;
import java.util.Stack;


public class Main {
    static PrintWriter out;
    static input_reader in;

    public static void main(String[] args) {
        out = new PrintWriter(System.out);
        in = new input_reader(System.in);
        for (int t = in.nextInt(); t > 0; t--) { // As same as scanner.nextInt()
            int length = in.nextInt();
            String temp = in.next();
            if (length % 2 == 1) {
                out.print("NO");
            } else {
                int[] temp2 = new int[length];
                for (int i = 0; i < length; i++) {
                    temp2[i] = temp.charAt(i);
                }
                Stack<Integer> brackets = new Stack<>();
                brackets.push(temp2[0]);
                for (int i = 1; i < length; i++) {
                    if (temp2[i] == 40 || temp2[i] == 123 || temp2[i] == 91) {
                        brackets.push(temp2[i]);
                    } else {
                        int temp3 = Math.abs(brackets.peek() - temp2[i]);
                        if (temp3 >= 1 && temp3 <= 2) {
                            brackets.pop();
                        } else {//out.print(i);
                            out.print("NO");
                            break;
                        }
                    }
                }
                if (brackets.empty()) {out.print("YES");}

            }
            if (t != 1) {out.println();}
        }
        out.close(); // Don't forget this line, otherwise you will output nothing. This sentence
        // flush the buffer.
    }

}
