import quick_read.input_reader;

import java.io.PrintWriter;
import java.math.BigInteger;

public class Main {
    static PrintWriter out;
    static input_reader in;

    public static void main(String[] args) {
        out = new PrintWriter(System.out);
        in = new input_reader(System.in);
        for (int t = in.nextInt(); t > 0; t--) { //As same as scanner.nextInt()
            int number = in.nextInt();
            long judgement = in.nextLong();
            long[] array = new long[number];
            for (int i = 0; i < number; i++) {
                array[i] = in.nextInt();
            }
            BigInteger cal = BigInteger.ZERO;
            int minsMax = 0;
            for (int i = 0; i < number - 2; i++) {
                minsMax = TwoFInd(array, judgement, i, minsMax);
                BigInteger temp = BigInteger.valueOf((long) (minsMax - i - 1) * (minsMax - i - 2) / 2);
                cal = cal.add(temp);
            }
            out.print(cal.toString());
            if (t != 1) {out.println();}
        }
        out.close(); //Don't forget this line, otherwise you will output nothing. This sentence flush the buffer.
    }

    public static int TwoFInd(long[] array, long diff, int start, int max) {
        int begin = max;
        int finalNumber = array.length - 1;
        int mid = 0;
        while (begin <= finalNumber) {
            mid = begin + ((finalNumber - begin) / 2);
            long temp = array[mid] - array[start];
            if (temp > diff) {
                finalNumber = mid - 1;
            } else if (temp <= diff) {
                begin = mid + 1;
            } else if (temp == diff) {
                return mid;
            }

        }
        return begin;
    }
}
