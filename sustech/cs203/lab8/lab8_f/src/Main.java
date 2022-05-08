import java.io.*;
import java.util.*;

class Main {
    static PrintWriter out;
    static InputReader in;

    public static void main(String args[]) throws IOException {
        out = new PrintWriter(System.out);
        in = new InputReader(System.in);
        for (int t = in.nextInt(); t > 0; t--) { // As same as scanner.nextInt()
            int n = in.nextInt();
            int m = in.nextInt();
            boolean[] isVisited = new boolean[n + 1];
            for (int i = 0; i < isVisited.length; i++) {
                isVisited[i] = false;
            }
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

    static class Graph {
        int number;
        Node[] GraphLine;

        Graph(int number) {
            this.number = number;
            this.GraphLine = new Node[number + 1];
            for (int j = 0; j < number + 1; j++) {
                GraphLine[j] = new Node(j);
            }
        }

        static class Node {
            int Value;
            ArrayList<Node> children;
            ArrayList<Integer> quanzhong;

            public Node(int value) {
                this.Value = value;
                children = new ArrayList<Node>();
                quanzhong = new ArrayList<Integer>();
            }
        }

        void bulidEdge(int begin, int finalNumber, Integer quanzhong) {
            if (GraphLine[begin].children.contains(finalNumber)) {
                int index = GraphLine[begin].children.indexOf(finalNumber);
                int temp = this.GraphLine[begin].quanzhong.get(index);
                this.GraphLine[begin].quanzhong.set(index,(int)Math.max(temp, quanzhong));
            }
            else {
                this.GraphLine[begin].children.add(GraphLine[finalNumber]);
                this.GraphLine[begin].quanzhong.add(quanzhong);
            }
        }

 /*       public boolean BFSway(int BFSInputNumber, int testNumber) {
            boolean[] isVisited = new boolean[this.number + 1];
            Queue<Integer> queue = new LinkedList<Integer>();
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
*/


        public void topoSort(int root, boolean[] visited, Stack<Integer> stack) {
            visited[root] = true;
            Stack<Integer> tempTopo = new Stack<Integer>();
            tempTopo.push(root);
            while(tempTopo.size() != 0) {
                int beginNumber = tempTopo.peek();
                boolean needPop = true;
                int count = 0;
                ArrayList<Node> tempIn = this.GraphLine[beginNumber].children;
                int finalNumber = tempIn.size();
                a2: while(count < finalNumber) {
                    if (!visited[tempIn.get(count).Value]) {
                        visited[tempIn.get(count).Value] = true;
                        tempTopo.push(tempIn.get(count).Value);
                        needPop = false;
                        break a2;
                    }
                    count++;
                }
                if (needPop) {
                    stack.push(tempTopo.pop());
                }
            }
        }

        public long longestPath(int begin) {
            Stack<Integer> stack = new Stack<Integer>();
            long[] distance = new long[this.number + 1];
            for (int i = 0; i < distance.length; i++) {
                distance[i] = Integer.MIN_VALUE;
            }
            distance[begin] = 0;
            boolean[] isvisited = new boolean[this.number + 1];
            for (int i = 1; i < this.number + 1; i++) {
                if (isvisited[i] == false) {
                    topoSort(i, isvisited, stack);
                }
            }
            //获取一个用stack存的

            while (stack.size() != 0) {
                int newRoot = stack.pop();
                //  out.println(newRoot + "*");
                if (distance[newRoot] != Integer.MIN_VALUE) {
                    for (int i = 0; i <GraphLine[newRoot].children.size(); i++) {
                        if (distance[GraphLine[newRoot].children.get(i).Value] < distance[GraphLine[newRoot].Value] + GraphLine[newRoot].quanzhong.get(i)) {
                            distance[GraphLine[newRoot].children.get(i).Value] = distance[GraphLine[newRoot].Value] + GraphLine[newRoot].quanzhong.get(i);
                        }
                    }
                }
            }
            long maxMium = 0;
            for (int i = 1; i < distance.length; i++) {
                maxMium = (long) Math.max(maxMium, distance[i]);
            }
            //out.println(Arrays.toString(distance));
            //out.println(Arrays.toString(isvisited));
            return maxMium;

        }
    }

}
/*
 * // public int DFSway(int DFSinputNumber) { // boolean[] isVisited = new
 * boolean[this.number + 1]; // Stack<Integer> stack = new Stack<Integer>(); //
 * isVisited[DFSinputNumber] = true; // stack.add(DFSinputNumber); //
 * while(stack.size() != 0) { // int beginNumber = stack.peek(); //
 * ArrayList<Node> tempIn = this.GraphLine[beginNumber].children; // int count =
 * 0; // int finalNumber = this.GraphLine[beginNumber].children.size(); // while
 * (count < finalNumber) { // if (!isVisited[tempIn.get(count).Value]) { //
 * isVisited[tempIn.get(count).Value] = true; //
 * stack.add(tempIn.get(count).Value); // } // count++; // // } // } // // //
 * return 0; // } }
 *
 * }
 */

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