import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

class Main {
    static PrintWriter out;
    static InputReader in;

    public static void main(String[] args) {
        out = new PrintWriter(System.out);
        in = new InputReader(System.in);
        for (int t = in.nextInt(); t > 0; t--) { // As same as scanner.nextInt()
            int n = in.nextInt();
            int m = in.nextInt();
            boolean[] isSon = new boolean[n + 1];
            Graph graph = new Graph(n);
            for (int i = 0; i < m; i++) {
                int begin = in.nextInt();
                int finalNumber = in.nextInt();
                Integer quanzhong = in.nextInt();
                //out.printf("%d %d %d%n",begin, finalNumber, quanzhong);
                isSon[finalNumber] = true;
                graph.bulidEdge(begin, finalNumber, quanzhong);
            }
            int root = 0;
            for (int i = 1; i < n + 1; i++) {
                if (!isSon[i]) {
                    root = i;
                }
            }
            root = (root == 0 ? (int) (Math.random() * n) + 1 : root);
            out.print(graph.primGetTree(root));

            if (t != 1) {
                out.println();
            }
        }
        out.close();
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

        public long primGetTree(int root) {
            boolean[] isVisited = new boolean[this.number + 1];
            int countNumber = 1;
            long willReturn = 0;
            isVisited[root] = true;
            ArrayList<Integer> Nodes = new ArrayList<>();
            Nodes.add(root);
            while (countNumber != this.number) {
                int length = Integer.MAX_VALUE;
                int finalNumber = 1;
                for (int i = 0; i < countNumber; i++) {
                    int beginNumber = Nodes.get(i);
                    ArrayList<Integer> connects = this.GraphLine[beginNumber].quanzhong;
                    for (int j = 0; j < connects.size(); j++) {
                        if (length > connects.get(j) && !isVisited[this.GraphLine[beginNumber].children.get(j).Value]) {
                            length = connects.get(j);
                            finalNumber = this.GraphLine[beginNumber].children.get(j).Value;

                        }
                    }
                }
                if (length != Integer.MAX_VALUE) {
                    isVisited[finalNumber] = true;
                    countNumber++;
                    willReturn += length;
                    Nodes.add(finalNumber);
                    //System.out.println(willReturn);
                }
            }


            return willReturn;
        }

        void bulidEdge(int begin, int finalNumber, Integer quanzhong) {
            this.GraphLine[begin].children.add(GraphLine[finalNumber]);
            this.GraphLine[finalNumber].children.add(GraphLine[begin]);
            this.GraphLine[begin].quanzhong.add(quanzhong);
            this.GraphLine[finalNumber].quanzhong.add(quanzhong);
        }

        private static final class Node {
            int Value;
            ArrayList<Node> children = new ArrayList<>();
            ArrayList<Integer> quanzhong = new ArrayList<>();

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
