import quick_read.input_reader;

import java.io.PrintWriter;

public class Main {
    static PrintWriter out;
    static input_reader in;

    public static void main(String[] args) {
        out = new PrintWriter(System.out);
        in = new input_reader(System.in);
        int t = in.nextInt();
        String[] result = new String[t];
        for (int useless = t; useless > 0; useless--) { // As same as scanner.nextInt()
            int number = in.nextInt();
            if (number <= 2) {
                for (int i = 0; i < number; i++) {
                    int temp = in.nextInt();
                }
                result[useless - 1] = "Y";
                //out.print("Y");
            } else {
                int[] number1 = new int[3];
                number1[0] = in.nextInt();
                number1[1] = in.nextInt();
                number1[2] = in.nextInt();
                if (number1[0] > number1[1] && number1[1] > number1[2]) {
                    result[useless - 1] = "N";
                    //out.print("N");
                } else {
                    boolean temp = false;
                    for (int i = 0; i < number - 3; i++) {
                        number1[0] = number1[1];
                        number1[1] = number1[2];
                        number1[2] = in.nextInt();
                        if (number1[0] > number1[1] && number1[1] > number1[2]) {
                            result[useless - 1] = "N";
                            //out.print("N");
                            temp = true;
                            break;
                        }
                    }
                    if (!temp) {
                        result[useless - 1] = "Y";
                        //out.print("Y");
                        temp = false;
                    }

                }
            }

        }
        for (int i = t; i > 0; i--) {
            out.print(result[i - 1]);
            if (i != 1) {
                out.println();
            }
        }
        out.close(); // Don't forget this line, otherwise you will output nothing. This sentence
        // flush the buffer.
    }

}
