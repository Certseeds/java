import java.io.*;
import java.util.*;

public class Main {
    static PrintWriter out;
    static InputReader in;

    public static void main(String args[]) throws IOException {
        out = new PrintWriter(System.out);
        in = new InputReader(System.in);
//        System.out.println("Hello World");
//        Node testcase = new Node(2);
//        System.out.println(testcase.Value);
//        System.out.println(testcase.leftson.Value);
//        System.out.println(testcase.rightson.Value);
        for (int t = in.nextInt(); t > 0; t--) { // As same as scanner.nextInt()
            int numbers = in.nextInt();
            Node[] nodelist = new Node[numbers + 1];
            boolean[] isFather = new boolean[numbers + 1];
            for (int i = 0; i < numbers + 1; i++) {
                nodelist[i] = new Node(i);
                isFather[i] = true;
            }

            for (int i = 1; i < numbers + 1; i++) {
                int leftValue = in.nextInt();
                int rightValue = in.nextInt();
                nodelist[i].leftson.Value = leftValue;
                nodelist[i].rightson.Value = rightValue;
                isFather[leftValue] = false;
                isFather[rightValue] = false;
            }
            boolean hasFather = false;
            int fatherNumber = 0;
            for (int i = 1; i < numbers + 1; i++) {
                hasFather = hasFather || isFather[i];
                if (isFather[i]) {
                    fatherNumber = i;
                }
            }
            //out.println(fatherNumber);
            boolean printOut = true;
            boolean beginLeave = false;
            if (hasFather && !isNull(nodelist[fatherNumber])) {
                Queue<Node> NodeQueue = new LinkedList<Node>();
                NodeQueue.add(nodelist[fatherNumber]);
                a1: while (!NodeQueue.isEmpty()) {
                    Node useNode = NodeQueue.poll();
                    if (beginLeave) {
                        if (!isNull(useNode)) {
                            printOut = false;
                            break a1;
                        }
                    }
                    if (useNode.leftson.Value != 0) {
                        NodeQueue.add(nodelist[useNode.leftson.Value]);
                        if (useNode.rightson.Value != 0) {
                            NodeQueue.add(nodelist[useNode.rightson.Value]);
                        } else {
                            beginLeave = true;
                        }

                    } else if (useNode.rightson.Value != 0) {
                        printOut = false;
                        break a1;
                    } else {
                        beginLeave = true;
                    }
                }

            } else {
                printOut = false;
            }
            if (printOut) {
                out.print("Yes");
            } else {
                out.print("No");
            }
            if (t != 1) {
                out.println();
            }
        }
        out.close(); // Don't forget this line, otherwise you will output nothing. This sentence
        // flush the buffer.
    }

    static boolean isNull(Node node) {
        if (node.leftson.Value == 0 && node.rightson.Value == 0) {
            return true;
        } else {
            return false;
        }
    }

    static class Node {
        int Value;
        Node leftson;
        Node rightson;

        public Node() {
            this.Value = 0;
        }

        public Node(int value) {
            this.Value = value;
            // children = new ArrayList<Node>();
            leftson = new Node();
            rightson = new Node();
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