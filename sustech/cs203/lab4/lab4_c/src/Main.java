import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static PrintWriter out;
    static InputReader in;
    public static void main(String args[]) throws IOException {
        out = new PrintWriter(System.out);
        in = new InputReader(System.in);
        for (int t = in.nextInt(); t > 0; t--) { //As same as scanner.nextInt()
            int number = in.nextInt();
            long judgement = in.nextLong();
            long []array = new long [number];
            for (int i = 0;i < number;i++) {
                array[i] = in.nextInt();
            }
            BigInteger cal =BigInteger.ZERO ;
            int minsMax = 0;
            for (int i = 0;i < number - 2;i++) {
                minsMax = TwoFInd(array,judgement,i,minsMax);
                //out.println(array[minsMax-1]);
                BigInteger temp = BigInteger.valueOf( (minsMax - i-1) *(minsMax - i-2)/2 );
                cal = cal.add(temp);
                //out.println(cal.toString());
            }
            out.print(cal.toString());
            if (t != 1) {out.println();}
        }
        out.close(); //Don't forget this line, otherwise you will output nothing. This sentence flush the buffer.
    }
    public static int TwoFInd (long[]array,long diff,int start,int max) {
        int begin = max;
        int finalNumber = array.length - 1 ;
        int mid = 0;
        a1 : while (begin <= finalNumber) {
            mid = begin + ((finalNumber - begin)/2);
            long temp = array[mid] - array[start];
            if(temp > diff){
                finalNumber = mid - 1;
            }
            else if(temp <= diff){
                begin = mid + 1 ;
            }
            else if (temp == diff){
                return mid;
            }

        }
        return begin;


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