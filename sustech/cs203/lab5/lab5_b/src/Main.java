import quick_read.input_reader;

import java.io.PrintWriter;

public class Main {
    static PrintWriter out;
    static input_reader in;

    public static void main(String[] args) {
        out = new PrintWriter(System.out);
        in = new input_reader(System.in);
        for (int t = in.nextInt(); t > 0; t--) { //As same as scanner.nextInt()
            int lengthFirst = in.nextInt();
            int lengthSecond = in.nextInt();
            String basic = in.next();
            String Search = in.next();
            if (!basic.contains("*")) {
                if (Search.contains(basic)) {out.print("YES");} else {out.print("NO");}
            } else {
                int index = basic.indexOf("*");
                if (index == 0) {
                    String halfBasic = "";
                    String halfBasicTwo = basic.substring(1);
                    if (Search.contains(halfBasicTwo)) {out.print("YES");} else {out.print("NO");}
                } else if (index == lengthFirst - 1) {
                    String halfBasicTwo = "";
                    String halfBasic = basic.substring(0, lengthFirst - 2);
                    if (Search.contains(halfBasic)) {out.print("YES");} else {out.print("NO");}
                } else {

                    String[] temp = basic.split("\\*");
                    String halfBasic = temp[0];
                    String halfBasicTwo = temp[1];
                    int firstAppear = Search.indexOf(halfBasic);
                    int secondAppear = Search.indexOf(halfBasicTwo, firstAppear + halfBasic.length());
                    if (firstAppear + halfBasic.length() <= secondAppear) {
                        out.print("YES");
                    } else {
                        //out.print(firstAppear+halfBasic.length()+" "+ secondAppear);
                        out.print("NO");
                    }
                }

            }
            if (t != 1) {out.println();}
        }
        out.close(); //Don't forget this line, otherwise you will output nothing. This sentence flush the buffer.
    }


}
