import java.io.*;
import java.util.Stack;
import java.util.StringTokenizer;

// it is for G
public class Main {
    static PrintWriter out;
    static InputReader in;

    public static void main(String args[]) throws IOException {
        out = new PrintWriter(System.out);
        in = new InputReader(System.in);
        for (int t = in.nextInt(); t > 0; t--) { //As same as scanner.nextInt()
            Stack<Integer> item = new Stack<Integer>();
            Stack<Integer> Maxmium = new Stack<Integer>();
            Stack<Integer> Minmium = new Stack<Integer>();
            int finalNumber = 0;
            int Max = -1;
            int Min = (int) Math.pow(10, 9) + 1;
            int numbers = 0;
            int length = in.nextInt();
            //String []temp = new String[length * 2];
            for (int i = 0; i < length; ) {
                String temp3 = in.next();
                //out.print(temp3);
                //out.println(Maxmium);
                //out.println(Minmium);
                if (temp3.equals("push") || temp3.equals("pop")) {
                    i++;
                }
                if (temp3.equals("push")) {
                    temp3 = in.next();
                    finalNumber = Integer.parseInt(temp3);
                    item.add(finalNumber);
                    Max = Max > finalNumber ? Max : finalNumber;
                    Maxmium.add(Max);
                    Min = Min > finalNumber ? finalNumber : Min;
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
                        finalNumber = item.peek();
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
                        finalNumber = 0;
                    } else {
                        out.println(0);
                        numbers = 0;
                        Max = -1;
                        Min = (int) Math.pow(10, 9) + 1;
                        finalNumber = 0;
                    }
                    //if (t != 1) {out.println();}
                }
            }
//          for(int i = 0;i<length;i++) {
//              if(temp[i][0].equals("push")) {
//                  numbers++;
//              }else {
//                  if (numbers>0) {
//                      out.println(Max - Min);
//                      numbers--;
//                  }else {
//                      out.println(0);
//                      numbers = 0;
//                  }
//
//              }
//          }

            //if (t != 1) {out.println();}
        }
        out.close(); //Don't forget this line, otherwise you will output nothing. This sentence flush the buffer.
    }
}

class InputReader {
    public BufferedReader br;
    public StringTokenizer tokenizer;

    public InputReader(InputStream stream) throws FileNotFoundException {
        br = new BufferedReader(new InputStreamReader(stream), 327680);
        tokenizer = null;
    }

    public boolean hasNext() {
        while (tokenizer == null || !tokenizer.hasMoreElements()) {
            try {
                tokenizer = new StringTokenizer(br.readLine());
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    public String next() {
        while (tokenizer == null || !tokenizer.hasMoreTokens()) {
            try {
                tokenizer = new StringTokenizer(br.readLine());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return tokenizer.nextToken();
    }

    public int nextInt() {
        try {
            int c = br.read();
            while (c <= 32) {
                c = br.read();
            }
            boolean negative = false;
            if (c == '-') {
                negative = true;
                c = br.read();
            }
            int x = 0;
            while (c > 32) {
                x = x * 10 + c - '0';
                c = br.read();
            }
            return negative ? -x : x;
        } catch (IOException e) {
            return -1;
        }
    }

    public long nextLong() {
        try {
            int c = br.read();
            while (c <= 32) {
                c = br.read();
            }
            boolean negative = false;
            if (c == '-') {
                negative = true;
                c = br.read();
            }
            long x = 0;
            while (c > 32) {
                x = x * 10 + c - '0';
                c = br.read();
            }
            return negative ? -x : x;
        } catch (IOException e) {
            return -1;
        }
    }

}