import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Main {
    static PrintWriter out;
    static InputReader in;

    public static void main(String[] args) {
        out = new PrintWriter(System.out);
        in = new InputReader(System.in);
        for (int t = in.nextInt(); t > 0; t--) { //As same as scanner.nextInt()
            int columns = in.nextInt();
            int edges = in.nextInt();
            Graph graph = new Graph(columns);
            for (int i = 0; i < edges; i++) {
                graph.bulidEdge(in.nextInt(), in.nextInt());
            }
            int operations = in.nextInt();
            for (int i = 0; i < operations; i++) {
                out.print(graph.BFSway(in.nextInt(), in.nextInt()) ? "YES" : "NO");
                if (t != 1 || i != operations - 1) {out.println();}
            }
        }
        out.close(); //Don't forget this line, otherwise you will output nothing. This sentence flush the buffer.
    }

    private static final class Graph {
        int number;
        Node[] GraphLine;

        Graph(int number) {
            this.number = number;
            this.GraphLine = new Node[number + 1];
            for (int j = 0; j < number + 1; j++) {
                GraphLine[j] = new Node(j);
            }
        }

        void bulidEdge(int begin, int finalNumber) {
            this.GraphLine[begin].children.add(GraphLine[finalNumber]);
        }

        public boolean BFSway(int BFSInputNumber, int testNumber) {

            boolean[] isVisited = new boolean[this.number + 1];
            Queue<Integer> queue = new LinkedList<>();
            isVisited[BFSInputNumber] = true;
            queue.add(BFSInputNumber);
            while (queue.size() != 0) {
                int beginNumber = queue.poll();
                ArrayList<Node> tempIn = this.GraphLine[beginNumber].children;
                int count = 0;
                int finalNumber = this.GraphLine[beginNumber].children.size();
                while (count < finalNumber) {
                    if (!isVisited[tempIn.get(count).Value]) {
                        isVisited[tempIn.get(count).Value] = true;
                        queue.add(tempIn.get(count).Value);
                    }
                    count++;

                }
            }
            boolean canVisited = isVisited[testNumber];

            return canVisited;
        }

        private static final class Node {
            int Value;
            ArrayList<Node> children = new ArrayList<>();

            public Node(int value) {
                this.Value = value;
            }
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
