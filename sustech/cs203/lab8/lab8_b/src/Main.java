import java.io.*;
import java.util.*;

class Main {
    static PrintWriter out;
    static InputReader in;
    public static void main(String args[]) throws IOException {
        out = new PrintWriter(System.out);
        in = new InputReader(System.in);
        for (int t = in.nextInt(); t > 0; t--) { //As same as scanner.nextInt()
            int columns = in.nextInt();
            int edges  = in.nextInt();
            Graph graph = new Graph(columns);
            for (int i = 0; i < edges; i++) {
                graph.bulidEdge(in.nextInt(), in.nextInt());
            }
            int operations = in.nextInt();
            for (int i = 0; i < operations; i++) {
                out.print(graph.BFSway(in.nextInt(),in.nextInt())?"YES":"NO");
                if (t != 1 || i!= operations -1) {out.println();}
            }
            //      if (t != 1) {out.println();}
        }
        out.close(); //Don't forget this line, otherwise you will output nothing. This sentence flush the buffer.
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
            public Node(int value) {
                this.Value = value;
                children = new ArrayList<Node>();
            }
        }

        void bulidEdge(int begin, int finalNumber) {
            this.GraphLine[begin].children.add(GraphLine[finalNumber]);
            //this.GraphLine[finalNumber].children.add(GraphLine[begin]);
        }

        public boolean BFSway(int BFSInputNumber,int testNumber) {
            // public int[] BFSway(int BFSInputNumber) {

            boolean[] isVisited = new boolean[this.number + 1];
            Queue<Integer> queue = new LinkedList<Integer>();
            //int[] distance = new int[this.number + 1];
//            for (int i = 0; i < distance.length; i++) {
//                distance[i] = 0;
//            }
            isVisited[BFSInputNumber] = true;
            queue.add(BFSInputNumber);
            while (queue.size() != 0) {
                //System.out.println(queue.toString());
                int beginNumber = queue.poll();
                //System.out.println(queue.toString());
                //System.out.println(beginNumber + " ");
                ArrayList<Node> tempIn = this.GraphLine[beginNumber].children;
                int count = 0;
                int finalNumber = this.GraphLine[beginNumber].children.size();
                while (count < finalNumber) {
                    // Node n = tempIn.get(count);
                    if (!isVisited[tempIn.get(count).Value]) {
                        //System.out.println(tempIn.get(count).Value);
                        isVisited[tempIn.get(count).Value] = true;
                        queue.add(tempIn.get(count).Value);
                        //
                        //  distance[tempIn.get(count).Value] = distance[beginNumber] + 1;
                    }
                    count++;

                }
                //System.out.println(Arrays.toString(isVisited));
                // System.out.println(Arrays.toString(distance));
            }
            // int[] returnArray = new int[2];
            //returnArray[0] = 0;
            //returnArray[1] = 0;
            boolean canVisited = isVisited[testNumber];
//            for (int i = 1; i < number + 1; i++) {
//                if (distance[i] > returnArray[0]) {
//                    returnArray[0] = distance[i];
//                    returnArray[1] = i;
//                }
            //0代表距离,1代表最大距离的时候的
            //}

            return canVisited;
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
    public boolean hasNext(){
        while(tokenizer == null || !tokenizer.hasMoreElements())
        {
            try
            {
                tokenizer = new StringTokenizer(br.readLine());
            }
            catch(Exception e)
            {
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
        }catch(IOException e){
            return  -1;
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
        }catch(IOException e){
            return  -1;
        }
    }

}