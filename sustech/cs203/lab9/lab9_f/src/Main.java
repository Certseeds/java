import java.io.*;
import java.util.*;


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
                int begin = in.nextInt();
                int finalNumber = in.nextInt();
                Integer quanzhong = in.nextInt();
                graph.bulidEdge(begin, finalNumber, quanzhong);
            }
            int bob = in.nextInt();
            int alice = in.nextInt();
            long[][] Bobs = graph.dijisitela(bob);
            long[] BobsDistance = Bobs[0];
            //long[] BobsQianqu = Bobs[1];
            long[][] Alices = graph.dijisitela(alice);
            long[] AlicesDistance = Alices[0];
            long[] netDistance = new long[n + 1];
            long min = Long.MAX_VALUE;
            for (int i = 1; i < n + 1; i++) {
                netDistance[i] = Math.max(BobsDistance[i], AlicesDistance[i]);
                if (min > netDistance[i]) {
                    min = netDistance[i];
                }
            }
            out.print(min);
            if (t != 1) {
                out.println("");
            }
        }
        out.close();
    }

    private static final class Graph {
        final Comparator<Node> sort = Comparator.comparingLong(a -> a.distance);
        int number;
        Node[] GraphLine;

        Graph(int number) {
            this.number = number;
            this.GraphLine = new Node[number + 1];
            for (int j = 0; j < number + 1; j++) {
                GraphLine[j] = new Node(j);
            }
        }

        public long[][] dijisitela(int root) {
            boolean[] haveVisited = new boolean[this.number + 1];
            long[] distance = new long[this.number + 1];
            long[] qianqu = new long[this.number + 1];
            Queue<Node> priQueue = new PriorityQueue<>(13, sort);
            for (int i = 1; i < this.number + 1; i++) {
                haveVisited[i] = false;
                this.GraphLine[i].distance = Integer.MAX_VALUE;
            }
            this.GraphLine[0].distance = 0;
            this.GraphLine[root].distance = 0;
            haveVisited[root] = true;
            for (int i = 0; i < this.GraphLine[root].children.size(); i++) {
                this.GraphLine[this.GraphLine[root].children.get(i).Value].distance = this.GraphLine[root].quanzhong.get(i);
                qianqu[this.GraphLine[root].children.get(i).Value] = root;
            }
            priQueue.addAll(Arrays.asList(this.GraphLine).subList(1, root));
            priQueue.addAll(Arrays.asList(this.GraphLine).subList(root + 1, this.number + 1));
            ArrayList<Node> queue = new ArrayList<>();
            queue.add(this.GraphLine[root]);
            while (queue.size() != this.number) {
                queue.add(this.GraphLine[priQueue.peek().Value]);
                // out.println(priQueue.peek().Value + " ?" +priQueue.size() );
                haveVisited[priQueue.peek().Value] = true;
                priQueue.poll();
                // out.println(priQueue.peek().Value + " ?" +priQueue.size() );
                for (Node temp : queue) {
                    for (int j = 0; j < temp.children.size(); j++) {
                        if (!haveVisited[temp.children.get(j).Value] && this.GraphLine[temp.Value].distance + temp.quanzhong.get(j) < this.GraphLine[temp.children.get(j).Value].distance) {
                            priQueue.remove(this.GraphLine[temp.children.get(j).Value]);
                            this.GraphLine[temp.children.get(j).Value].distance = this.GraphLine[temp.Value].distance + temp.quanzhong.get(j);
                            priQueue.offer(this.GraphLine[temp.children.get(j).Value]);
                            qianqu[temp.children.get(j).Value] = temp.Value;
                        }
                    }
                }
            }
            long[][] willBeReturn = new long[2][];
            distance[0] = 0;
            for (int i = 0; i < this.number + 1; i++) {
                // out.print(this.GraphLine[i].distance + " ** ");
                distance[i] = this.GraphLine[i].distance;
            }
            //   out.println();
            willBeReturn[0] = distance;
            willBeReturn[1] = qianqu;
            return willBeReturn;
        }

        void bulidEdge(int begin, int finalNumber, Integer quanzhong) {
            this.GraphLine[begin].children.add(GraphLine[finalNumber]);
            this.GraphLine[finalNumber].children.add(GraphLine[begin]);
            this.GraphLine[begin].quanzhong.add(quanzhong);
            this.GraphLine[finalNumber].quanzhong.add(quanzhong);
        }

        private static final class Node {
            int Value;
            long distance;
            ArrayList<Node> children;
            ArrayList<Integer> quanzhong;

            public Node(int value) {
                this.Value = value;
                children = new ArrayList<>();
                quanzhong = new ArrayList<>();
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
