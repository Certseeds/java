import java.io.*;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main {
    static PrintWriter out;
    static InputReader in;

    public static void main(String[] args) {
        out = new PrintWriter(System.out);
        in = new InputReader(System.in);
        for (int t = in.nextInt(); t > 0; t--) { // As same as scanner.nextInt()
            int planetNumber = in.nextInt();
            int destoryConstant = in.nextInt();
            int earthindex = in.nextInt();
            LinkedList<Integer> planetList = new LinkedList<>();
            for (int i = 0; i < earthindex; i++) {
                planetList.add(1);
            }
            planetList.add(2);
            for (int i = 0; i < planetNumber - earthindex - 1; i++) {
                planetList.add(1);
            }
            for (int i = 0; i < planetNumber - 1; i++) {
                int temp2 = (destoryConstant + 1) % planetList.size() - 1;
                temp2 = (temp2 >= 0 ? temp2 : planetList.size() - 1);
                planetList.remove(temp2);
                planetList = transfer(planetList, temp2);
            }
            if (planetList.getFirst() != 2) {out.print("No");} else {out.print("Yes");}
            if (t != 1) {out.println();}
        }
        out.close(); // Don't forget this line, otherwise you will output nothing. This sentence
        // flush the buffer.
    }

    public static LinkedList<Integer> transfer(LinkedList<Integer> linked1, int orderNumber) {
        LinkedList<Integer> linked2 = new LinkedList<>();
        int length1 = linked1.size();
        for (int i = orderNumber; i < length1; i++) {
            linked2.add(linked1.get(i));
        }
        for (int i = 0; i < orderNumber; i++) {
            linked2.add(linked1.get(i));
        }
        return linked2;

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
