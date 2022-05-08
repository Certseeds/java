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
            Graph graph = new Graph(n);
            for (int i = 0; i < m; i++) {
                graph.bulidEdge(in.nextInt(), in.nextInt());
            }
            long numberWIllPRint = graph.findClique();
            out.print(numberWIllPRint);
            if (t != 1) {
                out.println();
            }
        }
        out.close(); // Don't forget this line, otherwise you will output nothing. This sentence
        // flush the buffer.
    }

    private static final class Graph {
        int number;
        Node[] GraphLine;
        boolean[][] hasEdge;

        Graph(int number) {
            this.number = number;
            this.GraphLine = new Node[number + 1];
            for (int j = 0; j < number + 1; j++) {
                GraphLine[j] = new Node(j);
            }
            this.hasEdge = new boolean[number + 1][number + 1];
            for (int i = 0; i < this.number + 1; i++) {
                for (int j = 0; j < this.number + 1; j++) {
                    hasEdge[i][j] = false;
                }
            }
        }

        void bulidEdge(int begin, int finalNumber) {
            this.GraphLine[begin].children.add(GraphLine[finalNumber]);
            this.GraphLine[finalNumber].children.add(GraphLine[begin]);
            hasEdge[begin][finalNumber] = true;
            hasEdge[finalNumber][begin] = true;
        }

        public int findClique() {
            int numberOfClique = 0;
            for (int i = 1; i < this.number + 1; i++) {
                for (int j = i + 1; j < this.number + 1; j++) {
                    for (int k = j + 1; k < this.number + 1; k++) {
                        for (int l = k + 1; l < this.number + 1; l++) {
                            if (hasEdge[i][j] && hasEdge[i][k] && hasEdge[i][l] && hasEdge[j][k] && hasEdge[j][l] && hasEdge[k][l]) {
                                numberOfClique++;
                            }
                        }
                    }
                }
            }
            return numberOfClique;

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
