import quick_read.input_reader;

import java.io.PrintWriter;

public class Main {
    static PrintWriter out;
    static input_reader in;

    public static void main(String[] args) {
        out = new PrintWriter(System.out);
        in = new input_reader(System.in);
        for (int t = in.nextInt(); t > 0; t--) { //As same as scanner.nextInt()
            int length = in.nextInt();
            String skr = in.next();
            int[] skrNumber = stringToInt(skr);
            int[] skrNext = getNext(skr);
            if (length <= 2) {
                out.print(0);
                if (t != 1) {out.println();}
                continue;
            } else if (length == 3) {
                if (skrNumber[0] == skrNumber[1] && skrNumber[1] == skrNumber[2]) {
                    out.print(1);
                } else {
                    out.print(0);
                }
                if (t != 1) {out.println();}
                continue;
            } else if (length == 4) {
                if (skrNumber[0] == skrNumber[3] && (skrNumber[0] == skrNumber[1] || skrNumber[0] == skrNumber[2])) {
                    out.print(1);
                } else {
                    out.print(0);
                }
                if (t != 1) {out.println();}
                continue;
            } else if (length == 5) {
                if (skrNumber[0] == skrNumber[4] && (skrNumber[0] == skrNumber[1] || skrNumber[0] == skrNumber[2] || skrNumber[0] == skrNumber[3])) {
                    out.print(1);
                } else {
                    out.print(0);
                }
                if (t != 1) {out.println();}
                continue;
            }
            //int  begin = skr.length() - skr.length()/2;
            int begin = skr.length() - skr.length() / 3;
            int j = 0;
            while (begin < length) {
                if (j == -1 || skrNumber[begin] == skrNumber[j]) {
                    begin++;
                    j++;
                } else {
                    j = skrNext[j];
                }
            }
            boolean hadPrint = false;
            String maxCommon = skr.substring(0, j);
            String skrTemp = skr.substring(j, length - j);
            if (skrTemp.contains(maxCommon)) {
                out.print(j);
                hadPrint = true;
            } else {
                while (maxCommon.length() > 1) {
                    begin = 1;
                    j = 0;
                    skrNumber = stringToInt(maxCommon);
                    skrNext = getNext(maxCommon);
                    while (begin < maxCommon.length()) {
                        if (j == -1 || skrNumber[begin] == skrNumber[j]) {
                            begin++;
                            j++;
                        } else {
                            j = skrNext[j];
                        }
                    }
                    maxCommon = skr.substring(0, j);
                    skrTemp = skr.substring(j, length - j);
                    if (KMP(skrTemp, maxCommon)) {
                        out.print(j);
                        hadPrint = true;
                        break;
                    }
                }
            }
            if (!hadPrint) {
                out.print(0);
            }
            if (t != 1) {out.println();}
        }
        out.close(); //Don't forget this line, otherwise you will output nothing. This sentence flush the buffer.
    }

    public static int[] stringToInt(String tran) {
        int length = tran.length();
        int[] Toint = new int[length];
        for (int i = 0; i < length; i++) {
            Toint[i] = (tran.charAt(i));
        }
        return Toint;
    }

    public static int[] getNext(String temp) {
        int BasicLength = temp.length();
        int MaxCommon = 0;
        int[] BasicArrayInt = stringToInt(temp);
        int[] BasicNext = new int[BasicLength];
        BasicNext[0] = 0;
        for (int i = 1; i < BasicLength; i++) {
            while (MaxCommon > 0 && !(BasicArrayInt[i] == BasicArrayInt[MaxCommon])) {
                MaxCommon = BasicNext[MaxCommon - 1];
            }
            if (BasicArrayInt[i] == BasicArrayInt[MaxCommon]) {
                MaxCommon++;
            }
            BasicNext[i] = MaxCommon;
        }
        System.arraycopy(BasicNext, 0, BasicNext, 1, BasicLength - 1);
        BasicNext[0] = -1;
        return BasicNext;
    }

    public static boolean KMP(String search, String Basic) {
        int BasicLength = Basic.length();
        int searchLength = search.length();
        if (searchLength < BasicLength || BasicLength == 0 || searchLength == 0) {
            return false;
        }
        int[] BasicArrayInt = stringToInt(Basic);
        int[] searchArrayInt = stringToInt(search);
        int[] BasicNext = getNext(Basic);
        int i = 0;
        int j = 0;
        if (searchLength == 1 && BasicLength == 1) {
            if (BasicArrayInt[0] == searchArrayInt[0]) {
                return true;
            } else {
                return false;
            }
        }
        while (i < searchLength) {
            if (j == -1 || searchArrayInt[i] == BasicArrayInt[j]) {
                i++;
                j++;
                if (j == BasicLength) {
                    return true;
                }
            } else {
                j = BasicNext[j];
            }
        }
        return false;
    }


}
