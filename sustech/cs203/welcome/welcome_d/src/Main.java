import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Scanner cin = new Scanner(System.in);
        int times = cin.nextInt();
        for (int i = 0; i < times; i++) {
            int length = cin.nextInt();
            // int Weight = cin.nextInt();
            int height = cin.nextInt();
            int Weight = cin.nextInt();

            printThreeD(length, height, Weight, times, i);
        }
    }

    public static void printThreeD(int a, int c, int b, int d, int e) {
        String Lside = sides(a, '+', '-');
        String Hside = sides(a, '/', '.');
        String Wside = sides(a, '|', '.');
        String a1 = "";
        for (int i = 0; i < 2 * c; i++) {
            a1 = "";
            for (int j = i; j < 2 * c; j++) {
                a1 += '.';
            }
            if ((i + 2) % 2 == 0) {
                a1 += Lside;
                for (int k = 0; k < b && k < i / 2; k++) {
                    a1 += '.';
                    a1 += '+';
                }


                for (int j = 0; j < i / 2 - b; j++) {
                    a1 += '.';
                    a1 += '.';

                }
            } else {
                a1 += Hside;
                if (i != 0) {
                    a1 += '|';

                }
                //    for (int j = 0; j < i/2; j++) {
                //if (j == 0) {

                for (int k = 0; (k < i / 2) && (k + 1 < b); k++) {
                    a1 += '/';
                    a1 += '|';
                    //}
                }
                if (i > 2 * b) {
                    a1 += "/.";
                }
                //else {
                for (int k = 0; k < i / 2 - b; k++) {
                    a1 += '.';
                    a1 += '.';
                }
                //  }
                // }

            }
            System.out.println(a1);
            //the upperone
        }


        //   System.out.println("2");
//        for (int i = 0; i < 2 * b + 1 - 2 * c;i++) {
//            a1 = "";
//            if ((i + 2 ) % 2 == 0) {
//                a1 += Lside;
//                for (int j = 0; j < c; j++) {
//                    a1 += '.';
//                    a1 += '+';
//                }
//            }
//            else {
//                a1 += Wside;
//
//                for (int j = 0; j < c; j++) {
//                    a1 += '/';
//                    a1 += '|';
//
//                }
//            }
//            System.out.println(a1);
//        }
        //  System.out.println("2");
        //the middle one
        a1 = "";
        for (int i = 0; i < 2 * b + 1; i++) {
            a1 = "";
            if ((i + 2) % 2 == 0) {
                a1 += Lside;
                a1:
                for (int j = b; j > i / 2; j--) {
                    a1 += '.';
                    a1 += '+';
                    if (a1.length() > 2 * a + 2 * c) {
                        break a1;
                    }
                }
            } else {
                a1 += Wside;
                a1 += '/';
                a2:
                for (int j = b - 1; j > i / 2; j--) {
                    a1 += '|';
                    a1 += '/';
                    if (a1.length() > 2 * a + 2 * c + 1) {
                        break a2;
                    }

                }
            }
            a3:
            for (int j = 2 * c - 2 * b + i; j > 0; j--) {
                a1 += '.';
                if (a1.length() > 2 * a + 2 * c + 1) {
                    break a3;
                }
            }

            System.out.println(a1.substring(0, 2 * a + 2 * c + 1));
            //q System.out.println(a1.length());
        }


    }

    public static String sides(int a, char b, char c) {
        String side = "";
        side += b;
        for (int i = 0; i < a; i++) {
            side += c;
            side += b;
        }
        return side;

    }


}