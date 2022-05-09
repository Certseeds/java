import quick_read.input_reader;

import java.io.PrintWriter;


public class Main {
    static PrintWriter out;
    static input_reader in;

    public static void main(String[] args) {
        out = new PrintWriter(System.out);
        in = new input_reader(System.in);
        for (int t = in.nextInt(); t > 0; t--) { //As same as scanner.nextInt()
            int searchLength = in.nextInt();
            String Search = in.next();
            int basiclength = in.nextInt();
            String Basic = in.next();
            out.print(countKMP(Search, Basic));
            if (t != 1) {out.println();}
        }
        out.close(); //Don't forget this line, otherwise you will output nothing. This sentence flush the buffer.
    }

    public static int[] getNext(String temp) {
        int BasicLength = temp.length();
        int MaxCommon = 0;
        int[] BasicArray = stringToInt(temp);
        int[] BasicNext = new int[BasicLength];
        BasicNext[0] = 0;
        for (int i = 1; i < BasicLength; i++) {
            while (MaxCommon > 0 && !(BasicArray[i] == BasicArray[MaxCommon])) {
                MaxCommon = BasicNext[MaxCommon - 1];
            }
            if (BasicArray[i] == BasicArray[MaxCommon]) {
                MaxCommon++;
            }
            BasicNext[i] = MaxCommon;
        }
        System.arraycopy(BasicNext, 0, BasicNext, 1, BasicLength - 1);
        BasicNext[0] = -1;
        return BasicNext;
    }

    public static int countKMP(String search, String Basic) {
        int BasicLength = Basic.length();
        int searchLength = search.length();
        int[] BasicArrayInt = stringToInt(Basic);
        int[] searchArrayInt = stringToInt(search);
        int[] BasicNext = getNext(Basic);
        int count = 0;
        int i = 0;
        int j = 0;
        if (searchLength == 1 && BasicLength == 1) {
            if (search.contains(Basic)) {
                return 1;
            } else {
                return 0;
            }
        }
        while (i < searchLength) {
            if (j == -1 || searchArrayInt[i] == BasicArrayInt[j]) {
                i++;
                j++;
                if (j == BasicLength) {
                    count++;
                    j = BasicNext[j - 1];
                    i = i - 1;
                }
            } else {
                j = BasicNext[j];
            }
        }
        return count;
    }

    public static int[] stringToInt(String tran) {
        int length = tran.length();
        int[] Toint = new int[length];
        for (int i = 0; i < length; i++) {
            Toint[i] = (int) (tran.charAt(i));
        }
        return Toint;
    }


}
