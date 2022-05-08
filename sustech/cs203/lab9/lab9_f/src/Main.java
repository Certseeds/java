import java.io.*;
import java.util.*;


class Main {
    static PrintWriter out;
    static InputReader in;

    public static void main(String args[]) throws IOException {
        out = new PrintWriter(System.out);
        in = new InputReader(System.in);

        for (int t = in.nextInt(); t > 0; t--) { // As same as scanner.nextInt()
            // System.out.println("begin");
            int n = in.nextInt();
            int m = in.nextInt();
            boolean[] isSon = new boolean[n + 1];
            // boolean[] haveVisited = new boolean[n+1];
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
            //long[] AlicesQianqu = Alices[1];
            long[] netDistance = new long[n + 1];
            // out.print(Arrays.toString(BobsDistance));
            //    ArrayList<Integer> arrays = new ArrayList<Integer>();
//            int locat = alice;
//            while (locat != bob) {
//                arrays.add(0, locat);
//                locat = (int) BobsQianqu[locat];
//            }
            long min = Long.MAX_VALUE;
            // int locations = 0;
            for (int i = 1; i < n+1; i++) {
                netDistance[i] = Math.max(BobsDistance[i], AlicesDistance[i]);
                if (min > netDistance[i]) {
                    min = netDistance[i];
                    // locations = i;
                }
            }
            out.print(min);
            //out.print("this is min");
            if (t != 1) {
                out.println("");
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
            long distance;
            ArrayList<Node> children;
            ArrayList<Integer> quanzhong;

            public Node(int value) {
                this.Value = value;
                children = new ArrayList<Node>();
                quanzhong = new ArrayList<Integer>();
            }
        }

        Comparator<Node> sort = new Comparator<Node>() {
            public int compare(Node a, Node b) {
                // TODO Auto-generated method stub
                long numbera = a.distance;
                long numberb = b.distance;
                if (numberb > numbera) {
                    return -1;
                } else if (numberb < numbera) {
                    return 1;
                } else {
                    return 0;
                }
            }
        };

        public long[][] dijisitela(int root) {
            boolean[] haveVisited = new boolean[this.number + 1];
            long[] distance = new long[this.number + 1];
            long[] qianqu = new long[this.number + 1];
            Queue<Node> priQueue = new PriorityQueue<Node>(13, sort);
            for (int i = 1; i < this.number + 1; i++) {
                haveVisited[i] = false;
                // distance[i] = Integer.MAX_VALUE;
                this.GraphLine[i].distance = Integer.MAX_VALUE;
            }
            // distance[root] = 0;
            this.GraphLine[0].distance = 0;
            this.GraphLine[root].distance = 0;
            haveVisited[root] = true;
            for (int i = 0; i < this.GraphLine[root].children.size(); i++) {
                // distance[this.GraphLine[root].children.get(i).Value] =
                // this.GraphLine[root].quanzhong.get(i);
                this.GraphLine[this.GraphLine[root].children.get(i).Value].distance = this.GraphLine[root].quanzhong.get(i);
                qianqu[this.GraphLine[root].children.get(i).Value] = root;
            }
            for (int i = 1; i < root; i++) {
                priQueue.add(this.GraphLine[i]);
            }
            for (int i = root+1; i < this.number +1; i++) {
                priQueue.add(this.GraphLine[i]);
            }
            ArrayList<Node> queue = new ArrayList<Node>();
            queue.add(this.GraphLine[root]);
            while (queue.size() != this.number) {

                // out.println(Arrays.toString(distance));
                // out.println("?");
//                long min = Integer.MAX_VALUE;
//                int order = 1;
//                for (int i = 0; i < queue.size(); i++) {
//                    Node temp = queue.get(i);
//                    for (int j = 0; j < temp.children.size(); j++) {
//                        if (!haveVisited[temp.children.get(j).Value]
//                                && min > this.GraphLine[temp.children.get(j).Value].distance) {
//                            /// min = distance[temp.children.get(j).Value];
//                            min = this.GraphLine[temp.children.get(j).Value].distance;
//                            order = temp.children.get(j).Value;
//                        }
//                    }
//                }
                queue.add(this.GraphLine[priQueue.peek().Value]);
                // out.println(priQueue.peek().Value + " ?" +priQueue.size() );
                haveVisited[priQueue.peek().Value] = true;
                priQueue.poll();
                // out.println(priQueue.peek().Value + " ?" +priQueue.size() );
                for (int i = 0; i < queue.size(); i++) {
                    Node temp = queue.get(i);
                    for (int j = 0; j < temp.children.size(); j++) {
                        if ( !haveVisited[temp.children.get(j).Value] && this.GraphLine[temp.Value].distance + temp.quanzhong.get(j) < this.GraphLine[temp.children.get(j).Value].distance) {
                            // out.println(priQueue.size() + "begin");
                            priQueue.remove(this.GraphLine[temp.children.get(j).Value]);
                            this.GraphLine[temp.children.get(j).Value].distance = this.GraphLine[temp.Value].distance + temp.quanzhong.get(j);
                            //  out.println(priQueue.size() + "middle");
                            priQueue.offer(this.GraphLine[temp.children.get(j).Value]);
                            // out.println(priQueue.size() + "after");
                            qianqu[temp.children.get(j).Value] = temp.Value;
                        }
                    }
                }
            }
            long[][] willBeReturn = new long[2][];
            distance[0] = 0;
            for (int i = 0; i < this.number +1; i++) {
                // out.print(this.GraphLine[i].distance + " ** ");
                distance[i] = this.GraphLine[i].distance;
            }
            //   out.println();
            willBeReturn[0] = distance;
            willBeReturn[1] = qianqu;
            return willBeReturn;
        }

        public long primGetTree(int root) {
            boolean[] isVisited = new boolean[this.number + 1];
            int countNumber = 1;
            long willReturn = 0;
            isVisited[root] = true;
            ArrayList<Integer> Nodes = new ArrayList<Integer>();
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
                    // System.out.println(willReturn);
                }
            }

            return willReturn;
        }

