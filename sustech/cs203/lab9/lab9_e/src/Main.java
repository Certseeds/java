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
            boolean[] haveVisited = new boolean[n + 1];
            int[] outArray = new int[n + 1];
            Graph graph = new Graph(n);
            for (int i = 0; i < m; i++) {
                int begin = in.nextInt();
                int finalNumber = in.nextInt();
                outArray[finalNumber]++;
                haveVisited[begin] = true;
                haveVisited[finalNumber] = true;
                graph.bulidEdge(begin, finalNumber, (Integer) 1);
            }
            // 是否有可能
            Queue<Integer> beginQueue = new LinkedList<>();
            boolean isAllPointVisited = true;
            for (int i = 1; i < haveVisited.length; i++) {
                isAllPointVisited = isAllPointVisited && haveVisited[i];
                if (outArray[i] == 0) {
                    beginQueue.add(i);
                }
            }
            if (isAllPointVisited) {
                Queue<Integer> get = graph.TopoMin(beginQueue, outArray);
                int temp = get.size();
                if (temp == n) {
                    for (int i = 0; i < temp - 1; i++) {
                        out.print(get.poll() + " ");
                    }
                    out.print(get.poll());
                } else {
                    out.print("impossible");
                }
            } else {
                out.print("impossible");
            }
            if (t != 1) {
                out.println();
            }
        }
        out.close();
    }

    private static final class Graph {
        final Comparator<Node> sort = Comparator.comparingInt(a -> a.Value);
        int number;
        Node[] GraphLine;

        Graph(int number) {
            this.number = number;
            this.GraphLine = new Node[number + 1];
            for (int j = 0; j < number + 1; j++) {
                GraphLine[j] = new Node(j);
            }
        }

        void bulidEdge(int begin, int finalNumber, Integer quanzhong) {
            this.GraphLine[begin].children.add(GraphLine[finalNumber]);
            this.GraphLine[begin].quanzhong.add(quanzhong);
        }

        public void topoSort(int root, boolean[] visited, Stack<Integer> stack) {
            visited[root] = true;
            Stack<Integer> tempTopo = new Stack<>();
            tempTopo.push(root);
            while (tempTopo.size() != 0) {
                int beginNumber = tempTopo.peek();
                boolean needPop = true;
                int count = 0;
                ArrayList<Node> tempIn = this.GraphLine[beginNumber].children;
                int finalNumber = tempIn.size();
                while (count < finalNumber) {
                    if (!visited[tempIn.get(count).Value]) {
                        visited[tempIn.get(count).Value] = true;
                        tempTopo.push(tempIn.get(count).Value);
                        needPop = false;
                        break;
                    }
                    count++;
                }
                if (needPop) {
                    stack.push(tempTopo.pop());
                }
            }
        }

        public Queue<Integer> TopoMin(Queue<Integer> queue, int[] outArray) {
            Queue<Node> priQueue = new PriorityQueue<>(13, sort);
            Queue<Integer> willReturn = new LinkedList<>();
            int tempsize = queue.size();
            for (int i = 0; i < tempsize; i++) {
                priQueue.add(this.GraphLine[queue.poll()]);
            }
            while (priQueue.size() > 0) {
                Node temp = priQueue.poll();
                //System.out.println("this value is" + temp.Value);
                ArrayList<Node> tempQueue = temp.children;
                for (Node node : tempQueue) {
                    //  System.out.println(Arrays.toString(outArray));
                    outArray[node.Value] -= 1;
                    if (outArray[node.Value] == 0) {
                        priQueue.add(GraphLine[node.Value]);
                    }
                }
                willReturn.add(temp.Value);
            }
            return willReturn;
        }

        private static final class Node {
            int Value;
            final Comparator<Node> sort = Comparator.comparingInt(a -> a.Value);
            Queue<Node> fathers;
            ArrayList<Node> children;
            ArrayList<Integer> quanzhong;

            public Node(int value) {
                this.Value = value;
                fathers = new PriorityQueue<>(13, sort);
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
