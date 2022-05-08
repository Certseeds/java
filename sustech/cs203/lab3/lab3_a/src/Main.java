import java.io.*;
import java.util.*;


public class Main {
    static PrintWriter out;
    static InputReader in;
    public static void main(String args[]) throws IOException {
        out = new PrintWriter(System.out);
        in = new InputReader(System.in);
        for (int t = in.nextInt(); t > 0; t--) { //As same as scanner.nextInt()
            int numberFirst = in.nextInt();
            int numberSecond = in.nextInt();
            int []arrayFirst = new int[numberFirst + 1];
            int []arraySecond = new int[numberSecond + 1];
            for (int i  = 0; i < numberFirst; i++) {
                arrayFirst[i] = in.nextInt();
                //out.println(arrayFirst[i]);

            }arrayFirst[numberFirst] = 124638;

            for (int i  = 0; i < numberSecond; i++) {
                arraySecond[i] = in.nextInt();
                //out.println(arraySecond[i]);

            }arraySecond[numberSecond] = -54521;
            int []arrayFInal = new int[numberFirst + numberSecond];
            int first = 0;
            int second = 0;
            boolean judge = false;
            a1:for(int i = 0; i < numberFirst + numberSecond;i++ ) {
                //out.print(arrayFirst[first]);
                //out.close();
                if (arrayFirst[first] >= arraySecond[second]) {
                    arrayFInal[i] = arraySecond[second];
                    second++;
                }
                else {
                    arrayFInal[i]  = arrayFirst[first];
                    first++;
                }
                if (first== numberFirst && !judge) {
                    int temp = numberSecond - second;
                    for (int j = 0; j< temp; j++,second++) {
                        arrayFInal[i + j + 1] = arraySecond[second];
                    }
                    judge = true;
                    break a1;
                }
                else if (second == numberSecond && !judge) {
                    int temp = numberFirst - first;
                    for (int j = 0; j< temp ; j++,first++) {
                        arrayFInal[i + j +1] = arrayFirst[first];
                    }
                    judge = true;
                    break a1;
                }
            }
            for(int i = 0; i < numberFirst + numberSecond;i++ ) {
                out.print(arrayFInal[i]+ " ");
            }
            if (t != 1) {out.println();}
            //out.println("Your codes"); //replace System.out.println()
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
    public boolean hasNext(){
        while(tokenizer == null || !tokenizer.hasMoreElements())
        {
            try
            {
                tokenizer = new StringTokenizer(br.readLine());
            }
            catch(Exception e)
            {
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
        }catch(IOException e){
            return  -1;
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
        }catch(IOException e){
            return  -1;
        }
    }

}