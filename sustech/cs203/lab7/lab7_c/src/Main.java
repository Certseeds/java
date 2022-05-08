import java.io.*;
import java.util.*;

public class Main {
    static PrintWriter out;
    static InputReader in;
    static int count = 1;

    public static void main(String args[]) throws IOException {
        out = new PrintWriter(System.out);
        in = new InputReader(System.in);
        int count = in.nextInt();
        for (int t = 1; t < count + 1; t++) {
            int numbers = in.nextInt();
            boolean isAVL = true;
            boolean isBST = true;
            Node[] Tree = new Node[numbers +1];
            boolean[] isRoot = new boolean[numbers+1];
            Tree[0] = new Node();
            isRoot[0] = false;
            for (int i = 1; i < numbers + 1; i++) {
                Tree[i] = new Node(in.nextInt());
                isRoot[i] = true;
            }
            for (int i = 1; i < numbers +1; i++) {
                int left = in.nextInt();
                int right = in.nextInt();
                isRoot[left] = false;
                isRoot[right] = false;
                Tree[i].leftson = left > 0 ? Tree[left]:new Node();
                Tree[i].rightson = right > 0 ? Tree[right]:new Node();
                //System.out.println(Tree[i].Value + " " +Tree[i].leftson.Value + " " +Tree[i].rightson.Value);
            }
            //System.out.println(Arrays.toString(isRoot));
            int rootPosition = 0;
            a1: for (int i = 1; i < numbers+1; i++) {
                if (isRoot[i]) {
                    rootPosition = i;
                    break a1;
                }
            }//Queue<Node> queue = new LinkedList<Node>();
            //queue.add(Tree[rootPosition]);
            ArrayList<Integer> order = new ArrayList<Integer>();
            if(rootPosition != 0) {
                //非递归中序遍历
                Stack<Node> stack = new Stack<Node>();
                Node root = Tree[rootPosition];
                while (root.Value != 0 || !stack.empty()) {
                    while (root.Value != 0) {
                        stack.add(root);
                        root = root.leftson;
                    }
                    root = stack.pop();
                    order.add(root.Value);//System.out.print(root.Value + " ");
                    root = root.rightson;// System.out.println(root.Value);
                }
                int Max = 0;
                for (int i = 0; i < order.size() - 1; i++) {
                    Max = Math.max(Max, order.get(i));
                    if (Max >= order.get(i + 1)) {
                        isBST = false;
                        break;
                    }
                }
                // 非递归中序遍历结束,BST判定结束
                //isAVL = isAVL(Tree[rootPosition],0);
                isAVL = getHeight(Tree[rootPosition]) != -1 ;
            }//out.println(order.toString());//out.println(level.toString());

            if (isAVL && isBST) {
                out.print("Yes");
            } else {
                out.print("No");
            }
            if (t != count) {
                out.println();
            }
        }
        out.close();
    }

    static class Node {
        int Value;
        Node leftson = null;
        Node rightson = null;

        public Node() {
            this.Value = 0;
        }

        public Node(int value) {
            this.Value = value;
            leftson = new Node();
            rightson = new Node();
        }

    }
    static boolean isAVL(Node root,int height) {
        if(root.Value == 0) {
            height = 0;
            return true;
        }
        int left = 0;
        int right = 0;
        boolean IsLeft = isAVL(root.leftson, left);
        boolean IsRight = isAVL(root.rightson, right);
        if (IsLeft && IsRight) {
            int diff = right - left;
            if (Math.abs(diff) <=1) {
                height = 1+Math.max(right, left);
                return true;
            }
        }
        return false;
    }
    static int getHeight(Node root) {
        if(root.Value == 0) {
            return 0;
        }
        int left = getHeight(root.leftson);
        int right = getHeight(root.rightson);
        if (left == -1 || right == -1) {
            return -1;
        }
        return Math.abs(left-right)>1?-1:(1 + Math.max(left, right));
    }
}

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