import java.io.*;
import java.util.*;

public class Main{
    static PrintWriter out;
    static InputReader in;

    public static void main(String args[]) throws IOException {
        out = new PrintWriter(System.out);
        in = new InputReader(System.in);
        for (int t = in.nextInt(); t > 0; t--) { // As same as scanner.nextInt()
            int number = in.nextInt();
            int[] beginArray = new int[200010];
            int count = 0;
            beginArray[0] = (int) (Math.pow(10, 9) + 9);
            for (int i = 1; i < number + 1; i++) {
                beginArray[i] = in.nextInt();
                count = count + 1;
                judgeadd(i, count, beginArray);
            }
            int operationNumber = in.nextInt();
            for (int i = 0; i < operationNumber; i++) {
                int operation = in.nextInt();
                switch (operation) {
                    case 1: {
                        int addNumber = in.nextInt();
                        count++;
                        beginArray[count] = addNumber;
                        judgeadd(count, count, beginArray);
                        break;
                    }
                    case 2: {
                        swap(beginArray, 1, count);
                        count--;
                        if (count != 1) {
                            int fatherNumber = 1;
                            minusCircle(fatherNumber, count, beginArray);
                        }
                        break;
                    }
                    case 3: {
                        out.println(beginArray[1]);
                        break;
                    }
                }
            }

            // if (t != 1) {
            //    out.println();
            //}
        }
        out.close();

    }

    public static void judgeadd(int number, int countNumber, int[] beginArray) {
        if (number == 1)
            return;
        int fatherNumber = number / 2;
        while (!isaddOk(fatherNumber, countNumber, beginArray)) {
            fatherNumber = fatherNumber / 2;
        }
        return;
    }

    public static boolean isaddOk(int fatherNumber, int countNumber, int[] beginArray) {
        if (fatherNumber == 0)
            return true;
        int leftNumber = fatherNumber * 2;
        int rightNumber = leftNumber + 1;
        if (leftNumber > countNumber) {
            return true;
        } else if (leftNumber == countNumber && beginArray[leftNumber] < beginArray[fatherNumber]) {
            swap(beginArray, leftNumber, fatherNumber);
            return false;
        } else if (rightNumber <= countNumber) {
            // 堆只能直上直下,不能乱蹿
            int temp = beginArray[leftNumber] < beginArray[rightNumber] ? leftNumber : rightNumber;
            if (beginArray[fatherNumber] < beginArray[rightNumber]
                    && beginArray[fatherNumber] < beginArray[leftNumber]) {
                return true;
            } else {
                swap(beginArray, temp, fatherNumber);
                return false;
            }
        }
        return false;
    }

    public static void minusCircle(int fatherNumber, int count, int[] beginArray) {
        if (judgement(fatherNumber, count, beginArray)) {
            return;
        }
        minusCircle(fatherNumber * 2, count, beginArray);
        minusCircle(fatherNumber * 2 + 1, count, beginArray);
    }

    public static boolean judgement(int fatherNumber, int count, int[] beginArray) {
        if (count <= 1) {
            return true;
        }
        int leftNumber = fatherNumber * 2;
        int rightNumber = leftNumber + 1;
        if (leftNumber > count) {
            return true;
        } else if (leftNumber == count && beginArray[leftNumber] < beginArray[fatherNumber]) {
            swap(beginArray, leftNumber, fatherNumber);
            return false;
        } else if (rightNumber <= count) {
            int temp = beginArray[leftNumber] < beginArray[rightNumber] ? leftNumber : rightNumber;
            if (beginArray[fatherNumber] < beginArray[rightNumber]
                    && beginArray[fatherNumber] < beginArray[leftNumber]) {
                return true;
            } else {
                swap(beginArray, temp, fatherNumber);
                return false;
            }
        }
        return false;
    }

    public static void swap(int[] beginArray, int number1, int number2) {
        int temp = beginArray[number1];
        beginArray[number1] = beginArray[number2];
        beginArray[number2] = temp;
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

class BinaryTree {

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

    public Node root;

    public void insert(long addtion) {

    }

    public Node bulidTree(Node root, int[] numbers, int index, int length) {
        if (index >= length) {
            return null;
        }
        root = new Node(numbers[index]);
        root.leftChild = bulidTree(root.leftChild, numbers, 2 * index + 1, length);
        root.rightChild = bulidTree(root.rightChild, numbers, 2 * index + 2, length);
        return root;
    }

    void swap(int[] numbers, int x, int y) {
        int t;
        t = numbers[x];
        numbers[x] = numbers[y];
        numbers[y] = t;
    }

    public void beggingCaozuo(int[] numbers, int length) {
        int i = length / 2;
        for (; i >= 1; i--) {
            begging(numbers, i, length);
        }
    }

    public void begging(int[] numbers, int i, int length) {
        int tab = 0;
        int sign = 0;
        while (i * 2 <= length && sign == 0) {
            tab = (numbers[i] > numbers[i * 2]) ? 2 * i : i;
            if (i * 2 + 1 <= length && numbers[tab] > numbers[i * 2 + 1]) {
                tab = i * 2 + 1;
            }
            if (tab != i) {
                swap(numbers, tab, i);
                i = tab;
            } else {
                sign = 1;
            }
        }
    }

    public void preprint(Node root) {
        if (root == null) {
            return;
        }
        System.out.print(root.value + " ");
        if (root.leftChild != null) {
            preprint(root.leftChild);
        }
        if (root.rightChild != null) {
            preprint(root.rightChild);
        }
    }
}