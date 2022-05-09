import quick_read.input_reader;

import java.io.PrintWriter;

public class Main {
    static PrintWriter out;
    static input_reader in;

    public static void main(String[] args) {
        out = new PrintWriter(System.out);
        in = new input_reader(System.in);
        for (int t = in.nextInt(); t > 0; t--) {
            int numbers = in.nextInt();
            int kth = in.nextInt();
            int HeapNumbers = 0;
            int[] beginArray = new int[numbers + 1];
            //判断是大还是小;
            if ((kth < 5000)) {
                //第k大,用最大堆
                for (int i = 1; i < numbers + 1; i++) {
                    HeapNumbers++;
                    beginArray[i] = in.nextInt();
                    judgeaddMax(HeapNumbers, beginArray);
                }
                for (int i = 0; i < kth - 1; i++) {
                    swap(beginArray, 1, HeapNumbers);
                    HeapNumbers--;
                    if (HeapNumbers != 1) {
                        minusCircleMax(1, HeapNumbers, beginArray);
                    }
                }
                out.print(beginArray[1]);
            } else {
                //第N -K +1小,用最小堆
                kth = numbers - kth + 1;
                for (int i = 1; i < numbers + 1; i++) {
                    HeapNumbers++;
                    beginArray[i] = in.nextInt();
                    judgeadd(HeapNumbers, beginArray);
                }
                for (int i = 0; i < kth - 1; i++) {
                    swap(beginArray, 1, HeapNumbers);
                    HeapNumbers--;
                    if (HeapNumbers != 1) {
                        minusCircle(1, HeapNumbers, beginArray);
                    }
                }
                out.print(beginArray[1]);
            }
            if (t != 1) {
                out.println();
            }
        }
        out.close();
    }

    public static void judgeadd(int number, int[] beginArray) {
        if (number == 1)
            return;
        int fatherNumber = number / 2;
        while (!isaddOk(fatherNumber, number, beginArray)) {
            fatherNumber = fatherNumber / 2;
        }
    }

    public static void judgeaddMax(int number, int[] beginArray) {
        if (number == 1)
            return;
        int fatherNumber = number / 2;
        while (!isaddOkMax(fatherNumber, number, beginArray)) {
            fatherNumber = fatherNumber / 2;
        }
    }

    public static boolean isaddOkMax(int fatherNumber, int countNumber, int[] beginArray) {

        if (fatherNumber == 0)
            return true;
        int leftNumber = fatherNumber * 2;
        int rightNumber = leftNumber + 1;
        if (leftNumber > countNumber) {
            return true;
        } else if (leftNumber == countNumber && beginArray[leftNumber] > beginArray[fatherNumber]) {
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

}
