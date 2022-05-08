import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        final Scanner cin = new Scanner(System.in);
        final int times = cin.nextInt();
        cin.nextLine();
        for (int i = 0; i < times; i++) {
            final int a = cin.nextInt();
            final int b = cin.nextInt();
            final int c = cin.nextInt();
            final int n = cin.nextInt();
            final int m = cin.nextInt();
            cin.nextLine();
            test(n, m, a, b, c);
        }
    }

    public static void test(int length1, int length2, int a, int b, int c) {
        final int a1 = 2 * a + 2 * b;
        final int a2 = 2 * b + 2 * c;
        final int a3 = 2 * c + 2 * a;
        final int b1 = a + 2 * b;
        final int b2 = b + 2 * c;
        final int b3 = c + 2 * a;
        final int b4 = a + 2 * c;
        final int b5 = b + 2 * a;
        final int b6 = c + 2 * b;
        final int c1 = a + b + c;
        final int d1 = c1 + a;
        final int d2 = c1 + b;
        final int d3 = c1 + c;
        final int e1 = a1 / 2;
        final int e2 = a2 / 2;
        final int e3 = a3 / 2;
        final int f1 = e1 + 3 * c;
        final int f2 = e2 + 3 * a;
        final int f3 = e3 + 3 * b;
        boolean canitBe = false;
        if (test2(length1, length2, a1, b3)) {
            canitBe = true;
        }
        if (test2(length1, length2, a1, b6)) {
            canitBe = true;
        }
        if (test2(length1, length2, a2, b1)) {
            canitBe = true;
        }
        if (test2(length1, length2, a2, b4)) {
            canitBe = true;
        }
        if (test2(length1, length2, a3, b2)) {
            canitBe = true;
        }
        if (test2(length1, length2, a3, b5)) {
            canitBe = true;
        }
        if (test2(length1, length2, a1, c1)) {
            canitBe = true;
        }
        if (test2(length1, length2, a2, c1)) {
            canitBe = true;
        }
        if (test2(length1, length2, a3, c1)) {
            canitBe = true;
        }//9
        if (test2(length1, length2, d1, c1)) {
            canitBe = true;
        }
        if (test2(length1, length2, d1, b2)) {
            canitBe = true;
        }
        if (test2(length1, length2, d1, b6)) {
            canitBe = true;
        }//12
        if (test2(length1, length2, d2, c1)) {
            canitBe = true;
        }
        if (test2(length1, length2, d2, b3)) {
            canitBe = true;
        }
        if (test2(length1, length2, d2, b4)) {
            canitBe = true;
        }//15
        if (test2(length1, length2, d3, c1)) {
            canitBe = true;
        }
        if (test2(length1, length2, d3, b1)) {
            canitBe = true;
        }
        if (test2(length1, length2, d3, b5)) {
            canitBe = true;
        }//18
        if (test2(length1, length2, e1, f1)) {
            canitBe = true;
        }
        if (test2(length1, length2, e2, f2)) {
            canitBe = true;
        }
        if (test2(length1, length2, e3, f3)) {
            canitBe = true;
        }//21
        printIn(canitBe);
    }

    public static Boolean test2(int length1, int length2, int real1, int real2) {
        if (length1 >= real1 && length2 >= real2) {
            return true;
        } else if (length2 >= real1 && length1 >= real2) {
            return true;
        }
        return false;
    }

    public static void printIn(boolean judge) {
        String temp;
        if (judge) {
            temp = "Yes";
        } else {
            temp = "No";
        }
        System.out.println(temp);

    }
}
