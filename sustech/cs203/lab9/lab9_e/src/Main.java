import java.io.*;
import java.util.*;


class Main{
    static PrintWriter out;
    static InputReader in;

    public static void main(String args[]) throws IOException {
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
                graph.bulidEdge(begin, finalNumber,(Integer)1);
            }
            // 是否有可能
            Queue<Integer> beginQueue = new LinkedList<Integer>();
            boolean isAllPointVisited = true;
            for (int i = 1; i < haveVisited.length; i++) {
                isAllPointVisited = isAllPointVisited && haveVisited[i];
                if (outArray[i] == 0) {
                    beginQueue.add(i);
                    //System.out.println(i);
                }
            }
            // System.out.println("szie is" + beginQueue.size());
            if (isAllPointVisited) {
                Queue<Integer> get = graph.TopoMin(beginQueue, outArray);
                //out.println("begin print");
                //  out.println("size is" + get.size());
                int temp = get.size();
                if (temp == n) {
                    for (int i = 0; i < temp-1; i++) {
                        out.print(get.poll() +" ");
                    }
                    out.print(get.poll());
                }
                else {
                    out.print("impossible");
                }
            }else {
                out.print("impossible");
            }
            // out.print((isAllPointVisited == true) ? "" : "impossible");
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
            Queue<Node> fathers;
            ArrayList<Node> children;
            ArrayList<Integer> quanzhong;

            public Node(int value) {
                this.Value = value;
                fathers = new PriorityQueue<Node>(13, sort);
                children = new ArrayList<Node>();
                quanzhong = new ArrayList<Integer>();
            }

            Comparator<Node> sort = new Comparator<Node>() {
                public int compare(Node a, Node b) {
                    // TODO Auto-generated method stub
                    int numbera = a.Value;
                    int numberb = b.Value;
                    if (numberb > numbera) {
                        return -1;
                    } else if (numberb < numbera) {
                        return 1;
                    } else {
                        return 0;
                    }
                    // 应该是小的在前
                }
            };
        }

        /*
         * public int[] LearnClass(Node order, Boolean[] hadChoosen) { Queue<Integer>
         * willBeReturn = new LinkedList<Integer>(); if (hadChoosen[order.Value]) { }
         * else { if (order.fathers == null) { hadChoosen[order.Value] = true;
         * willBeReturn.add(order.Value); } else { int length = order.fathers.size();
         * for (int i = 0; i < length; i++) { int[] tempArray =
         * LearnClass(order.fathers.poll(), hadChoosen);
         *
         * } hadChoosen[order.Value] = true; } } int tempLength = willBeReturn.size();
         * int[] returnArray = new int[tempLength]; for (int i = 0; i < tempLength; i++)
         * { returnArray[i] = willBeReturn.poll(); } return returnArray; }
         */

        /*
         * public long ChooseTheOrder() { boolean[] hadChoosen = new boolean[this.number
         * + 1]; Queue<Integer> OutPutOrder = new LinkedList<Integer>(); int count = 0;
         * while (count != this.number) {
         *
         * } return 0; }
         */

        Comparator<Node> sort = new Comparator<Node>() {
            public int compare(Node a, Node b) {
                // TODO Auto-generated method stub
                int numbera = a.Value;
                int numberb = b.Value;
                if (numberb > numbera) {
                    return -1;
                } else if (numberb < numbera) {
                    return 1;
                } else {
                    return 0;
                }
            }
        };

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
            this.GraphLine[begin].children.add(GraphLine[finalNumber]);
            //this.GraphLine[finalNumber].children.add(GraphLine[begin]);
            this.GraphLine[begin].quanzhong.add(quanzhong);
            //this.GraphLine[finalNumber].quanzhong.add(quanzhong);
        }

        void bulidEdge(int begin, int finalNumber) {
            this.GraphLine[finalNumber].fathers.add(GraphLine[begin]);
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

        public Queue<Integer> TopoMin(Queue<Integer> queue,int[] outArray) {
            Queue<Node> priQueue = new PriorityQueue<Node>(13, sort);
            Queue<Integer> willReturn = new LinkedList<Integer>();
            int tempsize = queue.size();
            for (int i = 0; i < tempsize; i++) {
                //System.out.println(queue.peek());
                priQueue.add(this.GraphLine[queue.poll()]);
            }
            while(priQueue.size() > 0) {
                Node temp = priQueue.poll();
                //System.out.println("this value is" + temp.Value);
                ArrayList<Node> tempQueue = temp.children;
                for (int i = 0; i < tempQueue.size(); i++) {
                    //  System.out.println(Arrays.toString(outArray));
                    outArray[tempQueue.get(i).Value] -= 1;
                    if ( outArray[tempQueue.get(i).Value] == 0) {
                        priQueue.add(GraphLine[tempQueue.get(i).Value]);
                    }
                }
                willReturn.add(temp.Value);
            }
            return willReturn;
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