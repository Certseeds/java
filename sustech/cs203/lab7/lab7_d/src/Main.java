import java.io.*;
import java.util.*;

public class Main {
    static PrintWriter out;
    static InputReader in;

    public static void main(String args[]) throws IOException {
        out = new PrintWriter(System.out);
        in = new InputReader(System.in);
        for (int t = in.nextInt(); t >0; t--) {
            int numbers = in.nextInt();
            int kth = in.nextInt();
            int HeapNumbers =  0;
            int[] beginArray = new int[numbers + 1];
            //判断是大还是小;
            if( (kth<5000)) {
                //第k大,用最大堆
                for (int i = 1; i < numbers +1; i++) {
                    HeapNumbers++;
                    beginArray[i] = in.nextInt();
                    judgeaddMax(HeapNumbers,beginArray);
                    // System.out.println(Arrays.toString(beginArray));
                }
                for (int i = 0; i < kth-1; i++) {
                    //out.println(Arrays.toString(beginArray) + "*"  + HeapNumbers);
                    swap(beginArray, 1, HeapNumbers);
                    //beginArray[HeapNumbers] = 0;
                    HeapNumbers--;
                    if ( HeapNumbers != 1) {
                        minusCircleMax(1, HeapNumbers, beginArray);
                    }
                }
                out.print(beginArray[1]);
//                Arrays.sort(beginArray);
//                out.println(beginArray[48]);
            }
            else {
                //第N -K +1小,用最小堆
                kth = numbers - kth +1;
                //out.println(kth);
                for (int i = 1; i < numbers +1; i++) {
                    HeapNumbers ++;
                    beginArray[i] = in.nextInt();
                    judgeadd(HeapNumbers,beginArray);
                }
                for (int i = 0; i < kth-1; i++) {
                    //out.println(Arrays.toString(beginArray));
                    swap(beginArray, 1, HeapNumbers);
                    HeapNumbers--;
                    if ( HeapNumbers!= 1) {
                        minusCircle(1, HeapNumbers, beginArray);
                    }
                }
                out.print(beginArray[1]);
//                Arrays.sort(beginArray);
//                out.println(Arrays.toString(beginArray));
//                out.println(beginArray[48]);
            }
            if (t != 1) {
                out.println();
            }
        }
        out.close();
    }
    public static void judgeadd(int number,int[] beginArray) {
        if (number == 1)
            return;
        int fatherNumber = number / 2;
        while (!isaddOk(fatherNumber, number, beginArray)) {
            fatherNumber = fatherNumber / 2;
        }
        return;
    } public static void judgeaddMax(int number,int[] beginArray) {
        if (number == 1)
            return;
        int fatherNumber = number / 2;
        while (!isaddOkMax(fatherNumber, number, beginArray)) {
            fatherNumber = fatherNumber / 2;
        }
        return;
    }
    public static boolean isaddOkMax(int fatherNumber, int countNumber, int[] beginArray) {

        if (fatherNumber == 0)
            return true;
        int leftNumber = fatherNumber * 2;
        int rightNumber = leftNumber + 1;
        //System.out.println(fatherNumber + " "+ leftNumber + " "+rightNumber);
        if (leftNumber > countNumber) {
            return true;
        } else if (leftNumber == countNumber && beginArray[leftNumber] > beginArray[fatherNumber]) {
            //System.out.println(Arrays.toString(beginArray) + "!");
            swap(beginArray, leftNumber, fatherNumber);
            return false;
        } else if (rightNumber <= countNumber) {
            int temp = beginArray[leftNumber] > beginArray[rightNumber] ? leftNumber : rightNumber;
            if (beginArray[fatherNumber] > beginArray[rightNumber]
                    && beginArray[fatherNumber] > beginArray[leftNumber]) {
                return true;
            } else {
                swap(beginArray, temp, fatherNumber);
                return false;
            }
        }
        return false;
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
    public static void minusCircleMax(int fatherNumber, int count, int[] beginArray) {
        //System.out.println(fatherNumber + "!");
        // System.out.println(Arrays.toString(beginArray));
        if (judgementMax(fatherNumber, count, beginArray)) {
            return;
        }
        minusCircleMax(fatherNumber * 2, count, beginArray);
        minusCircleMax(fatherNumber * 2 + 1, count, beginArray);
    }
    public static void minusCircle(int fatherNumber, int count, int[] beginArray) {
        if (judgement(fatherNumber, count, beginArray)) {
            return;
        }
        minusCircle(fatherNumber * 2, count, beginArray);
        minusCircle(fatherNumber * 2 + 1, count, beginArray);
    }
    public static boolean judgementMax(int fatherNumber, int count, int[] beginArray) {
        if (count <= 1) {
            return true;
        }
        int leftNumber = fatherNumber * 2;
        int rightNumber = leftNumber + 1;
        if (leftNumber > count) {
            return true;
        } else if (leftNumber == count && beginArray[leftNumber] > beginArray[fatherNumber]) {
            swap(beginArray, leftNumber, fatherNumber);
            return false;
        } else if (rightNumber <= count) {
            //System.out.println(fatherNumber + " "+ leftNumber + " " + rightNumber + " " + count);
            int temp = beginArray[leftNumber] > beginArray[rightNumber] ? leftNumber : rightNumber;
            if (beginArray[fatherNumber] > beginArray[rightNumber]
                    && beginArray[fatherNumber] > beginArray[leftNumber]) {
                return true;
            } else {
                swap(beginArray, temp, fatherNumber);
                return false;
            }
        }
        return false;
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