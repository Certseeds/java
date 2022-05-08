import java.io.*;
import java.util.*;


public class Main {
    static PrintWriter out;
    static InputReader in;

    public static void main(String args[]) throws IOException {
        out = new PrintWriter(System.out);
        in = new InputReader(System.in);
        for (int t = in.nextInt(); t > 0; t--) { //As same as scanner.nextInt()
            Stack<String> lanran = new Stack<String>();
            String temp = in.next();
            String []temp2 = temp.split("");
//             for (int i = 0;i <temp2.length;i++){
//               out.print(temp2[i]);
//             }
            int size = 1;
            for (int i = 0;i <temp2.length;i++){
                if(size == 1 && temp2[i].equals("l")) {
                    lanran.push(temp2[i]);
                    size ++;
                    //out.print(size);
                }
                else if(size ==2 && temp2[i].equals("a")) {
                    lanran.push(temp2[i]);
                    size ++;
                    // out.print(size);
                }
                else if(size == 3 && temp2[i].equals("n")) {
                    lanran.push(temp2[i]);
                    size ++;
                    // out.print(size);
                }
                else if(size == 4 && temp2[i].equals("r")) {
                    lanran.push(temp2[i]);
                    size ++;
                    //out.print(size);
                }
                else if(size == 5 && temp2[i].equals("a")) {
                    lanran.push(temp2[i]);
                    size ++;
                    //out.print(size);
                }else if(size == 6 && temp2[i].equals("n")) {
                    lanran.push(temp2[i]);
                    size ++;
                    //out.print(size);
                }


            }
            if (lanran.size() < 6 ) {
                out.print("NO");
            }
            else {
                out.print("YES");
            }

            if (t != 1) {out.println();}}
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