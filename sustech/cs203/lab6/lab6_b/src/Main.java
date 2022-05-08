import java.io.*;
import java.util.*;

public class Main {
    static PrintWriter out;
    static InputReader in;

    public static void main(String args[]) throws IOException {
        out = new PrintWriter(System.out);
        in = new InputReader(System.in);
        for (int t = in.nextInt(); t > 0; t--) {
            shenmejiba tree = new shenmejiba();
            int inputNumber = in.nextInt();
            int[][] input = new int[inputNumber-1][2];
            for (int i = 0; i < inputNumber-1; i++) {
                input[i][0] = in.nextInt();
                input[i][1] = in.nextInt();

            }
            for (int i = 0; i < inputNumber-1; i++) {
                tree.shabidsaa(input[i][0],input[i][1]);
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
}
class shenmejiba {

    class Node {
        public long value;
        public Node leftChild;
        public Node rightChild;

        public Node(long value) {
            this.value = value;
            leftChild = null;
            rightChild = null;
        }
    }

    public shenmejiba() {
        Node root = new Node(0);
    }

    public Node root;

    public void shabidsaa(long parent_Value, long son_value) {

        Node newNode = new Node(son_value);
        if (root == null) {
            root = new Node(parent_Value);
            root.leftChild = newNode;
        } else {
            digui(root, parent_Value, son_value);
        }
    }

    public int digui(Node root, long parent_Value, long son_value) {
        Node newNode = new Node(son_value);
        if (root.value == parent_Value) {
            if (root.leftChild == null && root.rightChild == null) {
                root.leftChild = newNode;
                return 0;
            } else if (root.leftChild != null) {
                root.rightChild = newNode;
                return 0;
            }
        } else {
            if (root.leftChild != null) {
                digui(root.leftChild, parent_Value, son_value);
            }
            if (root.rightChild != null) {
                digui(root.rightChild, parent_Value, son_value);
            }

        }
        return 0;
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
    }public void postPrint(Node root) {
        if (root.leftChild != null) {
            postPrint(root.leftChild);
        }
        //  System.out.print(root.value + " ");
        if (root.rightChild != null) {
            postPrint(root.rightChild);
        }  System.out.print(root.value + " ");
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