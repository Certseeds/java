import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static PrintWriter out;
    static InputReader in;

    public static void main(String[] args) {
        out = new PrintWriter(System.out);
        in = new InputReader(System.in);
        for (int t = in.nextInt(); t > 0; t--) { //As same as scanner.nextInt()
            int number = in.nextInt();
            int[] array = new int[number];
            for (int i = 0; i < number; i++) {
                array[i] = in.nextInt();
            }
            quicksort(array, 0, number - 1);
            int begin = 0;
            int finalNumber = array[number - 1] - array[0];
            int middle = 0;
            while (begin < finalNumber) {
                middle = begin + (finalNumber - begin) / 2;
                if (judgement(array, middle)) {
                    begin = middle + 1;
                } else {
                    finalNumber = middle - 1;
                }
            }
            out.print(middle);


            if (t != 1) {out.println();}
        }
        out.close(); //Don't forget this line, otherwise you will output nothing. This sentence flush the buffer.
    }

    public static boolean judgement(int[] array, int number) {
        int length = array.length;
        int Max_Value = (length * (length - 1) / 2 + 1) / 2;
        int j = 0;
        for (int i = 1; i < length; i++) {
            j += twofind(array, number + array[i]) - array[i];
            if (j > Max_Value)
                return true;
        }
        return false;
    }

    public static int twofind(int[] array, int number) {
        int length = array.length;
        int begin = 0;
        int finalNumber = length - 1;
        int middle = 0;
        while (begin <= finalNumber) {
            middle = begin + (finalNumber - begin) / 2;
            if (array[middle] > number) {
                finalNumber = middle - 1;
            } else if (array[middle] < number) {
                begin = middle + 1;
            } else {
                return middle;
            }
        }
        return begin;
    }

    public static int getMiddle(int[] array, int begin, int finalNumber) {
        int temp = array[begin];
        while (begin < finalNumber) {
            while (begin < finalNumber && array[finalNumber] >= temp) {
                finalNumber--;
            }
            // if (begin < finalNumber) {
            array[begin] = array[finalNumber];
            // }
            while (begin < finalNumber && array[begin] <= temp) {
                begin++;
            }
            // if(begin < finalNumber) {
            array[finalNumber] = array[begin];
            // }
        }
        array[begin] = temp;
        return begin;
    }

    public static void quicksort(int[] array, int begin, int finalNumber) {
        int middle;
        if (begin < finalNumber) {
            middle = getMiddle(array, begin, finalNumber);
            quicksort(array, begin, middle - 1);
            quicksort(array, middle + 1, finalNumber);
        }
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
