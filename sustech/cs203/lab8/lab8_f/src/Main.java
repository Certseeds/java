import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
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
            boolean[] isVisited = new boolean[n + 1];
            Arrays.fill(isVisited, false);
            long longest = 0;
            Graph graph = new Graph(n);
            for (int i = 0; i < m; i++) {
                int begin = in.nextInt();
                int finalNumber = in.nextInt();
                Integer quanzhong = in.nextInt();
                //out.printf("%d %d %d%n",begin, finalNumber, quanzhong);
                isVisited[finalNumber] = true;
                graph.bulidEdge(begin, finalNumber, quanzhong);
            }
            for (int i = 1; i < n + 1; i++) {
                if (!isVisited[i]) {
                    longest = Math.max(graph.longestPath(i), longest);
                }
            }
            out.print(longest);
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

        void bulidEdge(int begin, int finalNumber, Integer quanzhong) {
            if (GraphLine[begin].children.contains(finalNumber)) {
                int index = GraphLine[begin].children.indexOf(finalNumber);
                int temp = this.GraphLine[begin].quanzhong.get(index);
                this.GraphLine[begin].quanzhong.set(index, (int) Math.max(temp, quanzhong));
            } else {
                this.GraphLine[begin].children.add(GraphLine[finalNumber]);
                this.GraphLine[begin].quanzhong.add(quanzhong);
            }
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

        public long longestPath(int begin) {
            Stack<Integer> stack = new Stack<>();
            long[] distance = new long[this.number + 1];
            Arrays.fill(distance, Integer.MIN_VALUE);
            distance[begin] = 0;
            boolean[] isvisited = new boolean[this.number + 1];
            for (int i = 1; i < this.number + 1; i++) {
                if (!isvisited[i]) {
                    topoSort(i, isvisited, stack);
                }
            }
            //获取一个用stack存的

            while (stack.size() != 0) {
                int newRoot = stack.pop();
                //  out.println(newRoot + "*");
                if (distance[newRoot] != Integer.MIN_VALUE) {
                    for (int i = 0; i < GraphLine[newRoot].children.size(); i++) {
                        if (distance[GraphLine[newRoot].children.get(i).Value] < distance[GraphLine[newRoot].Value] + GraphLine[newRoot].quanzhong.get(i)) {
                            distance[GraphLine[newRoot].children.get(i).Value] = distance[GraphLine[newRoot].Value] + GraphLine[newRoot].quanzhong.get(i);
                        }
                    }
                }
            }
            long maxMium = 0;
            for (int i = 1; i < distance.length; i++) {
                maxMium = Math.max(maxMium, distance[i]);
            }
            return maxMium;

        }

        private static final class Node {
            int Value;
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
