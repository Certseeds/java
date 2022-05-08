import java.io.*;
import java.util.*;

public class Main {
    static PrintWriter out;
    static InputReader in;

    public static void main(String args[]) throws IOException {
        out = new PrintWriter(System.out);
        in = new InputReader(System.in);
        // out.println("A");
        for (int t = in.nextInt(); t > 0; t--) { // As same as scanner.nextInt()
            int time1 = in.nextInt();
            int[][] poly1 = new int[time1 + 1][2];
            for (int i = 0; i < time1; i++) {
                poly1[i][1] = in.nextInt();
                poly1[i][0] = in.nextInt();
                // out.print(poly1[i][0]+ " ");
            }
            poly1[time1][1] = 65535;
            poly1[time1][0] = 65535;
            quicksort(poly1, 0, time1 - 1);
//          for (int i = 0; i < time1; i++) {
//              out.print(poly1[i][0] + " ");
//
//          }out.println();
//          for (int i = 0; i < time1; i++) {
//              out.print(poly1[i][1] + " ");
//
//          }out.println();
            // out.println("Are");
            int time2 = in.nextInt();
            int[][] poly2 = new int[time2 + 1][2];
            for (int i = 0; i < time2; i++) {
                poly2[i][1] = in.nextInt();
                poly2[i][0] = in.nextInt();
                // out.print(poly2[i][0]+ " ");
            }
            poly2[time2][1] = 65535;
            poly2[time2][0] = 65535;
            quicksort(poly2, 0, time2 - 1);
//          for (int i = 0; i < time2; i++) {
//              out.print(poly2[i][0] + " ");
//
//          }out.println();
//          for (int i = 0; i < time2; i++) {
//              out.print(poly2[i][1]);
//
//          }
            // out.println("Are you");
            // 先快排，后归并，然后遍历去重
            // 最后格式化打印
            int tempLength = time1 + time2;
            int tempLength2 = tempLength;
            // Lab3_B hello = new Lab3_B();
            int[][] array3 = new int[tempLength][2];
            array3 = merge(poly1, poly2);
//          for (int i = 0; i < tempLength; i++) {
//              out.print(array3[i][0] + " ");
//          }
//          for (int i = 0; i < tempLength; i++) {
//              out.print(array3[i][1] + " ");
//          }
            for (int i = 0; i < tempLength - 1; i++) {
                if (array3[i][0] == array3[i + 1][0]) {
                    array3[i + 1][1] += array3[i][1];
                }
            }
            // out.println("Are you ok");
            LinkedList<Integer> linked1 = new LinkedList<Integer>();
            LinkedList<Integer> linked2 = new LinkedList<Integer>();
//          for (int i = 0; i < tempLength; i++) {
//              out.print(array3[i][0] + " ");
//          }
//          for (int i = 0; i < tempLength; i++) {
//              out.print(array3[i][1] + " ");
//          }
            for (int i = 0; i < tempLength; i++) {
                linked1.addLast(array3[i][0]);
                linked2.addLast(array3[i][1]);
                if (i < tempLength - 1 && array3[i][0] == array3[i + 1][0]) {
                    linked1.removeLast();
                    linked2.removeLast();
                    tempLength2--;
                }

            }
            // out.println("Are you ok now");
            //out.println(linked1);
            // out.println(linked2);

            // finalbegin
            // [][0]是指数,linked2是系数

            // another
            // out.println(tempLength2);
            boolean name2 = false;
            boolean signFirst = false;
            for (int i = 0; i < tempLength2; i++) {
                if (linked2.getFirst() > 0 && i!= 0 && signFirst) {
                    out.print("+");
                }
                switch (linked2.getFirst()) {

                    case 1: {
                        name2 = true;
                        signFirst = true;
                        switch(linked1.getFirst()) {
                            case 0:{out.print("1");break;}
                            case 1:{out.print("x");break;}
                            default:{out.printf("%s%d","x^",linked1.getFirst());break;}
                        }break;
                    }
                    case -1: {
                        name2 = true;
                        signFirst = true;
                        switch(linked1.getFirst()) {
                            case 0:{out.print("-1");;break;}
                            case 1:{out.print("-x");break;}
                            default:{out.printf("%s%d","-x^",linked1.getFirst());break;}
                        }break;
                    }
                    case 0: {
                        break;
                    }
                    default: {
                        name2 = true;
                        signFirst = true;
                        switch(linked1.getFirst()) {
                            case 0:{out.print(linked2.getFirst());break;}
                            case 1:{out.print( linked2.getFirst() + "x");break;}
                            default:{out.printf("%d%s%d",  linked2.getFirst() , "x^", linked1.getFirst());break;}
                        }break;
                    }
                }
                linked1.removeFirst();
                linked2.removeFirst();
            }
            if (!name2) {
                out.print(0);
            }
            if (t != 1) {out.println();}

        }out.close();
        //willbePrint = "";}out.close(); // Don't forget this line, otherwise you will output nothing. This sentence
        // flush the buffer.

    }

    public static int getMiddle(int array[][], int begin, int finalNumber) {
        int temp = array[begin][0];
        int temp2 = array[begin][1];
        while (begin < finalNumber) {
            while (begin < finalNumber && array[finalNumber][0] >= temp) {
                finalNumber--;
            }
            // if (begin < finalNumber) {
            array[begin][0] = array[finalNumber][0];
            array[begin][1] = array[finalNumber][1];
            // }
            while (begin < finalNumber && array[begin][0] <= temp) {
                begin++;
            }
            // if(begin < finalNumber) {
            array[finalNumber][0] = array[begin][0];
            array[finalNumber][1] = array[begin][1];
            // }
        }
        array[begin][0] = temp;
        array[begin][1] = temp2;
        return begin;
    }

    public static void quicksort(int array[][], int begin, int finalNumber) {
        int middle;
        if (begin < finalNumber) {
            middle = getMiddle(array, begin, finalNumber);
            quicksort(array, begin, middle - 1);
            quicksort(array, middle + 1, finalNumber);
        }
    }

    public static int[][] merge(int array1[][], int array2[][]) {
        int numberFirst = array1.length - 1;
        int numberSecond = array2.length - 1;
        int first = 0;
        int second = 0;
        boolean judge = false;
        int[][] array3 = new int[numberFirst + numberSecond][2];
        a1: for (int i = 0; i < numberFirst + numberSecond; i++) {
            // out.print(array1[first]);
            // out.close();
            if (array1[first][0] >= array2[second][0]) {
                array3[i][0] = array2[second][0];
                array3[i][1] = array2[second][1];
                second++;
            } else {
                array3[i][0] = array1[first][0];
                array3[i][1] = array1[first][1];
                first++;
            }
            if (first == numberFirst && !judge) {
                int temp = numberSecond - second;
                for (int j = 0; j < temp; j++, second++) {
                    array3[i + j + 1][0] = array2[second][0];
                    array3[i + j + 1][1] = array2[second][1];
                }
                judge = true;
                break a1;
            } else if (second == numberSecond && !judge) {
                int temp = numberFirst - first;
                for (int j = 0; j < temp; j++, first++) {
                    array3[i + j + 1][0] = array1[first][0];
                    array3[i + j + 1][1] = array1[first][1];
                }
                judge = true;
                break a1;
            }
        }
        return array3;
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