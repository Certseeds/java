import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static PrintWriter out;
    static InputReader in;

    public static void main(String[] args) {
        out = new PrintWriter(System.out);
        in = new InputReader(System.in);
        for (int t = in.nextInt(); t > 0; t--) {
            wtf tree = new wtf();
            int inputNumber = in.nextInt();
            int[][] input = new int[inputNumber - 1][2];
            for (int i = 0; i < inputNumber - 1; i++) {
                input[i][0] = in.nextInt();
                input[i][1] = in.nextInt();

            }
            for (int i = 0; i < inputNumber - 1; i++) {
                tree.hs(input[i][0], input[i][1]);
            }
            tree.preprint(tree.root);
            System.out.println();
            tree.Inprint(tree.root);
            System.out.println();
            tree.postPrint(tree.root);
            System.out.println();
            if (t != 1) {
                out.println();
            }
        }
        out.close(); // Don't forget this line, otherwise you will output nothing. This sentence
        // flush the buffer.
    }

    private static final class wtf {

        public Node root;

        public wtf() {
            Node root = new Node(0);
        }

        public void hs(long parent_Value, long son_value) {

            Node newNode = new Node(son_value);
            if (root == null) {
                root = new Node(parent_Value);
                root.leftChild = newNode;
            } else {
                rec(root, parent_Value, son_value);
            }
        }

        public void rec(Node root, long parent_Value, long son_value) {
            Node newNode = new Node(son_value);
            if (root.value == parent_Value) {
                if (root.leftChild == null && root.rightChild == null) {
                    root.leftChild = newNode;
                } else if (root.leftChild != null) {
                    root.rightChild = newNode;
                }
            } else {
                if (root.leftChild != null) {
                    rec(root.leftChild, parent_Value, son_value);
                }
                if (root.rightChild != null) {
                    rec(root.rightChild, parent_Value, son_value);
                }

            }
        }

        public void preprint(Node root) {
            System.out.print(root.value + " ");
            if (root.leftChild != null) {
                preprint(root.leftChild);
            }
            if (root.rightChild != null) {
                preprint(root.rightChild);
            }
        }

        public void Inprint(Node root) {
            if (root.leftChild != null) {
                Inprint(root.leftChild);
            }
            System.out.print(root.value + " ");
            if (root.rightChild != null) {
                Inprint(root.rightChild);
            }
        }

        public void postPrint(Node root) {
            if (root.leftChild != null) {
                postPrint(root.leftChild);
            }
            //  System.out.print(root.value + " ");
            if (root.rightChild != null) {
                postPrint(root.rightChild);
            }
            System.out.print(root.value + " ");
        }

        private static final class Node {
            public long value;
            public Node leftChild;
            public Node rightChild;

            public Node(long value) {
                this.value = value;
                leftChild = null;
                rightChild = null;
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
