import java.io.*;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main {
    static PrintWriter out;
    static InputReader in;

    public static void main(String[] args) {
        out = new PrintWriter(System.out);
        in = new InputReader(System.in);
        int bookNumber = -1;
        while ((bookNumber = in.nextInt()) != 0) {
            int bookNumber2 = bookNumber;
            String temp = in.next();
            int length = temp.length();
            //int[] number = new int[length];
            LinkedList<Integer> numberList = new LinkedList<Integer>();
            for (int i = 0; i < length; i++) {
                //number[i] = (int) temp.charAt(i) - 64;
                numberList.add((int) temp.charAt(i) - 64);
            }
            LinkedList<Integer> costomerList = new LinkedList<Integer>();
            LinkedList<Integer> removeList = new LinkedList<Integer>();
            for (int i = 0; i < length; i++) {
                boolean temp2 = false;
                int temp3 = numberList.getFirst();
                if (costomerList.size() >= bookNumber) {
                    if (!costomerList.contains(temp3)) {
                        temp2 = true;
                        if (!removeList.contains(temp3)) {
                            removeList.add(temp3);
                        }
                        numberList.removeLastOccurrence(temp3);
                        length--;
                    }
                }
                if (costomerList.contains(temp3) && !temp2) {
                    temp2 = true;
                    costomerList.removeFirstOccurrence(temp3);
                    bookNumber2++;
                }
                if (costomerList.size() < bookNumber && !temp2) {
                    temp2 = true;
                    costomerList.add(temp3);
                    bookNumber2--;
                }
                numberList.removeFirst();
            }
            out.println(removeList.size());
        }
        out.close(); // Don't forget this line, otherwise you will output nothing. This sentence
        // flush the buffer.
    }


    private static final class InputReader {
        public BufferedReader br;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            br = new BufferedReader(new InputStreamReader(stream), 327680);
            tokenizer = null;
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
    }
}