        void bulidEdge(int begin, int finalNumber, Integer quanzhong) {
//          if (GraphLine[begin].children.contains(finalNumber)) {
//          int index = GraphLine[begin].children.indexOf(finalNumber);
//          int temp = this.GraphLine[begin].quanzhong.get(index);
//          this.GraphLine[begin].quanzhong.set(index,(int)Math.max(temp, quanzhong));
//      }
//      else {
            this.GraphLine[begin].children.add(GraphLine[finalNumber]);
            this.GraphLine[finalNumber].children.add(GraphLine[begin]);
            this.GraphLine[begin].quanzhong.add(quanzhong);
            this.GraphLine[finalNumber].quanzhong.add(quanzhong);
            //  }
        }

        public void topoSort(int root, boolean[] visited, Stack<Integer> stack) {
            visited[root] = true;
            Stack<Integer> tempTopo = new Stack<Integer>();
            tempTopo.push(root);
            while (tempTopo.size() != 0) {
                int beginNumber = tempTopo.peek();
                boolean needPop = true;
                int count = 0;
                ArrayList<Node> tempIn = this.GraphLine[beginNumber].children;
                int finalNumber = tempIn.size();
                a2: while (count < finalNumber) {
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
            // 获取一个用stack存的

            while (stack.size() != 0) {
                int newRoot = stack.pop();
                // out.println(newRoot + "*");
                if (distance[newRoot] != Integer.MIN_VALUE) {
                    for (int i = 0; i < GraphLine[newRoot].children.size(); i++) {
                        if (distance[GraphLine[newRoot].children.get(i).Value] < distance[GraphLine[newRoot].Value]
                                + GraphLine[newRoot].quanzhong.get(i)) {
                            distance[GraphLine[newRoot].children.get(i).Value] = distance[GraphLine[newRoot].Value]
                                    + GraphLine[newRoot].quanzhong.get(i);
                        }
                    }
                }
            }
            long maxMium = 0;
            for (int i = 1; i < distance.length; i++) {
                maxMium = (long) Math.max(maxMium, distance[i]);
            }
            // out.println(Arrays.toString(distance));
            // out.println(Arrays.toString(isvisited));
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