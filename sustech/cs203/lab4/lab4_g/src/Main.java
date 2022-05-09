import quick_read.input_reader;

import java.io.PrintWriter;
import java.util.Stack;

// it is for G
public class Main {
    static PrintWriter out;
    static input_reader in;

    public static void main(String[] args) {
        out = new PrintWriter(System.out);
        in = new input_reader(System.in);
        for (int t = in.nextInt(); t > 0; t--) { //As same as scanner.nextInt()
            final Stack<Integer> item = new Stack<>();
            final Stack<Integer> Maxmium = new Stack<>();
            final Stack<Integer> Minmium = new Stack<>();
            int finalNumber = 0;
            int Max = -1;
            int Min = (int) Math.pow(10, 9) + 1;
            int numbers = 0;
            int length = in.nextInt();
            for (int i = 0; i < length; ) {
                String temp3 = in.next();
                if (temp3.equals("push") || temp3.equals("pop")) {
                    i++;
                }
                if (temp3.equals("push")) {
                    temp3 = in.next();
                    finalNumber = Integer.parseInt(temp3);
                    item.add(finalNumber);
                    Max = Math.max(Max, finalNumber);
                    Maxmium.add(Max);
                    Min = Math.min(Min, finalNumber);
                    Minmium.add(Min);
                    //out.println("Max "+ Max );
                    numbers++;
                }
                if (temp3.equals("pop")) {
                    if (numbers > 1) {
                        item.pop();
                        Maxmium.pop();
                        Minmium.pop();
                        Max = Maxmium.peek();
                        Min = Minmium.peek();
                        out.println(Maxmium.peek() - Minmium.peek());
                        numbers--;
                    } else if (numbers == 1) {
                        out.println(0);
                        item.pop();
                        Maxmium.pop();
                        Minmium.pop();
                        numbers = 0;
                        Max = -1;
                        Min = (int) Math.pow(10, 9) + 1;
                    } else {
                        out.println(0);
                        numbers = 0;
                        Max = -1;
                        Min = (int) Math.pow(10, 9) + 1;
                    }
                }
            }

        }
        out.close(); //Don't forget this line, otherwise you will output nothing. This sentence flush the buffer.
    }
}
