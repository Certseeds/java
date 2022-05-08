import java.io.*;
import java.util.*;

public class Main {
    static PrintWriter out;
    static InputReader in;

    public static void main(String args[]) throws IOException {
        out = new PrintWriter(System.out);
        in = new InputReader(System.in);
        for (int t = in.nextInt(); t > 0; t--) { // As same as scanner.nextInt()
            int numberN = in.nextInt();
            int numberK = in.nextInt();
            int[] arrayN = new int[numberN];
            for (int i = 0; i < numberN; i++) {
                arrayN[i] = in.nextInt();
            }
            quicksort(arrayN, 0, numberN - 1, numberK);
            out.print(arrayN[numberK - 1]);
            if (t != 1) {
                out.println();
            }
        }
        out.close(); // Don't forget this line, otherwise you will output nothing. This sentence
        // flush the buffer.
    }

    public static int getMiddle(int array[], int begin, int finalNumber) {
        int Max = Math.max(array[begin], Math.max(array[begin+(finalNumber - begin)/2], array[finalNumber]));
        int Min = Math.min(array[begin], Math.min(array[begin+(finalNumber - begin)/2], array[finalNumber]));
        int temp = array[begin] + array[finalNumber] + array[begin+(finalNumber - begin)/2] - Max-Min;
        while (begin < finalNumber) {
            while (begin < finalNumber && array[finalNumber] >= temp) {
                finalNumber--;
            }
            array[begin] = array[finalNumber];
            while (begin < finalNumber && array[begin] <= temp) {
                begin++;
            }
            array[finalNumber] = array[begin];
        }
        array[begin] = temp;

        return begin;
    }

    public static void quicksort(int array[], int begin, int finalNumber, int k) {
        int middle;
        if (begin < finalNumber) {
            middle = getMiddle(array, begin, finalNumber);
            quicksort(array, begin, middle - 1, k);
            quicksort(array, middle + 1, finalNumber, k);
        }
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