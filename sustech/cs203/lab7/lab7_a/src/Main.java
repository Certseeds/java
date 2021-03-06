import quick_read.input_reader;

import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    static PrintWriter out;
    static input_reader in;

    public static void main(String[] args) {
        out = new PrintWriter(System.out);
        in = new input_reader(System.in);
        for (int t = in.nextInt(); t > 0; t--) { // As same as scanner.nextInt()
            int numbers = in.nextInt();
            Node[] nodelist = new Node[numbers + 1];
            boolean[] isFather = new boolean[numbers + 1];
            for (int i = 0; i < numbers + 1; i++) {
                nodelist[i] = new Node(i);
                isFather[i] = true;
            }

            for (int i = 1; i < numbers + 1; i++) {
                int leftValue = in.nextInt();
                int rightValue = in.nextInt();
                nodelist[i].leftson.Value = leftValue;
                nodelist[i].rightson.Value = rightValue;
                isFather[leftValue] = false;
                isFather[rightValue] = false;
            }
            boolean hasFather = false;
            int fatherNumber = 0;
            for (int i = 1; i < numbers + 1; i++) {
                hasFather = hasFather || isFather[i];
                if (isFather[i]) {
                    fatherNumber = i;
                }
            }
            boolean printOut = true;
            boolean beginLeave = false;
            if (hasFather && !isNull(nodelist[fatherNumber])) {
                Queue<Node> NodeQueue = new LinkedList<>();
                NodeQueue.add(nodelist[fatherNumber]);
                while (!NodeQueue.isEmpty()) {
                    Node useNode = NodeQueue.poll();
                    if (beginLeave) {
                        if (!isNull(useNode)) {
                            printOut = false;
                            break;
                        }
                    }
                    if (useNode.leftson.Value != 0) {
                        NodeQueue.add(nodelist[useNode.leftson.Value]);
                        if (useNode.rightson.Value != 0) {
                            NodeQueue.add(nodelist[useNode.rightson.Value]);
                        } else {
                            beginLeave = true;
                        }

                    } else if (useNode.rightson.Value != 0) {
                        printOut = false;
                        break;
                    } else {
                        beginLeave = true;
                    }
                }

            } else {
                printOut = false;
            }
            if (printOut) {
                out.print("Yes");
            } else {
                out.print("No");
            }
            if (t != 1) {
                out.println();
            }
        }
        out.close(); // Don't forget this line, otherwise you will output nothing. This sentence
        // flush the buffer.
    }

    static boolean isNull(Node node) {
        return node.leftson.Value == 0 && node.rightson.Value == 0;
    }

    private static final class Node {
        int Value;
        Node leftson, rightson;

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
