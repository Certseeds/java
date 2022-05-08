import java.io.*;
import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    static PrintWriter out;
    static InputReader in;

    public static void main(String[] args) {
        out = new PrintWriter(System.out);
        in = new InputReader(System.in);
        int count = in.nextInt();
        for (int t = 1; t < count + 1; t++) {
            int numbers = in.nextInt();
            boolean isAVL = true;
            boolean isBST = true;
            Node[] Tree = new Node[numbers + 1];
            boolean[] isRoot = new boolean[numbers + 1];
            Tree[0] = new Node();
            isRoot[0] = false;
            for (int i = 1; i < numbers + 1; i++) {
                Tree[i] = new Node(in.nextInt());
                isRoot[i] = true;
            }
            for (int i = 1; i < numbers + 1; i++) {
                int left = in.nextInt();
                int right = in.nextInt();
                isRoot[left] = false;
                isRoot[right] = false;
                Tree[i].leftson = left > 0 ? Tree[left] : new Node();
                Tree[i].rightson = right > 0 ? Tree[right] : new Node();
            }
            int rootPosition = 0;
            for (int i = 1; i < numbers + 1; i++) {
                if (isRoot[i]) {
                    rootPosition = i;
                    break;
                }
            }
            ArrayList<Integer> order = new ArrayList<>();
            if (rootPosition != 0) {
                //非递归中序遍历
                Stack<Node> stack = new Stack<>();
                Node root = Tree[rootPosition];
                while (root.Value != 0 || !stack.empty()) {
                    while (root.Value != 0) {
                        stack.add(root);
                        root = root.leftson;
                    }
                    root = stack.pop();
                    order.add(root.Value);
                    root = root.rightson;
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
                isAVL = getHeight(Tree[rootPosition]) != -1;
            }

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

    static int getHeight(Node root) {
        if (root.Value == 0) {
            return 0;
        }
        int left = getHeight(root.leftson);
        int right = getHeight(root.rightson);
        if (left == -1 || right == -1) {
            return -1;
        }
        return Math.abs(left - right) > 1 ? -1 : (1 + Math.max(left, right));
    }

    private static final class Node {
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
