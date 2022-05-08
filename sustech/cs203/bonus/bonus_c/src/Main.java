import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static PrintWriter out;
    static InputReader in;
    static long count = 0;

    public static void main(String[] args) throws IOException {
        out = new PrintWriter(System.out);
        in = new InputReader(System.in);
        for (int t = in.nextInt(); t > 0; t--) { // As same as scanner.nextInt()
            int number = in.nextInt();
            int[] array = new int[number];
            for (int i = 0; i < number; i++) {
                array[i] = in.nextInt();
            }
            mergesort(array, 0, number - 1);
            out.print(count);
            count = 0;
            if (t != 1) {
                out.println();
            }
        }
        out.close(); // Don't forget this line, otherwise you will output nothing. This sentence
        // flush the buffer.
    }

    private static void mergesort(int[] array, int begin, int finalNumber) {
        // System.out.println(Arrays.toString(array));
        int middle = begin + (finalNumber - begin) / 2;
        if (begin < finalNumber) {
            mergesort(array, begin, middle);
            mergesort(array, middle + 1, finalNumber);
            merge(array, begin, middle, finalNumber);
        }
    }

    private static void merge(int[] array, int begin, int middle, int finalNumber) {
        int[] newarray = new int[finalNumber - begin + 1];
        int i = begin;
        int j = middle + 1;
        int k = 0;
        while (i <= middle && j <= finalNumber) {
            if (array[i] <= array[j]) {
                newarray[k++] = array[i++];
            } else {
                newarray[k++] = array[j++];
                count = count + (middle - i + 1);
            }
        }
        while (i <= middle) {
            newarray[k++] = array[i++];
        }
        while (j <= finalNumber) {

            newarray[k++] = array[j++];
        }
        if (finalNumber - begin + 1 >= 0) {System.arraycopy(newarray, 0, array, begin, finalNumber - begin + 1);}

    }

    private static final class InputReader {
        public BufferedReader br;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            br = new BufferedReader(new InputStreamReader(stream), 327680);
            tokenizer = null;
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

    }
}
