import quick_read.input_reader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Stack;

public class Main {
    static PrintWriter out;
    static input_reader in;

    public static void main(String[] args) {
        out = new PrintWriter(System.out);
        in = new input_reader(System.in);
        int count = in.nextInt();
        for (int t = 1; t < count + 1; t++) {
            int numbers = in.nextInt();
            Node[] Tree = new Node[numbers + 1];
            boolean[] isFather = new boolean[numbers + 1];
            ArrayList<Integer> order = new ArrayList<>();
            boolean isBST = true;
            boolean haveRoot = false;
            int RootNumber = 0;
            Tree[0] = new Node(0);
            for (int i = 1; i < numbers + 1; i++) {
                Tree[i] = new Node(in.nextInt());
                isFather[i] = true;
            }
            int[] fathers = new int[numbers + 1];
            int[] sons = new int[numbers + 1];

            for (int i = 1; i < numbers; i++) {
                fathers[i] = in.nextInt();
                sons[i] = in.nextInt();
            }
            for (int i = 1; i < numbers; i++) {
                int beginNumber = fathers[i];
                int finalNumber = sons[i];
                isFather[finalNumber] = false;
                // null nodes case
                if (Tree[beginNumber].leftson.Value == 0 && Tree[beginNumber].rightson.Value == 0) {
                    if (Tree[beginNumber].Value > Tree[finalNumber].Value) {
                        Tree[beginNumber].leftson = Tree[finalNumber];
                    } else if (Tree[beginNumber].Value < Tree[finalNumber].Value) {
                        Tree[beginNumber].rightson = Tree[finalNumber];
                    } else {
                        isBST = false;
                        break;
                    }
                }
                // havew double sons cases
                else if (Tree[beginNumber].leftson.Value != 0 && Tree[beginNumber].rightson.Value != 0) {
                    isBST = false;
                    break;
                }
                // justLeftson
                else if (Tree[beginNumber].leftson.Value != 0 && Tree[beginNumber].rightson.Value == 0) {
                    if (Tree[beginNumber].Value < Tree[finalNumber].Value) {
                        Tree[beginNumber].rightson = Tree[finalNumber];
                    } else {
                        isBST = false;
                        break;
                    }
                }
                // justRightson
                else if (Tree[beginNumber].leftson.Value == 0 && Tree[beginNumber].rightson.Value != 0) {
                    if (Tree[beginNumber].Value > Tree[finalNumber].Value) {
                        Tree[beginNumber].leftson = Tree[finalNumber];
                    } else {
                        isBST = false;
                        break;
                    }
                }
            }
            if (isBST) {
                for (int i = 1; i < numbers + 1; i++) {
                    if (isFather[i]) {
                        RootNumber = i;
                        haveRoot = true;
                        break;
                    }
                }
                // 当且仅当是是树而且有Root节点的,中序遍历和检索
                if (haveRoot) {
                    Stack<Node> stack = new Stack<>();
                    Node root = Tree[RootNumber];
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
                }
            }
            // System.out.println(order.toString());
            if (isBST) {
                out.printf("Case #%d: YES", t);
            } else {
                out.printf("Case #%d: NO", t);
            }
            if (t != count) {
                out.println();
            }
        }
        // out.print("finish?");
        out.close();
    }

    private static final class Node {
        int Value;
        Node leftson;
        Node rightson;

        public Node() {
            this.Value = 0;
        }

        public Node(int value) {
            this.Value = value;
            leftson = new Node();
            rightson = new Node();
        }

    }


}
