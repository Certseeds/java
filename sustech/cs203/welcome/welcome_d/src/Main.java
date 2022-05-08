import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        final Scanner cin = new Scanner(System.in);
        final int times = cin.nextInt();
        for (int i = 0; i < times; i++) {
            final int length = cin.nextInt();
            final int height = cin.nextInt();
            final int Weight = cin.nextInt();
            printThreeD(length, height, Weight, times, i);
        }
    }

    public static void printThreeD(int a, int c, int b, int d, int e) {
        final String Lside = sides(a, '+', '-');
        final String Hside = sides(a, '/', '.');
        final String Wside = sides(a, '|', '.');
        StringBuilder a1 = new StringBuilder();
        for (int i = 0; i < 2 * c; i++) {
            a1 = new StringBuilder();
            a1.append(".".repeat(Math.max(0, 2 * c - i)));
            if ((i + 2) % 2 == 0) {
                a1.append(Lside);
                for (int k = 0; k < b && k < i / 2; k++) {
                    a1.append('.');
                    a1.append('+');
                }


                for (int j = 0; j < i / 2 - b; j++) {
                    a1.append('.').append('.');

                }
            } else {
                a1.append(Hside).append('|');
                for (int k = 0; (k < i / 2) && (k + 1 < b); k++) {
                    a1.append('/').append('|');
                }
                if (i > 2 * b) {
                    a1.append("/.");
                }
                //else {
                for (int k = 0; k < i / 2 - b; k++) {
                    a1.append('.').append('.');
                }

            }
            System.out.println(a1);
        }
        for (int i = 0; i < 2 * b + 1; i++) {
            a1 = new StringBuilder();
            if ((i + 2) % 2 == 0) {
                a1.append(Lside);
                for (int j = b; j > i / 2; j--) {
                    a1.append('.');
                    a1.append('+');
                    if (a1.length() > 2 * a + 2 * c) {
                        break;
                    }
                }
            } else {
                a1.append(Wside);
                a1.append('/');
                for (int j = b - 1; j > i / 2; j--) {
                    a1.append('|');
                    a1.append('/');
                    if (a1.length() > 2 * a + 2 * c + 1) {
                        break;
                    }

                }
            }
            for (int j = 2 * c - 2 * b + i; j > 0; j--) {
                a1.append('.');
                if (a1.length() > 2 * a + 2 * c + 1) {
                    break;
                }
            }

            System.out.println(a1.substring(0, 2 * a + 2 * c + 1));
        }


    }

    public static String sides(int a, char b, char c) {
        final StringBuilder side = new StringBuilder();
        side.append(b);
        for (int i = 0; i < a; i++) {
            side.append(c).append(b);
        }
        return side.toString();

    }


}